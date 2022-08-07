package io.github.funkyfight.eldarothapi

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bukkit.Bukkit
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.UUID

class HttpRequests {

    fun registerPlayer(uuid: UUID) {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/players?key=${EldarothAPI.apiKey}&playerUUID=${uuid.toString()}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonObject
        EldarothAPI.players.put(uuid.toString(), json)
    }

    fun updatePlayer(uuid: UUID, jsonData: JsonObject) {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/players/update?key=${EldarothAPI.apiKey}&playerUUID=${uuid.toString()}"
        val response = post(url, jsonData)
    }

    fun registerAnnouncements() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/announcements?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each announcement and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.announcements.add(json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.announcements.size} announcements loaded")
    }

    fun registerArtefacts() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/artefacts?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each artefact and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.artefacts.put(json.get(i).asJsonObject.get("namespacedKey").asString, json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.artefacts.size} artefacts loaded")
    }

    fun registerFoods() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/foods?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each food and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.food.put(json.get(i).asJsonObject.get("namespacedKey").asString, json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.food.size} foods loaded")
    }

    fun registerGachas() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/gacha?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each gacha and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.gacha.add(json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.gacha.size} gachas loaded")
    }

    fun registerInteractiveBlocks() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/interactiveBlocks?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each interactive block and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.interactiveBlocks.add(json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.interactiveBlocks.size} interactive blocks loaded")
    }

    fun registerItems() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/items?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each item and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.items.put(json.get(i).asJsonObject.get("namespacedKey").asString, json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.items.size} items loaded")
    }

    fun registerSouls() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/souls?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each soul and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.souls.put(json.get(i).asJsonObject.get("namespacedKey").asString, json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.souls.size} souls loaded")
    }

    fun registerWeapons() {
        // Create a new HttpClient and HttpRequest
        val url = "http://127.0.0.1:3000/api/weapons?key=${EldarothAPI.apiKey}"
        val response = get(url)

        // Convert response to JSON
        val json = JsonParser.parseString(response).asJsonArray

        // Loop through each weapon and add it to the list
        for (i in 0 until json.size()) {
            EldarothAPI.weapons.put(json.get(i).asJsonObject.get("namespacedKey").asString, json.get(i).asJsonObject)
        }

        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${EldarothAPI.weapons.size} weapons loaded")
    }

    fun get(url: String): String? {
        // Create HTTP client
        val client = HttpClient.newHttpClient()

        // Create request
        val request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build()

        // Send request
        var response = client.send(request, HttpResponse.BodyHandlers.ofString())

        // Return response
        return response.body()
    }

    fun post(url: String, body: JsonObject) {
        // Create HTTP client
        val client = HttpClient.newHttpClient()

        // Create request
        val request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build()

        // Send request
        client.send(request, HttpResponse.BodyHandlers.ofString())
    }

}