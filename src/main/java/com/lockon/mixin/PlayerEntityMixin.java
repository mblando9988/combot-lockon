package com.lockon.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.lockon.LockOnManager;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Inject(method = "attack", at = @At("TAIL"))
	private void onAttack(Entity target, CallbackInfo ci) {
		// Only process on client side
		if (target.getWorld().isClient && target instanceof LivingEntity) {
			LockOnManager.setTarget((LivingEntity) target);
		}
	}
}
