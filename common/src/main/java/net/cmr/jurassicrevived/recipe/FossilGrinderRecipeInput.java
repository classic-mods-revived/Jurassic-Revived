package net.cmr.jurassicrevived.recipe;

import net.minecraft.world.item.ItemStack;
//? if >1.20.1 {
/*import net.minecraft.world.item.crafting.RecipeInput;
 *///?} else {
import net.minecraft.world.SimpleContainer;
//?}

//? if >1.20.1 {
/*public record FossilGrinderRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index == 0) return input;
        throw new IllegalArgumentException("Unexpected slot index: " + index);
    }

    @Override
    public int size() {
        return 1;
    }
}
*///?} else {
public class FossilGrinderRecipeInput extends SimpleContainer {
	public FossilGrinderRecipeInput(ItemStack input) {
		super(input);
	}
}
//?}