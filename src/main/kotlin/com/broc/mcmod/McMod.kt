package com.broc.mcmod

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object McMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("mcmod")
	private val my_item = Registry.register(
		Registries.ITEM,
		Identifier("tutorial", "custom_item"),
		CustomItem(Item.Settings().maxCount(16))
	)

	override fun onInitialize() {
		FuelRegistry.INSTANCE.add(my_item, 300)

		// Add to current Item Group
		//ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register {
		//	content -> content.add(my_item)
		//}

		// Create new custom Item Group
		val group = FabricItemGroup.builder()
			.icon { ItemStack(my_item) }
			.displayName(Text.translatable("itemGroup.tutorial.test_group"))
			.entries { _, entries -> entries.add(my_item) }
			.build()

		Registry.register(
			Registries.ITEM_GROUP,
			Identifier("tutorial", "test_group"),
			group
		)
	}
}