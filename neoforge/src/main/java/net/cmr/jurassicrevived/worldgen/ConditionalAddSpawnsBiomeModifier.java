package net.cmr.jurassicrevived.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConditionalAddSpawnsBiomeModifier implements BiomeModifier {
	public static final MapCodec<ConditionalAddSpawnsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Biome.LIST_CODEC.fieldOf("biomes").forGetter(modifier -> modifier.delegate.biomes()),
			MobSpawnSettings.SpawnerData.CODEC.listOf().fieldOf("spawners").forGetter(modifier -> modifier.delegate.spawners())
		).apply(instance, ConditionalAddSpawnsBiomeModifier::new)
	);

	private final BiomeModifiers.AddSpawnsBiomeModifier delegate;

	public ConditionalAddSpawnsBiomeModifier(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners) {
		this.delegate = new BiomeModifiers.AddSpawnsBiomeModifier(biomes, spawners);
	}

	@Override
	public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, @NotNull ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (JRConfigManager.get().naturallySpawning) {
			delegate.modify(biome, phase, builder);
		}
	}

	@Override
	public @NotNull MapCodec<? extends BiomeModifier> codec() {
		return NeoForgeBiomeModifiers.CONDITIONAL_ADD_SPAWNS.get();
	}
}
