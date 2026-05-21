package net.cmr.jurassicrevived.recipe;

//? if >1.20.1 {
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
*///?} else {
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
//?}

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//? if >1.20.1 {
/*public record FossilGrinderRecipe(NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) implements Recipe<FossilGrinderRecipeInput> {
 *///?} else {
public class FossilGrinderRecipe implements Recipe<FossilGrinderRecipeInput> {
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final ResourceLocation id;
	private final Map<ResourceLocation, Integer> weights;
//?}

	//? if <=1.20.1 {
	public FossilGrinderRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) {
		this.id = id;
		this.inputs = inputs;
		this.output = output;
		this.weights = Map.copyOf(weights);
	}
	//?}

	@Override
	public boolean matches(@NotNull FossilGrinderRecipeInput input, Level level) {
		if (level.isClientSide()) return false;
		if (inputs.isEmpty()) return false;
		return inputs.get(0).test(input.getItem(0));
	}

	//? if >1.20.1 {
    /*@Override
    public @NotNull ItemStack assemble(FossilGrinderRecipeInput input, HolderLookup.Provider registries) {
        return getWeightedResult();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    *///?} else {
	@Override
	public ItemStack assemble(FossilGrinderRecipeInput input, RegistryAccess registries) {
		return getWeightedResult();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registries) {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}
	//?}

	private ItemStack getWeightedResult() {
		if (weights.isEmpty()) return output.copy();

		RandomSource random = RandomSource.create();
		long total = 0;
		List<ResourceLocation> keys = new ArrayList<>(weights.keySet());
		int[] weightsArray = new int[keys.size()];

		for (int i = 0; i < keys.size(); i++) {
			int w = Math.max(0, weights.getOrDefault(keys.get(i), 0));
			weightsArray[i] = w;
			total += w;
		}

		if (total <= 0) {
			Item fallback = BuiltInRegistries.ITEM.get(keys.get(random.nextInt(keys.size())));
			return new ItemStack(fallback);
		}

		long roll = 1L + (total <= Integer.MAX_VALUE ? random.nextInt((int) total) : Math.abs(random.nextLong()) % total);
		long cumulative = 0;
		for (int i = 0; i < keys.size(); i++) {
			cumulative += weightsArray[i];
			if (roll <= cumulative) {
				Item chosen = BuiltInRegistries.ITEM.get(keys.get(i));
				return new ItemStack(chosen);
			}
		}
		return new ItemStack(BuiltInRegistries.ITEM.get(keys.get(keys.size() - 1)));
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return ModRecipes.FOSSIL_GRINDER_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return ModRecipes.FOSSIL_GRINDER_RECIPE_TYPE.get();
	}

	//? if <=1.20.1 {
	public Map<ResourceLocation, Integer> weights() { return weights; }
	//?}

	//? if >1.20.1 {
    /*public Map<ResourceLocation, Integer> weights() { return weights; }
    public ItemStack output() { return output; }
    *///?}

	public int getWeightFor(Item item) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
		return weights.getOrDefault(key, 0);
	}

	public static class Serializer implements RecipeSerializer<FossilGrinderRecipe> {
		//? if >1.20.1 {
        /*private static final Codec<Map<ResourceLocation, Integer>> WEIGHTS_CODEC =
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT);

        public static final MapCodec<FossilGrinderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                    if (list.size() != 1) return DataResult.error(() -> "Must have 1 ingredient");
                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, list.toArray(Ingredient[]::new)));
                }, DataResult::success).forGetter(FossilGrinderRecipe::inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(FossilGrinderRecipe::output),
                WEIGHTS_CODEC.optionalFieldOf("weighted_outputs", Map.of()).forGetter(FossilGrinderRecipe::weights)
        ).apply(inst, FossilGrinderRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FossilGrinderRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).encode(buf, recipe.inputs());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                    buf.writeMap(recipe.weights(), ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT);
                },
                buf -> {
                    NonNullList<Ingredient> inputs = ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).decode(buf);
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    Map<ResourceLocation, Integer> weights = buf.readMap(
                                HashMap<ResourceLocation, Integer>::new,
                                ResourceLocation.STREAM_CODEC,
                                ByteBufCodecs.VAR_INT
                        );
                    return new FossilGrinderRecipe(inputs, output, weights);
                }
        );

        @Override public MapCodec<FossilGrinderRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, FossilGrinderRecipe> streamCodec() { return STREAM_CODEC; }
        *///?} else {
		@Override
		public FossilGrinderRecipe fromJson(ResourceLocation id, JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
			inputs.set(0, Ingredient.fromJson(ingredients.get(0)));
			Map<ResourceLocation, Integer> weights = new HashMap<>();
			if (json.has("weighted_outputs")) {
				JsonObject weightsObj = json.getAsJsonObject("weighted_outputs");
				for (String key : weightsObj.keySet()) {
					weights.put(ResourceLocation.tryParse(key), weightsObj.get(key).getAsInt());
				}
			}
			return new FossilGrinderRecipe(id, inputs, output, weights);
		}

		@Override
		public FossilGrinderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
			for (int i = 0; i < size; i++) {
				inputs.set(i, Ingredient.fromNetwork(buf));
			}
			ItemStack output = buf.readItem();
			Map<ResourceLocation, Integer> weights = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readVarInt);
			return new FossilGrinderRecipe(id, inputs, output, weights);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, FossilGrinderRecipe recipe) {
			buf.writeVarInt(recipe.inputs.size());
			for (Ingredient ing : recipe.inputs) {
				ing.toNetwork(buf);
			}
			buf.writeItem(recipe.output);
			buf.writeMap(recipe.weights, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeVarInt);
		}
		//?}
	}
}