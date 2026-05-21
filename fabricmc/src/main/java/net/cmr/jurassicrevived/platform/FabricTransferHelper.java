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

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidHandler;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidProvider;
import team.reborn.energy.api.EnergyStorage;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FabricTransferHelper implements ITransferHelper {
	private static final long MB_PER_BUCKET = 1000L;

	private static long dropletsToMb(long droplets) {
		if (droplets <= 0) return 0;
		return Math.max(1, droplets * MB_PER_BUCKET / FluidConstants.BUCKET);
	}

	private static long mbToDroplets(long mb) {
		if (mb <= 0) return 0;
		return mb * FluidConstants.BUCKET / MB_PER_BUCKET;
	}

	@Override
	public Optional<PlatformItemHandler> getItemHandler(Level level, BlockPos pos, Direction side) {
		Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, side);
		if (storage == null) return Optional.empty();
		return Optional.of(new FabricItemHandler(storage));
	}

	@Override
	public Optional<PlatformFluidHandler> getFluidHandler(Level level, BlockPos pos, Direction side) {
		if (level.getBlockEntity(pos) instanceof InternalFluidProvider provider) {
			InternalFluidHandler handler = provider.getFluidHandler(side);
			if (handler != null) {
				return Optional.of(new DirectInternalFluidHandler(handler));
			}
		}

		var storage = FluidStorage.SIDED.find(level, pos, side);
		if (storage == null) return Optional.empty();
		return Optional.of(new FabricFluidHandler(storage));
	}

	@Override
	public Optional<PlatformEnergyHandler> getEnergyHandler(Level level, BlockPos pos, Direction side) {
		EnergyStorage storage = EnergyStorage.SIDED.find(level, pos, side);
		if (storage == null) return Optional.empty();
		return Optional.of(new FabricEnergyHandler(storage));
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

			long maxAmountMb = dropletsToMb(maxAmount);
			FluidStack stack = FluidStack.create(resource.getFluid(), maxAmountMb);
			long insertedMb = handler.fill(stack, true);

			if (insertedMb > 0) {
				transaction.addCloseCallback((tx, result) -> {
					if (result.wasCommitted()) {
						handler.fill(FluidStack.create(resource.getFluid(), insertedMb), false);
					}
				});
			}

			return Math.min(maxAmount, mbToDroplets(insertedMb));
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

			long maxAmountMb = dropletsToMb(maxAmount);
			long extractedMb = Math.min(maxAmountMb, stored.getAmount());

			if (extractedMb > 0) {
				transaction.addCloseCallback((tx, result) -> {
					if (result.wasCommitted()) {
						handler.drain(extractedMb, false);
					}
				});
			}

			return Math.min(maxAmount, mbToDroplets(extractedMb));
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
					return mbToDroplets(stored.getAmount());
				}

				@Override
				public long getCapacity() {
					return mbToDroplets(handler.getCapacity());
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

	private static class DirectInternalFluidHandler implements PlatformFluidHandler {
		private final InternalFluidHandler handler;

		private DirectInternalFluidHandler(InternalFluidHandler handler) {
			this.handler = handler;
		}

		@Override
		public Iterable<FluidStack> getExtractableFluids() {
			FluidStack stored = handler.getFluid();
			if (stored.isEmpty()) {
				return List.of();
			}
			return List.of(stored.copy());
		}

		@Override
		public long extract(FluidStack stack, long amount, boolean simulate) {
			FluidStack stored = handler.getFluid();
			if (stored.isEmpty() || stored.getFluid() != stack.getFluid()) {
				return 0;
			}

			return handler.drain(amount, simulate).getAmount();
		}

		@Override
		public long insert(FluidStack stack, long amount, boolean simulate) {
			if (stack.isEmpty() || amount <= 0) {
				return 0;
			}

			FluidStack toInsert = stack.copy();
			toInsert.setAmount(amount);
			return handler.fill(toInsert, simulate);
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
				long amt = dropletsToMb(view.getAmount());
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
				long extractedDroplets = storage.extract(FluidVariant.of(stack.getFluid()), mbToDroplets(amount), tx);
				if (!simulate) tx.commit();
				return dropletsToMb(extractedDroplets);
			}
		}

		@Override
		public long insert(FluidStack stack, long amount, boolean simulate) {
			try (Transaction tx = Transaction.openOuter()) {
				long insertedDroplets = storage.insert(FluidVariant.of(stack.getFluid()), mbToDroplets(amount), tx);
				if (!simulate) tx.commit();
				return dropletsToMb(insertedDroplets);
			}
		}
	}

	private static class FabricEnergyHandler implements PlatformEnergyHandler {
		private final EnergyStorage storage;

		private FabricEnergyHandler(EnergyStorage storage) {
			this.storage = storage;
		}

		@Override
		public int extract(int amount, boolean simulate) {
			if (amount <= 0) {
				return 0;
			}

			try (Transaction tx = Transaction.openOuter()) {
				long extracted = storage.extract(amount, tx);
				if (!simulate) {
					tx.commit();
				}
				return (int) Math.min(Integer.MAX_VALUE, extracted);
			}
		}

		@Override
		public int insert(int amount, boolean simulate) {
			if (amount <= 0) {
				return 0;
			}

			try (Transaction tx = Transaction.openOuter()) {
				long inserted = storage.insert(amount, tx);
				if (!simulate) {
					tx.commit();
				}
				return (int) Math.min(Integer.MAX_VALUE, inserted);
			}
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
