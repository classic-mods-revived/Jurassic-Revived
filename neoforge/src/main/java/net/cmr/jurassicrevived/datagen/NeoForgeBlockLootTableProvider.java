package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.StreamSupport;

public class NeoForgeBlockLootTableProvider extends BlockLootSubProvider implements ModBlockLootTableProvider.BlockLootHelper {
    protected NeoForgeBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        ModBlockLootTableProvider.registerBlockLootTables(this);
    }

    @Override
    public void dropSelf(Block block) {
        super.dropSelf(block);
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }

    @Override
    public LootTable.Builder createMultipleOreDrops(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    public LootTable.Builder createPotFlowerItemTable(Block block) {
        return super.createPotFlowerItemTable(block);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return StreamSupport.stream(ModBlocks.BLOCKS.spliterator(), false)
                .map(dev.architectury.registry.registries.RegistrySupplier::get)
                .toList();
    }
}
