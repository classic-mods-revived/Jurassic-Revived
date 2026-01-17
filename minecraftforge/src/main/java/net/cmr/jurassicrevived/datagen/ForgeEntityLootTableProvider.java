package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ForgeEntityLootTableProvider extends EntityLootSubProvider implements ModEntityLootTableProvider.EntityLootHelper {

    public ForgeEntityLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
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
