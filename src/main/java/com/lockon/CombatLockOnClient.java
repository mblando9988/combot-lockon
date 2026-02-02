package com.lockon;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class CombatLockOnClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Register client tick event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				LockOnManager.update(client.player);
			}
		});
	}
}
