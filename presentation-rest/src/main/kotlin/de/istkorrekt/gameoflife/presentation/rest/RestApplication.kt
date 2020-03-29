package de.istkorrekt.gameoflife.presentation.rest

import spark.Spark.put

fun main() {
    put("/nextRound", "application/json", ::nextRound)
}