package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.platform.services.ITransferHelper;
import net.cmr.jurassicrevived.platform.transfer.PlatformItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidHandlerAdapter;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidProvider;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NeoForgeTransferHelper implements ITransferHelper {

	@Override
	public Optional<PlatformItemHandler> getItemHandler(Level level, BlockPos pos, Direction side) {
		IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, side);
		return Optional.ofNullable(handler).map(NeoForgeItemHandler::new);
	}

	@Override
	public Optional<PlatformFluidHandler> getFluidHandler(Level level, BlockPos pos, Direction side) {
		IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos, side);
		if (handler != null) return Optional.of(new NeoForgeFluidHandler(handler));

		BlockEntity be = level.getBlockEntity(pos);
		if (be instanceof InternalFluidProvider provider) {
			return Optional.of(new InternalFluidHandlerAdapter(provider.getFluidHandler(side)));
		}
		return Optional.empty();
	}

	@Override
	public Optional<PlatformEnergyHandler> getEnergyHandler(Level level, BlockPos pos, Direction side) {
		IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, side);
		return Optional.ofNullable(storage).map(NeoForgeEnergyHandler::new);
	}

	private static class NeoForgeFluidHandler implements PlatformFluidHandler {
		private final IFluidHandler handler;

		private NeoForgeFluidHandler(IFluidHandler handler) {
			this.handler = handler;
		}

		@Override
		public Iterable<FluidStack> getExtractableFluids() {
			List<FluidStack> stacks = new ArrayList<>();
			for (int i = 0; i < handler.getTanks(); i++) {
				net.neoforged.neoforge.fluids.FluidStack fs = handler.getFluidInTank(i);
				if (!fs.isEmpty()) {
					stacks.add(FluidStack.create(fs.getFluid(), fs.getAmount()));
				}
			}
			return stacks;
		}

		@Override
		public long extract(FluidStack stack, long amount, boolean simulate) {
			net.neoforged.neoforge.fluids.FluidStack req =
				new net.neoforged.neoforge.fluids.FluidStack(stack.getFluid(), (int) amount);
			net.neoforged.neoforge.fluids.FluidStack drained = handler.drain(
				req,
				simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE
			);
			return drained.getAmount();
		}

		@Override
		public long insert(FluidStack stack, long amount, boolean simulate) {
			net.neoforged.neoforge.fluids.FluidStack toFill =
				new net.neoforged.neoforge.fluids.FluidStack(stack.getFluid(), (int) amount);
			return handler.fill(
				toFill,
				simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE
			);
		}
	}

	private static class NeoForgeEnergyHandler implements PlatformEnergyHandler {
		private final IEnergyStorage storage;

		private NeoForgeEnergyHandler(IEnergyStorage storage) {
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

	private static class NeoForgeItemHandler implements PlatformItemHandler {
		private final IItemHandler handler;

		private NeoForgeItemHandler(IItemHandler handler) {
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
				if (!itemsMatch(slot, stack)) continue;

				ItemStack extracted = handler.extractItem(i, remaining, simulate);
				remaining -= extracted.getCount();
			}
			return amount - remaining;
		}

		private static boolean itemsMatch(ItemStack a, ItemStack b) {
			//? if >1.20.1 {
			return ItemStack.isSameItemSameComponents(a, b);
			//?} else {
			/*return ItemStack.isSameItemSameTags(a, b);*/
			//?}
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
}
