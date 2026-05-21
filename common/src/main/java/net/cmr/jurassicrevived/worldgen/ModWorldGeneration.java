package net.cmr.jurassicrevived.worldgen;

import dev.architectury.registry.level.biome.BiomeModifications;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModWorldGeneration {

	public static void generateWorldGen() {
		for (ModWorldgenDefinitions.OreDefinition ore : ModWorldgenDefinitions.ORES) {
			addOverworldOre(ore.placedFeatureKey());
		}

		Constants.LOG.info("Natural dinosaur spawning config loaded as: {}", JRConfigManager.get().naturallySpawning);

		if (JRConfigManager.get().naturallySpawning) {
			Constants.LOG.info("Registering natural dinosaur biome spawns");
			addSpawns();
		} else {
			Constants.LOG.info("Skipping natural dinosaur biome spawns");
		}
	}

	private static void addOverworldOre(ResourceKey<PlacedFeature> placedFeature) {
		BiomeModifications.addProperties(
			context -> context.hasTag(BiomeTags.IS_OVERWORLD),
			(context, properties) -> properties.getGenerationProperties()
				.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature)
		);
	}

	private static void addSpawns() {
		for (ModSpawnDefinitions.SpawnDefinition spawn : ModSpawnDefinitions.NATURAL_SPAWNS) {
			addSpawn(
				biome -> spawn.biomeTags().stream().anyMatch(biome::hasTag),
				spawn.entityType(),
				spawn.weight(),
				spawn.minCount(),
				spawn.maxCount()
			);
		}
	}

	private static void addSpawn(Predicate<BiomeModifications.BiomeContext> biomeSelector, Supplier<? extends EntityType<?>> entityType, int weight, int minCount, int maxCount) {
		BiomeModifications.addProperties(
			biomeSelector,
			(context, properties) -> properties.getSpawnProperties().addSpawn(
				MobCategory.CREATURE,
				new MobSpawnSettings.SpawnerData(entityType.get(), weight, minCount, maxCount)
			)
		);
	}
}