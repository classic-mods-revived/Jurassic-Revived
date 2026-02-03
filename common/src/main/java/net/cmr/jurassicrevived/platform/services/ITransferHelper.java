package net.cmr.jurassicrevived.platform.services;

import net.cmr.jurassicrevived.platform.transfer.PlatformItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.cmr.jurassicrevived.platform.transfer.PlatformEnergyHandler;
import net.cmr.jurassicrevived.platform.transfer.PlatformFluidHandler;

import java.util.Optional;

public interface ITransferHelper {
	Optional<PlatformItemHandler> getItemHandler(Level level, BlockPos pos, Direction side);
	Optional<PlatformFluidHandler> getFluidHandler(Level level, BlockPos pos, Direction side);
	Optional<PlatformEnergyHandler> getEnergyHandler(Level level, BlockPos pos, Direction side);
}
