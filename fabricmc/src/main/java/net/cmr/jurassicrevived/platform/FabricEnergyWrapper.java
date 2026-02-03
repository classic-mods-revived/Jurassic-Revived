package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public class FabricEnergyWrapper implements EnergyStorage {
	private final ModEnergyStorage storage;

	public FabricEnergyWrapper(ModEnergyStorage storage) {
		this.storage = storage;
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		return storage.receiveEnergy((int) maxAmount, true);
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		return storage.extractEnergy((int) maxAmount, true);
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
