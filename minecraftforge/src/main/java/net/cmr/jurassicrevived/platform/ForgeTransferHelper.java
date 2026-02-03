package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.platform.services.ITransferHelper;
import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.transfer.PlatformItemHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForgeTransferHelper implements ITransferHelper {

	@Override
	public Optional<PlatformItemHandler> getItemHandler(Level level, BlockPos pos, Direction side) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null) return Optional.empty();
		return be.getCapability(ForgeCapabilities.ITEM_HANDLER, side)
			.resolve()
			.map(ForgeItemHandler::new);
	}

	@Override
	public Optional<PlatformFluidHandler> getFluidHandler(Level level, BlockPos pos, Direction side) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null) return Optional.empty();
		return be.getCapability(ForgeCapabilities.FLUID_HANDLER, side)
			.resolve()
			.map(ForgeFluidHandler::new);
	}

	@Override
	public Optional<PlatformEnergyHandler> getEnergyHandler(Level level, BlockPos pos, Direction side) {
		BlockEntity be = level.getBlockEntity(pos);
		if (be == null) return Optional.empty();
		return be.getCapability(ForgeCapabilities.ENERGY, side)
			.resolve()
			.map(ForgeEnergyHandler::new);
	}

	private static class ForgeItemHandler implements PlatformItemHandler {
		private final IItemHandler handler;

		private ForgeItemHandler(IItemHandler handler) {
			this.handler = handler;
		}

		@Override
		public Iterable<ItemStack> getExtractableStacks() {
			List<ItemStack> stacks = new ArrayList<>();
			for (int i = 0; i < handler.getSlots(); i++) {
				ItemStack stack = handler.getStackInSlot(i);
				if (!stack.isEmpty()) stacks.add(stack.copy());
			}
			return stacks;
		}

		@Override
		public int extract(ItemStack stack, int amount, boolean simulate) {
			int remaining = amount;
			for (int i = 0; i < handler.getSlots() && remaining > 0; i++) {
				ItemStack slot = handler.getStackInSlot(i);
				if (!ItemHandlerHelper.canItemStacksStack(slot, stack)) continue;

				ItemStack extracted = handler.extractItem(i, remaining, simulate);
				remaining -= extracted.getCount();
			}
			return amount - remaining;
		}

		@Override
		public int insert(ItemStack stack, int amount, boolean simulate) {
			ItemStack toInsert = stack.copy();
			toInsert.setCount(amount);

			ItemStack remainder = toInsert;
			for (int i = 0; i < handler.getSlots() && !remainder.isEmpty(); i++) {
				remainder = handler.insertItem(i, remainder, simulate);
			}
			return amount - remainder.getCount();
		}
	}

	private static class ForgeFluidHandler implements PlatformFluidHandler {
		private final IFluidHandler handler;

		private ForgeFluidHandler(IFluidHandler handler) {
			this.handler = handler;
		}

		@Override
		public Iterable<FluidStack> getExtractableFluids() {
			List<FluidStack> stacks = new ArrayList<>();
			for (int i = 0; i < handler.getTanks(); i++) {
				net.minecraftforge.fluids.FluidStack fs = handler.getFluidInTank(i);
				if (!fs.isEmpty()) {
					stacks.add(FluidStack.create(fs.getFluid(), fs.getAmount(), fs.getTag()));
				}
			}
			return stacks;
		}

		@Override
		public long extract(FluidStack stack, long amount, boolean simulate) {
			net.minecraftforge.fluids.FluidStack req =
				new net.minecraftforge.fluids.FluidStack(stack.getFluid(), (int) amount, stack.getTag());
			net.minecraftforge.fluids.FluidStack drained = handler.drain(req, simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
			return drained.getAmount();
		}

		@Override
		public long insert(FluidStack stack, long amount, boolean simulate) {
			net.minecraftforge.fluids.FluidStack toFill =
				new net.minecraftforge.fluids.FluidStack(stack.getFluid(), (int) amount, stack.getTag());
			return handler.fill(toFill, simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
		}
	}

	private static class ForgeEnergyHandler implements PlatformEnergyHandler {
		private final IEnergyStorage storage;

		private ForgeEnergyHandler(IEnergyStorage storage) {
			this.storage = storage;
		}

		@Override
		public int extract(int amount, boolean simulate) {
			return storage.extractEnergy(amount, simulate);
		}

		@Override
		public int insert(int amount, boolean simulate) {
			return storage.receiveEnergy(amount, simulate);
		}
	}
}
