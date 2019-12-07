package de.istkorrekt.gameoflife

object MatchfieldFactory {

    fun createAllDead(size: Size): Matchfield {
        return ArrayMatchfield(size)
    }

    fun createWithAlive(size: Size, aliveLocations: Collection<Location>): Matchfield {
        return ArrayMatchfield(size, aliveLocations)
    }
}