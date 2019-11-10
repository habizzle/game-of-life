package de.istkorrekt.gameoflife;

import java.util.Comparator;
import java.util.Objects;

public final class Location implements Comparable<Location> {

    private final int x;
    private final int y;

    private Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Location location) {
        return Comparator.comparing(Location::getX).thenComparing(Location::getY).compare(this, location);
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

    public boolean isWithinBounds(Size size) {
        // x and y are zero-based
        return x < size.getWidth() && y < size.getHeight();
    }

    public Location upper() {
        int upperY = Math.max(y - 1, 0);
        return Location.of(x, upperY);
    }

    public Location right(Size bounds) {
        Location newLocation = Location.of(x + 1, y);
        if (newLocation.isWithinBounds(bounds)) {
            return newLocation;
        }
        return this;
    }

    public Location lower(Size bounds) {
        Location newLocation = Location.of(x, y + 1);
        if (newLocation.isWithinBounds(bounds)) {
            return newLocation;
        }
        return this;
    }

    public Location left() {
        int update = Math.max(x - 1, 0);
        return Location.of(update, y);
    }

    public static Location of(int x, int y) {
        return new Location(x, y);
    }
}
