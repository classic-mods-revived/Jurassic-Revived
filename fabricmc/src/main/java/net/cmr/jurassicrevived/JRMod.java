package net.cmr.jurassicrevived;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

public class JRMod implements ModInitializer
{

	@Override
	public void onInitialize() {

		// This method is invoked by the Fabric mod loader when it is ready
		// to load your mod. You can access Fabric and Common code in this
		// project.

		// Use Fabric to bootstrap the Common mod.
		Constants.LOG.info("Hello Fabric world!");
		CommonClass.init();

		/*
		 Items (Fabric Transfer API)
		ItemStorage.SIDED.registerForBlockEntities((be, side) -> (Storage<ItemVariant>) ((CrateBlockEntity)be).itemHandler, ModBlockEntities.CRATE_BE.get());

		 Energy (TeamReborn Energy API is standard for Fabric)
		EnergyStorage.SIDED.registerForBlockEntities((be, side) -> ((PowerCellBlockEntity)be).getEnergyStorage(side), ModBlockEntities.POWER_CELL_BE.get());

		 Fluids (Fabric Transfer API)
		FluidStorage.SIDED.registerForBlockEntities((be, side) -> ((TankBlockEntity)be).getTank(side), ModBlockEntities.TANK_BE.get());
		 */
	}
}
