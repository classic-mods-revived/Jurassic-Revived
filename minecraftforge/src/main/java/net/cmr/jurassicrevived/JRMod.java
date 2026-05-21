package net.cmr.jurassicrevived;

import dev.architectury.platform.forge.EventBuses;
import net.cmr.jurassicrevived.client.config.JRClothConfigScreens;
import net.cmr.jurassicrevived.worldgen.ForgeBiomeModifiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class JRMod {

	@SuppressWarnings({"deprecation", "removal"})
	public JRMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		EventBuses.registerModEventBus(Constants.MOD_ID, modEventBus);
		ForgeBiomeModifiers.register(modEventBus);

		CommonClass.init();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommonClientClass::init);

		modEventBus.addListener(this::clientSetup);

		ModLoadingContext.get().registerExtensionPoint(
			ConfigScreenHandler.ConfigScreenFactory.class,
			() -> new ConfigScreenHandler.ConfigScreenFactory(
				(mc, parent) -> JRClothConfigScreens.create(parent)
			)
		);
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(CommonClientClass::registerScreens);
	}
}