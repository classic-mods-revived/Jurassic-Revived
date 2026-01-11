package net.cmr.jurassicrevived.recipe;

import net.minecraft.world.item.ItemStack;
//? if >1.20.1 {
/*import net.minecraft.world.item.crafting.RecipeInput;
import java.util.List;
*///?} else {
import net.minecraft.world.SimpleContainer;
//?}

//? if >1.20.1 {
/*public record DNAHybridizerRecipeInput(List<ItemStack> inputs) implements RecipeInput {
    public DNAHybridizerRecipeInput(ItemStack i0, ItemStack i1, ItemStack i2, ItemStack i3, ItemStack i4, ItemStack i5, ItemStack i6, ItemStack i7, ItemStack i8) {
        this(List.of(i0, i1, i2, i3, i4, i5, i6, i7, i8));
    }

    @Override
    public ItemStack getItem(int index) {
        return inputs.get(index);
    }

    @Override
    public int size() {
        return inputs.size();
    }
}
*///?} else {
public class DNAHybridizerRecipeInput extends SimpleContainer {
    public DNAHybridizerRecipeInput(ItemStack i0, ItemStack i1, ItemStack i2, ItemStack i3, ItemStack i4, ItemStack i5, ItemStack i6, ItemStack i7, ItemStack i8) {
        super(i0, i1, i2, i3, i4, i5, i6, i7, i8);
    }
}
//?}