package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.Condition;

import java.util.Optional;
import java.util.List;

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
        ModBlockStateProvider.registerBlockStates(this);
    }
    
    // Helper to check mode
    private boolean isGeneratingBlocks() {
        return blockStateGenerator != null && itemModelGenerator == null;
    }

    private boolean isGeneratingItems() {
        return itemModelGenerator != null;
    }

    private void generateBlockItemModel(Block block) {
        if (isGeneratingItems()) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", ModelLocationUtils.getModelLocation(block).toString());
                return json;
            });
        }
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
        if (isGeneratingItems()) {
             itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", model.toString());
                return json;
            });
        }
    }
    
    @Override
    public void simpleBlockWithExistingModel(Block block, ResourceLocation model) {
        if (isGeneratingBlocks()) {
             blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
        }
        if (isGeneratingItems()) {
             itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", model.toString());
                return json;
            });
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
        if (isGeneratingItems()) {
            generateBlockItemModel(block);
        }
    }

	@Override
	public void blockWithItem(Block block, ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
		if (isGeneratingBlocks()) {
			TextureMapping mapping = new TextureMapping()
				.put(TextureSlot.SIDE, sideTexture)
				.put(TextureSlot.BOTTOM, bottomTexture)
				.put(TextureSlot.TOP, topTexture);
			ResourceLocation modelLocation = ModelTemplates.CUBE_BOTTOM_TOP.create(block, mapping, blockStateGenerator.modelOutput);
			blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, modelLocation));
		}
		if (isGeneratingItems()) {
			generateBlockItemModel(block);
		}
	}

	@Override
	public void randomTextureBlockWithItem(Block block, List<ResourceLocation> textures) {
		if (textures.isEmpty()) {
			throw new IllegalArgumentException("randomTextureBlockWithItem requires at least one texture");
		}

		ResourceLocation baseModel = null;

		if (isGeneratingBlocks()) {
			Variant[] variants = new Variant[textures.size()];

			for (int i = 0; i < textures.size(); i++) {
				TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, textures.get(i));
				ResourceLocation model = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_" + i, mapping, blockStateGenerator.modelOutput);

				if (i == 0) {
					baseModel = model;
				}

				variants[i] = Variant.variant()
					.with(VariantProperties.MODEL, model);
			}

			blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, variants));
		}

		if (isGeneratingItems()) {
			ResourceLocation itemParent = baseModel != null ? baseModel : ModelLocationUtils.getModelLocation(block, "_0");
			itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
				JsonObject json = new JsonObject();
				json.addProperty("parent", itemParent.toString());
				return json;
			});
		}
	}

    private PropertyDispatch createRotatedHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
    }

    @Override
    public void horizontalFacingWithItem(Block block) {
        if (isGeneratingBlocks()) {
            ResourceLocation model = ModelLocationUtils.getModelLocation(block);
            blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }
        if (isGeneratingItems()) {
            generateBlockItemModel(block);
        }
    }

    @Override
    public void horizontalFacingLitWithItem(Block block) {
        if (isGeneratingBlocks()) {
             ResourceLocation model = ModelLocationUtils.getModelLocation(block);
             blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createRotatedHorizontalFacingDispatch()));
        }
        if (isGeneratingItems()) {
            generateBlockItemModel(block);
        }
    }

    @Override
    public void horizontalFacingLitNoBlockstateWithItem(Block block) {
        if (isGeneratingBlocks()) {
             ResourceLocation model = ModelLocationUtils.getModelLocation(block);
             blockStateGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(createRotatedHorizontalFacingDispatch()));
        }
        if (isGeneratingItems()) {
            generateBlockItemModel(block);
        }
    }

    @Override
    public void eggLike(Block block) {
        if (isGeneratingBlocks()) {
            ResourceLocation eggModel = Constants.rl("block/egg");
            // Use multipart to ensure it covers all variants (properties)
            blockStateGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                    .with(Variant.variant().with(VariantProperties.MODEL, eggModel)));
        }
    }

    private void addDirectionalEnumPart(MultiPartGenerator multipart,
                                        String modelPath,
                                        EnumProperty<PipeBlock.ConnectionType> prop,
                                        PipeBlock.ConnectionType value,
                                        int rotX,
                                        int rotY) {
        Variant variant = Variant.variant()
                .with(VariantProperties.MODEL, Constants.rl(modelPath))
                .with(VariantProperties.X_ROT, VariantProperties.Rotation.values()[rotX / 90])
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[rotY / 90]);
        
        multipart.with(
                Condition.condition().term(prop, value),
                variant
        );
    }

    @Override
    public void pipeMultipartWithItem(Block block, String modelBaseName) {
        if (isGeneratingBlocks()) {
            MultiPartGenerator multipart = MultiPartGenerator.multiPart(block);
            
            // Base pipe model always present
            multipart.with(Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + modelBaseName)));

            // Interchange (pipe-to-pipe) connections
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.UP,   PipeBlock.ConnectionType.PIPE, 90, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.DOWN, PipeBlock.ConnectionType.PIPE, 270, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.NORTH, PipeBlock.ConnectionType.PIPE, 0, 180);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.EAST,  PipeBlock.ConnectionType.PIPE, 0, 270);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.SOUTH, PipeBlock.ConnectionType.PIPE, 0, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.WEST,  PipeBlock.ConnectionType.PIPE, 0, 90);

            // Connector (push) connections
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR, 90, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR, 270, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR, 0, 180);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR, 0, 270);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR, 0, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR, 0, 90);

            // Connector pull connections
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR_PULL, 90, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR_PULL, 270, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 180);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 270);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 0);
            addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 90);

            blockStateGenerator.blockStateOutput.accept(multipart);
        }
        if (isGeneratingItems()) {
             itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", Constants.rl("block/" + modelBaseName).toString());
                return json;
            });
        }
    }

    @Override
    public void customFenceMultipart(Block block, String base, String straight, String diag, BooleanProperty ne, BooleanProperty se, BooleanProperty sw, BooleanProperty nw) {
        if (isGeneratingBlocks()) {
            MultiPartGenerator multipart = MultiPartGenerator.multiPart(block);

            multipart.with(Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + base)));

            BooleanProperty northProp = (BooleanProperty) block.getStateDefinition().getProperty("north");
            BooleanProperty eastProp = (BooleanProperty) block.getStateDefinition().getProperty("east");
            BooleanProperty southProp = (BooleanProperty) block.getStateDefinition().getProperty("south");
            BooleanProperty westProp = (BooleanProperty) block.getStateDefinition().getProperty("west");

            multipart.with(
                    Condition.condition().term(northProp, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + straight)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
            );

            multipart.with(
                    Condition.condition().term(eastProp, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + straight)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            );

            multipart.with(
                    Condition.condition().term(southProp, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + straight)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            );

            multipart.with(
                    Condition.condition().term(westProp, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + straight)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            );

            multipart.with(
                    Condition.condition().term(ne, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + diag)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            );

            multipart.with(
                    Condition.condition().term(se, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + diag)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            );

            multipart.with(
                    Condition.condition().term(sw, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + diag)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            );

            multipart.with(
                    Condition.condition().term(nw, true),
                    Variant.variant().with(VariantProperties.MODEL, Constants.rl("block/" + diag)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
            );

            blockStateGenerator.blockStateOutput.accept(multipart);
        }
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.BOTTOM, texture).put(TextureSlot.TOP, texture).put(TextureSlot.SIDE, texture);
            ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createStairs(block, inner, straight, outer));
        }
    }

    @Override
    public void slabBlock(SlabBlock block, ResourceLocation texture, ResourceLocation sideTexture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.BOTTOM, texture)
                    .put(TextureSlot.TOP, texture)
                    .put(TextureSlot.SIDE, sideTexture)
                    .put(TextureSlot.END, texture); // Added END slot for CUBE_COLUMN
            ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation top = ModelTemplates.SLAB_TOP.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation doubleSlab = ModelTemplates.CUBE_COLUMN.createWithSuffix(block, "double", mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createSlab(block, bottom, top, doubleSlab));
        }
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        if (isGeneratingBlocks()) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL, texture);
            ResourceLocation post = ModelTemplates.WALL_POST.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation side = ModelTemplates.WALL_LOW_SIDE.create(block, mapping, blockStateGenerator.modelOutput);
            ResourceLocation sideTall = ModelTemplates.WALL_TALL_SIDE.create(block, mapping, blockStateGenerator.modelOutput);
            blockStateGenerator.blockStateOutput.accept(BlockModelGenerators.createWall(block, post, side, sideTall));
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
			// Simple parent inheritance is the most reliable way to ensure
			// the engine recognizes both tintable layers (0 and 1).
			itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(item), () -> {
				JsonObject json = new JsonObject();
				json.addProperty("parent", "minecraft:item/template_spawn_egg");
				return json;
			});
		}
	}

    @Override
    public void simpleBlockItemModel(Block block) {
        if (isGeneratingItems()) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "minecraft:item/generated");
                JsonObject textures = new JsonObject();
                textures.addProperty("layer0", TextureMapping.getBlockTexture(block).toString());
                json.add("textures", textures);
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
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "minecraft:block/wall_inventory");
                JsonObject textures = new JsonObject();
                textures.addProperty("wall", TextureMapping.getBlockTexture(baseBlock).toString());
                json.add("textures", textures);
                return json;
            });
        }
    }

    @Override
    public void buttonItem(Block block, Block baseBlock) {
        if (isGeneratingItems()) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "minecraft:block/button_inventory");
                JsonObject textures = new JsonObject();
                textures.addProperty("texture", TextureMapping.getBlockTexture(baseBlock).toString());
                json.add("textures", textures);
                return json;
            });
        }
    }

    @Override
    public void fenceItem(Block block, Block baseBlock) {
        if (isGeneratingItems()) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(block.asItem()), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "minecraft:block/fence_inventory");
                JsonObject textures = new JsonObject();
                textures.addProperty("texture", TextureMapping.getBlockTexture(baseBlock).toString());
                json.add("textures", textures);
                return json;
            });
        }
    }

    @Override
    public void withExistingParentModel(Item item, ResourceLocation parent) {
        if (isGeneratingItems()) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(item), () -> {
                JsonObject json = new JsonObject();
                json.addProperty("parent", parent.toString());
                return json;
            });
        }
    }
}