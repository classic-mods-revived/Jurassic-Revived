package net.cmr.jurassicrevived.platform.transfer;

import net.minecraft.world.item.ItemStack;

public interface PlatformItemHandler {
	Iterable<ItemStack> getExtractableStacks();
	int extract(ItemStack stack, int amount, boolean simulate);
	int insert(ItemStack stack, int amount, boolean simulate);
}
