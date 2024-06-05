package com.broc.mcmod

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import org.slf4j.LoggerFactory

class WalletItem(settings: Settings) : Item(settings) {
    private val logger = LoggerFactory.getLogger("wallet")
    private val countKey = "mcmod:counter"

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        if (world?.isClient()!!) {
            return TypedActionResult.fail(user?.getStackInHand(hand!!))
        }

        val stack = user?.getStackInHand(hand!!)!!
        val nbt = stack.orCreateNbt

        var count = 1;
        if (nbt.contains(countKey)) {
            count = nbt.getInt(countKey)
            count++
            nbt.putInt(countKey, count)
        } else {
            nbt.putInt(countKey, count)
        }
        logger.warn(" --> $count")

        return TypedActionResult.success(stack)
    }

    override fun useOnEntity(stack: ItemStack?, user: PlayerEntity?, entity: LivingEntity?, hand: Hand?): ActionResult {
        //TODO: Use held wallet to start trading with Entity?
        return super.useOnEntity(stack, user, entity, hand)
    }

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        //TODO: Use held wallet to start trading with Block?
        return super.useOnBlock(context)
    }
}