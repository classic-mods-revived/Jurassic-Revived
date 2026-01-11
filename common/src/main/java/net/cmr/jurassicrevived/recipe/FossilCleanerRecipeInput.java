package net.cmr.jurassicrevived.recipe;

import net.minecraft.world.item.ItemStack;
//? if >1.20.1 {
/*import net.minecraft.world.item.crafting.RecipeInput;
 *///?} else {
import net.minecraft.world.SimpleContainer;
//?}

import java.util.List;

//? if >1.20.1 {
/*public record FossilCleanerRecipeInput(ItemStack material, ItemStack fuel) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> material;
            case 1 -> fuel;
            default -> throw new IllegalArgumentException("Unexpected slot index: " + index);
        };
    }

    @Override
    public int size() {
        return 2;
    }
}
*///?} else {
public class FossilCleanerRecipeInput extends SimpleContainer {
	public FossilCleanerRecipeInput(ItemStack material, ItemStack fuel) {
		super(material, fuel);
	}
}
//?}