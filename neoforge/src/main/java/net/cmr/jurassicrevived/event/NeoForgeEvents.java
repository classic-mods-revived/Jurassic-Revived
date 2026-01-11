package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

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

         Energy
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.POWER_CELL_BE.get(), (be, side) -> be.getEnergyStorage(side));
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GENERATOR_BE.get(), (be, side) -> be.getEnergyStorage(side));

         Fluids
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.TANK_BE.get(), (be, side) -> be.getTank(side));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FOSSIL_CLEANER_BE.get(), (be, side) -> be.getTank(side));

         Machine Logic (if config allows)
        if (JRConfigManager.get().requirePower) {
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_EXTRACTOR_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_ANALYZER_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.FOSSIL_CLEANER_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.FOSSIL_GRINDER_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DNA_HYBRIDIZER_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EMBRYONIC_MACHINE_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BE.get(), (be, side) -> be.getEnergyStorage(side));
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.INCUBATOR_BE.get(), (be, side) -> be.getEnergyStorage(side));
        }

         */
	}
}
