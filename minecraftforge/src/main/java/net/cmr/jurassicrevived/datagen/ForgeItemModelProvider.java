package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ForgeItemModelProvider extends ItemModelProvider implements ModItemModelProvider.ItemModelHelper {

    public ForgeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItemModelProvider.registerItemModels(this);
    }

    @Override
    public ItemModelBuilder basicItem(Item item) {
        return super.basicItem(item);
    }

    @Override
    public void basicItemModel(Item item) {
        super.basicItem(item);
    }

    @Override
    public void spawnEgg(Item item) {
        withExistingParent(name(item), mcLoc("item/template_spawn_egg"));
    }

    @Override
    public void simpleBlockItemModel(Block block) {
        withExistingParent(name(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name(block)));
    }

    @Override
    public void flowerItem(Block block) {
        withExistingParent(name(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name(block)));
    }

    @Override
    public void wallItem(Block block, Block baseBlock) {
        withExistingParent(name(block), mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + name(baseBlock)));
    }

    @Override
    public void buttonItem(Block block, Block baseBlock) {
        withExistingParent(name(block), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/" + name(baseBlock)));
    }

    @Override
    public void fenceItem(Block block, Block baseBlock) {
        withExistingParent(name(block), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/" + name(baseBlock)));
    }

    @Override
    public void withExistingParentModel(Item item, ResourceLocation parent) {
        super.withExistingParent(name(item), parent);
    }

    private String name(Item item) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    private String name(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
