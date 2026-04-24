package net.cmr.jurassicrevived.platform.transfer;

import dev.architectury.fluid.FluidStack;

import java.util.List;

public class InternalFluidHandlerAdapter implements PlatformFluidHandler {
	private final InternalFluidHandler handler;

	public InternalFluidHandlerAdapter(InternalFluidHandler handler) {
		this.handler = handler;
	}

	@Override
	public Iterable<FluidStack> getExtractableFluids() {
		FluidStack fluid = handler.getFluid();
		return fluid.isEmpty() ? List.of() : List.of(fluid);
	}

	@Override
	public long extract(FluidStack stack, long amount, boolean simulate) {
		if (stack.isEmpty()) return 0;
		return handler.drain(amount, simulate).getAmount();
	}

	@Override
	public long insert(FluidStack stack, long amount, boolean simulate) {
		if (stack.isEmpty()) return 0;
		FluidStack toFill = stack.copy();
		toFill.setAmount(amount);
		return handler.fill(toFill, simulate);
	}
}
