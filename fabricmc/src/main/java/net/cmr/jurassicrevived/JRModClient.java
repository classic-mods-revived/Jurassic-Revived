package net.cmr.jurassicrevived;

import net.fabricmc.api.ClientModInitializer;

public class JRModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommonClientClass.init();
	}
}
