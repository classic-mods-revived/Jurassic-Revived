package net.cmr.jurassicrevived.recipe;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
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

public record DNAAnalyzerRecipe(
	NonNullList<Ingredient> inputs,
	ItemStack output,
	java.util.Map<ResourceLocation, Integer> weights
) implements Recipe<DNAAnalyzerRecipeInput> {

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public boolean matches(DNAAnalyzerRecipeInput input, Level level) {
		if (level.isClientSide) return false;
		ItemStack in0 = input.getItem(0);
		ItemStack in1 = input.getItem(1);
		return (inputs.get(0).test(in0) && inputs.get(1).test(in1)) ||
			   (inputs.get(0).test(in1) && inputs.get(1).test(in0));
	}

	/*? if >1.20.1 {*/
	/*@Override
	public ItemStack assemble(DNAAnalyzerRecipeInput input, HolderLookup.Provider provider) {
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return output.copy();
	}
	*//*?} else {*/
	@Override
	public ItemStack assemble(DNAAnalyzerRecipeInput input, RegistryAccess access) {
		return output.copy();
	}

	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return output.copy();
	}

	@Override
	public ResourceLocation getId() {
		// In 1.20.1, the ID was stored. In 1.21.1, it's handled by the registry.
		// If you need the ID specifically in 1.20.1, you'd need to add it to the record.
		return Constants.rl("dna_analyzing");
	}
	/*?}*/

	@Override
	public boolean canCraftInDimensions(int width, int height) { return true; }

	@Override
	public RecipeSerializer<?> getSerializer() { return ModRecipes.DNA_ANALYZER_SERIALIZER.get(); }

	@Override
	public RecipeType<?> getType() { return ModRecipes.DNA_ANALYZER_RECIPE_TYPE.get(); }

	public int getWeightFor(Item item) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
		return weights.getOrDefault(key, 1);
	}

	public static class Serializer implements RecipeSerializer<DNAAnalyzerRecipe> {
		/*? if >1.20.1 {*/
		/*public static final MapCodec<DNAAnalyzerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(
				l -> l.size() == 2 ? DataResult.success(NonNullList.of(Ingredient.EMPTY, l.get(0), l.get(1))) : DataResult.error(() -> "Needs 2 ingredients"),
				l -> DataResult.success(List.copyOf(l))
			).forGetter(DNAAnalyzerRecipe::inputs),
			ItemStack.CODEC.fieldOf("result").forGetter(DNAAnalyzerRecipe::output),
			Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT).optionalFieldOf("weights", java.util.Map.of()).forGetter(DNAAnalyzerRecipe::weights)
		).apply(inst, DNAAnalyzerRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, DNAAnalyzerRecipe> STREAM_CODEC = StreamCodec.of(
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
				return new DNAAnalyzerRecipe(ins, ItemStack.STREAM_CODEC.decode(buf), buf.readMap(m -> new java.util.HashMap<>(), ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_INT));
			}
		);

		@Override public MapCodec<DNAAnalyzerRecipe> codec() { return CODEC; }
		@Override public StreamCodec<RegistryFriendlyByteBuf, DNAAnalyzerRecipe> streamCodec() { return STREAM_CODEC; }
		*//*?} else {*/
		@Override
		public DNAAnalyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
			for (int i = 0; i < 2; i++) inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new DNAAnalyzerRecipe(inputs, output, java.util.Map.of()); // Weights logic can be added here
		}

		@Override
		public DNAAnalyzerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) inputs.set(i, Ingredient.fromNetwork(buf));
			return new DNAAnalyzerRecipe(inputs, buf.readItem(), java.util.Map.of());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, DNAAnalyzerRecipe recipe) {
			buf.writeInt(recipe.inputs().size());
			for (Ingredient ing : recipe.inputs()) ing.toNetwork(buf);
			buf.writeItem(recipe.output());
		}
		/*?}*/
	}
}