package com.broc.mcmod

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

class WalletItem(settings: Settings) : Item(settings) {
    override fun useOnEntity(stack: ItemStack?, user: PlayerEntity?, entity: LivingEntity?, hand: Hand?): ActionResult {
        //TODO: Use held wallet to start trading with Entity?
        return super.useOnEntity(stack, user, entity, hand)
    }

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        //TODO: Use held wallet to start trading with Block?
        return super.useOnBlock(context)
    }
}