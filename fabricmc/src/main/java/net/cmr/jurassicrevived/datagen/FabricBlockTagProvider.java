package net.cmr.jurassicrevived.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class FabricBlockTagProvider extends FabricTagProvider.BlockTagProvider implements ModBlockTagProvider.BlockTagHelper {

    public FabricBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        ModBlockTagProvider.registerBlockTags(this);
    }

    @Override
    public void tag(TagKey<Block> tag, Block... blocks) {
        getOrCreateTagBuilder(tag).add(blocks);
    }
}
