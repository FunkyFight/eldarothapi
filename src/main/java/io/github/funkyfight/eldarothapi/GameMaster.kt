package io.github.funkyfight.eldarothapi

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Bukkit

class GameMaster {

    fun giveItem(uuid: String, namespacedKey: String, amount: Int) {
        // Get player items
        val playerItems = EldarothAPI.players[uuid]?.get("items") as JsonArray

        // If player has the item, add the amount.
        // Else, create the item and add the amount.
        val itemExists = playerItems.any { it.asJsonObject.get("namespacedKey").asString == namespacedKey }

        if (itemExists) {
            val item = playerItems.find { it.asJsonObject.get("namespacedKey").asString == namespacedKey }!!.asJsonObject
            item.addProperty("amount", item.get("amount").asInt + amount)
        } else {
            val item = JsonObject()
            item.addProperty("namespacedKey", namespacedKey)
            item.addProperty("amount", amount)
            playerItems.add(item)
        }

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] $namespacedKey ($amount) -> $uuid")
    }

    fun giveArtefact(uuid: String, namespacedKey: String, artefactData: JsonObject) {
        // Get player artefacts
        val playerArtefacts = EldarothAPI.players[uuid]?.get("artefacts") as JsonArray

        // Add the property "instance" to the artefact (value is a random uuid)
        artefactData.addProperty("instance", java.util.UUID.randomUUID().toString())

        // Give the artefact
        playerArtefacts.add(artefactData)

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] $namespacedKey[${artefactData["instance"]}] -> $uuid")
    }

    fun giveNeptune(uuid: String, amount: Int) {
        // Get player neptunes as int
        val playerNeptunes = EldarothAPI.players[uuid]?.get("neptunes") as Int

        // Add the amount
        playerNeptunes.plus(amount)

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] $amount Neptunes -> $uuid")
    }

    fun giveSoul(uuid: String, namespacedKey: String) {
        // Get player souls
        val playerSouls = EldarothAPI.players[uuid]?.get("souls") as JsonArray

        // If player already has the soul, give 300 Neptune instead
        val soulExists = playerSouls.any { it.asJsonObject.get("namespacedKey").asString == namespacedKey }

        if (soulExists) {
            giveNeptune(uuid, 300)
        } else {
            // Else, get the soul data from webserver
            val soulData = EldarothAPI.souls[namespacedKey]?.deepCopy() as JsonObject
            playerSouls.add(soulData)
        }

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] $namespacedKey -> $uuid")
    }

    fun giveFood(uuid: String, namespacedKey: String, amount: Int) {
        // Like items, but for food
        val playerFood = EldarothAPI.players[uuid]?.get("food") as JsonArray

        // If player has the food, add the amount.
        // Else, create the food and add the amount.
        val foodExists = playerFood.any { it.asJsonObject.get("namespacedKey").asString == namespacedKey }

        if (foodExists) {
            val food = playerFood.find { it.asJsonObject.get("namespacedKey").asString == namespacedKey }!!.asJsonObject
            food.addProperty("amount", food.get("amount").asInt + amount)
        } else {
            val food = JsonObject()
            food.addProperty("namespacedKey", namespacedKey)
            food.addProperty("amount", amount)
            playerFood.add(food)
        }

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] $namespacedKey ($amount) -> $uuid")
    }

    fun giveWeapons(uuid: String, weaponData: JsonObject) {
        // Like artefacts, but for weapons
        val playerWeapons = EldarothAPI.players[uuid]?.get("weapons") as JsonArray

        // Add the property "instance" to the weapon (value is a random uuid)
        weaponData.addProperty("instance", java.util.UUID.randomUUID().toString())

        // Give the weapon
        playerWeapons.add(weaponData)

        // System out
        Bukkit.getConsoleSender().sendMessage("[EldarothAPI] ${weaponData["namespacedKey"]}[${weaponData["instance"]}] -> $uuid")
    }

}