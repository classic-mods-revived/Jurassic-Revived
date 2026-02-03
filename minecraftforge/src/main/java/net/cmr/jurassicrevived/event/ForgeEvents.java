// ... existing code ...
package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.platform.ForgeEnergyStorage;
import net.cmr.jurassicrevived.platform.ForgeTankFluidAdapter;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
		BlockEntity be = event.getObject();

		if (be instanceof PowerCellBlockEntity pc) {
			event.addCapability(Constants.rl("energy_power_cell"),
				new EnergyProvider(() -> new ForgeEnergyStorage(pc.getEnergyStorage(null))));
		}

		if (be instanceof GeneratorBlockEntity gen) {
			event.addCapability(Constants.rl("energy_generator"),
				new EnergyProvider(() -> new ForgeEnergyStorage(gen.getEnergyStorage(null))));
		}

		if (be instanceof TankBlockEntity tank) {
			event.addCapability(Constants.rl("fluid_tank"),
				new FluidProvider(() -> new ForgeTankFluidAdapter(tank.getTank(null))));
		}

		if (JRConfigManager.get().requirePower) {
			if (be instanceof DNAExtractorBlockEntity e) {
				event.addCapability(Constants.rl("energy_dna_extractor"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof DNAAnalyzerBlockEntity e) {
				event.addCapability(Constants.rl("energy_dna_analyzer"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof FossilCleanerBlockEntity e) {
				event.addCapability(Constants.rl("energy_fossil_cleaner"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof FossilGrinderBlockEntity e) {
				event.addCapability(Constants.rl("energy_fossil_grinder"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof DNAHybridizerBlockEntity e) {
				event.addCapability(Constants.rl("energy_dna_hybridizer"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof EmbryonicMachineBlockEntity e) {
				event.addCapability(Constants.rl("energy_embryonic_machine"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof EmbryoCalcificationMachineBlockEntity e) {
				event.addCapability(Constants.rl("energy_embryo_calcification"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
			if (be instanceof IncubatorBlockEntity e) {
				event.addCapability(Constants.rl("energy_incubator"),
					new EnergyProvider(() -> new ForgeEnergyStorage(e.getEnergyStorage(null))));
			}
		}
	}

	private static final class EnergyProvider implements ICapabilityProvider {
		private final LazyOptional<?> lazy;

		private EnergyProvider(NonNullSupplier<?> supplier) {
			this.lazy = LazyOptional.of(supplier);
		}

		@Override
		public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
			return cap == ForgeCapabilities.ENERGY ? lazy.cast() : LazyOptional.empty();
		}
	}

	private static final class FluidProvider implements ICapabilityProvider {
		private final LazyOptional<?> lazy;

		private FluidProvider(NonNullSupplier<?> supplier) {
			this.lazy = LazyOptional.of(supplier);
		}

		@Override
		public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
			return cap == ForgeCapabilities.FLUID_HANDLER ? lazy.cast() : LazyOptional.empty();
		}
	}
}