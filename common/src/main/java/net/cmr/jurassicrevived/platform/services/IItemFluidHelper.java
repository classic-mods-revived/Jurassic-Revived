package net.cmr.jurassicrevived.platform.services;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface IItemFluidHelper {
	record TransferResult(long amount, ItemStack stack) {}

	Optional<FluidStack> getContainedFluid(ItemStack stack);
	TransferResult drain(ItemStack stack, long amount, boolean simulate);
	TransferResult fill(ItemStack stack, FluidStack fluid, long amount, boolean simulate);
	boolean isFluidHandler(ItemStack stack);
}
