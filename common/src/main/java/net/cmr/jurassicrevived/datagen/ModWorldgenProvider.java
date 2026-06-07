package net.cmr.jurassicrevived.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.worldgen.ModSpawnDefinitions;
import net.cmr.jurassicrevived.worldgen.ModWorldgenDefinitions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ModWorldgenProvider implements DataProvider {
	private final PackOutput output;

	public ModWorldgenProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cachedOutput) {
		CompletableFuture<?>[] oreFutures = ModWorldgenDefinitions.ORES.stream()
			.flatMap(ore -> java.util.stream.Stream.of(
				saveConfiguredFeature(cachedOutput, ore),
				savePlacedFeature(cachedOutput, ore),
				saveForgeAddFeatureBiomeModifier(cachedOutput, ore),
				saveNeoForgeAddFeatureBiomeModifier(cachedOutput, ore)
			))
			.toArray(CompletableFuture[]::new);

		CompletableFuture<?>[] spawnFutures = ModSpawnDefinitions.NATURAL_SPAWNS.stream()
			.flatMap(spawn -> java.util.stream.Stream.concat(
				java.util.stream.IntStream.range(0, spawn.biomeTags().size())
					.mapToObj(index -> saveForgeAddSpawnBiomeModifier(cachedOutput, spawn, index)),
				java.util.stream.IntStream.range(0, spawn.biomeTags().size())
					.mapToObj(index -> saveNeoForgeAddSpawnBiomeModifier(cachedOutput, spawn, index))
			))
			.toArray(CompletableFuture[]::new);

		CompletableFuture<?> allGenerators = CompletableFuture.allOf(
			java.util.stream.Stream.concat(
				java.util.Arrays.stream(oreFutures),
				java.util.Arrays.stream(spawnFutures)
			).toArray(CompletableFuture[]::new)
		);

		return CompletableFuture.allOf(allGenerators, saveIsSnowyFallbackTag(cachedOutput));

	}

	private CompletableFuture<?> saveForgeAddFeatureBiomeModifier(CachedOutput cachedOutput, ModWorldgenDefinitions.OreDefinition ore) {
		JsonObject root = new JsonObject();
		root.addProperty("type", "forge:add_features");
		// Dynamically grab the biome tag
		root.addProperty("biomes", "#" + ore.biomeTag().location().toString());
		root.addProperty("features", Constants.rl(ore.name() + "_placed").toString());
		root.addProperty("step", "underground_ores");

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/forge/biome_modifier/add_" + ore.name() + ".json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private CompletableFuture<?> saveNeoForgeAddFeatureBiomeModifier(CachedOutput cachedOutput, ModWorldgenDefinitions.OreDefinition ore) {
		JsonObject root = new JsonObject();
		root.addProperty("type", "neoforge:add_features");
		// Dynamically grab the biome tag
		root.addProperty("biomes", "#" + ore.biomeTag().location().toString());
		root.addProperty("features", Constants.rl(ore.name() + "_placed").toString());
		root.addProperty("step", "underground_ores");

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/neoforge/biome_modifier/add_" + ore.name() + ".json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private CompletableFuture<?> saveForgeAddSpawnBiomeModifier(CachedOutput cachedOutput, ModSpawnDefinitions.SpawnDefinition spawn, int biomeTagIndex) {
		JsonObject root = createConditionalAddSpawnBiomeModifier(spawn, biomeTagIndex);

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/forge/biome_modifier/spawn_" + spawn.name() + "_" + biomeTagIndex + ".json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private CompletableFuture<?> saveNeoForgeAddSpawnBiomeModifier(CachedOutput cachedOutput, ModSpawnDefinitions.SpawnDefinition spawn, int biomeTagIndex) {
		JsonObject root = createConditionalAddSpawnBiomeModifier(spawn, biomeTagIndex);

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/neoforge/biome_modifier/spawn_" + spawn.name() + "_" + biomeTagIndex + ".json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private JsonObject createConditionalAddSpawnBiomeModifier(ModSpawnDefinitions.SpawnDefinition spawn, int biomeTagIndex) {
		JsonObject root = new JsonObject();
		root.addProperty("type", Constants.rl("conditional_add_spawns").toString());
		root.addProperty("biomes", "#" + spawn.biomeTags().get(biomeTagIndex).location());

		JsonArray spawners = new JsonArray();
		JsonObject spawner = new JsonObject();

		EntityType<?> entityType = spawn.entityType().get();
		spawner.addProperty("type", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
		spawner.addProperty("weight", spawn.weight());
		spawner.addProperty("minCount", spawn.minCount());
		spawner.addProperty("maxCount", spawn.maxCount());

		spawners.add(spawner);
		root.add("spawners", spawners);

		return root;
	}

	private CompletableFuture<?> saveConfiguredFeature(CachedOutput cachedOutput, ModWorldgenDefinitions.OreDefinition ore) {
		JsonObject root = new JsonObject();
		root.addProperty("type", "minecraft:ore");

		JsonObject config = new JsonObject();
		config.addProperty("discard_chance_on_air_exposure", 0.0);
		config.addProperty("size", ore.veinSize());

		JsonArray targets = new JsonArray();

		JsonObject targetEntry = new JsonObject();

		JsonObject state = new JsonObject();
		ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(ore.block().get());
		state.addProperty("Name", blockId.toString());
		targetEntry.add("state", state);

		JsonObject target = new JsonObject();
		target.addProperty("predicate_type", "minecraft:tag_match");
		target.addProperty("tag", ore.replaceableTag().location().toString());
		targetEntry.add("target", target);

		targets.add(targetEntry);
		config.add("targets", targets);

		root.add("config", config);

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/worldgen/configured_feature/" + ore.name() + ".json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private CompletableFuture<?> savePlacedFeature(CachedOutput cachedOutput, ModWorldgenDefinitions.OreDefinition ore) {
		JsonObject root = new JsonObject();
		root.addProperty("feature", Constants.rl(ore.name()).toString());

		JsonArray placement = new JsonArray();

		JsonObject count = new JsonObject();
		count.addProperty("type", "minecraft:count");
		count.addProperty("count", ore.count());
		placement.add(count);

		JsonObject inSquare = new JsonObject();
		inSquare.addProperty("type", "minecraft:in_square");
		placement.add(inSquare);

		JsonObject heightRange = new JsonObject();
		heightRange.addProperty("type", "minecraft:height_range");

		JsonObject height = new JsonObject();
		height.addProperty("type", "minecraft:uniform");

		JsonObject min = new JsonObject();
		min.addProperty("absolute", ore.minY());

		JsonObject max = new JsonObject();
		max.addProperty("absolute", ore.maxY());

		height.add("min_inclusive", min);
		height.add("max_inclusive", max);

		heightRange.add("height", height);
		placement.add(heightRange);

		JsonObject biome = new JsonObject();
		biome.addProperty("type", "minecraft:biome");
		placement.add(biome);

		root.add("placement", placement);

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/worldgen/placed_feature/" + ore.name() + "_placed.json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	private CompletableFuture<?> saveIsSnowyFallbackTag(CachedOutput cachedOutput) {
		JsonObject root = new JsonObject();
		root.addProperty("replace", false);

		JsonArray values = new JsonArray();

		// Optional Forge Tag
		JsonObject forgeTag = new JsonObject();
		forgeTag.addProperty("id", "#forge:is_snowy");
		forgeTag.addProperty("required", false);
		values.add(forgeTag);

		// Optional Fabric Tag
		JsonObject fabricTag = new JsonObject();
		fabricTag.addProperty("id", "#c:snowy");
		fabricTag.addProperty("required", false);
		values.add(fabricTag);

		// Vanilla Fallbacks
		values.add("minecraft:snowy_plains");
		values.add("minecraft:ice_spikes");
		values.add("minecraft:snowy_taiga");
		values.add("minecraft:snowy_beach");
		values.add("minecraft:grove");
		values.add("minecraft:snowy_slopes");
		values.add("minecraft:jagged_peaks");
		values.add("minecraft:frozen_peaks");

		root.add("values", values);

		Path path = output.getOutputFolder()
			.resolve("data/" + Constants.MOD_ID + "/tags/worldgen/biome/is_snowy.json");

		return DataProvider.saveStable(cachedOutput, root, path);
	}

	@Override
	public String getName() {
		return "Jurassic Revived Worldgen";
	}
}
