package net.cmr.jurassicrevived;

import dev.architectury.platform.forge.EventBuses;
import net.cmr.jurassicrevived.client.config.JRClothConfigScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class JRMod {

    @SuppressWarnings({"deprecation", "removal"})
    public JRMod() {

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

		EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Use Forge to bootstrap the Common mod.
        CommonClass.init();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommonClientClass::init);

		ModLoadingContext.get().registerExtensionPoint(
			ConfigScreenHandler.ConfigScreenFactory.class,
			() -> new ConfigScreenHandler.ConfigScreenFactory(
                (mc, parent) -> JRClothConfigScreens.create(parent)
            )
		);

    }
}