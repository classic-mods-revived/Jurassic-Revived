package net.cmr.jurassicrevived;

import dev.architectury.event.events.common.LifecycleEvent;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.ModCreativeTabs;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.networking.ModPackets;
import net.cmr.jurassicrevived.platform.Services;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.sound.ModSounds;
import net.cmr.jurassicrevived.util.FenceClimbHandler;
import net.cmr.jurassicrevived.util.ModEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass
{

	// The loader specific projects are able to import and use any code from the common project. This allows you to
	// write the majority of your code here and load it from your loader specific projects. This example has some
	// code that gets invoked by the entry point of the loader specific projects.
	public static void init() {

		// It is common for all supported loaders to provide a similar feature that can not be used directly in the
		// common code. A popular way to get around this is using Java's built-in service loader feature to create
		// your own abstraction layer. You can learn more about this in our provided services class. In this example
		// we have an interface in the common code and use a loader specific implementation to delegate our call to
		// the platform specific approach.
		//if (Services.PLATFORM.isModLoaded("examplemod")) {
        //
		//	Constants.LOG.info("Hello to examplemod");
		//}

		// Load config from the loader-specific config dir
		JRConfigManager.load(Services.PLATFORM.getConfigDir());

		ModBlocks.register();
		ModEntities.register();
		ModItems.register();
		ModCreativeTabs.register();
		ModEntities.registerAttributes();

		ModMenuTypes.register();

		ModRecipes.register();

		LifecycleEvent.SETUP.register(() -> {
			ModBlocks.setupPots();
			//ModEntities.registerSpawnPlacements();
		});

		ModBlockEntities.register();

		FenceClimbHandler.register();

		ModEvents.init();

		ModSounds.register();
		ModPackets.register();
	}
}