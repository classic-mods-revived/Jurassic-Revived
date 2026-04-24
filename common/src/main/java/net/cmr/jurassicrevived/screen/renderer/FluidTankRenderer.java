package net.cmr.jurassicrevived.screen.renderer;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.cmr.jurassicrevived.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

// CREDIT: https://github.com/mezz/JustEnoughItems by mezz
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.19/LICENSE.txt
public class FluidTankRenderer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FluidTankRenderer.class);
	private static final NumberFormat nf = NumberFormat.getIntegerInstance();
	private static final int TEXTURE_SIZE = 16;
	private static final int MIN_FLUID_HEIGHT = 1;

	private final long capacity;
	private final TooltipMode tooltipMode;
	private final int width;
	private final int height;

	enum TooltipMode {
		SHOW_AMOUNT,
		SHOW_AMOUNT_AND_CAPACITY,
		ITEM_LIST
	}

	public FluidTankRenderer(long capacity, boolean showCapacity, int width, int height) {
		this(capacity, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
	}

	private FluidTankRenderer(long capacity, TooltipMode tooltipMode, int width, int height) {
		Preconditions.checkArgument(capacity > 0, "capacity must be > 0");
		Preconditions.checkArgument(width > 0, "width must be > 0");
		Preconditions.checkArgument(height > 0, "height must be > 0");

		this.capacity = capacity;
		this.tooltipMode = tooltipMode;
		this.width = width;
		this.height = height;
	}

	public void render(GuiGraphics guiGraphics, int x, int y, FluidStack fluidStack) {
		RenderSystem.enableBlend();
		guiGraphics.pose().pushPose();
		{
			guiGraphics.pose().translate(x, y, 0);
			drawFluid(guiGraphics, width, height, fluidStack);
		}
		guiGraphics.pose().popPose();
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.disableBlend();
	}

	private void drawFluid(GuiGraphics guiGraphics, final int width, final int height, FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		if (fluid.isSame(Fluids.EMPTY)) {
			return;
		}

		TextureAtlasSprite fluidStillSprite = getStillFluidSprite(fluidStack);
		if (fluidStillSprite == null) return;
		
		int fluidColor = (int) FluidStackHooks.getColor(fluidStack);

		long amount = fluidStack.getAmount();
		long scaledAmount = (amount * height) / capacity;

		if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
			scaledAmount = MIN_FLUID_HEIGHT;
		}
		if (scaledAmount > height) {
			scaledAmount = height;
		}

		drawTiledSprite(guiGraphics, width, height, fluidColor, (int) scaledAmount, fluidStillSprite);
	}

	private TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
		TextureAtlasSprite sprite = FluidStackHooks.getStillTexture(fluidStack);
		if (isMissing(sprite)) {
			sprite = FluidStackHooks.getStillTexture(fluidStack.getFluid());
		}
		if (isMissing(sprite)) {
			// Fallback for water
			if (fluidStack.getFluid() == Fluids.WATER) {
				sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
					.apply(Constants.r2("block/water_still"));
			}
		}
		
		return sprite;
	}
	
	private static boolean isMissing(TextureAtlasSprite sprite) {
		return sprite == null || sprite.atlasLocation().getPath().contains("missingno");
	}

	private static void drawTiledSprite(GuiGraphics guiGraphics, final int tiledWidth, final int tiledHeight, int color, int scaledAmount, TextureAtlasSprite sprite) {
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		Matrix4f matrix = guiGraphics.pose().last().pose();
		setGLColorFromInt(color);

		final int xTileCount = tiledWidth / TEXTURE_SIZE;
		final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
		final int yTileCount = scaledAmount / TEXTURE_SIZE;
		final int yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

		final int yStart = tiledHeight;

		for (int xTile = 0; xTile <= xTileCount; xTile++) {
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
				int height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
				int x = (xTile * TEXTURE_SIZE);
				int y = yStart - ((yTile + 1) * TEXTURE_SIZE);
				if (width > 0 && height > 0) {
					int maskTop = TEXTURE_SIZE - height;
					int maskRight = TEXTURE_SIZE - width;

					drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight, 100);
				}
			}
		}
	}

	private static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;

		RenderSystem.setShaderColor(red, green, blue, alpha);
	}

	private static void drawTextureWithMasking(Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight, float zLevel) {
		float uMin = textureSprite.getU0();
		float uMax = textureSprite.getU1();
		float vMin = textureSprite.getV0();
		float vMax = textureSprite.getV1();
		uMax = uMax - (maskRight / 16F * (uMax - uMin));
		vMax = vMax - (maskTop / 16F * (vMax - vMin));

		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		Tesselator tessellator = Tesselator.getInstance();
		//? if >1.20.1 {
		/*BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.addVertex(matrix, xCoord, yCoord + 16, zLevel).setUv(uMin, vMax);
		bufferBuilder.addVertex(matrix, xCoord + 16 - maskRight, yCoord + 16, zLevel).setUv(uMax, vMax);
		bufferBuilder.addVertex(matrix, xCoord + 16 - maskRight, yCoord + maskTop, zLevel).setUv(uMax, vMin);
		bufferBuilder.addVertex(matrix, xCoord, yCoord + maskTop, zLevel).setUv(uMin, vMin);
		BufferUploader.drawWithShader(bufferBuilder.build());
		*///?} else {
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(matrix, xCoord, yCoord + 16, zLevel).uv(uMin, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + 16, zLevel).uv(uMax, vMax).endVertex();
		bufferBuilder.vertex(matrix, xCoord + 16 - maskRight, yCoord + maskTop, zLevel).uv(uMax, vMin).endVertex();
		bufferBuilder.vertex(matrix, xCoord, yCoord + maskTop, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
		//?}
	}

	public List<Component> getTooltip(FluidStack fluidStack, TooltipFlag tooltipFlag) {
		List<Component> tooltip = new ArrayList<>();

		Fluid fluidType = fluidStack.getFluid();
		try {
			if (fluidType.isSame(Fluids.EMPTY)) {
				return tooltip;
			}

			Component displayName = fluidStack.getName();
			tooltip.add(displayName);

			long amount = fluidStack.getAmount();

			if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
				MutableComponent amountString = Component.translatable("jurassicrevived.tooltip.liquid.amount.with.capacity", nf.format(amount), nf.format(capacity));
				tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
			} else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
				MutableComponent amountString = Component.translatable("jurassicrevived.tooltip.liquid.amount", nf.format(amount));
				tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
			}
		} catch (RuntimeException e) {
			LOGGER.error("Failed to get tooltip for fluid: " + e);
		}

		return tooltip;
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
}