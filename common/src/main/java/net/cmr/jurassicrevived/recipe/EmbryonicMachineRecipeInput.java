package net.cmr.jurassicrevived.recipe;

import net.minecraft.world.item.ItemStack;
//? if >1.20.1 {
/*import net.minecraft.world.item.crafting.RecipeInput;
 *///?} else {
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
//?}

import java.util.List;

//? if >1.20.1 {
/*public record EmbryonicMachineRecipeInput(ItemStack testTube, ItemStack material, ItemStack frog) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> testTube;
            case 1 -> material;
            case 2 -> frog;
            default -> throw new IllegalArgumentException("Unexpected slot index: " + index);
        };
    }

    @Override
    public int size() {
        return 3;
    }
}
*///?} else {
public class EmbryonicMachineRecipeInput extends SimpleContainer {
	public EmbryonicMachineRecipeInput(ItemStack testTube, ItemStack material, ItemStack frog) {
		super(testTube, material, frog);
	}
}
//?}