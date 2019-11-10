package de.istkorrekt.gameoflife;

import java.util.Collection;
import java.util.Collections;

public class MatchfieldFactory {

    public Matchfield createAllDead(Size size) {
        return createWithAlive(Collections.emptyList(), size);
    }

    public Matchfield createWithAlive(Collection<Location> locations, Size size) {
        return new ArrayMatchfield(locations, size);
    }
}
