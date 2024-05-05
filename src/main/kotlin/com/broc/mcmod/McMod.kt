package com.broc.mcmod

import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object McMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("mcmod")
	private val my_item = Registry.register(
		Registries.ITEM,
		Identifier("tutorial", "custom_item"),
		Item(Item.Settings())
	)

	override fun onInitialize() {
	}
}