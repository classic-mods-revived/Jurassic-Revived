package net.cmr.jurassicrevived.recipe;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/*? if >1.20.1 {*/
/*import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.DataResult;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.HolderLookup;
*//*?} else {*/
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.RegistryAccess;
/*?}*/

public record DNAHybridizerRecipe(NonNullList<Ingredient> inputs, ItemStack output) implements Recipe<DNAHybridizerRecipeInput> {

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public boolean matches(DNAHybridizerRecipeInput recipeInput, Level level) {
		if (level.isClientSide) return false;

		// Catalyst matching (Slot 8)
		Ingredient catalystNeed = inputs.size() > 8 ? inputs.get(8) : Ingredient.EMPTY;
		ItemStack catalystIn = recipeInput.getItem(8);
		if (!catalystNeed.test(catalystIn)) return false;

		// Unordered matching for DNA slots (0-7)
		List<Ingredient> dnaNeeds = new ArrayList<>();
		for (int i = 0; i < 8 && i < inputs.size(); i++) {
			if (!inputs.get(i).isEmpty()) dnaNeeds.add(inputs.get(i));
		}

		List<ItemStack> dnaInputs = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			if (!recipeInput.getItem(i).isEmpty()) dnaInputs.add(recipeInput.getItem(i));
		}

		if (dnaNeeds.size() != dnaInputs.size()) return false;

		boolean[] used = new boolean[dnaNeeds.size()];
		for (ItemStack stack : dnaInputs) {
			boolean matched = false;
			for (int i = 0; i < dnaNeeds.size(); i++) {
				if (!used[i] && dnaNeeds.get(i).test(stack)) {
					used[i] = true;
					matched = true;
					break;
				}
			}
			if (!matched) return false;
		}
		return true;
	}

	/*? if >1.20.1 {*/
	/*@Override public ItemStack assemble(DNAHybridizerRecipeInput input, HolderLookup.Provider p) { return output.copy(); }
	@Override public ItemStack getResultItem(HolderLookup.Provider p) { return output.copy(); }
	*//*?} else {*/
	@Override public ItemStack assemble(DNAHybridizerRecipeInput input, RegistryAccess a) { return output.copy(); }
	@Override public ItemStack getResultItem(RegistryAccess a) { return output.copy(); }
	@Override public ResourceLocation getId() { return Constants.rl("dna_hybridizing"); }
	/*?}*/

	@Override public boolean canCraftInDimensions(int w, int h) { return true; }
	@Override public RecipeSerializer<?> getSerializer() { return ModRecipes.DNA_HYBRIDIZER_SERIALIZER.get(); }
	@Override public RecipeType<?> getType() { return ModRecipes.DNA_HYBRIDIZER_RECIPE_TYPE.get(); }


	public static class Serializer implements RecipeSerializer<DNAHybridizerRecipe> {
		/*? if >1.20.1 {*/
		/*public static final MapCodec<DNAHybridizerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
			Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(
				l -> l.isEmpty() || l.size() > 9 ? DataResult.error(() -> "Needs 1-9 ingredients") : DataResult.success(NonNullList.of(Ingredient.EMPTY, l.toArray(new Ingredient[0]))),
				l -> DataResult.success(List.copyOf(l))
			).forGetter(DNAHybridizerRecipe::inputs),
			ItemStack.CODEC.fieldOf("result").forGetter(DNAHybridizerRecipe::output)
		).apply(inst, DNAHybridizerRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, DNAHybridizerRecipe> STREAM_CODEC = StreamCodec.of(
			(buf, r) -> {
				buf.writeVarInt(r.inputs().size());
				for(Ingredient i : r.inputs()) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, i);
				ItemStack.STREAM_CODEC.encode(buf, r.output());
			},
			buf -> {
				int size = buf.readVarInt();
				NonNullList<Ingredient> ins = NonNullList.create();
				for(int i=0; i<size; i++) ins.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
				return new DNAHybridizerRecipe(ins, ItemStack.STREAM_CODEC.decode(buf));
			}
		);
		@Override public MapCodec<DNAHybridizerRecipe> codec() { return CODEC; }
		@Override public StreamCodec<RegistryFriendlyByteBuf, DNAHybridizerRecipe> streamCodec() { return STREAM_CODEC; }
		*//*?} else {*/
		@Override public DNAHybridizerRecipe fromJson(ResourceLocation id, JsonObject json) {
			JsonArray ings = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);
			for (int i = 0; i < Math.min(ings.size(), 8); i++) inputs.set(i, Ingredient.fromJson(ings.get(i)));
			if (json.has("catalyst")) inputs.set(8, Ingredient.fromJson(json.get("catalyst")));
			return new DNAHybridizerRecipe(inputs, ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result")));
		}
		@Override public DNAHybridizerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(9, Ingredient.EMPTY);
			for (int i = 0; i < 9; i++) inputs.set(i, Ingredient.fromNetwork(buf));
			return new DNAHybridizerRecipe(inputs, buf.readItem());
		}
		@Override public void toNetwork(FriendlyByteBuf buf, DNAHybridizerRecipe r) {
			for (int i = 0; i < 9; i++) (i < r.inputs().size() ? r.inputs().get(i) : Ingredient.EMPTY).toNetwork(buf);
			buf.writeItem(r.output());
		}
		/*?}*/
	}
}