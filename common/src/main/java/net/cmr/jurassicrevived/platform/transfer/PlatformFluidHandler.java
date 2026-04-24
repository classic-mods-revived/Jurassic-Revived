package net.cmr.jurassicrevived.platform.transfer;

import dev.architectury.fluid.FluidStack;

public interface PlatformFluidHandler {
	Iterable<FluidStack> getExtractableFluids();
	long extract(FluidStack stack, long amount, boolean simulate);
	long insert(FluidStack stack, long amount, boolean simulate);
}
