package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NeoForgeEntityLootTableProvider extends EntityLootSubProvider implements ModEntityLootTableProvider.EntityLootHelper {

    public NeoForgeEntityLootTableProvider(HolderLookup.Provider provider) {
        super(FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    public void generate() {
        ModEntityLootTableProvider.registerEntityLootTables(this);
    }

    @Override
    public void add(EntityType<?> type, LootTable.Builder builder) {
        super.add(type, builder);
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return StreamSupport.stream(ModEntities.ENTITIES.spliterator(), false)
                .map(dev.architectury.registry.registries.RegistrySupplier::get);
    }
}
