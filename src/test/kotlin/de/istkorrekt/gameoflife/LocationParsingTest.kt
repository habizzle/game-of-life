package de.istkorrekt.gameoflife

import de.istkorrekt.gameoflife.util.parse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class LocationParsingTest {
    @Test
    fun testLocationCanBeParsed() {
        assertEqualLocations(Location(1, 2), actual = parse("(1|2)"))
    }

    @Test
    fun testMultipleLocationsCanBeParsed() {
        assertEqualLocations(Location(1, 2), Location(2, 3), actual = parse("(1|2), (2|3)"))
    }

    companion object {
        private fun assertEqualLocations(vararg expected: Location, actual: Collection<Location>) {
            Assertions.assertTrue(actual.containsAll(expected.asList()))
            Assertions.assertEquals(expected.size, actual.size)
        }
    }
}