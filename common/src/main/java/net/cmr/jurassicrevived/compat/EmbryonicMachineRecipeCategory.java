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
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.EmbryonicMachineRecipe;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmbryonicMachineRecipeCategory implements IRecipeCategory<EmbryonicMachineRecipe> {
    public static final ResourceLocation UID = Constants.rl("embryonic_machining");
    public static final ResourceLocation TEXTURE = Constants.rl("textures/gui/embryonic_machine/embryonic_machine_gui.png");
    private static final ResourceLocation SYRINGE_BAR_REVERSED_TEXTURE = Constants.rl("textures/gui/generic/syringe_bar_reversed.png");
    private static final ResourceLocation WHITE_SYRINGE_BAR_REVERSED_TEXTURE = Constants.rl("textures/gui/generic/white_syringe_bar_reversed.png");
    private static final ResourceLocation POWER_BAR_TEXTURE = Constants.rl("textures/gui/generic/power_bar.png");

    public static final RecipeType<EmbryonicMachineRecipe> EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, EmbryonicMachineRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public EmbryonicMachineRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EMBRYONIC_MACHINE.get()));
    }

    @Override
    public RecipeType<EmbryonicMachineRecipe> getRecipeType() {
        return EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.embryonic_machine");
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
    public void draw(EmbryonicMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        guiGraphics.blit(SYRINGE_BAR_REVERSED_TEXTURE,  76, 35, 0, 0, 24, 16, 24, 16);
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
                guiGraphics.blit(WHITE_SYRINGE_BAR_REVERSED_TEXTURE, 76, 35, 0, 0, progFilled, 16, 24, 16);
            }

            int requiredFE = 2000;
            int capacityFE = 64000;
            int filled = (int)(barH * (requiredFE / (float)capacityFE));
            // Render red fill similar to EnergyDisplayTooltipArea
            guiGraphics.fillGradient(barX, barY + (barH - filled), barX + barW, barY + barH, 0xffb51500, 0xff600b00);

            // Tooltip "2000 / 64000 FE" on hover over the energy area
            int mx = (int) mouseX;
            int my = (int) mouseY;
            if (mx >= barX && mx < barX + barW && my >= barY && my < barY + barH) {
                List<Component> tips = List.of(Component.literal("2000 / 64000 FE"));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tips, java.util.Optional.empty(), mx, my);
            }
        }
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EmbryonicMachineRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 39, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 35).addIngredients(recipe.getIngredients().get(1));
        // Frog DNA third input at (48,53)
        if (recipe.getIngredients().size() >= 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 48, 53).addIngredients(recipe.getIngredients().get(2));
        }

        ItemStack amber = new ItemStack(ModItems.MOSQUITO_IN_AMBER.get());
        boolean isMosquitoRecipe = recipe.getIngredients().size() > 1 && recipe.getIngredients().get(1).test(amber);

        if (isMosquitoRecipe) {
            var level = Minecraft.getInstance().level;
            if (level != null) {
                var itemRegistry = level.registryAccess().registryOrThrow(Registries.ITEM);
                var dnaTagOpt = itemRegistry.getTag(ModTags.Items.DNA);
                List<ItemStack> dnaOutputs = dnaTagOpt.map(holderSet ->
                        holderSet.stream()
                                .map(h -> new ItemStack(h.value(), Math.max(1, recipe.getResultItem(null).getCount())))
                                .collect(java.util.stream.Collectors.toList())
                ).orElse(List.of());

                var slot = builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 35).addItemStacks(dnaOutputs);
                slot.addRichTooltipCallback((view, tooltip) -> {
                    var opt = view.getDisplayedItemStack();
                    if (opt.isPresent()) {
                        int weight = recipe.getWeightFor(opt.get().getItem());
                        tooltip.add(Component.literal("Weight: " + weight));
                    }
                });
                return;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 35).addItemStack(recipe.getResultItem(null));
    }
}
