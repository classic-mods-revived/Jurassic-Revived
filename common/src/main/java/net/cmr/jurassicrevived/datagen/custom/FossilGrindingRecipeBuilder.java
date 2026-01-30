package net.cmr.jurassicrevived.datagen.custom;

import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipe;
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
import net.minecraft.world.item.Items;
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

public class FossilGrindingRecipeBuilder {
    private java.util.Optional<ItemLike> inputItem = java.util.Optional.empty();
    private java.util.Optional<Item> resultItem = java.util.Optional.empty();
    private final int count;
    //? if >1.20.1 {
    /*private final Map<String, Criterion<?>> criteria;
    *///?} else {
    private final Map<String, InventoryChangeTrigger.TriggerInstance> criteria;
    //?}
    private final java.util.Map<ResourceLocation, Integer> weights = new java.util.HashMap<>();

    public FossilGrindingRecipeBuilder(ItemLike input, ItemLike result, int count) {
        this.inputItem = java.util.Optional.of(input);
        this.resultItem = java.util.Optional.of(result.asItem());
        this.count = count;
        this.criteria = new LinkedHashMap();
    }

    public static FossilGrindingRecipeBuilder fossilWeighted(ItemLike fossil, ItemLike tissueResult, int count) {
        FossilGrindingRecipeBuilder b = new FossilGrindingRecipeBuilder(fossil, tissueResult, count);
        b.addWeightedOutput(Items.BONE_MEAL, 40)
         .addWeightedOutput(ModItems.CRUSHED_FOSSIL.get(), 40)
         .addWeightedOutput(tissueResult, 20);
        return b;
    }

    public static FossilGrindingRecipeBuilder skullToTissue(ItemLike skull, ItemLike tissueResult, int count) {
        return new FossilGrindingRecipeBuilder(skull, tissueResult, count);
    }

    public FossilGrindingRecipeBuilder addWeightedOutput(ItemLike item, int weight) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.asItem());
        if (id != null) {
            weights.put(id, Math.max(0, weight));
        }
        return this;
    }

    //? if >1.20.1 {
    /*public void saveFossil(RecipeOutput output) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_fossil_" + "grinding"
        );
        save(output, id);
    }

    public void saveSkull(RecipeOutput output) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_skull_" + "grinding"
        );
        save(output, id);
    }

    public void save(RecipeOutput output, ResourceLocation recipeId) {
        NonNullList<Ingredient> inputs = NonNullList.create();
        inputs.add(Ingredient.of(inputItem.orElseThrow()));
        ItemStack result = new ItemStack(resultItem.orElseThrow(), this.count);
        FossilGrinderRecipe recipe = new FossilGrinderRecipe(inputs, result, java.util.Map.copyOf(this.weights));

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
    public void saveFossil(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = new ResourceLocation(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_fossil_" + "grinding"
        );
        save(consumer, id);
    }

    public void saveSkull(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = new ResourceLocation(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_skull_" + "grinding"
        );
        save(consumer, id);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
        advancementBuilder.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(RequirementsStrategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        consumer.accept(new Result(
                recipeId,
                Ingredient.of(inputItem.orElseThrow()),
                this.resultItem.orElseThrow(),
                this.count,
                this.weights,
                advancementBuilder,
                new ResourceLocation(recipeId.getNamespace(), "recipes/" + recipeId.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item result;
        private final int count;
        private final Map<ResourceLocation, Integer> weights;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient input, Item result, int count, Map<ResourceLocation, Integer> weights, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.input = input;
            this.result = result;
            this.count = count;
            this.weights = weights;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredients = new JsonArray();
            ingredients.add(input.toJson());
            json.add("ingredients", ingredients);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
            if (count > 1) {
                resultObj.addProperty("count", count);
            }
            json.add("result", resultObj);

            if (!weights.isEmpty()) {
                JsonObject weightsObj = new JsonObject();
                weights.forEach((k, v) -> weightsObj.addProperty(k.toString(), v));
                json.add("weights", weightsObj);
            }
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.FOSSIL_GRINDER_SERIALIZER.get();
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
    /*public FossilGrindingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
    *///?} else {
    public FossilGrindingRecipeBuilder unlockedBy(String name, InventoryChangeTrigger.TriggerInstance criterion) {
    //?}
        this.criteria.put(name, criterion);
        return this;
    }
}
