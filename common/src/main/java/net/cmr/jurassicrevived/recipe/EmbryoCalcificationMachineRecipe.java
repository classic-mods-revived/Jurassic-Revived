package net.cmr.jurassicrevived.recipe;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/*? if >1.20.1 {*/
/*import com.mojang.serialization.MapCodec;
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

public record EmbryoCalcificationMachineRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output)
	implements Recipe<EmbryoCalcificationMachineRecipeInput> {

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public boolean matches(EmbryoCalcificationMachineRecipeInput recipeInput, Level level) {
		if (level.isClientSide) return false;
		if (recipeInput.size() < 2 || inputs.size() < 2) return false;

		ItemStack in0 = recipeInput.getItem(0);
		ItemStack in1 = recipeInput.getItem(1);
		Ingredient a = inputs.get(0);
		Ingredient b = inputs.get(1);

		// Symmetric match: (A+B) or (B+A)
		return (a.test(in0) && b.test(in1)) || (a.test(in1) && b.test(in0));
	}

	/*? if >1.20.1 {*/
	/*@Override public ItemStack assemble(EmbryoCalcificationMachineRecipeInput input, HolderLookup.Provider p) { return output.copy(); }
	@Override public ItemStack getResultItem(HolderLookup.Provider p) { return output.copy(); }
	*//*?} else {*/
	@Override public ItemStack assemble(EmbryoCalcificationMachineRecipeInput input, RegistryAccess a) { return output.copy(); }
	@Override public ItemStack getResultItem(RegistryAccess a) { return output.copy(); }
	@Override public ResourceLocation getId() { return id; }
	/*?}*/

	@Override public boolean canCraftInDimensions(int width, int height) { return true; }
	@Override public RecipeSerializer<?> getSerializer() { return ModRecipes.EMBRYO_CALCIFICATION_MACHINE_SERIALIZER.get(); }
	@Override public RecipeType<?> getType() { return ModRecipes.EMBRYO_CALCIFICATION_MACHINE_RECIPE_TYPE.get(); }

	public static class Serializer implements RecipeSerializer<EmbryoCalcificationMachineRecipe> {
		/*? if >1.20.1 {*/
		/*public static final MapCodec<EmbryoCalcificationMachineRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(
				list -> list.size() != 2 ? DataResult.error(() -> "Requires exactly 2 ingredients") : DataResult.success(NonNullList.of(Ingredient.EMPTY, list.get(0), list.get(1))),
				list -> DataResult.success(List.copyOf(list))
			).forGetter(EmbryoCalcificationMachineRecipe::inputs),
			ItemStack.CODEC.fieldOf("result").forGetter(EmbryoCalcificationMachineRecipe::output)
		).apply(instance, EmbryoCalcificationMachineRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, EmbryoCalcificationMachineRecipe> STREAM_CODEC = StreamCodec.of(
			(buf, recipe) -> {
				buf.writeVarInt(recipe.inputs().size());
				for(Ingredient i : recipe.inputs()) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, i);
				ItemStack.STREAM_CODEC.encode(buf, recipe.output());
			},
			buf -> {
				int size = buf.readVarInt();
				NonNullList<Ingredient> ins = NonNullList.create();
				for(int i=0; i<size; i++) ins.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
				return new EmbryoCalcificationMachineRecipe(ins, ItemStack.STREAM_CODEC.decode(buf));
			}
		);
		@Override public MapCodec<EmbryoCalcificationMachineRecipe> codec() { return CODEC; }
		@Override public StreamCodec<RegistryFriendlyByteBuf, EmbryoCalcificationMachineRecipe> streamCodec() { return STREAM_CODEC; }
		*//*?} else {*/
		@Override public EmbryoCalcificationMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
			for (int i = 0; i < 2; i++) inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new EmbryoCalcificationMachineRecipe(id, inputs, output);
		}
		@Override public EmbryoCalcificationMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) inputs.set(i, Ingredient.fromNetwork(buf));
			return new EmbryoCalcificationMachineRecipe(id, inputs, buf.readItem());
		}
		@Override public void toNetwork(FriendlyByteBuf buf, EmbryoCalcificationMachineRecipe recipe) {
			buf.writeInt(recipe.inputs().size());
			for (Ingredient ing : recipe.inputs()) ing.toNetwork(buf);
			buf.writeItem(recipe.output());
		}
		/*?}*/
	}
}