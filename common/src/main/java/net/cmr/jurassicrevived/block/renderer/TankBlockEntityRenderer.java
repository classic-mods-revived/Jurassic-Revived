package net.cmr.jurassicrevived.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

// Credits to TurtyWurty
// Under MIT-License: https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod?tab=MIT-1-ov-file#readme
public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {
    public TankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TankBlockEntity pBlockEntity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int packedOverlay) {
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
                     .apply(new ResourceLocation("block/water_still"));
             }
        }
        
        if (isMissing(sprite)) return;
        
        int tintColor = FluidStackHooks.getColor(fluidStack);
        FluidState state = fluidStack.getFluid().defaultFluidState();
        VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        // Adjust Y bounds to match tank model (2 pixels from bottom, 2 pixels from top)
        float MIN_Y = 0.125f; // 2/16
        float MAX_Y = 0.875f; // 14/16
        float MIN_X = 0.1f;
        float MAX_X = 0.9f;
        float MIN_Z = 0.1f;
        float MAX_Z = 0.9f;

        float fluidHeight = ((float) fluidStack.getAmount() / 64000f) * (MAX_Y - MIN_Y);
        float yTop = MIN_Y + fluidHeight;

        // UV coordinates mapping
        float uMin = sprite.getU(MIN_X * 16);
        float uMax = sprite.getU(MAX_X * 16);
        float vMinZ = sprite.getV(MIN_Z * 16);
        float vMaxZ = sprite.getV(MAX_Z * 16);
        
        // V coords for sides
        float vTop = sprite.getV((1 - yTop) * 16);
        float vBottom = sprite.getV((1 - MIN_Y) * 16);

        // Top Face (visible from above)
        // Normal +Y
        // CCW: (MIN_X, MAX_Z) -> (MAX_X, MAX_Z) -> (MAX_X, MIN_Z) -> (MIN_X, MIN_Z)
        addQuad(builder, pPoseStack, 
            MIN_X, yTop, MAX_Z, uMin, vMaxZ,
            MAX_X, yTop, MAX_Z, uMax, vMaxZ,
            MAX_X, yTop, MIN_Z, uMax, vMinZ,
            MIN_X, yTop, MIN_Z, uMin, vMinZ,
            pPackedLight, tintColor);

        // Bottom Face (visible from below)
        // Normal -Y
        // CCW looking from below: (MIN_X, MIN_Z) -> (MAX_X, MIN_Z) -> (MAX_X, MAX_Z) -> (MIN_X, MAX_Z)
        addQuad(builder, pPoseStack, 
            MIN_X, MIN_Y, MIN_Z, uMin, vMinZ,
            MAX_X, MIN_Y, MIN_Z, uMax, vMinZ,
            MAX_X, MIN_Y, MAX_Z, uMax, vMaxZ,
            MIN_X, MIN_Y, MAX_Z, uMin, vMaxZ,
            pPackedLight, tintColor);

        // North Face (Z=MIN_Z) - Visible from North (-Z)
        // Normal -Z
        // Vertices: (MAX_X, yTop) -> (MAX_X, MIN_Y) -> (MIN_X, MIN_Y) -> (MIN_X, yTop)
        addQuad(builder, pPoseStack, 
            MAX_X, yTop, MIN_Z, uMax, vTop,
            MAX_X, MIN_Y, MIN_Z, uMax, vBottom,
            MIN_X, MIN_Y, MIN_Z, uMin, vBottom,
            MIN_X, yTop, MIN_Z, uMin, vTop,
            pPackedLight, tintColor);

        // South Face (Z=MAX_Z) - Visible from South (+Z)
        // Normal +Z
        // Vertices: (MIN_X, yTop) -> (MIN_X, MIN_Y) -> (MAX_X, MIN_Y) -> (MAX_X, yTop)
        addQuad(builder, pPoseStack, 
            MIN_X, yTop, MAX_Z, uMin, vTop,
            MIN_X, MIN_Y, MAX_Z, uMin, vBottom,
            MAX_X, MIN_Y, MAX_Z, uMax, vBottom,
            MAX_X, yTop, MAX_Z, uMax, vTop,
            pPackedLight, tintColor);

        // West Face (X=MIN_X) - Visible from West (-X)
        // Normal -X
        // Vertices: (MIN_X, yTop, MIN_Z) -> (MIN_X, MIN_Y, MIN_Z) -> (MIN_X, MIN_Y, MAX_Z) -> (MIN_X, yTop, MAX_Z)
        addQuad(builder, pPoseStack, 
            MIN_X, yTop, MIN_Z, uMin, vTop,
            MIN_X, MIN_Y, MIN_Z, uMin, vBottom,
            MIN_X, MIN_Y, MAX_Z, uMax, vBottom,
            MIN_X, yTop, MAX_Z, uMax, vTop,
            pPackedLight, tintColor);

        // East Face (X=MAX_X) - Visible from East (+X)
        // Normal +X
        // Vertices: (MAX_X, yTop, MAX_Z) -> (MAX_X, MIN_Y, MAX_Z) -> (MAX_X, MIN_Y, MIN_Z) -> (MAX_X, yTop, MIN_Z)
        addQuad(builder, pPoseStack, 
            MAX_X, yTop, MAX_Z, uMax, vTop,
            MAX_X, MIN_Y, MAX_Z, uMax, vBottom,
            MAX_X, MIN_Y, MIN_Z, uMin, vBottom,
            MAX_X, yTop, MIN_Z, uMin, vTop,
            pPackedLight, tintColor);
    }
    
    private static boolean isMissing(TextureAtlasSprite sprite) {
        return sprite == null || sprite.atlasLocation().getPath().contains("missingno");
    }

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
        builder.addVertex(poseStack.last().pose(), x, y, z)
                .setColor(color)
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