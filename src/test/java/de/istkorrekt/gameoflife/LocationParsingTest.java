package de.istkorrekt.gameoflife;

import de.istkorrekt.gameoflife.util.LocationParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

class LocationParsingTest {

    @Test
    void testLocationCanBeParsed() {
        Collection<Location> actual = LocationParser.parse("(1|2)");
        assertLocationContained(Location.of(1, 2), actual);
    }

    @Test
    void testMultipleLocationsCanBeParsed() {
        Collection<Location> actual = LocationParser.parse("(1|2), (2|3)");
        assertLocationContained(Location.of(1, 2), actual);
        assertLocationContained(Location.of(2, 3), actual);
        Assertions.assertEquals(2, actual.size());
    }

    private static void assertLocationContained(Location expected, Collection<Location> locations) {
        Assertions.assertTrue(locations.contains(expected));
    }
}
