package com.broc.mcmod.mixin;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface NewDayCallback {
    Event<NewDayCallback> EVENT = EventFactory.createArrayBacked(NewDayCallback.class,
        (listeners) -> (world) -> {
            for (NewDayCallback l : listeners) {
                ActionResult res = l.interact(world);

                if (res != ActionResult.PASS) {
                    return res;
                }
            }
            return ActionResult.PASS;
        });

    ActionResult interact(World world);
}
