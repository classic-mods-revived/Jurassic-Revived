package net.cmr.jurassicrevived.platform.transfer;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public interface InternalFluidProvider {
	InternalFluidHandler getFluidHandler(@Nullable Direction side);
}
