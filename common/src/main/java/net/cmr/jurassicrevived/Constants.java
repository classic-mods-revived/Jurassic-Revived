package net.cmr.jurassicrevived;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants
{

	public static final String MOD_ID = "jurassicrevived";
	public static final String MOD_NAME = "JurassicRevived";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation rl(String path) {
		//? if >1.20.1 {
		/*return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
		 *///?} else {
		return new ResourceLocation(MOD_ID, path);
		//?}
	}
}