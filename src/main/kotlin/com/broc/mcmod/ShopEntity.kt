package com.broc.mcmod

import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.world.World

class ShopEntity(type: EntityType<PathAwareEntity>, world: World) : PathAwareEntity(type, world) {
    fun createDefaultAttributes(): DefaultAttributeContainer.Builder? {
        return createMobAttributes()
    }

    override fun onDeath(damageSource: DamageSource?) {
        super.onDeath(damageSource)
    }
}