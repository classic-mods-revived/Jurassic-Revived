package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgeBlockTagProvider extends BlockTagsProvider implements ModBlockTagProvider.BlockTagHelper {
    public ForgeBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModBlockTagProvider.registerBlockTags(this);
    }

    @Override
    public void tag(TagKey<Block> tag, Block... blocks) {
        tag(tag).add(blocks);
    }
}
