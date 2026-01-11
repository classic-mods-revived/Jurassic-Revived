package net.cmr.jurassicrevived.block.entity.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ModEnergyUtil {
	public static boolean move(BlockPos from, BlockPos to, int amount, Level level) {
		BlockEntity fromBE = level.getBlockEntity(from);
		BlockEntity toBE = level.getBlockEntity(to);

		// In a common module, we check if our custom BEs implement a common energy provider
		// or access the storage directly if they are your mod's blocks.
		if (fromBE instanceof EnergyProvider fromProvider && toBE instanceof EnergyProvider toProvider) {
			ModEnergyStorage fromStorage = fromProvider.getEnergyStorage(null);
			ModEnergyStorage toStorage = toProvider.getEnergyStorage(null);

			if (fromStorage == null || toStorage == null) return false;

			if (canEnergyStorageExtractThisAmount(fromStorage, amount)) {
				return false;
			}

			if (canEnergyStorageStillReceiveEnergy(toStorage)) {
				return false;
			}

			int maxAmountToReceive = toStorage.receiveEnergy(amount, true);
			int extractedEnergy = fromStorage.extractEnergy(maxAmountToReceive, false);
			toStorage.receiveEnergy(extractedEnergy, false);

			return true;
		}
		return false;
	}

	private static boolean canEnergyStorageStillReceiveEnergy(ModEnergyStorage toStorage) {
		return toStorage.getEnergyStored() >= toStorage.getMaxEnergyStored() || !toStorage.canReceive();
	}

	private static boolean canEnergyStorageExtractThisAmount(ModEnergyStorage fromStorage, int amount) {
		return fromStorage.getEnergyStored() <= 0 || fromStorage.getEnergyStored() < amount || !fromStorage.canExtract();
	}

	public static boolean doesBlockHaveEnergyStorage(BlockPos positionToCheck, Level level) {
		BlockEntity be = level.getBlockEntity(positionToCheck);
		return be instanceof EnergyProvider;
	}

	// Common interface for your BlockEntities to implement
	public interface EnergyProvider {
		ModEnergyStorage getEnergyStorage(net.minecraft.core.Direction direction);
	}
}