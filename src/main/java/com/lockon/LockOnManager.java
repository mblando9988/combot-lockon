package com.lockon;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.network.ClientPlayerEntity;

public class LockOnManager {
	private static LivingEntity targetEntity = null;
	private static boolean isLocked = false;
	private static final float MAX_RANGE = 30.0f;
	private static final float ROTATION_SPEED = 0.15f;

	public static void setTarget(LivingEntity target) {
		targetEntity = target;
		isLocked = true;
	}

	public static void clearTarget() {
		targetEntity = null;
		isLocked = false;
	}

	public static LivingEntity getTarget() {
		return targetEntity;
	}

	public static boolean isLocked() {
		return isLocked && targetEntity != null;
	}

	public static void update(ClientPlayerEntity player) {
		if (!isLocked || targetEntity == null) {
			return;
		}

		// Check if target is still valid
		if (targetEntity.isRemoved() || targetEntity.isDead()) {
			clearTarget();
			return;
		}

		// Check if target is in range
		double distance = player.distanceTo(targetEntity);
		if (distance > MAX_RANGE) {
			clearTarget();
			return;
		}

		// Calculate rotation to target
		Vec3d targetPos = targetEntity.getEyePos();
		Vec3d playerPos = player.getEyePos();
		Vec3d diff = targetPos.subtract(playerPos);

		// Calculate yaw and pitch
		float yaw = (float) Math.toDegrees(Math.atan2(-diff.x, diff.z));
		float pitch = (float) Math.toDegrees(Math.atan2(-diff.y,
			Math.sqrt(diff.x * diff.x + diff.z * diff.z)));

		// Clamp pitch to Minecraft's limits (-90 to 90)
		pitch = Math.max(-90.0f, Math.min(90.0f, pitch));

		// Smoothly interpolate rotation
		player.setYaw(player.getYaw() + (yaw - player.getYaw()) * ROTATION_SPEED);
		player.setPitch(player.getPitch() + (pitch - player.getPitch()) * ROTATION_SPEED);
	}
}
