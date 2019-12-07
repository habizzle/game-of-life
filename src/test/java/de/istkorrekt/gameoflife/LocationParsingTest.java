package de.istkorrekt.gameoflife;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static de.istkorrekt.gameoflife.util.LocationParserKt.parse;

class LocationParsingTest {

    @Test
    void testLocationCanBeParsed() {
        Collection<Location> actual = parse("(1|2)");
        assertLocationContained(new Location(1, 2), actual);
    }

    @Test
    void testMultipleLocationsCanBeParsed() {
        Collection<Location> actual = parse("(1|2), (2|3)");
        assertLocationContained(new Location(1, 2), actual);
        assertLocationContained(new Location(2, 3), actual);
        Assertions.assertEquals(2, actual.size());
    }

    private static void assertLocationContained(Location expected, Collection<Location> locations) {
        Assertions.assertTrue(locations.contains(expected));
    }
}
