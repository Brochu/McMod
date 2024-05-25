package com.broc.mcmod

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.village.Merchant
import net.minecraft.village.TradeOffer
import net.minecraft.village.TradeOfferList
import net.minecraft.world.World
import org.slf4j.LoggerFactory

class ShopBlock(settings: Settings, private val shop: DailyShop) : Block(settings), Merchant {
    private val logger = LoggerFactory.getLogger("shopblock")
    private var customer: PlayerEntity? = null

    @Deprecated("Deprecated, but not for me")
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
                setCustomer(player)
                sendOffers(customer, Text.literal("Welcome to Daily Shop"), 0)
            }
            else {
                player?.sendMessage(Text.literal("CLOSED!"), false)
            }
        }
        return ActionResult.SUCCESS
    }

    override fun setCustomer(c: PlayerEntity?) {
        customer = c
    }

    override fun getCustomer(): PlayerEntity? {
        return customer
    }

    override fun getOffers(): TradeOfferList {
        return shop.offers
    }

    override fun setOffersFromServer(offers: TradeOfferList?) {
        //Ignore, offers are rolled by DailyShop
        //Will maybe need to handle this when multiple players are shopping at the same time
        //Check screenHandler
    }

    override fun trade(offer: TradeOffer?) {
        //Are there any actions to take after completed trade
        logger.warn("Trade completed $offer")
    }

    override fun onSellingItem(stack: ItemStack?) {
        //This is called when selecting a trade from the shop UI
        //Do we need to react here?
    }

    override fun getExperience(): Int {
        return 0
    }

    override fun setExperienceFromServer(experience: Int) {
        //Ignore, No experience should be gained from this shop
    }

    override fun isLeveledMerchant(): Boolean {
        //TODO: Look into what this means
        return false;
    }

    override fun getYesSound(): SoundEvent {
        logger.warn("get yes sound")
        return SoundEvents.ENTITY_CREEPER_PRIMED
    }

    override fun isClient(): Boolean {
        //TODO: Need to find a way to access server / world
        logger.warn("is client")
        return false
    }
}