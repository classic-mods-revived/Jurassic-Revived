package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import com.google.gson.JsonObject;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Optional;

public class FabricModModelProvider extends FabricModelProvider implements ModBlockStateProvider.BlockStateHelper, ModItemModelProvider.ItemModelHelper {

    private BlockModelGenerators blockStateGenerator;
    private ItemModelGenerators itemModelGenerator;

    public FabricModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateGenerator) {
        this.blockStateGenerator = blockStateGenerator;
        ModBlockStateProvider.registerBlockStates(this);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.itemModelGenerator = itemModelGenerator;
        ModItemModelProvider.registerItemModels(this);
    }
    
    // Helper to check mode
    private boolean isGeneratingBlocks() {
        return blockStateGenerator != null && itemModelGenerator == null;
    }

    private boolean isGeneratingItems() {
        return itemModelGenerator != null;
    }

    // --- BlockStateHelper Implementation ---

    @Override
    public void simpleBlock(Block block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, texture);
            ResourceLocation modelLocation = ModelTemplates.CUBE_ALL.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, modelLocation));
        }
    }

    @Override
    public void simpleBlock(Block block, String modelName, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, texture);
            ResourceLocation modelLocation = ModelTemplates.CUBE_ALL.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, modelLocation));
        }
    }

    @Override
    public void simpleBlockItem(Block block, ResourceLocation model) {
        // This is called from ModBlockStateProvider, intended to register item models for blocks.
        // Since we are merging, we can potentially handle this here if we are in item generation mode?
        // But ModBlockStateProvider calls this during registerBlockStates, which is called during generateBlockStateModels.
        // So we are in block generation mode.
        // We can't generate item models here because itemModelGenerator is null.
        // We should probably ignore this here and rely on ModItemModelProvider to register block items?
        // OR, we can store these requests and run them later?
        // But ModItemModelProvider seems to have its own logic.
        // Let's assume ModItemModelProvider handles all item models, including block items.
    }
    
    @Override
    public void simpleBlockWithExistingModel(Block block, ResourceLocation model) {
        if (isGeneratingBlocks()) {
             blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
        }
    }

    @Override
    public void crossBlock(Block block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.CROSS, texture);
            ResourceLocation model = ModelTemplates.CROSS.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
        }
    }

    @Override
    public void pottedPlantBlock(Block block, ResourceLocation plantTexture) {
        if (isGeneratingBlocks()) {
            TextureSlot flowerPotSlot = TextureSlot.create("flowerpot");
            ResourceLocation flowerPotBlock = Constants.r2("minecraft:block/flower_pot");
            TextureMapping mapping = new TextureMapping().put(TextureSlot.PLANT, plantTexture).put(flowerPotSlot, flowerPotBlock);
            ResourceLocation model = ModelTemplates.FLOWER_POT_CROSS.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
        }
    }

    @Override
    public void blockWithItem(Block block) {
        if (isGeneratingBlocks()) {
            blockStateGenerator.createTrivialCube(block);
        }
    }

    @Override
    public void horizontalFacingWithItem(Block block) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = TextureMapping.cube(block);
            ResourceLocation modelLoc = ModelTemplates.CUBE_ALL.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, modelLoc)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }
    }

    @Override
    public void horizontalFacingLitWithItem(Block block) {
        if (isGeneratingBlocks()) {
            // Placeholder implementation
             ResourceLocation model = ModelLocationUtils.getModelLocation(block);
             blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }
    }

    @Override
    public void horizontalFacingLitNoBlockstateWithItem(Block block) {
        if (isGeneratingBlocks()) {
             ResourceLocation model = ModelLocationUtils.getModelLocation(block);
             blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }
    }

    @Override
    public void eggLike(Block block) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public void pipeMultipartWithItem(Block block, String modelBaseName) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public void customFenceMultipart(Block block, String base, String straight, String diag, BooleanProperty ne, BooleanProperty se, BooleanProperty sw, BooleanProperty nw) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public void slabBlock(SlabBlock block, ResourceLocation texture, ResourceLocation sideTexture) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            // Placeholder
        }
    }

    @Override
    public ResourceLocation key(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block);
    }

    // --- ItemModelHelper Implementation ---

    @Override
    public void basicItemModel(Item item) {
        if (isGeneratingItems()) {
            itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }
    }

    @Override
    public void spawnEgg(Item item) {
        if (isGeneratingItems()) {
            // Create a custom template that defines TWO layers. 
            // The order of TextureSlots here determines the tintindex (0, 1, etc.)
            ModelTemplate spawnEggTemplate = new ModelTemplate(
                Optional.of(Constants.r2("minecraft:item/generated")),
                Optional.empty(), 
                TextureSlot.LAYER0, 
                TextureSlot.LAYER1
            );

            TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.LAYER0, Constants.r2("minecraft:item/spawn_egg"))
                .put(TextureSlot.LAYER1, Constants.r2("minecraft:item/spawn_egg_overlay"));

            spawnEggTemplate.create(ModelLocationUtils.getModelLocation(item), mapping, itemModelGenerator.output);
        }
    }

    @Override
    public void simpleBlockItemModel(Block block) {
        if (isGeneratingItems()) {
            // Ensure simple block items also use a clean parent inheritance
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", ModelLocationUtils.getModelLocation(block).toString());
                return json;
            });
        }
    }

    @Override
    public void flowerItem(Block block) {
        if (isGeneratingItems()) {
            // Use FLAT_ITEM instead of CROSS for the inventory icon to fix the "shadow" issue
            TextureMapping mapping = new TextureMapping().put(TextureSlot.LAYER0, TextureMapping.getBlockTexture(block));
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), mapping, itemModelGenerator.output);
        }
    }

    @Override
    public void wallItem(Block block, Block baseBlock) {
        if (isGeneratingItems()) {
            // Placeholder
        }
    }

    @Override
    public void buttonItem(Block block, Block baseBlock) {
        if (isGeneratingItems()) {
            // Placeholder
        }
    }

    @Override
    public void fenceItem(Block block, Block baseBlock) {
        if (isGeneratingItems()) {
            // Placeholder
        }
    }

    @Override
    public void withExistingParentModel(Item item, ResourceLocation parent) {
        if (isGeneratingItems()) {
            // Placeholder
        }
    }
}
