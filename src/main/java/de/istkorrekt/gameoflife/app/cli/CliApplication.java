package de.istkorrekt.gameoflife.app.cli;

import de.istkorrekt.gameoflife.Location;
import de.istkorrekt.gameoflife.Matchfield;
import de.istkorrekt.gameoflife.MatchfieldFactory;
import de.istkorrekt.gameoflife.Size;
import de.istkorrekt.gameoflife.util.LocationParser;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CliApplication {

    private static final Logger LOG = Logger.getLogger("game");

    public static void main(String[] args) {
        Collection<Location> locations = LocationParser.parse(args[0]);
        int size = Integer.parseInt(args[1]);
        Matchfield matchfield = new MatchfieldFactory().createWithAlive(locations, Size.of(size));

        if (args.length >= 3) {
            int rounds = Integer.parseInt(args[2]);
            autoPlay(matchfield, rounds);
        } else {
            LOG.info(matchfield.toString());
        }
    }

    private static void autoPlay(Matchfield initialMatchfield, int rounds) {
        Instant start = Instant.now();

        AtomicInteger roundsCounter = new AtomicInteger();
        Stream.iterate(initialMatchfield, Matchfield::nextGeneration)
                .limit(rounds + 1)
                .peek(game -> LOG.info("Round " + roundsCounter.getAndIncrement() + "\n" + game))
                .reduce((first, second) -> second)
                .orElseThrow();

        LOG.info("\nGame finished within " + Duration.between(start, Instant.now()) + " after " + rounds + " rounds.");
    }
}
