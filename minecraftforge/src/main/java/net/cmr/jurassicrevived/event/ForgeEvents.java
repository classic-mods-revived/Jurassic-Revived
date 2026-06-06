// ... existing code ...
package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.platform.ForgeEnergyStorage;
import net.cmr.jurassicrevived.platform.ForgeTankFluidAdapter;
import net.cmr.jurassicrevived.platform.transfer.InternalFluidHandler;
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
import net.minecraftforge.items.wrapper.InvWrapper;
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
				new FluidProvider(() -> new ForgeInternalFluidHandlerAdapter(tank.getFluidHandler(null))));
		}

		if (be instanceof FossilCleanerBlockEntity cleaner) {
			event.addCapability(Constants.rl("fluid_fossil_cleaner"),
				new FluidProvider(() -> new ForgeInternalFluidHandlerAdapter(cleaner.getFluidHandler(null))));
		}

		if (be instanceof GeneratorBlockEntity gen) {
			event.addCapability(Constants.rl("item_generator"),
				new ItemProvider(() -> new InvWrapper(gen.itemHandler)));
		}
		if (be instanceof DNAExtractorBlockEntity dna) {
			event.addCapability(Constants.rl("item_dna_extractor"),
				new ItemProvider(() -> new InvWrapper(dna.itemHandler)));
		}
		if (be instanceof DNAAnalyzerBlockEntity dna) {
			event.addCapability(Constants.rl("item_dna_analyzer"),
				new ItemProvider(() -> new InvWrapper(dna.itemHandler)));
		}
		if (be instanceof DNAHybridizerBlockEntity dna) {
			event.addCapability(Constants.rl("item_dna_hybridizer"),
				new ItemProvider(() -> new InvWrapper(dna.itemHandler)));
		}
		if (be instanceof FossilCleanerBlockEntity fc) {
			event.addCapability(Constants.rl("item_fossil_cleaner"),
				new ItemProvider(() -> new InvWrapper(fc.itemHandler)));
		}
		if (be instanceof FossilGrinderBlockEntity fg) {
			event.addCapability(Constants.rl("item_fossil_grinder"),
				new ItemProvider(() -> new InvWrapper(fg.itemHandler)));
		}
		if (be instanceof EmbryonicMachineBlockEntity em) {
			event.addCapability(Constants.rl("item_embryonic_machine"),
				new ItemProvider(() -> new InvWrapper(em.itemHandler)));
		}
		if (be instanceof EmbryoCalcificationMachineBlockEntity ec) {
			event.addCapability(Constants.rl("item_embryo_calcification"),
				new ItemProvider(() -> new InvWrapper(ec.itemHandler)));
		}
		if (be instanceof IncubatorBlockEntity inc) {
			event.addCapability(Constants.rl("item_incubator"),
				new ItemProvider(() -> new InvWrapper(inc.itemHandler)));
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

	private static final class ItemProvider implements ICapabilityProvider {
		private final LazyOptional<?> lazy;

		private ItemProvider(NonNullSupplier<?> supplier) {
			this.lazy = LazyOptional.of(supplier);
		}

		@Override
		public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
			return cap == ForgeCapabilities.ITEM_HANDLER ? lazy.cast() : LazyOptional.empty();
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

	private static class ForgeInternalFluidHandlerAdapter implements net.minecraftforge.fluids.capability.IFluidHandler {
		private final InternalFluidHandler handler;

		public ForgeInternalFluidHandlerAdapter(InternalFluidHandler handler) {
			this.handler = handler;
		}

		@Override
		public int getTanks() {
			return 1;
		}

		@Override
		public @NotNull net.minecraftforge.fluids.FluidStack getFluidInTank(int tank) {
			if (tank != 0) return net.minecraftforge.fluids.FluidStack.EMPTY;
			var fluid = handler.getFluid();
			return new net.minecraftforge.fluids.FluidStack(fluid.getFluid(), (int) fluid.getAmount());
		}

		@Override
		public int getTankCapacity(int tank) {
			return tank == 0 ? (int) handler.getCapacity() : 0;
		}

		@Override
		public boolean isFluidValid(int tank, @NotNull net.minecraftforge.fluids.FluidStack stack) {
			return tank == 0;
		}

		@Override
		public int fill(net.minecraftforge.fluids.FluidStack resource, FluidAction action) {
			if (resource.isEmpty()) return 0;
			long filled = handler.fill(dev.architectury.fluid.FluidStack.create(resource.getFluid(), resource.getAmount()), action.simulate());
			return (int) filled;
		}

		@Override
		public @NotNull net.minecraftforge.fluids.FluidStack drain(net.minecraftforge.fluids.FluidStack resource, FluidAction action) {
			if (resource.isEmpty()) return net.minecraftforge.fluids.FluidStack.EMPTY;
			var drained = handler.drain(resource.getAmount(), action.simulate());
			return new net.minecraftforge.fluids.FluidStack(drained.getFluid(), (int) drained.getAmount());
		}

		@Override
		public @NotNull net.minecraftforge.fluids.FluidStack drain(int maxDrain, FluidAction action) {
			var drained = handler.drain(maxDrain, action.simulate());
			return new net.minecraftforge.fluids.FluidStack(drained.getFluid(), (int) drained.getAmount());
		}
	}
}