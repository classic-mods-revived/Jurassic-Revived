package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.platform.services.ITransferHelper;
import net.cmr.jurassicrevived.platform.transfer.PlatformItemHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import dev.architectury.fluid.FluidStack;
import team.reborn.energy.api.EnergyStorage;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
//import net.fabricmc.fabric.api.transfer.v1.energy.EnergyStorage;
//import team.reborn.energy.api.EnergyStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FabricTransferHelper implements ITransferHelper {

	@Override
	public Optional<PlatformItemHandler> getItemHandler(Level level, BlockPos pos, Direction side) {
		Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, side);
		if (storage == null) return Optional.empty();
		return Optional.of(new FabricItemHandler(storage));
	}

	@Override
	public Optional<PlatformFluidHandler> getFluidHandler(Level level, BlockPos pos, Direction side) {
		var storage = FluidStorage.SIDED.find(level, pos, side);
		if (storage == null) return Optional.empty();
		return Optional.of(new FabricFluidHandler(storage));
	}

	@Override
	public Optional<PlatformEnergyHandler> getEnergyHandler(Level level, BlockPos pos, Direction side) {
		EnergyStorage storage = EnergyStorage.SIDED.find(level, pos, side);
		return Optional.empty();
	}

	public static class InternalFluidStorage implements Storage<FluidVariant> {
		private final net.cmr.jurassicrevived.platform.transfer.InternalFluidHandler handler;

		public InternalFluidStorage(net.cmr.jurassicrevived.platform.transfer.InternalFluidHandler handler) {
			this.handler = handler;
		}

		@Override
		public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
			if (resource.isBlank() || maxAmount <= 0) {
				return 0;
			}

			FluidStack stack = FluidStack.create(resource.getFluid(), maxAmount);
			long inserted = handler.fill(stack, true);

			if (inserted > 0) {
				transaction.addCloseCallback((tx, result) -> {
					if (result.wasCommitted()) {
						handler.fill(FluidStack.create(resource.getFluid(), inserted), false);
					}
				});
			}

			return inserted;
		}

		@Override
		public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
			if (resource.isBlank() || maxAmount <= 0) {
				return 0;
			}

			FluidStack stored = handler.getFluid();
			if (stored.isEmpty() || stored.getFluid() != resource.getFluid()) {
				return 0;
			}

			long extracted = Math.min(maxAmount, stored.getAmount());

			if (extracted > 0) {
				transaction.addCloseCallback((tx, result) -> {
					if (result.wasCommitted()) {
						handler.drain(extracted, false);
					}
				});
			}

			return extracted;
		}

		@Override
		public Iterator<StorageView<FluidVariant>> iterator() {
			FluidStack stored = handler.getFluid();
			return List.<StorageView<FluidVariant>>of(new StorageView<>() {
				@Override
				public FluidVariant getResource() {
					return stored.isEmpty() ? FluidVariant.blank() : FluidVariant.of(stored.getFluid());
				}

				@Override
				public long getAmount() {
					return stored.getAmount();
				}

				@Override
				public long getCapacity() {
					return handler.getCapacity();
				}

				@Override
				public boolean isResourceBlank() {
					return stored.isEmpty();
				}

				@Override
				public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
					return InternalFluidStorage.this.extract(resource, maxAmount, transaction);
				}
			}).iterator();
		}
	}

	private static class FabricFluidHandler implements PlatformFluidHandler {
		private final Storage<FluidVariant> storage;

		private FabricFluidHandler(Storage<FluidVariant> storage) {
			this.storage = storage;
		}

		@Override
		public Iterable<FluidStack> getExtractableFluids() {
			List<FluidStack> stacks = new ArrayList<>();
			for (StorageView<FluidVariant> view : storage) {
				if (view.isResourceBlank()) continue;
				FluidVariant v = view.getResource();
				long amt = view.getAmount();
				if (amt > 0) {
					FluidStack stack = FluidStack.create(v.getFluid(), amt);
					stacks.add(stack);
				}
			}
			return stacks;
		}

		@Override
		public long extract(FluidStack stack, long amount, boolean simulate) {
			try (Transaction tx = Transaction.openOuter()) {
				long extracted = storage.extract(FluidVariant.of(stack.getFluid()), amount, tx);
				if (!simulate) tx.commit();
				return extracted;
			}
		}

		@Override
		public long insert(FluidStack stack, long amount, boolean simulate) {
			try (Transaction tx = Transaction.openOuter()) {
				long inserted = storage.insert(FluidVariant.of(stack.getFluid()), amount, tx);
				if (!simulate) tx.commit();
				return inserted;
			}
		}
	}

	private static class FabricEnergyHandler implements PlatformEnergyHandler {
		private FabricEnergyHandler() {
		}

		@Override
		public int extract(int amount, boolean simulate) {
			return 0;
		}

		@Override
		public int insert(int amount, boolean simulate) {
			return 0;
		}
	}

	private static class FabricItemHandler implements PlatformItemHandler {
		private final Storage<ItemVariant> storage;

		private FabricItemHandler(Storage<ItemVariant> storage) {
			this.storage = storage;
		}

		@Override
		public Iterable<ItemStack> getExtractableStacks() {
			List<ItemStack> stacks = new ArrayList<>();
			for (StorageView<ItemVariant> view : storage) {
				if (view.isResourceBlank()) continue;
				ItemVariant v = view.getResource();
				long amt = view.getAmount();
				if (amt > 0) stacks.add(v.toStack((int) Math.min(amt, Integer.MAX_VALUE)));
			}
			return stacks;
		}

		@Override
		public int extract(ItemStack stack, int amount, boolean simulate) {
			try (Transaction tx = Transaction.openOuter()) {
				long extracted = storage.extract(ItemVariant.of(stack), amount, tx);
				if (!simulate) tx.commit();
				return (int) extracted;
			}
		}

		@Override
		public int insert(ItemStack stack, int amount, boolean simulate) {
			try (Transaction tx = Transaction.openOuter()) {
				long inserted = storage.insert(ItemVariant.of(stack), amount, tx);
				if (!simulate) tx.commit();
				return (int) inserted;
			}
		}
	}
}
