package com.broc.mcmod

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory
import java.io.InputStreamReader
import java.nio.file.Paths

val CONFIGPATH = Paths.get(FabricLoader.getInstance().configDir.toString(), "ShopConf.json")
/**
 *  Handles all server wide related shop logic
 *  Refreshes, picking orders, opening & closing shop and loading configuration
 */
class DailyShop {
    private val logger = LoggerFactory.getLogger("dailyshop")
    /**
     * Configuration object for the daily shop
     */
    class ShopConfig {
        var enabled = false;

        var rollHour = 0   // Between 0-23
        var openHour = 3   // Between 0-23
        var closeHour = 22 // Between 0-23

        //TODO: Add weight for the random picks
        var shopSize = 1
        var itemPool = ArrayList<String>();
    }
    val conf = ShopConfig()

    fun init() {
        logger.warn("Here's an empty config: $conf")
        //TODO: Read config from CONFIGPATH, testing with resource for now
        //TODO: Make sure we deal with lifetimes well...
        val json = this.javaClass.getResourceAsStream("/ShopConf.json")!!.reader().readText()
        val obj = Json.decodeFromString<JsonObject>(json)
        logger.warn("Trying to decode default config: $obj")
        logger.warn("We should be getting the config from $CONFIGPATH")

        // Set config with default if missing
        conf.enabled = obj["enabled"]?.jsonPrimitive?.boolean ?: false
        conf.rollHour = obj["rollHour"]?.jsonPrimitive?.int ?: 0
        conf.openHour = obj["openHour"]?.jsonPrimitive?.int ?: 3
        conf.closeHour = obj["closeHour"]?.jsonPrimitive?.int ?: 22
        conf.shopSize = obj["shopSize"]?.jsonPrimitive?.int ?: 1
        conf.itemPool = ArrayList(obj["itemPool"]?.jsonArray?.map { elem ->
            elem.jsonPrimitive.content
        } ?: listOf())
        logger.warn("DONE LOADING CONFIG")
    }
}