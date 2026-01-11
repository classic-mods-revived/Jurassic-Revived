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

import net.cmr.jurassicrevived.util.ModTags;
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

//? if >1.20.1 {
/*public record FossilCleanerRecipe(NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) implements Recipe<FossilCleanerRecipeInput> {
 *///?} else {
public class FossilCleanerRecipe implements Recipe<FossilCleanerRecipeInput> {
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final ResourceLocation id;
	private final Map<ResourceLocation, Integer> weights;
//?}

	//? if <=1.20.1 {
	public FossilCleanerRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) {
		this.id = id;
		this.inputs = inputs;
		this.output = output;
		this.weights = Map.copyOf(weights);
	}
	//?}

	@Override
	public boolean matches(@NotNull FossilCleanerRecipeInput input, Level level) {
		if (level.isClientSide()) return false;
		if (inputs.isEmpty()) return false;

		// Single fossil block ingredient lives in machine slot 1 (input.getItem(1))
		return inputs.get(0).test(input.getItem(1));
	}

	//? if >1.20.1 {
    /*@Override
    public @NotNull ItemStack assemble(FossilCleanerRecipeInput input, HolderLookup.Provider registries) {
        return getWeightedResult(registries);
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    *///?} else {
	@Override
	public ItemStack assemble(FossilCleanerRecipeInput input, RegistryAccess registries) {
		return getWeightedResult(registries);
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

	private ItemStack getWeightedResult(Object registries) {
		//? if >1.20.1 {
        /*var fossilTag = BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.FOSSILS);
        List<ItemStack> candidates = fossilTag.stream().map(ItemStack::new).toList();
        *///?} else {
		ItemStack[] candidatesArr = Ingredient.of(ModTags.Items.FOSSILS).getItems();
		List<ItemStack> candidates = List.of(candidatesArr);
		//?}

		if (candidates.isEmpty()) return output.copy();

		RandomSource random = RandomSource.create();
		long total = 0;
		int[] weightsArray = new int[candidates.size()];
		for (int i = 0; i < candidates.size(); i++) {
			Item item = candidates.get(i).getItem();
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
			int w = Math.max(0, weights.getOrDefault(key, 1));
			weightsArray[i] = w;
			total += w;
		}

		if (total <= 0) return candidates.get(random.nextInt(candidates.size())).copy();

		long roll = 1L + (total <= Integer.MAX_VALUE ? random.nextInt((int) total) : Math.abs(random.nextLong()) % total);
		long cumulative = 0;
		for (int i = 0; i < candidates.size(); i++) {
			cumulative += weightsArray[i];
			if (roll <= cumulative) return candidates.get(i).copy();
		}
		return candidates.get(candidates.size() - 1).copy();
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
		return ModRecipes.FOSSIL_CLEANER_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return ModRecipes.FOSSIL_CLEANER_RECIPE_TYPE.get();
	}

	public int getWeightFor(Item item) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
		return weights.getOrDefault(key, 1);
	}

	//? if <=1.20.1 {
	public ItemStack output() { return output; }
	//?}

	//? if >1.20.1 {
    /*public Map<ResourceLocation, Integer> weights() { return weights; }
    public ItemStack output() { return output; }
    *///?}

	public static class Serializer implements RecipeSerializer<FossilCleanerRecipe> {
		//? if >1.20.1 {
        /*private static final Codec<Map<ResourceLocation, Integer>> WEIGHTS_CODEC =
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT);

        public static final MapCodec<FossilCleanerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                    if (list.size() != 1) return DataResult.error(() -> "Must have 1 ingredient");
                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, list.toArray(Ingredient[]::new)));
                }, DataResult::success).forGetter(FossilCleanerRecipe::inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(FossilCleanerRecipe::output),
                WEIGHTS_CODEC.optionalFieldOf("fossil_weights", Map.of()).forGetter(FossilCleanerRecipe::weights)
        ).apply(inst, FossilCleanerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FossilCleanerRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).encode(buf, recipe.inputs());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                    buf.writeMap(recipe.weights(), ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT);
                },
                buf -> {
                    NonNullList<Ingredient> inputs = ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).decode(buf);
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    Map<ResourceLocation, Integer> weights = buf.readMap(ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT);
                    return new FossilCleanerRecipe(inputs, output, weights);
                }
        );

        @Override public MapCodec<FossilCleanerRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, FossilCleanerRecipe> streamCodec() { return STREAM_CODEC; }
        *///?} else {
		@Override
		public FossilCleanerRecipe fromJson(ResourceLocation id, JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
			inputs.set(0, Ingredient.fromJson(ingredients.get(0)));
			Map<ResourceLocation, Integer> weights = new HashMap<>();
			if (json.has("fossil_weights")) {
				JsonObject weightsObj = json.getAsJsonObject("fossil_weights");
				for (String key : weightsObj.keySet()) {
					weights.put(ResourceLocation.tryParse(key), weightsObj.get(key).getAsInt());
				}
			}
			return new FossilCleanerRecipe(id, inputs, output, weights);
		}

		@Override
		public FossilCleanerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
			for (int i = 0; i < size; i++) {
				inputs.set(i, Ingredient.fromNetwork(buf));
			}
			ItemStack output = buf.readItem();
			Map<ResourceLocation, Integer> weights = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readVarInt);
			return new FossilCleanerRecipe(id, inputs, output, weights);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, FossilCleanerRecipe recipe) {
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