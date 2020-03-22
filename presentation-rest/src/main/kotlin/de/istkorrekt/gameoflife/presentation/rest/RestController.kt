package de.istkorrekt.gameoflife.presentation.rest

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import de.istkorrekt.gameoflife.Location
import de.istkorrekt.gameoflife.Matchfield
import de.istkorrekt.gameoflife.MatchfieldFactory.createWithAlive
import de.istkorrekt.gameoflife.Size
import spark.Request
import spark.Response

fun nextRound(request: Request, response: Response): String {
    val matchfield = try {
        matchfieldFromRequest(request)
    } catch (e: IllegalStateException) {
        response.status(400)
        return "Request body cannot be parsed"
    }
    matchfield.nextGeneration()
    response.apply {
        status(200)
        type("application/json")
    }
    return matchfieldToJson(matchfield).toString()
}

private fun matchfieldFromRequest(request: Request): Matchfield {
    val json = JsonParser.parseString(request.body()).asJsonObject
    return matchfieldFromJson(json["matchfield"].asJsonObject)
}

private fun matchfieldFromJson(json: JsonObject): Matchfield {
    return createWithAlive(
            sizeFromJson(json["size"].asJsonObject),
            aliveLocationsFromJson(json["alive"].asJsonArray)
    )
}

private fun sizeFromJson(json: JsonObject): Size {
    return Size(width = json["width"].asInt, height = json["height"].asInt)
}

private fun aliveLocationsFromJson(json: JsonArray): List<Location> {
    return json.map { item -> locationFromJson(item.asJsonObject) }
}

private fun locationFromJson(json: JsonObject): Location {
    return Location(x = json["x"].asInt, y = json["y"].asInt)
}

private fun matchfieldToJson(matchfield: Matchfield): JsonElement {
    val json = JsonObject()
    json.add("size", sizeToJson(matchfield.getSize()))
    json.add("alive", aliveLocationsToJson(matchfield))
    return json
}

private fun sizeToJson(size: Size): JsonElement {
    val json = JsonObject()
    json.addProperty("width", size.width)
    json.addProperty("height", size.height)
    return json
}

private fun aliveLocationsToJson(matchfield: Matchfield): JsonElement {
    val jsonArray = JsonArray()
    matchfield.collectAllLocations()
            .filter { matchfield.getCellAt(it).isAlive() }
            .forEach { jsonArray.add(locationToJson(it)) }
    return jsonArray
}

private fun locationToJson(location: Location): JsonElement {
    val json = JsonObject()
    json.addProperty("x", location.x)
    json.addProperty("y", location.y)
    return json
}

