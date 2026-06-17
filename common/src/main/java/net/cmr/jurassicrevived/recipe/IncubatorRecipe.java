package net.cmr.jurassicrevived.recipe;

//? if >1.20.1 {
/*import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
*///?} else {
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
//?}

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//? if >1.20.1 {
/*public record IncubatorRecipe(NonNullList<Ingredient> inputs, ItemStack output) implements Recipe<IncubatorRecipeInput> {
 *///?} else {
public class IncubatorRecipe implements Recipe<IncubatorRecipeInput> {
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final ResourceLocation id;
//?}

	//? if <=1.20.1 {
	public IncubatorRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output) {
		this.id = id;
		this.inputs = inputs;
		this.output = output;
	}
	//?}

	@Override
	public boolean matches(@NotNull IncubatorRecipeInput input, Level level) {
		if (level.isClientSide()) return false;
		if (inputs.isEmpty()) return false;
		return inputs.get(0).test(input.getItem(0));
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	//? if >1.20.1 {
    /*@Override
    public @NotNull ItemStack assemble(IncubatorRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    *///?} else {
	@Override
	public ItemStack assemble(IncubatorRecipeInput input, RegistryAccess registries) {
		return output.copy();
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
		return ModRecipes.INCUBATOR_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return ModRecipes.INCUBATOR_RECIPE_TYPE.get();
	}

	//? if >1.20.1 {
	/*public ItemStack output() { return output; }
	 *///?}

	public static class Serializer implements RecipeSerializer<IncubatorRecipe> {
		//? if >1.20.1 {
        /*public static final MapCodec<IncubatorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                    if (list.size() != 1) return DataResult.error(() -> "Must have 1 ingredient");
                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, list.toArray(Ingredient[]::new)));
                }, DataResult::success).forGetter(IncubatorRecipe::inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(IncubatorRecipe::output)
        ).apply(inst, IncubatorRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, IncubatorRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).encode(buf, recipe.inputs());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                },
                buf -> {
                    NonNullList<Ingredient> inputs = ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC).decode(buf);
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    return new IncubatorRecipe(inputs, output);
                }
        );

        @Override public MapCodec<IncubatorRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, IncubatorRecipe> streamCodec() { return STREAM_CODEC; }
        *///?} else {
		@Override
		public IncubatorRecipe fromJson(ResourceLocation id, JsonObject json) {
			JsonArray arr = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
			inputs.set(0, Ingredient.fromJson(arr.get(0)));
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new IncubatorRecipe(id, inputs, output);
		}

		@Override
		public IncubatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
			for (int i = 0; i < size; i++) {
				inputs.set(i, Ingredient.fromNetwork(buf));
			}
			ItemStack output = buf.readItem();
			return new IncubatorRecipe(id, inputs, output);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, IncubatorRecipe recipe) {
			buf.writeVarInt(recipe.inputs.size());
			for (Ingredient ing : recipe.inputs) {
				ing.toNetwork(buf);
			}
			buf.writeItem(recipe.output);
		}
		//?}
	}
}