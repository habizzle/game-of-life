package de.istkorrekt.gameoflife

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ArrayMatchfieldTest {
    @Test
    fun testMatchfieldWithSize3Has9Locations() {
        val matchfield = ArrayMatchfield(Size(3), emptySet())
        val amountOfLocations = matchfield.collectAllLocations().size.toLong()

        assertEquals(9, amountOfLocations)
    }

    @Test
    fun testAllCellsAreDeadIfNoAliveLocationsAreProvided() {
        val matchfield = ArrayMatchfield(Size(3))
        val amountOfAliveCells = matchfield.collectAllLocations()
                .map { matchfield.getCellAt(it) }
                .filter { it.isAlive() }
                .count()

        assertEquals(0, amountOfAliveCells)
    }

    @Test
    fun testOneCellIsAliveIfAliveLocationIsProvided() {
        val matchfield = ArrayMatchfield(Size(3), setOf(Location(1, 1)))
        val amountOfAliveCells = matchfield.collectAllLocations()
                .map { matchfield.getCellAt(it) }
                .filter { it.isAlive() }
                .count()

        assertEquals(1, amountOfAliveCells)
    }

    @Test
    fun testLocationHas8Neighbors() {
        val matchfield = ArrayMatchfield(Size(3), setOf(Location(1, 1)))
        val amountOfNeighbors = matchfield.collectNeighborLocations(Location(0, 0)).size.toLong()

        assertEquals(8, amountOfNeighbors)
    }

    @Test
    fun testNeighborLocationIncludesAllSurroundingLocations() {
        val matchfield = ArrayMatchfield(Size(4))
        val expectedNeighbors = listOf(
                Location(0, 0),
                Location(1, 0),
                Location(2, 0),
                Location(0, 1),
                Location(2, 1),
                Location(0, 2),
                Location(1, 2),
                Location(2, 2)
        )

        assertLocationIsNeighborTo(matchfield, Location(1, 1), expectedNeighbors)
    }

    @Test
    fun testNeighborLocationOverflowsIfLocationIsAtTopBorder() {
        val matchfield = ArrayMatchfield(Size(4))
        val expectedNeighbors = listOf(
                Location(0, 3),
                Location(1, 3),
                Location(2, 3),
                Location(0, 0),
                Location(2, 0),
                Location(0, 1),
                Location(1, 1),
                Location(2, 1)
        )

        assertLocationIsNeighborTo(matchfield, Location(1, 0), expectedNeighbors)
    }

    @Test
    fun testNeighborLocationOverflowsIfLocationIsAtLeftBorder() {
        val matchfield = ArrayMatchfield(Size(4))
        val expectedNeighbors = listOf(
                Location(3, 0),
                Location(0, 0),
                Location(1, 0),
                Location(3, 1),
                Location(1, 1),
                Location(3, 2),
                Location(0, 2),
                Location(1, 2)
        )

        assertLocationIsNeighborTo(matchfield, Location(0, 1), expectedNeighbors)
    }

    @Test
    fun testNeighborLocationOverflowsIfLocationIsAtCorner() {
        val matchfield = ArrayMatchfield(Size(4))
        val expectedNeighbors = listOf(
                Location(3, 3),
                Location(0, 3),
                Location(1, 3),
                Location(3, 0),
                Location(1, 0),
                Location(3, 1),
                Location(0, 1),
                Location(1, 1)
        )

        assertLocationIsNeighborTo(matchfield, Location(0, 0), expectedNeighbors)
    }

    @Test
    fun testSwitchCellKillsAliveCell() {
        val location = Location(0, 0)
        val matchfield = ArrayMatchfield(Size(1), setOf(location))
        matchfield.switchCellAt(location)

        assertCellIsDeadAt(matchfield, location)
    }

    @Test
    fun testSwitchCellResurrectsDeadCell() {
        val location = Location(0, 0)
        val matchfield = ArrayMatchfield(Size(1))
        matchfield.switchCellAt(location)

        assertCellIsAliveAt(matchfield, location)
    }

    @Test
    fun testWidthIsAsProvided() {
        val expectedWidth = 3
        val matchfield = ArrayMatchfield(Size(expectedWidth, 1))

        assertEquals(expectedWidth, matchfield.getSize().width)
    }

    @Test
    fun testHeightIsAsProvided() {
        val expectedHeight = 3
        val matchfield = ArrayMatchfield(Size(1, expectedHeight))

        assertEquals(expectedHeight, matchfield.getSize().height)
    }

    @Test
    fun testWidthAndHeightAreAsProvidedSize() {
        val expected = 3
        val matchfield = ArrayMatchfield(Size(expected))

        assertEquals(expected, matchfield.getSize().width)
        assertEquals(expected, matchfield.getSize().height)
    }

    @Test
    fun testNextGenerationRotor() {
        // "rotor" is a special set of locations, which "rotate" infinitely
        val rotorLocations = listOf(
                Location(1, 1),
                Location(2, 1),
                Location(3, 1)
        )
        val nextGeneration = ArrayMatchfield(Size(5), rotorLocations).nextGeneration()

        assertAmountOfAliveCells(nextGeneration, 3)
    }

    companion object {
        private fun assertAmountOfAliveCells(matchfield: Matchfield, expected: Int) {
            val actual = matchfield.collectAllLocations()
                    .map { matchfield.getCellAt(it) }
                    .filter { it.isAlive() }
                    .count()
            assertEquals(expected, actual)
        }

        private fun assertCellIsDeadAt(matchfield: Matchfield, location: Location) {
            val cell = matchfield.getCellAt(location)
            assertFalse(cell.isAlive())
        }

        private fun assertCellIsAliveAt(matchfield: Matchfield, location: Location) {
            val cell = matchfield.getCellAt(location)
            assertTrue(cell.isAlive())
        }

        private fun assertLocationIsNeighborTo(matchfield: ArrayMatchfield, location: Location, expectedNeighbors: Collection<Location>) {
            val actualNeighbors = matchfield.collectNeighborLocations(location)
            assertEquals(actualNeighbors.toSortedSet(), expectedNeighbors.toSortedSet())
        }
    }
}