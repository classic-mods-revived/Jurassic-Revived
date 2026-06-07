package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class FabricBlockLootTableProvider extends net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider implements ModBlockLootTableProvider.BlockLootHelper {

    //? if >1.20.1 {
    /*public FabricBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
    *///?} else {
    public FabricBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    //?}

    @Override
    public void generate() {
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
        //? if >1.20.1 {
        /*HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
        *///?} else {
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
        //?}
    }

	@Override
	public LootTable.Builder createRandomOreDrops(Block block, Item silkTouchDrop, Item firstDrop, Item secondDrop, float minRolls, float maxRolls, double firstDropChance) {
		return this.createSilkTouchDispatchTable(block,
			AlternativesEntry.alternatives(
				this.applyExplosionDecay(block,
					LootItem.lootTableItem(firstDrop)
						.when(LootItemRandomChanceCondition.randomChance((float) firstDropChance))
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(minRolls, maxRolls)))),
				this.applyExplosionDecay(block,
					LootItem.lootTableItem(secondDrop)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(minRolls, maxRolls))))
			));
	}

    @Override
    public LootTable.Builder createPotFlowerItemTable(Block block) {
        return super.createPotFlowerItemTable(block);
    }
}
