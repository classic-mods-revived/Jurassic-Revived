package net.cmr.jurassicrevived;

import net.fabricmc.api.ClientModInitializer;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class JRModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommonClientClass.init();

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TANK.get(), RenderType.translucent());
	}
}
