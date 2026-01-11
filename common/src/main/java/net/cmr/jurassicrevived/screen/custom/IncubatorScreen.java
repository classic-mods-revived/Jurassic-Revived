package net.cmr.jurassicrevived.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.screen.renderer.EnergyDisplayTooltipArea;
import net.cmr.jurassicrevived.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class IncubatorScreen extends AbstractContainerScreen<IncubatorMenu> {
	private static final ResourceLocation GUI_TEXTURE =
		Constants.rl("textures/gui/incubator/incubator_gui.png");
	private static final ResourceLocation LANTERN_TEXTURE =
		Constants.rl("textures/gui/generic/lantern.png");
	private static final ResourceLocation POWER_BAR_TEXTURE =
		Constants.rl("textures/gui/generic/power_bar.png");
	private static final ResourceLocation EGG_TEXTURE =
		Constants.rl("textures/gui/generic/egg.png");
	private EnergyDisplayTooltipArea energyInfoArea;

	public IncubatorScreen(IncubatorMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void init() {
		super.init();

		this.inventoryLabelY = 10000;
		this.titleLabelY = 10000;

		assignEnergyInfoArea();
	}

	private void renderEnergyAreaTooltip(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
		if(isMouseAboveArea(pMouseX, pMouseY, x, y, 160, 11, 8, 64) && JRConfigManager.get().requirePower) {
			guiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(),
				Optional.empty(), pMouseX - x, pMouseY - y);
		}
	}

	private void assignEnergyInfoArea() {
		energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 160,
			((height - imageHeight) / 2) + 11, menu.blockEntity.getEnergyStorage(null));
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		if (JRConfigManager.get().requirePower) {
			renderEnergyAreaTooltip(guiGraphics, pMouseX, pMouseY, x, y);
		}
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) /2;

		guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 176, 166);

		guiGraphics.blit(EGG_TEXTURE,  x + 50, y + 35, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(EGG_TEXTURE,  x + 80, y + 35, 0, 0, 16, 16, 16, 16);
		guiGraphics.blit(EGG_TEXTURE,  x + 110, y + 35, 0, 0, 16, 16, 16, 16);


		if (JRConfigManager.get().requirePower) {
			guiGraphics.blit(POWER_BAR_TEXTURE, x+159, y+10, 0, 0, 10, 66, 10, 66);

		}
	}

	private void renderProgressArrowForSlot(GuiGraphics g, int x, int y, int slotIdx, int slotPixelX) {
		if (this.menu.isCrafting(slotIdx)) {
			int l = Mth.ceil(this.menu.getScaledArrowProgress(slotIdx) * 13.0F) + 1;
			g.blit(LANTERN_TEXTURE, x + slotPixelX, y +16 , 0, 0, 16, l, 16, 16);
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		//? if >1.20.1 {
		/*this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		 *///?} else {
		renderBackground(guiGraphics);
		//?}
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseY);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) /2;

		renderProgressArrowForSlot(guiGraphics, x, y, 0, 50);
		renderProgressArrowForSlot(guiGraphics, x, y, 1, 80);
		renderProgressArrowForSlot(guiGraphics, x, y, 2, 110);

		if (JRConfigManager.get().requirePower) {
			energyInfoArea.render(guiGraphics);
		}
	}

	public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
		return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
	}
}