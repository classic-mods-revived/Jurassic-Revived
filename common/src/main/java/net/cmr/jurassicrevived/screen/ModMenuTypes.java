package net.cmr.jurassicrevived.screen;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.screen.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS =
		DeferredRegister.create(Constants.MOD_ID, Registries.MENU);

	public static final RegistrySupplier<MenuType<GeneratorMenu>> GENERATOR_MENU =
		MENUS.register("generator_menu", () -> MenuRegistry.ofExtended(GeneratorMenu::new));
	public static final RegistrySupplier<MenuType<DNAExtractorMenu>> DNA_EXTRACTOR_MENU =
		MENUS.register("dna_extractor_menu", () -> MenuRegistry.ofExtended(DNAExtractorMenu::new));
	public static final RegistrySupplier<MenuType<DNAAnalyzerMenu>> DNA_ANALYZER_MENU =
		MENUS.register("dna_analyzer_menu", () -> MenuRegistry.ofExtended(DNAAnalyzerMenu::new));
	public static final RegistrySupplier<MenuType<FossilGrinderMenu>> FOSSIL_GRINDER_MENU =
		MENUS.register("fossil_grinder_menu", () -> MenuRegistry.ofExtended(FossilGrinderMenu::new));
	public static final RegistrySupplier<MenuType<FossilCleanerMenu>> FOSSIL_CLEANER_MENU =
		MENUS.register("fossil_cleaner_menu", () -> MenuRegistry.ofExtended(FossilCleanerMenu::new));
	public static final RegistrySupplier<MenuType<DNAHybridizerMenu>> DNA_HYBRIDIZER_MENU =
		MENUS.register("dna_hybridizer_menu", () -> MenuRegistry.ofExtended(DNAHybridizerMenu::new));
	public static final RegistrySupplier<MenuType<EmbryonicMachineMenu>> EMBRYONIC_MACHINE_MENU =
		MENUS.register("embryonic_machine_menu", () -> MenuRegistry.ofExtended(EmbryonicMachineMenu::new));
	public static final RegistrySupplier<MenuType<EmbryoCalcificationMachineMenu>> EMBRYO_CALCIFICATION_MACHINE_MENU =
		MENUS.register("embryo_calcification_machine_menu", () -> MenuRegistry.ofExtended(EmbryoCalcificationMachineMenu::new));
	public static final RegistrySupplier<MenuType<IncubatorMenu>> INCUBATOR_MENU =
		MENUS.register("incubator_menu", () -> MenuRegistry.ofExtended(IncubatorMenu::new));
	public static final RegistrySupplier<MenuType<CultivatorMenu>> CULTIVATOR_MENU =
		MENUS.register("cultivator_menu", () -> MenuRegistry.ofExtended(CultivatorMenu::new));
	public static final RegistrySupplier<MenuType<TankMenu>> TANK_MENU =
		MENUS.register("tank_menu", () -> MenuRegistry.ofExtended(TankMenu::new));
	public static final RegistrySupplier<MenuType<PowerCellMenu>> POWER_CELL_MENU =
		MENUS.register("power_cell_menu", () -> MenuRegistry.ofExtended(PowerCellMenu::new));
	public static final RegistrySupplier<MenuType<CrateMenu>> WOOD_CRATE_MENU =
		MENUS.register("wood_crate_menu", () -> MenuRegistry.ofExtended(CrateMenu::new));
	public static final RegistrySupplier<MenuType<CrateMenu>> IRON_CRATE_MENU =
		MENUS.register("iron_crate_menu", () -> MenuRegistry.ofExtended(CrateMenu::new));

	public static void register() {
		MENUS.register();
	}
}