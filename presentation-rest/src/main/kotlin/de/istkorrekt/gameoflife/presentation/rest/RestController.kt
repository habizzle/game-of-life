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

fun nextRound(request: Request, response: Response) {
    val json = JsonParser.parseString(request.body()).asJsonObject
    val matchfield = matchfieldFromJson(json["matchfield"].asJsonObject)
    matchfield.nextGeneration()
    response.body(matchfieldToJson(matchfield).asString)
}

fun matchfieldFromJson(json: JsonObject): Matchfield {
    return createWithAlive(
            sizeFromJson(json["size"].asJsonObject),
            aliveLocationsFromJson(json["alive"].asJsonArray)
    )
}

fun sizeFromJson(json: JsonObject): Size {
    return Size(width = json["width"].asInt, height = json["height"].asInt)
}

fun aliveLocationsFromJson(json: JsonArray): List<Location> {
    return json.map { item -> locationFromJson(item.asJsonObject) }
}

fun locationFromJson(json: JsonObject): Location {
    return Location(x = json["x"].asInt, y = json["y"].asInt)
}

fun matchfieldToJson(matchfield: Matchfield): JsonElement {
    val json = JsonObject()
    json.add("size", sizeToJson(matchfield.getSize()))
    json.add("alive", aliveLocationsToJson(matchfield))
    return json
}

fun sizeToJson(size: Size): JsonElement {
    val json = JsonObject()
    json.addProperty("width", size.width)
    json.addProperty("height", size.height)
    return json
}

fun aliveLocationsToJson(matchfield: Matchfield): JsonElement {
    val jsonArray = JsonArray()
    matchfield.collectAllLocations()
            .filter { matchfield.getCellAt(it).isAlive() }
            .forEach { jsonArray.add(locationToJson(it)) }
    return jsonArray
}

fun locationToJson(location: Location): JsonElement {
    val json = JsonObject()
    json.addProperty("x", location.x)
    json.addProperty("y", location.y)
    return json
}

