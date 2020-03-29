package de.istkorrekt.gameoflife.presentation.rest

import spark.Filter
import spark.Spark.*

fun main() {
    allowAllOrigins()
    put("/nextRound", "application/json", ::nextRound)
}

fun allowAllOrigins() {
    options("/*") { request, response ->
        val accessControlRequestHeaders: String? = request.headers("Access-Control-Request-Headers")
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }
        val accessControlRequestMethod: String? = request.headers("Access-Control-Request-Method")
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }
        "OK"
    }

    before(Filter { _, response ->
        response.header("Access-Control-Allow-Origin", "*")
    })
}