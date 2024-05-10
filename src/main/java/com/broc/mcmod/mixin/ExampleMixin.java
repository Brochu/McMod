package com.broc.mcmod.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "tickTime", cancellable = true)
	private void tickTime(CallbackInfo ci) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		//ActionResult res = NewDayCallback.EVENT.invoker().interact((ServerWorld)(Object)this);

		//if (res == ActionResult.FAIL) {
		//	ci.cancel();
		//}
	}
}