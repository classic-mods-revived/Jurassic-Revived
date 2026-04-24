package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public record ForgeTankFluidAdapter(TankBlockEntity.TankFluidHandler tank) implements IFluidHandler {

	@Override
	public int getTanks() {
		return 1;
	}

	@Override
	public FluidStack getFluidInTank(int tankIndex) {
		if (tankIndex != 0) return FluidStack.EMPTY;
		return new FluidStack(tank.getFluid().getFluid(), (int) tank.getFluid().getAmount());
	}

	@Override
	public int getTankCapacity(int tankIndex) {
		return tankIndex == 0 ? (int) tank.getCapacity() : 0;
	}

	@Override
	public boolean isFluidValid(int tankIndex, FluidStack stack) {
		return tankIndex == 0;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (resource.isEmpty()) return 0;
		long filled = tank.fill(dev.architectury.fluid.FluidStack.create(resource.getFluid(), resource.getAmount()), action.simulate());
		return (int) filled;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		if (resource.isEmpty()) return FluidStack.EMPTY;
		var drained = tank.drain(resource.getAmount(), action.simulate());
		return new FluidStack(drained.getFluid(), (int) drained.getAmount());
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		var drained = tank.drain(maxDrain, action.simulate());
		return new FluidStack(drained.getFluid(), (int) drained.getAmount());
	}
}
