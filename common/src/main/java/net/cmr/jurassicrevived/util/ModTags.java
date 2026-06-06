package net.cmr.jurassicrevived.util;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static class Blocks {
		public static final TagKey<Block> AQUATIC_PLACEMENT_REPLACEABLES = createTag("aquatic_placement_replaceables");
		public static final TagKey<Block> INCUBATED_EGGS = createTag("incubated_eggs");
		public static final TagKey<Block> PLANTS = createTag("plants");

		private static TagKey<Block> createTag(String name) {
			//? if >1.20.1 {
			/*return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
			*///?} else {
			return TagKey.create(Registries.BLOCK, new ResourceLocation(Constants.MOD_ID, name));
			//?}
		}
	}
				public static class EntityTypes {
					public static TagKey<EntityType<?>> dino(String namespace, String entityIdPath) {
				return TagKey.create(Registries.ENTITY_TYPE, Constants.r2(namespace + ":" + entityIdPath));
			}

			public static TagKey<EntityType<?>> forgeDino(String entityIdPath) {
				return dino("forge", entityIdPath);
			}

			public static TagKey<EntityType<?>> neoforgeDino(String entityIdPath) {
				return dino("neoforge", entityIdPath);
			}

			public static TagKey<EntityType<?>> fabricDino(String entityIdPath) {
				return dino("fabric", entityIdPath);
			}
	}


	public static class Items {
		public static final TagKey<Item> TISSUES = createTag("tissues");
		public static final TagKey<Item> DNA = createTag("dna");
		public static final TagKey<Item> SYRINGES = createTag("syringes");
		public static final TagKey<Item> EGGS = createTag("eggs");
		public static final TagKey<Item> FOSSILS = createTag("fossils");
		public static final TagKey<Item> SKULLS = createTag("skulls");

		private static TagKey<Item> createTag(String name) {
			//? if >1.20.1 {
			/*return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
			*///?} else {
			return TagKey.create(Registries.ITEM, new ResourceLocation(Constants.MOD_ID, name));
			//?}
		}
	}
}