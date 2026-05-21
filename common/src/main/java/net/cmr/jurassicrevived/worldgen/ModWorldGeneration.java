package net.cmr.jurassicrevived.worldgen;

import dev.architectury.registry.level.biome.BiomeModifications;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModWorldGeneration {
	private static final RuleTest STONE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	private static final RuleTest DEEPSLATE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

	private static final Supplier<PlacedFeature> GYPSUM_STONE = () -> oreFeature(
		STONE_REPLACEABLES,
		() -> ModBlocks.GYPSUM_STONE.get().defaultBlockState(),
		18,
		20,
		32,
		64
	);

	private static final Supplier<PlacedFeature> STONE_FOSSIL = () -> oreFeature(
		STONE_REPLACEABLES,
		() -> ModBlocks.STONE_FOSSIL.get().defaultBlockState(),
		8,
		15,
		0,
		64
	);

	private static final Supplier<PlacedFeature> DEEPSLATE_FOSSIL = () -> oreFeature(
		DEEPSLATE_REPLACEABLES,
		() -> ModBlocks.DEEPSLATE_FOSSIL.get().defaultBlockState(),
		8,
		15,
		-32,
		0
	);

	private static final Supplier<PlacedFeature> AMBER_ORE = () -> oreFeature(
		STONE_REPLACEABLES,
		() -> ModBlocks.AMBER_ORE.get().defaultBlockState(),
		3,
		4,
		0,
		32
	);

	private static final Supplier<PlacedFeature> DEEPSLATE_ICE_SHARD_ORE = () -> oreFeature(
		DEEPSLATE_REPLACEABLES,
		() -> ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get().defaultBlockState(),
		3,
		6,
		-32,
		0
	);

	public static void generateWorldGen() {
		addOverworldOre(GYPSUM_STONE);
		addOverworldOre(STONE_FOSSIL);
		addOverworldOre(DEEPSLATE_FOSSIL);
		addOverworldOre(AMBER_ORE);
		addOverworldOre(DEEPSLATE_ICE_SHARD_ORE);

		Constants.LOG.info("Natural dinosaur spawning config loaded as: {}", JRConfigManager.get().naturallySpawning);

		if (JRConfigManager.get().naturallySpawning) {
			Constants.LOG.info("Registering natural dinosaur biome spawns");
			addSpawns();
		} else {
			Constants.LOG.info("Skipping natural dinosaur biome spawns");
		}
	}

	private static void addOverworldOre(Supplier<PlacedFeature> placedFeature) {
		BiomeModifications.addProperties(
			context -> context.hasTag(BiomeTags.IS_OVERWORLD),
			(context, properties) -> properties.getGenerationProperties()
				.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(placedFeature.get()))
		);
	}

	private static PlacedFeature oreFeature(RuleTest replaceables, Supplier<BlockState> blockState, int veinSize, int count, int minY, int maxY) {
		ConfiguredFeature<?, ?> configuredFeature = new ConfiguredFeature<>(
			Feature.ORE,
			new OreConfiguration(
				List.of(OreConfiguration.target(replaceables, blockState.get())),
				veinSize
			)
		);

		return new PlacedFeature(
			Holder.direct(configuredFeature),
			List.of(
				CountPlacement.of(count),
				InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
				BiomeFilter.biome()
			)
		);
	}

	private static void addSpawns() {
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA), ModEntities.ALBERTOSAURUS, 12, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.ALLOSAURUS, 10, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.ALVAREZSAURUS, 28, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.ANKYLOSAURUS, 14, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.APATOSAURUS, 10, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.ARAMBOURGIANIA, 6, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.BARYONYX, 8, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.BRACHIOSAURUS, 7, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CARCHARODONTOSAURUS, 6, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CARNOTAURUS, 11, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.CEARADACTYLUS, 6, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CERATOSAURUS, 9, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CHASMOSAURUS, 18, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_TAIGA), ModEntities.COELOPHYSIS, 30, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.COELURUS, 28, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.COMPSOGNATHUS, 36, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CONCAVENATOR, 10, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.CORYTHOSAURUS, 24, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.DEINONYCHUS, 14, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE), ModEntities.DILOPHOSAURUS, 22, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.DIMORPHODON, 7, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_TAIGA), ModEntities.DIPLODOCUS, 8, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.DRYOSAURUS, 32, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.EDMONTOSAURUS, 22, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.GALLIMIMUS, 36, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.GEOSTERNBERGIA, 7, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.GIGANOTOSAURUS, 3, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.GUANLONG, 20, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.GUIDRACO, 7, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.HADROSAURUS, 26, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.HERRERASAURUS, 24, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.HYPSILOPHODON, 34, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA), ModEntities.INOSTRANCEVIA, 5, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.LAMBEOSAURUS, 24, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.LUDODACTYLUS, 6, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.MAJUNGASAURUS, 8, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE), ModEntities.MAMENCHISAURUS, 7, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.METRIACANTHOSAURUS, 10, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.MOGANOPTERUS, 7, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.NYCTOSAURUS, 6, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.ORNITHOLESTES, 30, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.ORNITHOMIMUS, 30, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.OURANOSAURUS, 22, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.OVIRAPTOR, 34, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.PACHYCEPHALOSAURUS, 22, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.PARASAUROLOPHUS, 22, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_TAIGA), ModEntities.PROCERATOSAURUS, 24, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.PROCOMPSOGNATHUS, 34, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.PROTOCERATOPS, 28, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.PTERANODON, 8, 2, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.PTERODAUSTRO, 12, 2, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.QUETZALCOATLUS, 4, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.RAJASAURUS, 10, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_FOREST) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.RUGOPS, 10, 2, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.SEGISAURUS, 36, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.SHANTUNGOSAURUS, 8, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.SPINOSAURUS, 3, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.STEGOSAURUS, 14, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.STYRACOSAURUS, 22, 2, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.TAPEJARA, 6, 2, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.THERIZINOSAURUS, 8, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.TITANOSAURUS, 7, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.TRICERATOPS, 20, 3, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.TROODON, 28, 3, 6);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.TROPEOGNATHUS, 6, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_JUNGLE) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.TUPUXUARA, 6, 2, 5);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.TYRANNOSAURUS_REX, 5, 1, 2);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_TAIGA) || biome.hasTag(BiomeTags.IS_FOREST), ModEntities.UTAHRAPTOR, 16, 1, 3);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BADLANDS) || biome.hasTag(BiomeTags.IS_OVERWORLD), ModEntities.VELOCIRAPTOR, 26, 2, 4);
		addSpawn(biome -> biome.hasTag(BiomeTags.IS_BEACH) || biome.hasTag(BiomeTags.IS_MOUNTAIN), ModEntities.ZHENYUANOPTERUS, 7, 2, 5);
	}

	private static boolean is(BiomeModifications.BiomeContext biome, ResourceKey<Biome> key) {
		return biome.hasTag(BiomeTags.IS_TAIGA) || biome.getKey().equals(key);
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