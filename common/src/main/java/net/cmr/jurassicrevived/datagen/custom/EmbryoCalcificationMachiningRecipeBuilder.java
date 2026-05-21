package net.cmr.jurassicrevived.datagen.custom;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.recipe.EmbryoCalcificationMachineRecipe;
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

public class EmbryoCalcificationMachiningRecipeBuilder {
    private java.util.Optional<ItemLike> firstItem = java.util.Optional.empty();
    private java.util.Optional<ItemLike> secondItem = java.util.Optional.empty();
    private java.util.Optional<Item> resultItem = java.util.Optional.empty();
    private final int count;
    //? if >1.20.1 {
    /*private final Map<String, Criterion<?>> criteria;
    *///?} else {
    private final Map<String, InventoryChangeTrigger.TriggerInstance> criteria;
    //?}

    public EmbryoCalcificationMachiningRecipeBuilder(ItemLike ingredient, ItemLike secondIngredient, ItemLike result, int count) {
        this.firstItem = java.util.Optional.of(ingredient);
        this.secondItem = java.util.Optional.of(secondIngredient);
        this.resultItem = java.util.Optional.of(result.asItem());
        this.count = count;
        this.criteria = new LinkedHashMap();
    }

    //? if >1.20.1 {
    /*public void save(RecipeOutput output) {
        ResourceLocation resultKey = BuiltInRegistries.ITEM.getKey(this.resultItem.orElseThrow());
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                resultKey.getNamespace(),
                resultKey.getPath() + "_from_embryo_calcification_machining"
        );
        save(output, id);
    }

    public void save(RecipeOutput output, ResourceLocation recipeId) {
        NonNullList<Ingredient> inputs = NonNullList.create();
        inputs.add(Ingredient.of(firstItem.orElseThrow()));
        inputs.add(Ingredient.of(secondItem.orElseThrow()));
        ItemStack result = new ItemStack(resultItem.orElseThrow(), this.count);

        EmbryoCalcificationMachineRecipe recipe = new EmbryoCalcificationMachineRecipe(Constants.rl("embryo_calcification_machine"), inputs, result);

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
                resultKey.getPath() + "_from_embryo_calcification_machining"
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
                Ingredient.of(firstItem.orElseThrow()),
                Ingredient.of(secondItem.orElseThrow()),
                this.resultItem.orElseThrow(),
                this.count,
                advancementBuilder,
                new ResourceLocation(recipeId.getNamespace(), "recipes/" + recipeId.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient first;
        private final Ingredient second;
        private final Item result;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient first, Ingredient second, Item result, int count, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.first = first;
            this.second = second;
            this.result = result;
            this.count = count;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredients = new JsonArray();
            ingredients.add(first.toJson());
            ingredients.add(second.toJson());
            json.add("ingredients", ingredients);

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
            return ModRecipes.EMBRYO_CALCIFICATION_MACHINE_SERIALIZER.get();
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
    /*public EmbryoCalcificationMachiningRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
    *///?} else {
    public EmbryoCalcificationMachiningRecipeBuilder unlockedBy(String name, InventoryChangeTrigger.TriggerInstance criterion) {
    //?}
        this.criteria.put(name, criterion);
        return this;
    }
}
