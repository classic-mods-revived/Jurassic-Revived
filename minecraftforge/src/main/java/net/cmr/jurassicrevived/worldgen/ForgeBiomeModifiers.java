package net.cmr.jurassicrevived.worldgen;

import com.mojang.serialization.Codec;
import net.cmr.jurassicrevived.Constants;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ForgeBiomeModifiers {
	private ForgeBiomeModifiers() {
	}

	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
		DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

	public static final RegistryObject<Codec<ConditionalAddSpawnsBiomeModifier>> CONDITIONAL_ADD_SPAWNS =
		BIOME_MODIFIER_SERIALIZERS.register("conditional_add_spawns", () -> ConditionalAddSpawnsBiomeModifier.CODEC);

	public static void register(IEventBus eventBus) {
		BIOME_MODIFIER_SERIALIZERS.register(eventBus);
	}
}
