package net.cmr.jurassicrevived.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConditionalAddSpawnsBiomeModifier implements BiomeModifier {
	public static final Codec<ConditionalAddSpawnsBiomeModifier> CODEC = RecordCodecBuilder.create(instance ->
		instance.group(
			Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConditionalAddSpawnsBiomeModifier::biomes),
			MobSpawnSettings.SpawnerData.CODEC.listOf().fieldOf("spawners").forGetter(ConditionalAddSpawnsBiomeModifier::spawners)
		).apply(instance, ConditionalAddSpawnsBiomeModifier::new)
	);

	private final HolderSet<Biome> biomes;
	private final List<MobSpawnSettings.SpawnerData> spawners;

	public ConditionalAddSpawnsBiomeModifier(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners) {
		this.biomes = biomes;
		this.spawners = spawners;
	}

	public HolderSet<Biome> biomes() {
		return biomes;
	}

	public List<MobSpawnSettings.SpawnerData> spawners() {
		return spawners;
	}

	@Override
	public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, @NotNull ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase != Phase.ADD) {
			return;
		}

		if (!JRConfigManager.get().naturallySpawning) {
			return;
		}

		if (!biomes.contains(biome)) {
			return;
		}

		for (MobSpawnSettings.SpawnerData spawner : spawners) {
			builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, spawner);
		}
	}

	@Override
	public @NotNull Codec<? extends BiomeModifier> codec() {
		return ForgeBiomeModifiers.CONDITIONAL_ADD_SPAWNS.get();
	}
}