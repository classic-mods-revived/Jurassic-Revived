package net.cmr.jurassicrevived.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.entity.custom.CultivatorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

// Credits to TurtyWurty
// Under MIT-License: https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod?tab=MIT-1-ov-file#readme
public class CultivatorBlockEntityRenderer implements BlockEntityRenderer<CultivatorBlockEntity> {
    public CultivatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

	@Override
	public void render(CultivatorBlockEntity pBlockEntity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int packedOverlay) {
		FluidStack fluidStack = pBlockEntity.getFluid();
		if (fluidStack.isEmpty())
			return;

		Level level = pBlockEntity.getLevel();
		if (level == null)
			return;

		TextureAtlasSprite sprite = FluidStackHooks.getStillTexture(fluidStack);
		if (isMissing(sprite)) {
			sprite = FluidStackHooks.getStillTexture(fluidStack.getFluid());
		}
		if (isMissing(sprite)) {
			if (fluidStack.getFluid() == Fluids.WATER) {
				sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
					.apply(Constants.r2("block/water_still"));
			}
		}

		if (isMissing(sprite)) return;

		int tintColor = FluidStackHooks.getColor(fluidStack);
		FluidState state = fluidStack.getFluid().defaultFluidState();
		VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

		// 1. Adjust bounds for a 2-block tall model
		float MIN_Y = 0.125f; // 2 pixels from the bottom
		float MAX_Y = 1.875f; // 2 blocks (2.0) minus 2 pixels from the top (0.125)
		float MIN_X = 0.1f;
		float MAX_X = 0.9f;
		float MIN_Z = 0.1f;
		float MAX_Z = 0.9f;

		// 2. Calculate actual fluid height
		float capacity = 16000f;
		float fluidHeight = ((float) fluidStack.getAmount() / capacity) * (MAX_Y - MIN_Y);
		float absoluteTop = MIN_Y + fluidHeight;

		// UV coordinates mapping for horizontal planes
		float uMin = spriteU(sprite, MIN_X);
		float uMax = spriteU(sprite, MAX_X);
		float vMinZ = spriteV(sprite, MIN_Z);
		float vMaxZ = spriteV(sprite, MAX_Z);

		// 3. Render Top Face (visible from above)
		addQuad(builder, pPoseStack,
			MIN_X, absoluteTop, MAX_Z, uMin, vMaxZ,
			MAX_X, absoluteTop, MAX_Z, uMax, vMaxZ,
			MAX_X, absoluteTop, MIN_Z, uMax, vMinZ,
			MIN_X, absoluteTop, MIN_Z, uMin, vMinZ,
			pPackedLight, tintColor);

		// 4. Render Bottom Face (visible from below)
		addQuad(builder, pPoseStack,
			MIN_X, MIN_Y, MIN_Z, uMin, vMinZ,
			MAX_X, MIN_Y, MIN_Z, uMax, vMinZ,
			MAX_X, MIN_Y, MAX_Z, uMax, vMaxZ,
			MIN_X, MIN_Y, MAX_Z, uMin, vMaxZ,
			pPackedLight, tintColor);

		// 5. Render Sides in 1-block vertical chunks to prevent texture atlas bleeding
		int bottomBlock = (int) Math.floor(MIN_Y);
		int topBlock = (int) Math.floor(absoluteTop);

		for (int yLevel = bottomBlock; yLevel <= topBlock; yLevel++) {
			float chunkMinY = Math.max(MIN_Y, yLevel);
			float chunkMaxY = Math.min(absoluteTop, yLevel + 1f);

			if (chunkMinY >= chunkMaxY) continue; // Skip empty chunks

			// Get local block coordinates (0.0 to 1.0) for proper UV mapping
			float localMinY = chunkMinY - yLevel;
			float localMaxY = chunkMaxY - yLevel;

			float vTop = spriteV(sprite, 1f - localMaxY);
			float vBottom = spriteV(sprite, 1f - localMinY);

			// North Face (Z=MIN_Z)
			addQuad(builder, pPoseStack,
				MAX_X, chunkMaxY, MIN_Z, uMax, vTop,
				MAX_X, chunkMinY, MIN_Z, uMax, vBottom,
				MIN_X, chunkMinY, MIN_Z, uMin, vBottom,
				MIN_X, chunkMaxY, MIN_Z, uMin, vTop,
				pPackedLight, tintColor);

			// South Face (Z=MAX_Z)
			addQuad(builder, pPoseStack,
				MIN_X, chunkMaxY, MAX_Z, uMin, vTop,
				MIN_X, chunkMinY, MAX_Z, uMin, vBottom,
				MAX_X, chunkMinY, MAX_Z, uMax, vBottom,
				MAX_X, chunkMaxY, MAX_Z, uMax, vTop,
				pPackedLight, tintColor);

			// West Face (X=MIN_X)
			addQuad(builder, pPoseStack,
				MIN_X, chunkMaxY, MIN_Z, uMin, vTop,
				MIN_X, chunkMinY, MIN_Z, uMin, vBottom,
				MIN_X, chunkMinY, MAX_Z, uMax, vBottom,
				MIN_X, chunkMaxY, MAX_Z, uMax, vTop,
				pPackedLight, tintColor);

			// East Face (X=MAX_X)
			addQuad(builder, pPoseStack,
				MAX_X, chunkMaxY, MAX_Z, uMax, vTop,
				MAX_X, chunkMinY, MAX_Z, uMax, vBottom,
				MAX_X, chunkMinY, MIN_Z, uMin, vBottom,
				MAX_X, chunkMaxY, MIN_Z, uMin, vTop,
				pPackedLight, tintColor);
		}
	}
    
    private static boolean isMissing(TextureAtlasSprite sprite) {
        return sprite == null || sprite.atlasLocation().getPath().contains("missingno");
    }

	//? if >1.20.1 {
    /*private static float spriteU(TextureAtlasSprite sprite, float normalizedU) {
        return sprite.getU0() + (sprite.getU1() - sprite.getU0()) * normalizedU;
    }

    private static float spriteV(TextureAtlasSprite sprite, float normalizedV) {
        return sprite.getV0() + (sprite.getV1() - sprite.getV0()) * normalizedV;
    }
	*///?} else {
	private static float spriteU(TextureAtlasSprite sprite, float normalizedU) {
		return sprite.getU(normalizedU * 16);
	}

	private static float spriteV(TextureAtlasSprite sprite, float normalizedV) {
		return sprite.getV(normalizedV * 16);
	}
	//?}

    private static void addQuad(VertexConsumer builder, PoseStack poseStack, 
                                float x0, float y0, float z0, float u0, float v0,
                                float x1, float y1, float z1, float u1, float v1,
                                float x2, float y2, float z2, float u2, float v2,
                                float x3, float y3, float z3, float u3, float v3,
                                int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x2, y2, z2, u2, v2, packedLight, color);
        drawVertex(builder, poseStack, x3, y3, z3, u3, v3, packedLight, color);
    }

	//? if >1.20.1 {
    /*private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        if (a == 0) {
            a = 255;
        }

        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setLight(packedLight)
                .setNormal(1, 0, 0);
    }
	*///?} else {
	private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
		int a = (color >> 24) & 0xFF;
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color) & 0xFF;

		builder.vertex(poseStack.last().pose(), x, y, z)
			.color(r, g, b, a)
			.uv(u, v)
			.uv2(packedLight)
			.normal(poseStack.last().normal(), 1, 0, 0)
			.endVertex();
	}
	//?}
}