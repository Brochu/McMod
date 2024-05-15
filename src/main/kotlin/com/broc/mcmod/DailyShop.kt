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

    @OptIn(ExperimentalSerializationApi::class)
    fun init() {
        logger.warn("Here's an empty config: $conf")
        //TODO: Read config from CONFIGPATH, testing with resource for now
        val streamConf = this.javaClass.getResourceAsStream("/ShopConf.json")!!
        val obj = Json.decodeFromStream<JsonObject>(streamConf)
        logger.warn("Trying to decode default config: $obj")

        // Set config
        conf.enabled = obj["enabled"]!!.jsonPrimitive.boolean
        conf.rollHour = obj["rollHour"]!!.jsonPrimitive.int
        conf.openHour = obj["openHour"]!!.jsonPrimitive.int
        conf.closeHour = obj["closeHour"]!!.jsonPrimitive.int
        conf.shopSize = obj["shopSize"]!!.jsonPrimitive.int
        //TODO: Look into extra " with jsonPrimitive.toString()...
        conf.itemPool = ArrayList(obj["itemPool"]!!.jsonArray.map { elem ->
            elem.jsonPrimitive.toString()
        })
        logger.warn("DONE LOADING CONFIG")
    }
}