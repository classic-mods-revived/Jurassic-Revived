package net.cmr.jurassicrevived.platform;

import dev.architectury.fluid.FluidStack;
import net.cmr.jurassicrevived.platform.services.IItemFluidHelper;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
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

	@Override
	public Optional<FluidStack> getContainedFluid(ItemStack stack) {
		ContainerItemContext ctx = ContainerItemContext.withConstant(stack.copy());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
			if (stack.is(Items.WATER_BUCKET)) return Optional.of(FluidStack.create(Fluids.WATER, 1000));
			if (stack.is(Items.LAVA_BUCKET)) return Optional.of(FluidStack.create(Fluids.LAVA, 1000));
			return Optional.empty();
		}

		for (StorageView<FluidVariant> view : storage) {
			if (!view.isResourceBlank() && view.getAmount() > 0) {
				return Optional.of(FluidStack.create(view.getResource().getFluid(), view.getAmount()));
			}
		}
		return Optional.of(FluidStack.empty());
	}

	@Override
	public TransferResult drain(ItemStack stack, long amount, boolean simulate) {
		ContainerItemContext ctx = ContainerItemContext.withConstant(stack.copy());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
			if (stack.is(Items.WATER_BUCKET) && amount >= 1000) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
			}
			if (stack.is(Items.LAVA_BUCKET) && amount >= 1000) {
				return new TransferResult(1000, simulate ? stack : new ItemStack(Items.BUCKET));
			}
			return new TransferResult(0, stack);
		}

		try (Transaction tx = Transaction.openOuter()) {
			long extracted = 0;
			for (StorageView<FluidVariant> view : storage) {
				if (!view.isResourceBlank()) {
					extracted = storage.extract(view.getResource(), amount, tx);
					if (extracted > 0) break;
				}
			}

			if (extracted > 0) {
				if (!simulate) tx.commit();
				ItemStack resultStack = ctx.getItemVariant().toStack((int) ctx.getAmount());
				return new TransferResult(extracted, resultStack);
			}
		}
		return new TransferResult(0, stack);
	}

	@Override
	public TransferResult fill(ItemStack stack, FluidStack fluid, long amount, boolean simulate) {
		ContainerItemContext ctx = ContainerItemContext.withConstant(stack.copy());
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		if (storage == null) {
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

		try (Transaction tx = Transaction.openOuter()) {
			long inserted = storage.insert(FluidVariant.of(fluid.getFluid()), amount, tx);
			if (inserted > 0) {
				if (!simulate) tx.commit();
				ItemStack resultStack = ctx.getItemVariant().toStack((int) ctx.getAmount());
				return new TransferResult(inserted, resultStack);
			}
		}
		return new TransferResult(0, stack);
	}

	@Override
	public boolean isFluidHandler(ItemStack stack) {
		ContainerItemContext ctx = ContainerItemContext.withConstant(stack);
		Storage<FluidVariant> storage = ctx.find(FluidStorage.ITEM);
		return storage != null || stack.is(Items.BUCKET) || stack.is(Items.WATER_BUCKET) || stack.is(Items.LAVA_BUCKET);
	}
}
