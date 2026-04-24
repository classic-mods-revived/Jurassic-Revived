package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NeoForgeBlockStateProvider extends BlockStateProvider implements ModBlockStateProvider.BlockStateHelper {

    public NeoForgeBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlockStateProvider.registerBlockStates(this);
    }

    @Override
    public void simpleBlock(Block block, ResourceLocation texture) {
        super.simpleBlock(block, models().cubeAll(name(block), texture));
    }

    @Override
    public void simpleBlock(Block block, String modelName, ResourceLocation texture) {
        super.simpleBlock(block, models().cubeAll(modelName, texture));
    }

    @Override
    public void simpleBlockItem(Block block, ResourceLocation model) {
        super.simpleBlockItem(block, new ModelFile.UncheckedModelFile(model));
    }

    @Override
    public void simpleBlockWithExistingModel(Block block, ResourceLocation model) {
        ModelFile modelFile = new ModelFile.UncheckedModelFile(model);
        super.simpleBlock(block, modelFile);
        super.simpleBlockItem(block, modelFile);
    }

    @Override
    public void crossBlock(Block block, ResourceLocation texture) {
        super.simpleBlock(block, models().cross(name(block), texture).renderType("cutout"));
    }

    @Override
    public void pottedPlantBlock(Block block, ResourceLocation plantTexture) {
        super.simpleBlock(block, models().singleTexture(name(block), ResourceLocation.parse("flower_pot_cross"), "plant", plantTexture).renderType("cutout"));
    }

    @Override
    public void blockWithItem(Block block) {
        super.simpleBlockWithItem(block, models().cubeAll(name(block), blockTexture(block)));
    }

    @Override
    public void horizontalFacingWithItem(Block block) {
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name(block)));
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

    @Override
    public void horizontalFacingLitWithItem(Block block) {
        String base = name(block);
        ModelFile unlit = new ModelFile.UncheckedModelFile(modLoc("block/" + base));
        ModelFile lit   = new ModelFile.UncheckedModelFile(modLoc("block/" + base + "_lit"));

        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(0).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(lit).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(lit).rotationY(0).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(lit).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(lit).rotationY(270).addModel();

        simpleBlockItem(block, unlit);
    }

    @Override
    public void horizontalFacingLitNoBlockstateWithItem(Block block) {
        String base = name(block);
        ModelFile unlit = new ModelFile.UncheckedModelFile(modLoc("block/" + base));

        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(0).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
                .modelForState().modelFile(unlit).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(unlit).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(unlit).rotationY(0).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(unlit).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
                .modelForState().modelFile(unlit).rotationY(270).addModel();
        simpleBlockItem(block, unlit);
    }

    @Override
    public void eggLike(Block block) {
        ModelFile eggModel = new ModelFile.UncheckedModelFile(modLoc("block/egg"));
        super.simpleBlock(block, eggModel);
    }

    @Override
    public void pipeMultipartWithItem(Block block, String modelBaseName) {
        var multipart = getMultipartBuilder(block);

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + modelBaseName)))
                .addModel()
                .end();

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.UP,   PipeBlock.ConnectionType.PIPE, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.DOWN, PipeBlock.ConnectionType.PIPE, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.NORTH, PipeBlock.ConnectionType.PIPE, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.EAST,  PipeBlock.ConnectionType.PIPE, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.SOUTH, PipeBlock.ConnectionType.PIPE, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.WEST,  PipeBlock.ConnectionType.PIPE, 0, 90);

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR, 0, 90);

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR_PULL, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR_PULL, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 90);

        ModelFile itemParent = new ModelFile.UncheckedModelFile(modLoc("block/" + modelBaseName));
        simpleBlockItem(block, itemParent);
    }

    private void addDirectionalEnumPart(MultiPartBlockStateBuilder multipart,
                                        String modelPath,
                                        EnumProperty<PipeBlock.ConnectionType> prop,
                                        PipeBlock.ConnectionType value,
                                        int rotX,
                                        int rotY) {
        multipart.part()
                .modelFile(models().getExistingFile(modLoc(modelPath)))
                .rotationX(rotX)
                .rotationY(rotY)
                .addModel()
                .condition(prop, value)
                .end();
    }

    @Override
    public void customFenceMultipart(Block block, String baseModelName, String straightArmModelName, String diagonalArmModelName, BooleanProperty neProp, BooleanProperty seProp, BooleanProperty swProp, BooleanProperty nwProp) {
        var multipart = getMultipartBuilder(block);

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + baseModelName)))
                .addModel()
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(0)
                .addModel()
                .condition(BlockStateProperties.NORTH, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.EAST, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(180)
                .addModel()
                .condition(BlockStateProperties.SOUTH, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(270)
                .addModel()
                .condition(BlockStateProperties.WEST, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(90)
                .addModel()
                .condition(neProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(180)
                .addModel()
                .condition(seProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(270)
                .addModel()
                .condition(swProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(0)
                .addModel()
                .condition(nwProp, true)
                .end();
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        super.stairsBlock(block, texture);
    }

    @Override
    public void slabBlock(SlabBlock block, ResourceLocation texture, ResourceLocation sideTexture) {
        super.slabBlock(block, texture, sideTexture);
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        super.wallBlock(block, texture);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    @Override
	public ResourceLocation key(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block);
    }
}
