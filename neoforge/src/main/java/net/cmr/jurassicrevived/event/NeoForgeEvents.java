package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.neoforge.capabilities.NeoForgeEnergyStorage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeEvents
{
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        /* Items
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GENERATOR_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DNA_EXTRACTOR_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DNA_ANALYZER_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DNA_HYBRIDIZER_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FOSSIL_CLEANER_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FOSSIL_GRINDER_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.EMBRYONIC_MACHINE_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CRATE_BE.get(), (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.INCUBATOR_BE.get(), (be, side) -> be.getItemHandler(side));
		*/

		// Energy
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.POWER_CELL_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((PowerCellBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GENERATOR_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((GeneratorBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_ANALYZER_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((DNAAnalyzerBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_EXTRACTOR_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((DNAExtractorBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_HYBRIDIZER_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((DNAHybridizerBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((EmbryoCalcificationMachineBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EMBRYONIC_MACHINE_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((EmbryonicMachineBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.FOSSIL_CLEANER_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((FossilCleanerBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.FOSSIL_GRINDER_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((FossilGrinderBlockEntity) be).getEnergyStorage(side)));

		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.INCUBATOR_BE.get(),
			(be, side) -> new NeoForgeEnergyStorage(((IncubatorBlockEntity) be).getEnergyStorage(side)));

		// Fluids
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.TANK_BE.get(),
			(be, side) -> new TankFluidAdapter(((TankBlockEntity) be).getTank(side)));
	}

	private record TankFluidAdapter(TankBlockEntity.TankFluidHandler tank) implements IFluidHandler {

		@Override
		public int getTanks() {
			return 1;
		}

		@Override
		public FluidStack getFluidInTank(int tankIndex) {
			if (tankIndex != 0) return FluidStack.EMPTY;
			return new FluidStack(tank.getFluid().getFluid(), (int) tank.getFluid().getAmount());
		}

		@Override
		public int getTankCapacity(int tankIndex) {
			return tankIndex == 0 ? (int) tank.getCapacity() : 0;
		}

		@Override
		public boolean isFluidValid(int tankIndex, FluidStack stack) {
			return tankIndex == 0;
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			if (resource.isEmpty()) return 0;
			long filled = tank.fill(dev.architectury.fluid.FluidStack.create(resource.getFluid(), resource.getAmount()), action.simulate());
			return (int) filled;
		}

		@Override
		public FluidStack drain(FluidStack resource, FluidAction action) {
			if (resource.isEmpty()) return FluidStack.EMPTY;
			var drained = tank.drain(resource.getAmount(), action.simulate());
			return new FluidStack(drained.getFluid(), (int) drained.getAmount());
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			var drained = tank.drain(maxDrain, action.simulate());
			return new FluidStack(drained.getFluid(), (int) drained.getAmount());
		}
	}
}
