package net.cmr.jurassicrevived.platform;

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.services.IItemFluidHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Optional;

public class ForgeItemFluidHelper implements IItemFluidHelper {

	@Override
	public Optional<FluidStack> getContainedFluid(ItemStack stack) {
		Optional<FluidStack> handlerFluid = FluidUtil.getFluidHandler(stack.copy())
			.map(handler -> {
				if (handler.getTanks() <= 0) return FluidStack.empty();
				var fs = handler.getFluidInTank(0);
				if (fs.isEmpty()) return FluidStack.empty();
				return FluidStack.create(fs.getFluid(), fs.getAmount(), fs.getTag());
			});

		if (handlerFluid.isPresent() && !handlerFluid.get().isEmpty()) return handlerFluid;

		if (stack.is(Items.WATER_BUCKET)) {
			return Optional.of(FluidStack.create(Fluids.WATER, 1000));
		}
		if (stack.is(Items.LAVA_BUCKET)) {
			return Optional.of(FluidStack.create(Fluids.LAVA, 1000));
		}
		return Optional.of(FluidStack.empty());
	}

	@Override
	public TransferResult drain(ItemStack stack, long amount, boolean simulate) {
		Optional<TransferResult> handlerResult = FluidUtil.getFluidHandler(stack.copy())
			.map(handler -> {
				var drained = handler.drain((int) amount,
					simulate ? IFluidHandlerItem.FluidAction.SIMULATE : IFluidHandlerItem.FluidAction.EXECUTE);
				return new TransferResult(drained.getAmount(), handler.getContainer());
			});

		if (handlerResult.isPresent()) return handlerResult.get();

		if (stack.is(Items.WATER_BUCKET) && amount >= 1000) {
			return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
		}
		if (stack.is(Items.LAVA_BUCKET) && amount >= 1000) {
			return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
		}

		return new TransferResult(0, stack);
	}

	@Override
	public TransferResult fill(ItemStack stack, FluidStack fluid, long amount, boolean simulate) {
		Optional<TransferResult> handlerResult = FluidUtil.getFluidHandler(stack.copy())
			.map(handler -> {
				var toFill = new net.minecraftforge.fluids.FluidStack(fluid.getFluid(), (int) amount, fluid.getTag());
				int filled = handler.fill(toFill,
					simulate ? IFluidHandlerItem.FluidAction.SIMULATE : IFluidHandlerItem.FluidAction.EXECUTE);
				return new TransferResult(filled, handler.getContainer());
			});

		if (handlerResult.isPresent()) return handlerResult.get();

		if (stack.is(Items.BUCKET) && amount >= 1000) {
			if (fluid.getFluid().isSame(Fluids.WATER)) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.WATER_BUCKET));
			}
			if (fluid.getFluid().isSame(Fluids.LAVA)) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.LAVA_BUCKET));
			}
		}
		return new TransferResult(0, stack);
	}

	@Override
	public boolean isFluidHandler(ItemStack stack) {
		return FluidUtil.getFluidHandler(stack).isPresent() || stack.is(Items.BUCKET) || stack.is(Items.WATER_BUCKET) || stack.is(Items.LAVA_BUCKET);
	}
}
