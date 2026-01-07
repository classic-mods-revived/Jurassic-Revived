package net.cmr.jurassicrevived.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);

	// --- Examples ---
	public static final RegistrySupplier<Item> AMBER_SHARD = ITEMS.register("amber_shard",
		() -> new Item(new Item.Properties()));

	public static final RegistrySupplier<Item> DNA_SYRINGE = ITEMS.register("dna_syringe",
		() -> new Item(new Item.Properties().stacksTo(1)));

	public static void register() {
		ITEMS.register();
	}
}