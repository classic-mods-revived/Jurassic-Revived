package net.cmr.jurassicrevived.datagen.custom;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >1.20.1 {
/*import net.minecraft.data.recipes.RecipeOutput;
*///?}
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashMap;
import java.util.Map;

//? if <=1.20.1 {
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
//?}

public class DNAHybridizingRecipeBuilder {
    private java.util.Optional<Item> resultItem = java.util.Optional.empty();
    private final int count;
    //? if >1.20.1 {
    /*private final Map<String, Criterion<?>> criteria;
    *///?} else {
    private final Map<String, InventoryChangeTrigger.TriggerInstance> criteria;
    //?}
    private final NonNullList<Ingredient> ingredients = NonNullList.create();

    public DNAHybridizingRecipeBuilder(ItemLike result, int count) {
        this.resultItem = java.util.Optional.of(result.asItem());
        this.count = count;
        this.criteria = new LinkedHashMap();
    }

    public static DNAHybridizingRecipeBuilder result(ItemLike result, int count) {
        return new DNAHybridizingRecipeBuilder(result, count);
    }

    public DNAHybridizingRecipeBuilder addIngredient(ItemLike item) {
        if (this.ingredients.size() >= 8) {
            throw new IllegalStateException("DNAHybridizer supports at most 8 input ingredients");
        }
        this.ingredients.add(Ingredient.of(item));
        return this;
    }

    public DNAHybridizingRecipeBuilder addIngredient(Ingredient ingredient) {
        if (this.ingredients.size() >= 8) {
            throw new IllegalStateException("DNAHybridizer supports at most 8 input ingredients");
        }
        this.ingredients.add(ingredient);
        return this;
    }

    //? if >1.20.1 {
    /*public void save(RecipeOutput output) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_dna_hybridizing"
        );
        save(output, id);
    }

    public void save(RecipeOutput output, ResourceLocation recipeId) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("DNAHybridizingRecipeBuilder requires at least 1 ingredient");
        }
        NonNullList<Ingredient> inputs = NonNullList.create();
        inputs.addAll(this.ingredients);

        ItemStack result = new ItemStack(resultItem.orElseThrow(), this.count);

        DNAHybridizerRecipe recipe = new DNAHybridizerRecipe(Constants.rl("dna_hybridizer"), inputs, result);

        AdvancementHolder advancementHolder = null;
        if (!this.criteria.isEmpty()) {
            Advancement.Builder builder = output.advancement();
            for (Map.Entry<String, Criterion<?>> e : this.criteria.entrySet()) {
                builder.addCriterion(e.getKey(), e.getValue());
            }
            builder.rewards(AdvancementRewards.Builder.recipe(recipeId));
            builder.requirements(AdvancementRequirements.Strategy.OR);
            advancementHolder = builder.build(recipeId.withPrefix("recipes/"));
        }

        output.accept(recipeId, recipe, advancementHolder);
    }
    *///?} else {
    public void save(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = new ResourceLocation(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_dna_hybridizing"
        );
        save(consumer, id);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("DNAHybridizingRecipeBuilder requires at least 1 ingredient");
        }
        NonNullList<Ingredient> inputs = NonNullList.create();
        inputs.addAll(this.ingredients);

        Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
        advancementBuilder.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(RequirementsStrategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        consumer.accept(new Result(
                recipeId,
                inputs,
                this.resultItem.orElseThrow(),
                this.count,
                advancementBuilder,
                new ResourceLocation(recipeId.getNamespace(), "recipes/" + recipeId.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final NonNullList<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, NonNullList<Ingredient> ingredients, Item result, int count, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.ingredients = ingredients;
            this.result = result;
            this.count = count;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredientsJson = new JsonArray();
            for (Ingredient ingredient : ingredients) {
                ingredientsJson.add(ingredient.toJson());
            }
            json.add("ingredients", ingredientsJson);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
            if (count > 1) {
                resultObj.addProperty("count", count);
            }
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.DNA_HYBRIDIZER_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
    //?}

    //? if >1.20.1 {
    /*public DNAHybridizingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
    *///?} else {
    public DNAHybridizingRecipeBuilder unlockedBy(String name, InventoryChangeTrigger.TriggerInstance criterion) {
    //?}
        this.criteria.put(name, criterion);
        return this;
    }
}
