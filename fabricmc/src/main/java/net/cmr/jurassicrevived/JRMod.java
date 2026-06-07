package net.cmr.jurassicrevived;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.cmr.jurassicrevived.worldgen.ModWorldgenDefinitions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.cmr.jurassicrevived.platform.FabricEnergyWrapper;
import net.cmr.jurassicrevived.platform.FabricTransferHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.world.level.levelgen.GenerationStep;
import team.reborn.energy.api.EnergyStorage;

public class JRMod implements ModInitializer
{

	@Override
	public void onInitialize() {

		// This method is invoked by the Fabric mod loader when it is ready
		// to load your mod. You can access Fabric and Common code in this
		// project.

		// Use Fabric to bootstrap the Common mod.
		CommonClass.init();

		for (ModWorldgenDefinitions.OreDefinition ore : ModWorldgenDefinitions.ORES) {
			BiomeModifications.addFeature(
				BiomeSelectors.tag(ore.biomeTag()), // Dynamically uses IS_OVERWORLD or IS_SNOWY
				GenerationStep.Decoration.UNDERGROUND_ORES,
				ore.placedFeatureKey()
			);
		}

		FluidStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricTransferHelper.InternalFluidStorage(((TankBlockEntity) be).getFluidHandler(side)),
			ModBlockEntities.TANK_BE.get()
		);

		FluidStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricTransferHelper.InternalFluidStorage(((FossilCleanerBlockEntity) be).getFluidHandler(side)),
			ModBlockEntities.FOSSIL_CLEANER_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((GeneratorBlockEntity) be).itemHandler, side),
			ModBlockEntities.GENERATOR_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((DNAExtractorBlockEntity) be).itemHandler, side),
			ModBlockEntities.DNA_EXTRACTOR_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((DNAAnalyzerBlockEntity) be).itemHandler, side),
			ModBlockEntities.DNA_ANALYZER_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((DNAHybridizerBlockEntity) be).itemHandler, side),
			ModBlockEntities.DNA_HYBRIDIZER_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((FossilCleanerBlockEntity) be).itemHandler, side),
			ModBlockEntities.FOSSIL_CLEANER_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((FossilGrinderBlockEntity) be).itemHandler, side),
			ModBlockEntities.FOSSIL_GRINDER_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((EmbryonicMachineBlockEntity) be).itemHandler, side),
			ModBlockEntities.EMBRYONIC_MACHINE_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((EmbryoCalcificationMachineBlockEntity) be).itemHandler, side),
			ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get()
		);

		ItemStorage.SIDED.registerForBlockEntities((be, side) ->
			InventoryStorage.of(((IncubatorBlockEntity) be).itemHandler, side),
			ModBlockEntities.INCUBATOR_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((PowerCellBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.POWER_CELL_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((GeneratorBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.GENERATOR_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((DNAExtractorBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.DNA_EXTRACTOR_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((DNAAnalyzerBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.DNA_ANALYZER_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((DNAHybridizerBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.DNA_HYBRIDIZER_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((FossilCleanerBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.FOSSIL_CLEANER_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((FossilGrinderBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.FOSSIL_GRINDER_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((EmbryonicMachineBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.EMBRYONIC_MACHINE_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((EmbryoCalcificationMachineBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get()
		);

		EnergyStorage.SIDED.registerForBlockEntities((be, side) ->
			new FabricEnergyWrapper(((IncubatorBlockEntity) be).getEnergyStorage(side)),
			ModBlockEntities.INCUBATOR_BE.get()
		);
	}
}
