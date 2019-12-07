package de.istkorrekt.gameoflife.app.cli

import de.istkorrekt.gameoflife.Location
import de.istkorrekt.gameoflife.Matchfield
import de.istkorrekt.gameoflife.MatchfieldFactory.createWithAlive
import de.istkorrekt.gameoflife.Size
import de.istkorrekt.gameoflife.util.parse
import java.util.logging.Logger
import kotlin.time.ExperimentalTime
import kotlin.time.MonoClock

val logger = Logger.getGlobal()

@ExperimentalTime
fun main(args: Array<String>) {
    val locations: Collection<Location> = parse(args[0])
    val size = args[1].toInt()
    val matchfield = createWithAlive(Size(size), locations)

    if (args.size >= 3) {
        val rounds = args[2].toInt()
        autoPlay(matchfield, rounds)
    } else {
        logger.info(matchfield.toString())
    }
}

@ExperimentalTime
fun autoPlay(initialMatchfield: Matchfield, rounds: Int) {
    val start = MonoClock.markNow()

    MatchfieldIterator(initialMatchfield, rounds).forEach {
        logger.info(it.toString())
    }
    logger.info("Game finished within ${start.elapsedNow()} after $rounds rounds.")
}

private class MatchfieldIterator(var current: Matchfield, val rounds: Int) : Iterator<Matchfield> {
    var currentRound = 1

    override fun hasNext(): Boolean {
        return currentRound < rounds
    }

    override fun next(): Matchfield {
        check(hasNext())
        current = current.nextGeneration()
        currentRound++
        return current
    }
}