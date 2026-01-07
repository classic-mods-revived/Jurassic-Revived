package net.cmr.jurassicrevived.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> TABS =
		DeferredRegister.create(Constants.MOD_ID, Registries.CREATIVE_MODE_TAB);

	public static final RegistrySupplier<CreativeModeTab> JURASSIC_TAB = TABS.register("jurassic_tab",
		() -> CreativeTabRegistry.create(
			Component.translatable("itemGroup." + Constants.MOD_ID + ".jurassic_tab"),
			() -> new ItemStack(ModItems.AMBER_SHARD.get()) // Tab Icon
		));

	public static void register() {
		// Items must be explicitly added to tabs in 1.20+
		// This usually goes in your Common setup, but you can trigger it here if your
		// loader-specific entry points call this method.

		TABS.register();
	}
}