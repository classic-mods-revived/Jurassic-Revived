package net.cmr.jurassicrevived.platform.transfer;

import dev.architectury.fluid.FluidStack;

public interface InternalFluidHandler {
	FluidStack getFluid();
	long getCapacity();
	long fill(FluidStack stack, boolean simulate);
	FluidStack drain(long amount, boolean simulate);
}
