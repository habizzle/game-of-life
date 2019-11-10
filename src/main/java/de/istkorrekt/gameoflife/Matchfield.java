package de.istkorrekt.gameoflife;

import java.util.stream.Stream;

public interface Matchfield {
    Cell getCellAt(Location location);

    Stream<Location> streamAllLocations();

    ArrayMatchfield nextGeneration();

    void switchCellAt(Location location);

    Size getSize();
}
