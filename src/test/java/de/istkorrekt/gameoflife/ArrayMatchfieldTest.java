package de.istkorrekt.gameoflife;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatchfieldTest {

    @Test
    void testMatchfieldWithSize3Has9Locations() {
        Matchfield matchfield = new ArrayMatchfield(new Size(3), Collections.emptySet());
        long amountOfLocations = matchfield.streamAllLocations().size();
        assertEquals(9, amountOfLocations);
    }

    @Test
    void testAllCellsAreDeadIfNoAliveLocationsAreProvided() {
        Matchfield matchfield = new ArrayMatchfield(new Size(3));
        long amountOfAliveCells = matchfield.streamAllLocations().stream().map(matchfield::getCellAt).filter(Cell::isAlive).count();
        assertEquals(0, amountOfAliveCells);
    }

    @Test
    void testOneCellIsAliveIfAliveLocationIsProvided() {
        Matchfield matchfield = new ArrayMatchfield(new Size(3), Collections.singleton(new Location(1, 1)));
        long amountOfAliveCells = matchfield.streamAllLocations().stream().map(matchfield::getCellAt).filter(Cell::isAlive).count();
        assertEquals(1, amountOfAliveCells);
    }

    @Test
    void testLocationHas8Neighbors() {
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(3), Collections.singleton(new Location(1, 1)));
        long amountOfNeighbors = matchfield.collectNeighborLocations(new Location(0, 0)).size();
        assertEquals(8, amountOfNeighbors);
    }

    @Test
    void testNeighborLocationIncludesAllSurroundingLocations() {
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                new Location(0, 0),
                new Location(1, 0),
                new Location(2, 0),
                new Location(0, 1),
                new Location(2, 1),
                new Location(0, 2),
                new Location(1, 2),
                new Location(2, 2)
        );
        assertLocationIsNeighborTo(matchfield, new Location(1, 1), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtTopBorder() {
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                new Location(0, 3),
                new Location(1, 3),
                new Location(2, 3),
                new Location(0, 0),
                new Location(2, 0),
                new Location(0, 1),
                new Location(1, 1),
                new Location(2, 1)
        );
        assertLocationIsNeighborTo(matchfield, new Location(1, 0), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtLeftBorder() {
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                new Location(3, 0),
                new Location(0, 0),
                new Location(1, 0),
                new Location(3, 1),
                new Location(1, 1),
                new Location(3, 2),
                new Location(0, 2),
                new Location(1, 2)
        );
        assertLocationIsNeighborTo(matchfield, new Location(0, 1), expectedNeighbors);
    }

    @Test
    void testNeighborLocationOverflowsIfLocationIsAtCorner() {
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(4));

        Collection<Location> expectedNeighbors = Arrays.asList(
                new Location(3, 3),
                new Location(0, 3),
                new Location(1, 3),
                new Location(3, 0),
                new Location(1, 0),
                new Location(3, 1),
                new Location(0, 1),
                new Location(1, 1)
        );
        assertLocationIsNeighborTo(matchfield, new Location(0, 0), expectedNeighbors);
    }

    @Test
    void testSwitchCellKillsAliveCell() {
        Location location = new Location(0, 0);
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(1), Collections.singleton(location));

        matchfield.switchCellAt(location);

        assertCellIsDeadAt(matchfield, location);
    }

    @Test
    void testSwitchCellResurrectsDeadCell() {
        Location location = new Location(0, 0);
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(1));

        matchfield.switchCellAt(location);

        assertCellIsAliveAt(matchfield, location);
    }

    @Test
    void testWidthIsAsProvided() {
        int expectedWidth = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(expectedWidth, 1));

        assertEquals(expectedWidth, matchfield.getSize().getWidth());
    }

    @Test
    void testHeightIsAsProvided() {
        int expectedHeight = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(1, expectedHeight));

        assertEquals(expectedHeight, matchfield.getSize().getHeight());
    }

    @Test
    void testWidthAndHeightAreAsProvidedSize() {
        int expected = 3;
        ArrayMatchfield matchfield = new ArrayMatchfield(new Size(expected));

        assertEquals(expected, matchfield.getSize().getWidth());
        assertEquals(expected, matchfield.getSize().getHeight());
    }

    @Test
    void testNextGenerationRotor() {
        // "rotor" is a special set of locations, which "rotate" infinitely
        Collection<Location> rotorLocations = Arrays.asList(
                new Location(1, 1),
                new Location(2, 1),
                new Location(3, 1)
        );

        Matchfield nextGeneration = new ArrayMatchfield(new Size(5), rotorLocations).nextGeneration();

        assertAmountOfAliveCells(nextGeneration, 3);
    }

    private static void assertAmountOfAliveCells(Matchfield matchfield, int expected) {
        long actual = matchfield.streamAllLocations().stream()
                .map(matchfield::getCellAt)
                .filter(Cell::isAlive)
                .count();

        Assertions.assertEquals(expected, actual);
    }

    private static void assertCellIsDeadAt(Matchfield matchfield, Location location) {
        Cell cell = matchfield.getCellAt(location);
        assertFalse(cell.isAlive());
    }

    private static void assertCellIsAliveAt(Matchfield matchfield, Location location) {
        Cell cell = matchfield.getCellAt(location);
        assertTrue(cell.isAlive());
    }

    private static void assertLocationIsNeighborTo(ArrayMatchfield matchfield, Location location, Collection<Location> expectedNeighbors) {
        Collection<Location> actualNeighbors = matchfield.collectNeighborLocations(location);
        assertEquals(new TreeSet<>(actualNeighbors), new TreeSet<>(expectedNeighbors));
    }
}
