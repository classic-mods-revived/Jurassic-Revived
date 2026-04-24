package net.cmr.jurassicrevived.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class FabricItemTagProvider extends FabricTagProvider.ItemTagProvider implements ModItemTagProvider.ItemTagHelper {

    public FabricItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        ModItemTagProvider.registerItemTags(this);
    }

    @Override
    public void tag(TagKey<Item> tag, Item... items) {
        getOrCreateTagBuilder(tag).add(items);
    }
}
