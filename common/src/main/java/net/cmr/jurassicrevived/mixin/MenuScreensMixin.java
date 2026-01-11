package net.cmr.jurassicrevived.mixin;

import dev.architectury.registry.menu.MenuRegistry;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.screen.custom.*;
import net.minecraft.client.gui.screens.MenuScreens;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MenuScreens.class)
public class MenuScreensMixin {
    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void onClinit(CallbackInfo ci) {
		//? if >1.20.1 {
        /*// This runs when MenuScreens class is loaded by the JVM
        // Registering here ensures the MenuTypes are already constructed and the registries are ready
        MenuRegistry.registerScreenFactory(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DNAExtractorScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_ANALYZER_MENU.get(), DNAAnalyzerScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.FOSSIL_GRINDER_MENU.get(), FossilGrinderScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.FOSSIL_CLEANER_MENU.get(), FossilCleanerScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_HYBRIDIZER_MENU.get(), DNAHybridizerScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(), EmbryonicMachineScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.EMBRYO_CALCIFICATION_MACHINE_MENU.get(), EmbryoCalcificationMachineScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.INCUBATOR_MENU.get(), IncubatorScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.TANK_MENU.get(), TankScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.POWER_CELL_MENU.get(), PowerCellScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.WOOD_CRATE_MENU.get(), CrateScreen::new);
        MenuRegistry.registerScreenFactory(ModMenuTypes.IRON_CRATE_MENU.get(), CrateScreen::new);
		*///?}
    }
}
