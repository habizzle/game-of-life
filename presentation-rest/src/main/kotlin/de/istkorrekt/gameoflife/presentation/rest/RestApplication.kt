package de.istkorrekt.gameoflife.presentation.rest

import spark.Spark.get

fun main() {
    get("/nextRound", ::nextRound)
}