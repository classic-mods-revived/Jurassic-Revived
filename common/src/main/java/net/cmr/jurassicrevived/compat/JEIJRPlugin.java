package net.cmr.jurassicrevived.compat;

import dev.architectury.fluid.FluidStack;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.recipe.*;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.screen.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIJRPlugin implements IModPlugin {
    public static final IIngredientType<FluidStack> FLUID_STACK_TYPE = () -> FluidStack.class;

    @Override
    public ResourceLocation getPluginUid() {
        Constants.LOG.info("Registering Jurassic Revived JEI plugin");
        return Constants.rl("jei_plugin");
    }

    public static @org.jetbrains.annotations.Nullable IIngredientManager INGREDIENT_MANAGER;

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        Constants.LOG.info("Jurassic Revived JEI runtime available");
        INGREDIENT_MANAGER = jeiRuntime.getIngredientManager();
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        Constants.LOG.info("Registering Jurassic Revived JEI recipe categories");
        registration.addRecipeCategories(new DNAExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DNAAnalyzerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FossilGrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FossilCleanerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DNAHybridizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EmbryonicMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EmbryoCalcificationMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new IncubatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CultivatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        //? if >1.20.1 {
        /*List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.DNA_EXTRACTOR_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<DNAAnalyzerRecipe> dnaAnalyzerRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.DNA_ANALYZER_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<FossilGrinderRecipe> fossilGrinderRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.FOSSIL_GRINDER_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<FossilCleanerRecipe> fossilCleanerRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.FOSSIL_CLEANER_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<DNAHybridizerRecipe> dnaHybridizerRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.DNA_HYBRIDIZER_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<EmbryonicMachineRecipe> embryonicMachineRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.EMBRYONIC_MACHINE_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<EmbryoCalcificationMachineRecipe> embryoCalcificationMachineRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.EMBRYO_CALCIFICATION_MACHINE_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<IncubatorRecipe> incubatorRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.INCUBATOR_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        List<CultivatorRecipe> cultivatorRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CULTIVATOR_RECIPE_TYPE.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).toList();
        *///?} else {
        List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager.getAllRecipesFor(ModRecipes.DNA_EXTRACTOR_RECIPE_TYPE.get());
        List<DNAAnalyzerRecipe> dnaAnalyzerRecipes = recipeManager.getAllRecipesFor(ModRecipes.DNA_ANALYZER_RECIPE_TYPE.get());
        List<FossilGrinderRecipe> fossilGrinderRecipes = recipeManager.getAllRecipesFor(ModRecipes.FOSSIL_GRINDER_RECIPE_TYPE.get());
        List<FossilCleanerRecipe> fossilCleanerRecipes = recipeManager.getAllRecipesFor(ModRecipes.FOSSIL_CLEANER_RECIPE_TYPE.get());
        List<DNAHybridizerRecipe> dnaHybridizerRecipes = recipeManager.getAllRecipesFor(ModRecipes.DNA_HYBRIDIZER_RECIPE_TYPE.get());
        List<EmbryonicMachineRecipe> embryonicMachineRecipes = recipeManager.getAllRecipesFor(ModRecipes.EMBRYONIC_MACHINE_RECIPE_TYPE.get());
        List<EmbryoCalcificationMachineRecipe> embryoCalcificationMachineRecipes = recipeManager.getAllRecipesFor(ModRecipes.EMBRYO_CALCIFICATION_MACHINE_RECIPE_TYPE.get());
        List<IncubatorRecipe> incubatorRecipes = recipeManager.getAllRecipesFor(ModRecipes.INCUBATOR_RECIPE_TYPE.get());
        List<CultivatorRecipe> cultivatorRecipes = recipeManager.getAllRecipesFor(ModRecipes.CULTIVATOR_RECIPE_TYPE.get());
        //?}

        registration.addRecipes(DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE, dnaExtractorRecipes);
        registration.addRecipes(DNAAnalyzerRecipeCategory.DNA_ANALYZER_RECIPE_RECIPE_TYPE, dnaAnalyzerRecipes);
        registration.addRecipes(FossilGrinderRecipeCategory.FOSSIL_GRINDER_RECIPE_RECIPE_TYPE, fossilGrinderRecipes);
        registration.addRecipes(FossilCleanerRecipeCategory.FOSSIL_CLEANER_RECIPE_RECIPE_TYPE, fossilCleanerRecipes);
        registration.addRecipes(DNAHybridizerRecipeCategory.DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE, dnaHybridizerRecipes);
        registration.addRecipes(EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE, embryonicMachineRecipes);
        registration.addRecipes(EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINE_RECIPE_RECIPE_TYPE, embryoCalcificationMachineRecipes);
        registration.addRecipes(IncubatorRecipeCategory.INCUBATOR_RECIPE_RECIPE_TYPE, incubatorRecipes);
        registration.addRecipes(CultivatorRecipeCategory.CULTIVATOR_RECIPE_RECIPE_TYPE, cultivatorRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DNAExtractorScreen.class, 81, 31, 14, 25,
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(DNAAnalyzerScreen.class, 81, 31, 14, 25,
                DNAAnalyzerRecipeCategory.DNA_ANALYZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilGrinderScreen.class, 80, 25, 18, 36,
                FossilGrinderRecipeCategory.FOSSIL_GRINDER_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilCleanerScreen.class, 74, 35, 29, 16,
                FossilCleanerRecipeCategory.FOSSIL_CLEANER_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(DNAHybridizerScreen.class, 105, 35, 24, 16,
                DNAHybridizerRecipeCategory.DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(EmbryonicMachineScreen.class, 76, 35, 24, 16,
                EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(EmbryoCalcificationMachineScreen.class, 76, 35, 24, 16,
                EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(IncubatorScreen.class, 51, 16, 72, 16,
                IncubatorRecipeCategory.INCUBATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(CultivatorScreen.class, 51, 16, 72, 16,
				CultivatorRecipeCategory.CULTIVATOR_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_EXTRACTOR.get()), DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_ANALYZER.get()), DNAAnalyzerRecipeCategory.DNA_ANALYZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.FOSSIL_GRINDER.get()), FossilGrinderRecipeCategory.FOSSIL_GRINDER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.FOSSIL_CLEANER.get()), FossilCleanerRecipeCategory.FOSSIL_CLEANER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_HYBRIDIZER.get()), DNAHybridizerRecipeCategory.DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMBRYONIC_MACHINE.get()), EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get()), EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.INCUBATOR.get()), IncubatorRecipeCategory.INCUBATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CULTIVATOR.get()), CultivatorRecipeCategory.CULTIVATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_DNA_EXTRACTOR.get()), DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_DNA_ANALYZER.get()), DNAAnalyzerRecipeCategory.DNA_ANALYZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_FOSSIL_GRINDER.get()), FossilGrinderRecipeCategory.FOSSIL_GRINDER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_FOSSIL_CLEANER.get()), FossilCleanerRecipeCategory.FOSSIL_CLEANER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_DNA_HYBRIDIZER.get()), DNAHybridizerRecipeCategory.DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_EMBRYONIC_MACHINE.get()), EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get()), EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINE_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_INCUBATOR.get()), IncubatorRecipeCategory.INCUBATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WHITE_CULTIVATOR.get()), CultivatorRecipeCategory.CULTIVATOR_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                DNAExtractorMenu.class,
                ModMenuTypes.DNA_EXTRACTOR_MENU.get(),
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                DNAAnalyzerMenu.class,
                ModMenuTypes.DNA_ANALYZER_MENU.get(),
                DNAAnalyzerRecipeCategory.DNA_ANALYZER_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                FossilGrinderMenu.class,
                ModMenuTypes.FOSSIL_GRINDER_MENU.get(),
                FossilGrinderRecipeCategory.FOSSIL_GRINDER_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                1,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                FossilCleanerMenu.class,
                ModMenuTypes.FOSSIL_CLEANER_MENU.get(),
                FossilCleanerRecipeCategory.FOSSIL_CLEANER_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                DNAHybridizerMenu.class,
                ModMenuTypes.DNA_HYBRIDIZER_MENU.get(),
                DNAHybridizerRecipeCategory.DNA_HYBRIDIZER_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                9,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                EmbryonicMachineMenu.class,
                ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(),
                EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINE_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                3,  // The NUMBER of recipe input slots (slots 36, 37, 38)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                EmbryoCalcificationMachineMenu.class,
                ModMenuTypes.EMBRYO_CALCIFICATION_MACHINE_MENU.get(),
                EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINE_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                IncubatorMenu.class,
                ModMenuTypes.INCUBATOR_MENU.get(),
                IncubatorRecipeCategory.INCUBATOR_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                3,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
				CultivatorMenu.class,
                ModMenuTypes.CULTIVATOR_MENU.get(),
				CultivatorRecipeCategory.CULTIVATOR_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
    }
}
