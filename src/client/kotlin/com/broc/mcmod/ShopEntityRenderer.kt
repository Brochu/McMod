package com.broc.mcmod

import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.util.Identifier

class ShopEntityRenderer(context: Context?)
: MobEntityRenderer<ShopEntity, ShopEntityModel>(context, ShopEntityModel(), 0.5f) {

    override fun getTexture(entity: ShopEntity?): Identifier {
        TODO("Not yet implemented")
    }
}