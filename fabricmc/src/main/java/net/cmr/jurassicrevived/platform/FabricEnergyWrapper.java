package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public class FabricEnergyWrapper implements EnergyStorage {
	private final ModEnergyStorage storage;

	public FabricEnergyWrapper(ModEnergyStorage storage) {
		this.storage = storage;
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		int accepted = storage.receiveEnergy((int) Math.min(Integer.MAX_VALUE, maxAmount), true);
		if (accepted <= 0) {
			return 0;
		}

		try (Transaction nested = transaction.openNested()) {
			int inserted = storage.receiveEnergy(accepted, false);
			nested.commit();
			return inserted;
		}
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		int extracted = storage.extractEnergy((int) Math.min(Integer.MAX_VALUE, maxAmount), true);
		if (extracted <= 0) {
			return 0;
		}

		try (Transaction nested = transaction.openNested()) {
			int removed = storage.extractEnergy(extracted, false);
			nested.commit();
			return removed;
		}
	}

	@Override
	public long getAmount() {
		return storage.getEnergyStored();
	}

	@Override
	public long getCapacity() {
		return storage.getMaxEnergyStored();
	}
}
