package com.broc.mcmod

import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory

const val CONFIGFILE = "ShopConf.json"
val CONFIGPATH: Path = Paths.get(FabricLoader.getInstance().configDir.toString(), CONFIGFILE)
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
        if (!CONFIGPATH.exists()) {
            logger.warn("Could not find config file at $CONFIGPATH, creating default config")

            val resFile = File(this.javaClass.getResource("/$CONFIGFILE")!!.path)
            val confFile = File(CONFIGPATH.toString())
            resFile.copyTo(confFile)
        }

        //TODO: Careful about reading the whole file at once, to change if file becomes too large
        val json = CONFIGPATH.readText()
        val obj = Json.decodeFromString<JsonObject>(json)

        // Set config with default if missing
        conf.enabled = obj["enabled"]?.jsonPrimitive?.boolean ?: false
        conf.rollHour = obj["rollHour"]?.jsonPrimitive?.int ?: 0
        conf.openHour = obj["openHour"]?.jsonPrimitive?.int ?: 3
        conf.closeHour = obj["closeHour"]?.jsonPrimitive?.int ?: 22
        conf.shopSize = obj["shopSize"]?.jsonPrimitive?.int ?: 1
        conf.itemPool = ArrayList(obj["itemPool"]?.jsonArray?.map { elem ->
            elem.jsonPrimitive.content
        } ?: listOf())
    }
}