package net.cmr.jurassicrevived.platform;

import net.cmr.jurassicrevived.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper
{

	@Override
	public String getPlatformName() {

		return "NeoForge";
	}

	@Override
	public boolean isModLoaded(String modId) {

		return ModList.get().isLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		
		//? if >=1.21.9 {
		/*return !FMLLoader.getCurrent().isProduction();
		*///?} else {
		return !FMLLoader.isProduction();
		//?}
	}

	@Override
	public Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}
}