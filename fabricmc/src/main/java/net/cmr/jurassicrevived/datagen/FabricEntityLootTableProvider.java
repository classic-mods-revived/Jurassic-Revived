package net.cmr.jurassicrevived.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FabricEntityLootTableProvider extends SimpleFabricLootTableProvider implements ModEntityLootTableProvider.EntityLootHelper {

    //? if >1.20.1 {
    /*public FabricEntityLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        this.currentBiConsumer = biConsumer;
        ModEntityLootTableProvider.registerEntityLootTables(this);
        this.currentBiConsumer = null;
    }

    @Override
    public void add(EntityType<?> type, LootTable.Builder builder) {
        if (currentBiConsumer != null) {
            currentBiConsumer.accept(type.getDefaultLootTable(), builder);
        }
    }
    
    private BiConsumer<ResourceKey<LootTable>, LootTable.Builder> currentBiConsumer;
    *///?} else {
    public FabricEntityLootTableProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.currentBiConsumer = biConsumer;
        ModEntityLootTableProvider.registerEntityLootTables(this);
        this.currentBiConsumer = null;
    }

    @Override
    public void add(EntityType<?> type, LootTable.Builder builder) {
        if (currentBiConsumer != null) {
            currentBiConsumer.accept(type.getDefaultLootTable(), builder);
        }
    }
    
    private BiConsumer<ResourceLocation, LootTable.Builder> currentBiConsumer;
    //?}
}
