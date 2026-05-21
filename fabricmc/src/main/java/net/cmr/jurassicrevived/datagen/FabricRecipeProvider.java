package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.datagen.custom.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

//? if <=1.20.1 {
import net.minecraft.data.recipes.FinishedRecipe;
//?}

public class FabricRecipeProvider extends net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider implements ModRecipeProvider.RecipeHelper {

    //? if >1.20.1 {
    /*public FabricRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private RecipeOutput output;

    @Override
    public void buildRecipes(RecipeOutput output) {
        this.output = output;
        ModRecipeProvider.registerRecipes(this);
        this.output = null;
    }
    *///?} else {
    public FabricRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private Consumer<FinishedRecipe> output;

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        this.output = output;
        ModRecipeProvider.registerRecipes(this);
        this.output = null;
    }
    //?}

    @Override
    public void buildShaped(RecipeCategory category, ItemLike result, int count, String[] patterns, Object... keys) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(category, result, count);
        for (String pattern : patterns) {
            builder.pattern(pattern);
        }
        for (int i = 0; i < keys.length; i += 2) {
            Character key = (Character) keys[i];
            Object ingredient = keys[i + 1];
            if (ingredient instanceof ItemLike) {
                builder.define(key, (ItemLike) ingredient);
            } else if (ingredient instanceof TagKey) {
                builder.define(key, (TagKey<Item>) ingredient);
            } else if (ingredient instanceof Ingredient) {
                builder.define(key, (Ingredient) ingredient);
            }
        }
        builder.unlockedBy("has_item", has(result));
        builder.save(output);
    }

    @Override
    public void buildShapeless(RecipeCategory category, ItemLike result, int count, String name, ItemLike... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(category, result, count);
        for (ItemLike ingredient : ingredients) {
            builder.requires(ingredient);
        }
        builder.unlockedBy("has_item", has(result));
        //? if >1.20.1 {
        /*builder.save(output, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        *///?} else {
        builder.save(output, new ResourceLocation(Constants.MOD_ID, name));
        //?}
    }

    @Override
    public void buildSmelting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        //? if >1.20.1 {
        /*oreCooking(output, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
        *///?} else {
        oreCooking120(output, RecipeSerializer.SMELTING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
        //?}
    }

    @Override
    public void buildBlasting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        //? if >1.20.1 {
        /*oreCooking(output, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
        *///?} else {
        oreCooking120(output, RecipeSerializer.BLASTING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
        //?}
    }

    @Override
    public void buildSmelting(TagKey<Item> tag, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        //? if >1.20.1 {
        /*SimpleCookingRecipeBuilder.generic(Ingredient.of(tag), category, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .group(group).unlockedBy("has_" + group, has(tag))
                .save(output, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, getItemName(result) + "_from_smelting_" + group));
        *///?} else {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(tag), category, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE)
                .group(group).unlockedBy("has_" + group, has(tag))
                .save(output, new ResourceLocation(Constants.MOD_ID, getItemName(result) + "_from_smelting_" + group));
        //?}
    }

    @Override
    public void buildBlasting(TagKey<Item> tag, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        //? if >1.20.1 {
        /*SimpleCookingRecipeBuilder.generic(Ingredient.of(tag), category, result, experience, cookingTime, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new)
                .group(group).unlockedBy("has_" + group, has(tag))
                .save(output, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, getItemName(result) + "_from_blasting_" + group));
        *///?} else {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(tag), category, result, experience, cookingTime, RecipeSerializer.BLASTING_RECIPE)
                .group(group).unlockedBy("has_" + group, has(tag))
                .save(output, new ResourceLocation(Constants.MOD_ID, getItemName(result) + "_from_blasting_" + group));
        //?}
    }

    //? if >1.20.1 {
	/*public static <T extends AbstractCookingRecipe> void oreCooking(
		RecipeOutput pRecipeOutput,
		RecipeSerializer<T> pCookingSerializer,
		AbstractCookingRecipe.Factory<T> factory,
		List<ItemLike> pIngredients,
		RecipeCategory pCategory,
		ItemLike pResult,
		float pExperience,
		int pCookingTime,
		String pGroup,
		String pRecipeName
	) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)));
        }
    }
    *///?} else {
    protected static <T extends AbstractCookingRecipe> void oreCooking120(Consumer<FinishedRecipe> pRecipeOutput, RecipeSerializer<T> pCookingSerializer,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, new ResourceLocation(Constants.MOD_ID, getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)));
        }
    }
    //?}

    @Override
    public void dnaExtracting(ItemLike testTube, ItemLike tissue, ItemLike dna, int count) {
        //? if >1.20.1 {
        /*new DNAExtractingRecipeBuilder(testTube, tissue, dna, count)
                .unlockedBy("has_tissue", has(tissue))
                .save(output);
        *///?} else {
        new DNAExtractingRecipeBuilder(testTube, tissue, dna, count)
                .unlockedBy("has_tissue", has(tissue))
                .save(output);
        //?}
    }

    @Override
    public void dnaAnalyzing(ItemLike testTube, ItemLike material, ItemLike dna, int count) {
        //? if >1.20.1 {
        /*new DNAAnalyzingRecipeBuilder(testTube, material, dna, count)
                .unlockedBy("has_material", has(material))
                .save(output);
        *///?} else {
        new DNAAnalyzingRecipeBuilder(testTube, material, dna, count)
                .unlockedBy("has_material", has(material))
                .save(output);
        //?}
    }

	@Override
	public void dnaHybridizing(ItemLike result, int count, ItemLike... ingredients) {
		//? if >1.20.1 {
		/*DNAHybridizingRecipeBuilder builder = new DNAHybridizingRecipeBuilder(result, count);
		for (ItemLike ingredient : ingredients) {
			builder.addIngredient(ingredient);
		}
		builder.unlockedBy("has_dna", has(ingredients[0]))
			.save(output);
		*///?} else {
        DNAHybridizingRecipeBuilder builder = new DNAHybridizingRecipeBuilder(result, count);
        for (ItemLike ingredient : ingredients) {
            builder.addIngredient(ingredient);
        }
        builder.unlockedBy("has_dna", has(ingredients[0]))
                .save(output);
        //?}
	}

    @Override
    public void embryonicMachine(ItemLike syringe, ItemLike dna, ItemLike catalyst, ItemLike result, int count) {
        //? if >1.20.1 {
        /*new EmbryonicMachineRecipeBuilder(syringe, dna, catalyst, result, count)
                .unlockedBy("has_dna", has(dna))
                .save(output);
        *///?} else {
        new EmbryonicMachineRecipeBuilder(syringe, dna, catalyst, result, count)
                .unlockedBy("has_dna", has(dna))
                .save(output);
        //?}
    }

    @Override
    public void embryoCalcification(ItemLike syringe, ItemLike egg, ItemLike result, int count) {
        //? if >1.20.1 {
        /*new EmbryoCalcificationMachiningRecipeBuilder(syringe, egg, result, count)
                .unlockedBy("has_egg", has(egg))
                .save(output);
        *///?} else {
        new EmbryoCalcificationMachiningRecipeBuilder(syringe, egg, result, count)
                .unlockedBy("has_egg", has(egg))
                .save(output);
        //?}
    }

    @Override
    public void incubating(ItemLike egg, ItemLike result, int count) {
        //? if >1.20.1 {
        /*new IncubatingRecipeBuilder(egg, result, count)
                .unlockedBy("has_egg", has(egg))
                .save(output);
        *///?} else {
        new IncubatingRecipeBuilder(egg, result, count)
                .unlockedBy("has_egg", has(egg))
                .save(output);
        //?}
    }

    @Override
    public void fossilGrinding(ItemLike fossil, ItemLike tissue, int count) {
        //? if >1.20.1 {
        /*FossilGrindingRecipeBuilder.fossilWeighted(fossil, tissue, count)
                .unlockedBy("has_fossil", has(fossil))
                .saveFossil(output);
        *///?} else {
        FossilGrindingRecipeBuilder.fossilWeighted(fossil, tissue, count)
                .unlockedBy("has_fossil", has(fossil))
                .saveFossil(output);
        //?}
    }

    @Override
    public void skullToTissue(ItemLike skull, ItemLike tissue, int count) {
        //? if >1.20.1 {
        /*FossilGrindingRecipeBuilder.skullToTissue(skull, tissue, count)
                .unlockedBy("has_skull", has(skull))
                .saveSkull(output);
        *///?} else {
        FossilGrindingRecipeBuilder.skullToTissue(skull, tissue, count)
                .unlockedBy("has_skull", has(skull))
                .saveSkull(output);
        //?}
    }

    @Override
    public void fossilCleaning(ItemLike fossilBlock, ItemLike result, int count) {
        //? if >1.20.1 {
        /*FossilCleaningRecipeBuilder.randomFossil(fossilBlock, result, count)
                .unlockedBy("has_fossil_block", has(fossilBlock))
                .save(output);
        *///?} else {
        FossilCleaningRecipeBuilder.randomFossil(fossilBlock, result, count)
                .unlockedBy("has_fossil_block", has(fossilBlock))
                .save(output);
        //?}
    }

    @Override
    public void amberRandomDNA(ItemLike testTube, ItemLike amber, ItemLike defaultDna, int count) {
        //? if >1.20.1 {
        /*DNAExtractingRecipeBuilder.amberRandomDNAUniform(testTube, amber, defaultDna, count)
                .unlockedBy("has_amber", has(amber))
                .save(output);
        *///?} else {
        DNAExtractingRecipeBuilder.amberRandomDNAUniform(testTube, amber, defaultDna, count)
                .unlockedBy("has_amber", has(amber))
                .save(output);
        //?}
    }
}
