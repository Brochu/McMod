package com.broc.mcmod

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object McMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("mcmod")
	private val custom_item = Item(FabricItemSettings())

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		logger.info("[START] Preparing the mod")
		Registry.register(Registries.ITEM, Identifier("tutorial", "custom_item"), custom_item)
		logger.info("[DONE] Mod is prepped")
	}
}