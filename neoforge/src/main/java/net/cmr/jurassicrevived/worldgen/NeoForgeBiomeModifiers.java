package net.cmr.jurassicrevived.worldgen;

import com.mojang.serialization.MapCodec;
import net.cmr.jurassicrevived.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class NeoForgeBiomeModifiers {
	private NeoForgeBiomeModifiers() {
	}

	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
		DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

	public static final Supplier<MapCodec<ConditionalAddSpawnsBiomeModifier>> CONDITIONAL_ADD_SPAWNS =
		BIOME_MODIFIER_SERIALIZERS.register("conditional_add_spawns", () -> ConditionalAddSpawnsBiomeModifier.CODEC);

	public static void register(IEventBus eventBus) {
		BIOME_MODIFIER_SERIALIZERS.register(eventBus);
	}
}
