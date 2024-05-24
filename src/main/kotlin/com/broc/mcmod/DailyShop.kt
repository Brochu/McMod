package com.broc.mcmod

import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.village.TradeOffer
import net.minecraft.village.TradeOfferList
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
        var enabled = false

        var rollHour = 0   // Between 0-23
        var openHour = 3   // Between 0-23
        var closeHour = 22 // Between 0-23

        class Category {
            var size = 1
            var name = ""
            //TODO: We need more info per item, stack size, price, discount multiplier, weight
            var items = ArrayList<String>()
        }
        var categories = ArrayList<Category>()
    }
    private val conf = ShopConfig()
    var isOpen = false
    val offers = TradeOfferList()

    fun init(currentHour: Int) {
        if (!CONFIGPATH.exists()) {
            logger.warn("Could not find config file at $CONFIGPATH, creating default config")

            val resFile = File(this.javaClass.getResource("/$CONFIGFILE")!!.path)
            val confFile = File(CONFIGPATH.toString())
            resFile.copyTo(confFile)
        }

        //TODO: Careful about reading the whole file at once, to change if file becomes too large
        val json = CONFIGPATH.readText()
        val obj = Json.decodeFromString<JsonObject>(json)

        // Set config, fill default if missing
        conf.enabled = obj["enabled"]?.jsonPrimitive?.boolean ?: false
        conf.rollHour = obj["rollHour"]?.jsonPrimitive?.int ?: 0
        conf.openHour = obj["openHour"]?.jsonPrimitive?.int ?: 3
        conf.closeHour = obj["closeHour"]?.jsonPrimitive?.int ?: 22
        conf.categories = ArrayList(obj["categories"]?.jsonArray?.map { c ->
            val catObj = c.jsonObject
            val cat  = ShopConfig.Category()
            cat.size = catObj["size"]?.jsonPrimitive?.int ?: 1
            //TODO: Better error handling?
            cat.name = catObj["name"]?.jsonPrimitive?.content ?: "INVALID"
            //TODO: 2 layers deep of ArrayList parsing is a bit cursed...
            cat.items = ArrayList(catObj["items"]?.jsonArray?.map { i ->
                i.jsonPrimitive.content
            } ?: listOf())
            cat
        } ?: listOf())
        logger.warn("Done parsing config: $conf")

        if (!conf.enabled) { return }
        // -------------------------------------
        isOpen = checkIsOpen(currentHour)
        logger.warn("Is the shop open? $isOpen")
        rollOffers()
        ServerTickEvents.END_SERVER_TICK.register { sworld ->
            val props = sworld.saveProperties.mainWorldProperties
            if ((props.timeOfDay - 1) % 1000 == 0L) { // Lil Hacky
                val newHour = (props.timeOfDay / 1000) % 24
                hourChanged(newHour.toInt())
            }
        }
    }

    private fun hourChanged(newHour: Int) {
        logger.warn("[NEW HOUR] $newHour")

        if (newHour == conf.closeHour) {
            isOpen = false
            logger.warn("Shop is now CLOSED")
        }
        if (newHour == conf.openHour) {
            isOpen = true
            logger.warn("Shop is now OPEN")
        }

        //TODO: Offer reroll logic
        if (newHour == conf.rollHour) {
            rollOffers()
        }
    }

    private fun checkIsOpen(currentHour: Int): Boolean {
        var i = conf.openHour
        if (conf.openHour > conf.closeHour) {
            i -= 24
        }

        while (i < conf.closeHour) {
            if (i == currentHour) { return true }
            i++
        }
        return false
    }

    private fun rollOffers() {
        logger.warn("[ROLL OFFERS]")
        val getId = { name: String ->
            val sep = name.indexOf(':')
            val dir = name.substring(0..<sep)
            val item = name.substring(sep+1)

            Identifier(dir, item)
        }

        //TODO: Get offers from all categories
        for (c in conf.categories) {
            logger.warn("CATEGORY ${c.name}:")
            for (i in c.items) {
                val item = Registries.ITEM.getOrEmpty(getId(i))
                val buy = Registries.ITEM.get(Identifier("minecraft", "emerald"))
                if (!item.isEmpty) {
                    val stack = ItemStack(item.get(), 1)
                    val trade = ItemStack(buy, 1)
                    logger.warn("\tstack = $stack")
                    offers.add(TradeOffer(
                        stack,
                        trade,
                        0, 0, 1.0f
                    ))
                }
            }
        }
    }
}