package de.istkorrekt.gameoflife.util;

import de.istkorrekt.gameoflife.Location;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocationParser {

    public static List<Location> parse(String raw) {
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .map(LocationParser::parseSingle)
                .collect(Collectors.toList());
    }

    private static final Pattern PATTERN = Pattern.compile("\\((\\d+)\\|(\\d+)\\)");

    private static Location parseSingle(String raw) {
        Matcher matcher = PATTERN.matcher(raw);
        matcher.find();
        String rawX = matcher.group(1);
        String rawY = matcher.group(2);
        return Location.of(Integer.parseInt(rawX), Integer.parseInt(rawY));
    }
}
