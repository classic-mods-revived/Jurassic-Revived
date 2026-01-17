package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(event.includeClient(), new NeoForgeBlockStateProvider(packOutput, existingFileHelper));
		generator.addProvider(event.includeClient(), new NeoForgeItemModelProvider(packOutput, existingFileHelper));

		BlockTagsProvider blockTagsProvider = new NeoForgeBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTagsProvider);
		generator.addProvider(event.includeServer(), new NeoForgeItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

		generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
				List.of(
						new LootTableProvider.SubProviderEntry(NeoForgeBlockLootTableProvider::new, LootContextParamSets.BLOCK),
						new LootTableProvider.SubProviderEntry(NeoForgeEntityLootTableProvider::new, LootContextParamSets.ENTITY)
				),
				lookupProvider
		));

		generator.addProvider(event.includeServer(), new NeoForgeGlobalLootModifierProvider(packOutput));
	}
}
