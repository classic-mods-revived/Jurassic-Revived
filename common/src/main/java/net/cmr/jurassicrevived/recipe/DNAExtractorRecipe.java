package net.cmr.jurassicrevived.recipe;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

/*? if >1.20.1 {*/
/*import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.DataResult;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.HolderLookup;
import java.util.List;
*//*?} else {*/
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.RegistryAccess;
/*?}*/

public record DNAExtractorRecipe(
	ResourceLocation id,
	NonNullList<Ingredient> inputs,
	ItemStack output,
	java.util.Map<ResourceLocation, Integer> weights
) implements Recipe<DNAExtractorRecipeInput> {

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public boolean matches(DNAExtractorRecipeInput input, Level level) {
		if (level.isClientSide) return false;
		ItemStack in0 = input.getItem(0);
		ItemStack in1 = input.getItem(1);
		return (inputs.get(0).test(in0) && inputs.get(1).test(in1)) ||
			   (inputs.get(0).test(in1) && inputs.get(1).test(in0));
	}

	private ItemStack handleAmberExtraction(DNAExtractorRecipeInput input) {
		if (!input.getItem(1).isEmpty() && input.getItem(1).is(ModItems.MOSQUITO_IN_AMBER.get())) {
			ItemStack[] candidates = Ingredient.of(ModTags.Items.DNA).getItems();
			if (candidates.length > 0) {
				return new ItemStack(pickWeightedRandomDNA(candidates, RandomSource.create()));
			}
		}
		return output.copy();
	}

	private net.minecraft.world.item.Item pickWeightedRandomDNA(ItemStack[] candidates, RandomSource random) {
		long total = 0;
		int[] itemWeights = new int[candidates.length];
		for (int i = 0; i < candidates.length; i++) {
			ResourceLocation key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(candidates[i].getItem());
			int w = Math.max(0, weights.getOrDefault(key, 1));
			itemWeights[i] = w;
			total += w;
		}
		if (total <= 0) return candidates[random.nextInt(candidates.length)].getItem();
		long roll = 1L + (total <= Integer.MAX_VALUE ? random.nextInt((int) total) : Math.abs(random.nextLong()) % total);
		long cumulative = 0;
		for (int i = 0; i < candidates.length; i++) {
			cumulative += itemWeights[i];
			if (roll <= cumulative) return candidates[i].getItem();
		}
		return candidates[candidates.length - 1].getItem();
	}

	/*? if >1.20.1 {*/
	/*@Override public ItemStack assemble(DNAExtractorRecipeInput input, HolderLookup.Provider p) { return handleAmberExtraction(input); }
	@Override public ItemStack getResultItem(HolderLookup.Provider p) { return output.copy(); }
	*//*?} else {*/
	@Override public ItemStack assemble(DNAExtractorRecipeInput input, RegistryAccess a) { return handleAmberExtraction(input); }
	@Override public ItemStack getResultItem(RegistryAccess a) { return output.copy(); }
	@Override public ResourceLocation getId() { return id; }
	/*?}*/

	@Override public boolean canCraftInDimensions(int w, int h) { return true; }
	@Override public RecipeSerializer<?> getSerializer() { return ModRecipes.DNA_EXTRACTOR_SERIALIZER.get(); }
	@Override public RecipeType<?> getType() { return ModRecipes.DNA_EXTRACTOR_RECIPE_TYPE.get(); }

	public int getWeightFor(Item item) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
		return weights.getOrDefault(key, 1);
	}

	public static class Serializer implements RecipeSerializer<DNAExtractorRecipe> {
		/*? if >1.20.1 {*/
		/*public static final MapCodec<DNAExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(
				l -> l.size() == 2 ? DataResult.success(NonNullList.of(Ingredient.EMPTY, l.get(0), l.get(1))) : DataResult.error(() -> "Needs 2 ingredients"),
				l -> DataResult.success(List.copyOf(l))
			).forGetter(DNAExtractorRecipe::inputs),
			ItemStack.CODEC.fieldOf("result").forGetter(DNAExtractorRecipe::output),
			Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT).optionalFieldOf("weights", java.util.Map.of()).forGetter(DNAExtractorRecipe::weights)
		).apply(inst, DNAExtractorRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, DNAExtractorRecipe> STREAM_CODEC = StreamCodec.of(
			(buf, r) -> {
				buf.writeVarInt(r.inputs().size());
				for(Ingredient i : r.inputs()) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, i);
				ItemStack.STREAM_CODEC.encode(buf, r.output());
				buf.writeMap(r.weights(), ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT);
			},
			buf -> {
				int size = buf.readVarInt();
				NonNullList<Ingredient> ins = NonNullList.create();
				for(int i=0; i<size; i++) ins.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
				return new DNAExtractorRecipe(ins, ItemStack.STREAM_CODEC.decode(buf), buf.readMap(m -> new java.util.HashMap<>(), ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT));
			}
		);
		@Override public MapCodec<DNAExtractorRecipe> codec() { return CODEC; }
		@Override public StreamCodec<RegistryFriendlyByteBuf, DNAExtractorRecipe> streamCodec() { return STREAM_CODEC; }
		*//*?} else {*/
		@Override public DNAExtractorRecipe fromJson(ResourceLocation id, JsonObject json) {
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
			for (int i = 0; i < 2; i++) inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			return new DNAExtractorRecipe(id, inputs, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result")), java.util.Map.of());
		}
		@Override public DNAExtractorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) inputs.set(i, Ingredient.fromNetwork(buf));
			return new DNAExtractorRecipe(id, inputs, buf.readItem(), java.util.Map.of());
		}
		@Override public void toNetwork(FriendlyByteBuf buf, DNAExtractorRecipe recipe) {
			buf.writeInt(recipe.inputs().size());
			for (Ingredient ing : recipe.inputs()) ing.toNetwork(buf);
			buf.writeItem(recipe.output());
		}
		/*?}*/
	}
}