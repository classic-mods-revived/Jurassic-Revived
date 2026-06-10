package net.cmr.jurassicrevived.worldgen;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Supplier;

public final class ModWorldgenDefinitions {
	private ModWorldgenDefinitions() {
	}
	public static final TagKey<Biome> SNOWY_BIOMES = TagKey.create(Registries.BIOME, Constants.rl("is_snowy"));


	public static final List<OreDefinition> ORES = List.of(
		new OreDefinition(
			"gypsum_stone",
			ModBlocks.GYPSUM_STONE,
			BlockTags.STONE_ORE_REPLACEABLES,
			18,
			20,
			32,
			64,
			BiomeTags.IS_OVERWORLD
		),
		new OreDefinition(
			"stone_fossil",
			ModBlocks.STONE_FOSSIL,
			BlockTags.STONE_ORE_REPLACEABLES,
			8,
			15,
			0,
			64,
			BiomeTags.IS_OVERWORLD
		),
		new OreDefinition(
			"deepslate_fossil",
			ModBlocks.DEEPSLATE_FOSSIL,
			BlockTags.DEEPSLATE_ORE_REPLACEABLES,
			8,
			15,
			-32,
			0,
			BiomeTags.IS_OVERWORLD
		),
		new OreDefinition(
			"amber_ore",
			ModBlocks.AMBER_ORE,
			BlockTags.STONE_ORE_REPLACEABLES,
			3,
			4,
			0,
			32,
			BiomeTags.IS_OVERWORLD
		),
		new OreDefinition(
			"deepslate_ice_shard_ore",
			ModBlocks.DEEPSLATE_ICE_SHARD_ORE,
			BlockTags.DEEPSLATE_ORE_REPLACEABLES,
			3,
			6,
			-32,
			0,
			BiomeTags.IS_OVERWORLD
		),
		new OreDefinition(
			"permafrost",
			ModBlocks.PERMAFROST,
			BlockTags.DIRT,
			8,
			3,
			60,
			140,
			SNOWY_BIOMES
		)
	);

	public record OreDefinition(
		String name,
		Supplier<? extends Block> block,
		TagKey<Block> replaceableTag,
		int veinSize,
		int count,
		int minY,
		int maxY,
		TagKey<Biome> biomeTag
	) {
		public ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey() {
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, Constants.rl(name));
		}

		public ResourceKey<PlacedFeature> placedFeatureKey() {
			return ResourceKey.create(Registries.PLACED_FEATURE, Constants.rl(name + "_placed"));
		}
	}
}