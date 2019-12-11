package de.istkorrekt.gameoflife.util

import de.istkorrekt.gameoflife.Location

private val regex = Regex("""\((\d+)\|(\d+)\)""")

fun parse(raw: String): List<Location> {
    return raw.split(",")
            .map { it.trim() }
            .map { parseSingle(it) }
}

private fun parseSingle(raw: String): Location {
    val result = regex.find(raw)
    return result!!.let {
        val x = it.groupValues[1].toInt()
        val y = it.groupValues[2].toInt()
        Location(x, y)
    }
}