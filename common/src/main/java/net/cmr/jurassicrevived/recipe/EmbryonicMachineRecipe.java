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
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
//?}

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

//? if >1.20.1 {
/*public record EmbryonicMachineRecipe(NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) implements Recipe<EmbryonicMachineRecipeInput> {
 *///?} else {
public class EmbryonicMachineRecipe implements Recipe<EmbryonicMachineRecipeInput> {
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final ResourceLocation id;
	private final Map<ResourceLocation, Integer> weights;
//?}

	//? if <=1.20.1 {
	public EmbryonicMachineRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, Map<ResourceLocation, Integer> weights) {
		this.id = id;
		this.inputs = inputs;
		this.output = output;
		this.weights = Map.copyOf(weights);
	}
	//?}

	@Override
	public boolean matches(@NotNull EmbryonicMachineRecipeInput input, Level level) {
		if (level.isClientSide()) return false;

		//? if >1.20.1 {
        /*ItemStack in0 = input.getItem(0);
        ItemStack in1 = input.getItem(1);
        ItemStack in2 = input.getItem(2);
        *///?} else {
		ItemStack in0 = input.getItem(0);
		ItemStack in1 = input.getItem(1);
		ItemStack in2 = input.getItem(2);
		//?}

		boolean testTubeOk = inputs.get(0).test(in0);
		boolean materialOk = inputs.get(1).test(in1);
		boolean frogOk = inputs.size() > 2 && inputs.get(2).test(in2);

		return testTubeOk && materialOk && frogOk;
	}

	//? if >1.20.1 {
    /*@Override
    public @NotNull ItemStack assemble(EmbryonicMachineRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }
    *///?} else {
	@Override
	public ItemStack assemble(EmbryonicMachineRecipeInput input, RegistryAccess registries) {
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
		return ModRecipes.EMBRYONIC_MACHINE_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return ModRecipes.EMBRYONIC_MACHINE_RECIPE_TYPE.get();
	}

	public int getWeightFor(Item item) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
		return weights.getOrDefault(key, 1);
	}

	//? if >1.20.1 {
    /*public Map<ResourceLocation, Integer> weights() { return weights; }
    public ItemStack output() { return output; }
    *///?}

	public static class Serializer implements RecipeSerializer<EmbryonicMachineRecipe> {
		//? if >1.20.1 {
        /*private static final Codec<Map<ResourceLocation, Integer>> WEIGHTS_CODEC =
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT);

        public static final MapCodec<EmbryonicMachineRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                    if (list.size() != 3) return DataResult.error(() -> "Must have 3 ingredients");
                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, list.toArray(Ingredient[]::new)));
                }, DataResult::success).forGetter(EmbryonicMachineRecipe::inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(EmbryonicMachineRecipe::output),
                WEIGHTS_CODEC.optionalFieldOf("weights", Map.of()).forGetter(EmbryonicMachineRecipe::weights)
        ).apply(inst, EmbryonicMachineRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, EmbryonicMachineRecipe> STREAM_CODEC = StreamCodec.of(
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
                    return new EmbryonicMachineRecipe(inputs, output, weights);
                }
        );

        @Override public MapCodec<EmbryonicMachineRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, EmbryonicMachineRecipe> streamCodec() { return STREAM_CODEC; }
        *///?} else {
		@Override
		public EmbryonicMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
			NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);
			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
			}
			Map<ResourceLocation, Integer> weights = new HashMap<>();
			if (json.has("weights")) {
				JsonObject weightsObj = json.getAsJsonObject("weights");
				for (String key : weightsObj.keySet()) {
					weights.put(ResourceLocation.tryParse(key), weightsObj.get(key).getAsInt());
				}
			}
			return new EmbryonicMachineRecipe(id, inputs, output, weights);
		}

		@Override
		public EmbryonicMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			int size = buf.readVarInt();
			NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
			for (int i = 0; i < size; i++) {
				inputs.set(i, Ingredient.fromNetwork(buf));
			}
			ItemStack output = buf.readItem();
			Map<ResourceLocation, Integer> weights = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readVarInt);
			return new EmbryonicMachineRecipe(id, inputs, output, weights);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, EmbryonicMachineRecipe recipe) {
			buf.writeVarInt(recipe.getIngredients().size());
			for (Ingredient ing : recipe.getIngredients()) {
				ing.toNetwork(buf);
			}
			buf.writeItem(recipe.output);
			buf.writeMap(recipe.weights, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeVarInt);
		}
		//?}
	}
}