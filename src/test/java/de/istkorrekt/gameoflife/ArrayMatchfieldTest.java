package de.istkorrekt.gameoflife;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatchfieldTest {

    @Test
    void testMatchfieldWithSize3Has9Locations() {
        Matchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(3));
        long amountOfLocations = matchfield.streamAllLocations().count();
        Assertions.assertEquals(9, amountOfLocations);
    }

    @Test
    void testAllCellsAreDeadIfNoAliveLocationsAreProvided() {
        Matchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(3));
        long amountOfAliveCells = matchfield.streamAllLocations().map(matchfield::getCellAt).filter(Cell::isAlive).count();
        Assertions.assertEquals(0, amountOfAliveCells);
    }

    @Test
    void testOneCellIsAliveIfAliveLocationIsProvided() {
        Matchfield matchfield = new ArrayMatchfield(Collections.singleton(Location.of(1, 1)), Size.of(3));
        long amountOfAliveCells = matchfield.streamAllLocations().map(matchfield::getCellAt).filter(Cell::isAlive).count();
        Assertions.assertEquals(1, amountOfAliveCells);
    }

    @Test
    void testLocationHas8Neighbors() {
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.singleton(Location.of(1, 1)), Size.of(3));
        long amountOfNeighbors = matchfield.streamNeighborLocations(Location.of(0, 0)).count();
        Assertions.assertEquals(8, amountOfNeighbors);
    }

    @Test
    void testNeighborLocationIncludesAllSurroundingLocations() {
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                Location.of(0, 0),
                Location.of(1, 0),
                Location.of(2, 0),
                Location.of(0, 1),
                Location.of(2, 1),
                Location.of(0, 2),
                Location.of(1, 2),
                Location.of(2, 2)
        );
        assertLocationIsNeighborTo(matchfield, Location.of(1, 1), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtTopBorder() {
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                Location.of(0, 3),
                Location.of(1, 3),
                Location.of(2, 3),
                Location.of(0, 0),
                Location.of(2, 0),
                Location.of(0, 1),
                Location.of(1, 1),
                Location.of(2, 1)
        );
        assertLocationIsNeighborTo(matchfield, Location.of(1, 0), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtLeftBorder() {
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                Location.of(3, 0),
                Location.of(0, 0),
                Location.of(1, 0),
                Location.of(3, 1),
                Location.of(1, 1),
                Location.of(3, 2),
                Location.of(0, 2),
                Location.of(1, 2)
        );
        assertLocationIsNeighborTo(matchfield, Location.of(0, 1), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtCorner() {
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptySet(), Size.of(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                Location.of(3, 3),
                Location.of(0, 3),
                Location.of(1, 3),
                Location.of(3, 0),
                Location.of(1, 0),
                Location.of(3, 1),
                Location.of(0, 1),
                Location.of(1, 1)
        );
        assertLocationIsNeighborTo(matchfield, Location.of(0, 0), expectedNeighbors);
    }

    @Test
    void testSwitchCellKillsAliveCell() {
        Location location = Location.of(0, 0);
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.singleton(location), Size.of(1));

        matchfield.switchCellAt(location);

        assertCellIsDeadAt(matchfield, location);
    }

    @Test
    void testSwitchCellResurrectsDeadCell() {
        Location location = Location.of(0, 0);
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptyList(), Size.of(1));

        matchfield.switchCellAt(location);

        assertCellIsAliveAt(matchfield, location);
    }

    @Test
    void testWidthIsAsProvided() {
        int expectedWidth = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptyList(), Size.of(expectedWidth, 1));

        assertEquals(expectedWidth, matchfield.width());
    }

    @Test
    void testHeightIsAsProvided() {
        int expectedHeight = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptyList(), Size.of(1, expectedHeight));

        assertEquals(expectedHeight, matchfield.height());
    }

    @Test
    void testWidthAndHeightAreAsProvidedSize() {
        int expected = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(Collections.emptyList(), Size.of(expected));

        assertEquals(expected, matchfield.width());
        assertEquals(expected, matchfield.height());
    }

    private static void assertCellIsDeadAt(ArrayMatchfield matchfield, Location location) {
        Cell cell = matchfield.getCellAt(location);
        assertFalse(cell.isAlive());
    }

    private static void assertCellIsAliveAt(ArrayMatchfield matchfield, Location location) {
        Cell cell = matchfield.getCellAt(location);
        assertTrue(cell.isAlive());
    }

    private static void assertLocationIsNeighborTo(ArrayMatchfield matchfield, Location location, Collection<Location> expectedNeighbors) {
        Collection<Location> actualNeighbors = matchfield.streamNeighborLocations(location).collect(Collectors.toList());
        Assertions.assertEquals(new TreeSet<>(actualNeighbors), new TreeSet<>(expectedNeighbors));
    }
}
