package net.cmr.jurassicrevived.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS =
		DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK);

	// --- Examples ---
	public static final RegistrySupplier<Block> FOSSIL_ORE = registerBlock("fossil_ore",
		() -> new Block(BlockBehaviour.Properties.of().strength(3.0f)));


	// --- Helper Methods ---

	/**
	 * Registers a block and a corresponding BlockItem.
	 */

	private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
		RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistrySupplier<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register() {
		BLOCKS.register();
	}
}