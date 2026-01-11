package net.cmr.jurassicrevived.recipe;

import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Objects;

/*? if >1.20.1 {*/
/*import net.minecraft.world.item.crafting.RecipeInput;
*//*?} else {*/
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
/*?}*/

public record EmbryoCalcificationMachineRecipeInput(List<ItemStack> inputs)
	/*? if >1.20.1 {*/ /*implements RecipeInput *//*?} else {*/ implements Container /*?}*/ {

	public EmbryoCalcificationMachineRecipeInput {
		Objects.requireNonNull(inputs, "inputs");
		if (inputs.size() != 2) {
			throw new IllegalArgumentException("EmbryoCalcificationMachineRecipeInput requires exactly 2 input stacks");
		}
	}

	public EmbryoCalcificationMachineRecipeInput(ItemStack first, ItemStack second) {
		this(List.of(first, second));
	}

	@Override
	public ItemStack getItem(int i) {
		return inputs.get(i);
	}
	
	public int size() {
		return inputs.size();
	}

	/*? if <=1.20.1 {*/
	@Override public int getContainerSize() { return size(); }
	@Override public boolean isEmpty() { return inputs.stream().allMatch(ItemStack::isEmpty); }
	@Override public ItemStack removeItem(int i, int j) { return ItemStack.EMPTY; }
	@Override public ItemStack removeItemNoUpdate(int i) { return ItemStack.EMPTY; }
	@Override public void setItem(int i, ItemStack itemStack) {}
	@Override public void setChanged() {}
	@Override public boolean stillValid(Player player) { return true; }
	@Override public void clearContent() {}
	/*?}*/
}