package net.cmr.jurassicrevived.platform;

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.services.IItemFluidHelper;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

public class FabricItemFluidHelper implements IItemFluidHelper {
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
	public Optional<FluidStack> getContainedFluid(ItemStack stack) {
		if (stack.is(Items.WATER_BUCKET)) return Optional.of(FluidStack.create(Fluids.WATER, 1000));
		if (stack.is(Items.LAVA_BUCKET)) return Optional.of(FluidStack.create(Fluids.LAVA, 1000));

		ContainerItemContext ctx = ContainerItemContext.withConstant(ItemVariant.of(stack), stack.getCount());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
			return Optional.empty();
		}

		for (StorageView<FluidVariant> view : storage) {
			if (!view.isResourceBlank() && view.getAmount() > 0) {
				return Optional.of(FluidStack.create(view.getResource().getFluid(), dropletsToMb(view.getAmount())));
			}
		}
		return Optional.of(FluidStack.empty());
	}

	@Override
	public TransferResult drain(ItemStack stack, long amount, boolean simulate) {
		if (stack.is(Items.WATER_BUCKET) && amount >= 1000) {
			return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
		}
		if (stack.is(Items.LAVA_BUCKET) && amount >= 1000) {
			return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
		}

		ContainerItemContext ctx = ContainerItemContext.withConstant(ItemVariant.of(stack), stack.getCount());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
			return new TransferResult(0, stack);
		}

		try (Transaction tx = Transaction.openOuter()) {
			long requestedDroplets = mbToDroplets(amount);
			long extractedDroplets = 0;

			for (StorageView<FluidVariant> view : storage) {
				if (!view.isResourceBlank()) {
					extractedDroplets = storage.extract(view.getResource(), requestedDroplets, tx);
					if (extractedDroplets > 0) break;
				}
			}

			if (extractedDroplets > 0) {
				long extractedMb = dropletsToMb(extractedDroplets);

				if (!simulate) tx.commit();

				ItemStack resultStack = ctx.getItemVariant().toStack((int) ctx.getAmount());
				return new TransferResult(extractedMb, resultStack);
			}
		}
		return new TransferResult(0, stack);
	}

	@Override
	public TransferResult fill(ItemStack stack, FluidStack fluid, long amount, boolean simulate) {
		if (stack.is(Items.BUCKET) && amount >= 1000) {
			if (fluid.getFluid().isSame(Fluids.WATER)) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.WATER_BUCKET));
			}
			if (fluid.getFluid().isSame(Fluids.LAVA)) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.LAVA_BUCKET));
			}
		}

		ContainerItemContext ctx = ContainerItemContext.withConstant(ItemVariant.of(stack), stack.getCount());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
			return new TransferResult(0, stack);
		}

		try (Transaction tx = Transaction.openOuter()) {
			long requestedDroplets = mbToDroplets(amount);
			long insertedDroplets = storage.insert(FluidVariant.of(fluid.getFluid()), requestedDroplets, tx);

			if (insertedDroplets > 0) {
				long insertedMb = dropletsToMb(insertedDroplets);

				if (!simulate) tx.commit();

				ItemStack resultStack = ctx.getItemVariant().toStack((int) ctx.getAmount());
				return new TransferResult(insertedMb, resultStack);
			}
		}
		return new TransferResult(0, stack);
	}

	@Override
	public boolean isFluidHandler(ItemStack stack) {
		if (stack.is(Items.BUCKET) || stack.is(Items.WATER_BUCKET) || stack.is(Items.LAVA_BUCKET)) {
			return true;
		}

		ContainerItemContext ctx = ContainerItemContext.withConstant(ItemVariant.of(stack), stack.getCount());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		return storage != null;
	}
}
