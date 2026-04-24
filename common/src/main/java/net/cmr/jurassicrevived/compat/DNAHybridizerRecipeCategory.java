package net.cmr.jurassicrevived.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DNAHybridizerRecipeCategory implements IRecipeCategory<DNAHybridizerRecipe> {
    public static final ResourceLocation UID = Constants.rl("dna_hybridizing");
    public static final ResourceLocation TEXTURE = Constants.rl("textures/gui/dna_hybridizer/dna_hybridizer_gui.png");
    public static final ResourceLocation ARROW_TEXTURE = Constants.rl("textures/gui/generic/arrow.png");
    public static final ResourceLocation WHITE_ARROW_TEXTURE = Constants.rl("textures/gui/generic/white_arrow.png");
    private static final ResourceLocation POWER_BAR_TEXTURE = Constants.rl("textures/gui/generic/power_bar.png");

    public static final RecipeType<DNAHybridizerRecipe> DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, DNAHybridizerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DNAHybridizerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_HYBRIDIZER.get()));
    }

    @Override
    public RecipeType<DNAHybridizerRecipe> getRecipeType() {
        return DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.dna_hybridizer");
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public void draw(DNAHybridizerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        guiGraphics.blit(ARROW_TEXTURE,  105, 35, 0, 0, 24, 16, 24, 16);
        if (JRConfigManager.get().requirePower) {
            guiGraphics.blit(POWER_BAR_TEXTURE,  159, 10, 0, 0, 10, 66, 10, 66);
            // Fill amount for JEI: show total required energy (2000 FE) relative to 64000 FE capacity
            // Our simple fill is purely visual for JEI, not tied to any BE
            int barX = 160;
            int barY = 11;
            int barW = 8;
            int barH = 64;

            int maxTicks = 200;
            long now = System.currentTimeMillis();
            int progress = (int)((now / 50L) % maxTicks); // ~20 TPS
            int arrowPixels = 24;
            int progFilled = progress * arrowPixels / maxTicks;
            if (progFilled > 0) {
                guiGraphics.blit(WHITE_ARROW_TEXTURE, 105, 35, 0, 0, progFilled, 16, 24, 16);
            }

            int requiredFE = 30000;
            int capacityFE = 64000;
            int filled = (int)(barH * (requiredFE / (float)capacityFE));
            // Render red fill similar to EnergyDisplayTooltipArea
            guiGraphics.fillGradient(barX, barY + (barH - filled), barX + barW, barY + barH, 0xffb51500, 0xff600b00);

            // Tooltip "2000 / 64000 FE" on hover over the energy area
            int mx = (int) mouseX;
            int my = (int) mouseY;
            if (mx >= barX && mx < barX + barW && my >= barY && my < barY + barH) {
                List<Component> tips = List.of(Component.literal("30000 / 64000 FE"));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tips, java.util.Optional.empty(), mx, my);
            }
        }
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DNAHybridizerRecipe recipe, IFocusGroup focuses) {

        int[][] coords = {
                {8, 25}, {26, 25}, {44, 25},
                {62, 25}, {8, 43}, {26, 43},
                {44, 43}, {62, 43}, {83, 35}
        };

        int count = Math.min(9, recipe.getIngredients().size());
        for (int i = 0; i < Math.min(8, count); i++) {
            var ing = recipe.getIngredients().get(i);
            if (ing.isEmpty()) continue;
            builder.addSlot(RecipeIngredientRole.INPUT, coords[i][0], coords[i][1]).addIngredients(ing);
        }

        // Catalyst slot: if present in recipe (index 8), show that; otherwise show nothing
        if (count >= 9 && !recipe.getIngredients().get(8).isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, coords[8][0], coords[8][1])
                       .addIngredients(recipe.getIngredients().get(8));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 35).addItemStack(recipe.getResultItem(null));
    }
}
