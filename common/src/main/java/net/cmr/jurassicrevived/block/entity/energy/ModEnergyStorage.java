package net.cmr.jurassicrevived.block.entity.energy;

import net.minecraft.nbt.CompoundTag;

public abstract class ModEnergyStorage {
	protected int energy;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ModEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer, 0);
	}

	public ModEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.energy = Math.max(0, Math.min(capacity, energy));
	}

	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate && energyReceived != 0) {
			energy += energyReceived;
			onEnergyChanged();
		}
		return energyReceived;
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate && energyExtracted != 0) {
			energy -= energyExtracted;
			onEnergyChanged();
		}
		return energyExtracted;
	}

	public int getEnergyStored() {
		return energy;
	}

	public int getMaxEnergyStored() {
		return capacity;
	}

	public boolean canExtract() {
		return this.maxExtract > 0;
	}

	public boolean canReceive() {
		return this.maxReceive > 0;
	}

	public void setEnergy(int energy) {
		this.energy = Math.max(0, Math.min(capacity, energy));
		onEnergyChanged();
	}

	public abstract void onEnergyChanged();

	public CompoundTag saveNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("Energy", energy);
		return tag;
	}

	public void loadNBT(CompoundTag tag) {
		this.energy = tag.getInt("Energy");
	}
}