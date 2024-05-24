package com.broc.mcmod

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvent
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.village.Merchant
import net.minecraft.village.TradeOffer
import net.minecraft.village.TradeOfferList
import net.minecraft.world.World

class ShopBlock(settings: Settings, shop: DailyShop) : Block(settings), Merchant {
    private val shop = shop

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (!world?.isClient!!) {
            if (shop.isOpen) {
                player?.sendMessage(Text.literal("Wanna shop?"), false)
            }
            else {
                player?.sendMessage(Text.literal("CLOSED!"), false)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun setCustomer(customer: PlayerEntity?) {
        TODO("Not yet implemented")
    }

    override fun getCustomer(): PlayerEntity? {
        TODO("Not yet implemented")
    }

    override fun getOffers(): TradeOfferList {
        TODO("Not yet implemented")
    }

    override fun setOffersFromServer(offers: TradeOfferList?) {
        TODO("Not yet implemented")
    }

    override fun trade(offer: TradeOffer?) {
        TODO("Not yet implemented")
    }

    override fun onSellingItem(stack: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun getExperience(): Int {
        TODO("Not yet implemented")
    }

    override fun setExperienceFromServer(experience: Int) {
        TODO("Not yet implemented")
    }

    override fun isLeveledMerchant(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getYesSound(): SoundEvent {
        TODO("Not yet implemented")
    }

    override fun isClient(): Boolean {
        TODO("Not yet implemented")
    }
}