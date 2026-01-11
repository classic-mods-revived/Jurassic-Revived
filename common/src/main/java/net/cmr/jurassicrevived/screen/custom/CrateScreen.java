package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrateScreen extends AbstractContainerScreen<CrateMenu> {
    private static final ResourceLocation WOOD_CRATE =
            Constants.rl("textures/gui/crate/wood_crate_gui.png");
    private static final ResourceLocation IRON_CRATE =
            Constants.rl("textures/gui/crate/iron_crate_gui.png");

    public CrateScreen(CrateMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics g, float partial, int mouseX, int mouseY) {
        var tex = menu.blockEntity.getSize() == 9 ? WOOD_CRATE : IRON_CRATE;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        g.blit(tex, x, y, 0, 0, imageWidth, imageHeight, 176, 166);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
		//? if >1.20.1 {
		/*this.renderBackground(g, mouseX, mouseY, delta);
		 *///?} else {
		renderBackground(g);
		//?}
        super.render(g, mouseX, mouseY, delta);
        renderTooltip(g, mouseX, mouseY);
    }
}
