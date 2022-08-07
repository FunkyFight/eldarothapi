package io.github.funkyfight.eldarothapi

import com.google.gson.JsonObject
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.ArrayList

class EldarothAPI : JavaPlugin() {

        override fun onEnable() {
            // Loading each collections
            val requests = HttpRequests()

            requests.registerFoods()
            requests.registerWeapons()
            requests.registerAnnouncements()
            requests.registerArtefacts()
            requests.registerSouls()
            requests.registerInteractiveBlocks()
            requests.registerGachas()
        }

        override fun onDisable() {
            // Plugin shutdown logic
        }

        companion object {
            // Collections
            var players = HashMap<String, JsonObject>()
            var announcements = ArrayList<JsonObject>()
            var artefacts = HashMap<String, JsonObject>()
            var food = HashMap<String, JsonObject>()
            var gacha = ArrayList<JsonObject>()
            var interactiveBlocks = ArrayList<JsonObject>()
            var items = HashMap<String, JsonObject>()
            var souls = HashMap<String, JsonObject>()
            var weapons = HashMap<String, JsonObject>()
            var apiKey = "iFKXTCYYB447cszmREPHqT47ogrJ6jbKxgS5czrc"
        }

}