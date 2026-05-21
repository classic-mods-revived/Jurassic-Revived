package net.cmr.jurassicrevived;

import net.cmr.jurassicrevived.client.config.JRClothConfigScreens;
import net.cmr.jurassicrevived.worldgen.NeoForgeBiomeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class JRMod {

    public JRMod(IEventBus eventBus) {
		NeoForgeBiomeModifiers.register(eventBus);

        CommonClass.init();

        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(
            IConfigScreenFactory.class,
			(Supplier<IConfigScreenFactory>) () -> (client, parent) -> JRClothConfigScreens.create(parent)
		);

		// Add this to initialize client-side logic on NeoForge
		if (FMLEnvironment.dist.isClient()) {
			CommonClientClass.init();
		}
    }
}