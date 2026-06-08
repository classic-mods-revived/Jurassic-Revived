package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.custom.FencePoleBlock;
import net.cmr.jurassicrevived.block.custom.FenceWireBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;

public class ModBlockStateProvider {

    public interface BlockStateHelper {
        void simpleBlock(Block block, ResourceLocation texture);
        void simpleBlock(Block block, String modelName, ResourceLocation texture);
        void simpleBlockItem(Block block, ResourceLocation model);
        
        void simpleBlockWithExistingModel(Block block, ResourceLocation model);

        void crossBlock(Block block, ResourceLocation texture);
        void pottedPlantBlock(Block block, ResourceLocation plantTexture);

        void blockWithItem(Block block);
		void blockWithItem(Block block, ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture);
		void randomTextureBlockWithItem(Block block, List<ResourceLocation> textures);
		void horizontalFacingWithItem(Block block);
        void horizontalFacingLitWithItem(Block block);
        void horizontalFacingLitNoBlockstateWithItem(Block block);
        void eggLike(Block block);
        
        void pipeMultipartWithItem(Block block, String modelBaseName);
        void customFenceMultipart(Block block, String base, String straight, String diag, BooleanProperty ne, BooleanProperty se, BooleanProperty sw, BooleanProperty nw);
        
        void stairsBlock(StairBlock block, ResourceLocation texture);
        void slabBlock(SlabBlock block, ResourceLocation texture, ResourceLocation sideTexture);
        void wallBlock(WallBlock block, ResourceLocation texture);

		ResourceLocation key(Block block);
	}

    private static ResourceLocation modLoc(String path) {
        return Constants.rl(path);
    }
    
    public static void registerBlockStates(BlockStateHelper helper) {
             helper.crossBlock(ModBlocks.ROYAL_FERN.get(), 
                 modLoc("block/" + ModBlocks.ROYAL_FERN.getId().getPath()));
             helper.pottedPlantBlock(ModBlocks.POTTED_ROYAL_FERN.get(), 
                 modLoc("block/" + ModBlocks.ROYAL_FERN.getId().getPath()));
             helper.crossBlock(ModBlocks.HORSETAIL_FERN.get(), 
                 modLoc("block/" + ModBlocks.HORSETAIL_FERN.getId().getPath()));
             helper.pottedPlantBlock(ModBlocks.POTTED_HORSETAIL_FERN.get(), 
                 modLoc("block/" + ModBlocks.HORSETAIL_FERN.getId().getPath()));
             helper.crossBlock(ModBlocks.WESTERN_SWORD_FERN.get(), 
                 modLoc("block/" + ModBlocks.WESTERN_SWORD_FERN.getId().getPath()));
             helper.pottedPlantBlock(ModBlocks.POTTED_WESTERN_SWORD_FERN.get(), 
                 modLoc("block/" + ModBlocks.WESTERN_SWORD_FERN.getId().getPath()));
             helper.crossBlock(ModBlocks.ONYCHIOPSIS.get(), 
                 modLoc("block/" + ModBlocks.ONYCHIOPSIS.getId().getPath()));
             helper.pottedPlantBlock(ModBlocks.POTTED_ONYCHIOPSIS.get(), 
                 modLoc("block/" + ModBlocks.ONYCHIOPSIS.getId().getPath()));
        
        helper.blockWithItem(ModBlocks.GYPSUM_STONE.get());

		helper.stairsBlock((StairBlock) ModBlocks.GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE.getId().getPath()));
		helper.slabBlock((SlabBlock) ModBlocks.GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE.getId().getPath()), modLoc("block/" + ModBlocks.GYPSUM_STONE.getId().getPath()));
		helper.wallBlock((WallBlock) ModBlocks.GYPSUM_STONE_WALL.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE_SLAB.getId().getPath()));

		helper.blockWithItem(ModBlocks.GYPSUM_COBBLESTONE.get());

		helper.stairsBlock((StairBlock) ModBlocks.GYPSUM_COBBLESTONE_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE.getId().getPath()));
		helper.slabBlock((SlabBlock) ModBlocks.GYPSUM_COBBLESTONE_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE.getId().getPath()), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE.getId().getPath()));
		helper.wallBlock((WallBlock) ModBlocks.GYPSUM_COBBLESTONE_WALL.get(), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.GYPSUM_COBBLESTONE_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.GYPSUM_COBBLESTONE_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_COBBLESTONE_SLAB.getId().getPath()));

        helper.blockWithItem(ModBlocks.GYPSUM_STONE_BRICKS.get());
        helper.blockWithItem(ModBlocks.SMOOTH_GYPSUM_STONE.get());

		helper.stairsBlock((StairBlock) ModBlocks.SMOOTH_GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE.getId().getPath()));
		helper.slabBlock((SlabBlock) ModBlocks.SMOOTH_GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE.getId().getPath()), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE.getId().getPath()));
		helper.wallBlock((WallBlock) ModBlocks.SMOOTH_GYPSUM_STONE_WALL.get(), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.SMOOTH_GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.SMOOTH_GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.SMOOTH_GYPSUM_STONE_SLAB.getId().getPath()));

        helper.blockWithItem(ModBlocks.CHISELED_GYPSUM_STONE.get());

		helper.stairsBlock((StairBlock) ModBlocks.CHISELED_GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE.getId().getPath()));
		helper.slabBlock((SlabBlock) ModBlocks.CHISELED_GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE.getId().getPath()), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE.getId().getPath()));
		helper.wallBlock((WallBlock) ModBlocks.CHISELED_GYPSUM_STONE_WALL.get(), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.CHISELED_GYPSUM_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.CHISELED_GYPSUM_STONE_SLAB.get(), modLoc("block/" + ModBlocks.CHISELED_GYPSUM_STONE_SLAB.getId().getPath()));

		helper.randomTextureBlockWithItem(ModBlocks.STONE_FOSSIL.get(), List.of(
			Constants.rl("block/stone_fossil_egg"),
			Constants.rl("block/stone_fossil_rib"),
			Constants.rl("block/stone_fossil_skull")
		));
		helper.randomTextureBlockWithItem(ModBlocks.DEEPSLATE_FOSSIL.get(), List.of(
			Constants.rl("block/deepslate_fossil_egg"),
			Constants.rl("block/deepslate_fossil_rib"),
			Constants.rl("block/deepslate_fossil_skull")
		));
        helper.blockWithItem(ModBlocks.AMBER_ORE.get());
        helper.blockWithItem(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get());
		helper.blockWithItem(ModBlocks.PERMAFROST.get(),
			Constants.rl("block/permafrost_side"),
			Constants.rl("block/permafrost_bottom"),
			Constants.rl("block/permafrost_top"));

        helper.blockWithItem(ModBlocks.REINFORCED_STONE.get());
        helper.blockWithItem(ModBlocks.REINFORCED_STONE_BRICKS.get());
        helper.blockWithItem(ModBlocks.CHISELED_REINFORCED_STONE.get());

        helper.stairsBlock((StairBlock) ModBlocks.REINFORCED_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE.getId().getPath()));
        helper.slabBlock((SlabBlock) ModBlocks.REINFORCED_STONE_SLAB.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE.getId().getPath()), modLoc("block/" + ModBlocks.REINFORCED_STONE.getId().getPath()));
        helper.wallBlock((WallBlock) ModBlocks.REINFORCED_STONE_WALL.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.REINFORCED_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.REINFORCED_STONE_SLAB.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE_SLAB.getId().getPath()));

        helper.stairsBlock((StairBlock) ModBlocks.REINFORCED_BRICK_STAIRS.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE_BRICKS.getId().getPath()));
        helper.slabBlock((SlabBlock) ModBlocks.REINFORCED_BRICK_SLAB.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE_BRICKS.getId().getPath()), modLoc("block/" + ModBlocks.REINFORCED_STONE_BRICKS.getId().getPath()));
        helper.wallBlock((WallBlock) ModBlocks.REINFORCED_BRICK_WALL.get(), modLoc("block/" + ModBlocks.REINFORCED_STONE_BRICKS.getId().getPath()));

        helper.stairsBlock((StairBlock) ModBlocks.CHISELED_REINFORCED_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE.getId().getPath()));
        helper.slabBlock((SlabBlock) ModBlocks.CHISELED_REINFORCED_STONE_SLAB.get(), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE.getId().getPath()), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE.getId().getPath()));
        helper.wallBlock((WallBlock) ModBlocks.CHISELED_REINFORCED_STONE_WALL.get(), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.CHISELED_REINFORCED_STONE_STAIRS.get(), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.CHISELED_REINFORCED_STONE_SLAB.get(), modLoc("block/" + ModBlocks.CHISELED_REINFORCED_STONE_SLAB.getId().getPath()));

        helper.simpleBlockItem(ModBlocks.REINFORCED_BRICK_STAIRS.get(), modLoc("block/" + ModBlocks.REINFORCED_BRICK_STAIRS.getId().getPath()));
        helper.simpleBlockItem(ModBlocks.REINFORCED_BRICK_SLAB.get(), modLoc("block/" + ModBlocks.REINFORCED_BRICK_SLAB.getId().getPath()));

        helper.stairsBlock((StairBlock) ModBlocks.GYPSUM_BRICK_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE_BRICKS.getId().getPath()));
        helper.slabBlock((SlabBlock) ModBlocks.GYPSUM_BRICK_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE_BRICKS.getId().getPath()), modLoc("block/" + ModBlocks.GYPSUM_STONE_BRICKS.getId().getPath()));
        helper.wallBlock((WallBlock) ModBlocks.GYPSUM_BRICK_WALL.get(), modLoc("block/" + ModBlocks.GYPSUM_STONE_BRICKS.getId().getPath()));

        helper.simpleBlockItem(ModBlocks.GYPSUM_BRICK_STAIRS.get(), modLoc("block/" + ModBlocks.GYPSUM_BRICK_STAIRS.getId().getPath()));
        helper.simpleBlockItem(ModBlocks.GYPSUM_BRICK_SLAB.get(), modLoc("block/" + ModBlocks.GYPSUM_BRICK_SLAB.getId().getPath()));

        helper.horizontalFacingWithItem(ModBlocks.CAT_PLUSHIE.get());
        helper.horizontalFacingWithItem(ModBlocks.TRASH_CAN.get());
        helper.horizontalFacingWithItem(ModBlocks.BENCH.get());
        helper.blockWithItem(ModBlocks.CHARRED_TERRACOTTA.get());
		helper.blockWithItem(ModBlocks.SANDSTONE_PATH.get());
		helper.blockWithItem(ModBlocks.CHISELED_BRICK_PATH.get());
		helper.blockWithItem(ModBlocks.CONCRETE_PATH.get());
		helper.blockWithItem(ModBlocks.LIGHT_GRAY_TERRACOTTA_PATH.get());

		helper.stairsBlock((StairBlock) ModBlocks.CHARRED_TERRACOTTA_STAIRS.get(), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA.getId().getPath()));
		helper.slabBlock((SlabBlock) ModBlocks.CHARRED_TERRACOTTA_SLAB.get(), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA.getId().getPath()), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA.getId().getPath()));
		helper.wallBlock((WallBlock) ModBlocks.CHARRED_TERRACOTTA_WALL.get(), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA.getId().getPath()));

		helper.simpleBlockItem(ModBlocks.CHARRED_TERRACOTTA_STAIRS.get(), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA_STAIRS.getId().getPath()));
		helper.simpleBlockItem(ModBlocks.CHARRED_TERRACOTTA_SLAB.get(), modLoc("block/" + ModBlocks.CHARRED_TERRACOTTA_SLAB.getId().getPath()));

		helper.horizontalFacingWithItem(ModBlocks.FENCE_LIGHT.get());
        helper.horizontalFacingWithItem(ModBlocks.LIGHT_POST.get());

        helper.simpleBlockWithExistingModel(ModBlocks.TANK.get(), modLoc("block/tank"));
        helper.simpleBlockWithExistingModel(ModBlocks.POWER_CELL.get(), modLoc("block/power_cell"));
        helper.simpleBlockWithExistingModel(ModBlocks.WOOD_CRATE.get(), modLoc("block/wood_crate"));
        helper.simpleBlockWithExistingModel(ModBlocks.IRON_CRATE.get(), modLoc("block/iron_crate"));

        helper.horizontalFacingLitWithItem(ModBlocks.GENERATOR.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.DNA_EXTRACTOR.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.DNA_ANALYZER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.FOSSIL_GRINDER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.FOSSIL_CLEANER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.DNA_HYBRIDIZER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.EMBRYONIC_MACHINE.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get());
        helper.horizontalFacingLitWithItem(ModBlocks.INCUBATOR.get());
        
        helper.horizontalFacingLitWithItem(ModBlocks.WHITE_GENERATOR.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_DNA_EXTRACTOR.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_DNA_ANALYZER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_FOSSIL_GRINDER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_FOSSIL_CLEANER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_DNA_HYBRIDIZER.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_EMBRYONIC_MACHINE.get());
        helper.horizontalFacingLitNoBlockstateWithItem(ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get());
        helper.horizontalFacingLitWithItem(ModBlocks.WHITE_INCUBATOR.get());

        helper.eggLike(ModBlocks.VELOCIRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.TYRANNOSAURUS_REX_EGG.get());
        helper.eggLike(ModBlocks.TRICERATOPS_EGG.get());
        helper.eggLike(ModBlocks.SPINOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.OURANOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.PARASAUROLOPHUS_EGG.get());
        helper.eggLike(ModBlocks.INDOMINUS_REX_EGG.get());
        helper.eggLike(ModBlocks.GALLIMIMUS_EGG.get());
        helper.eggLike(ModBlocks.DIPLODOCUS_EGG.get());
        helper.eggLike(ModBlocks.DILOPHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.COMPSOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.CERATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.BRACHIOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.ALBERTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.APATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.BARYONYX_EGG.get());
        helper.eggLike(ModBlocks.CARNOTAURUS_EGG.get());
        helper.eggLike(ModBlocks.CONCAVENATOR_EGG.get());
        helper.eggLike(ModBlocks.DEINONYCHUS_EGG.get());
        helper.eggLike(ModBlocks.EDMONTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.GIGANOTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.GUANLONG_EGG.get());
        helper.eggLike(ModBlocks.HERRERASAURUS_EGG.get());
        helper.eggLike(ModBlocks.MAJUNGASAURUS_EGG.get());
        helper.eggLike(ModBlocks.PROCOMPSOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.PROTOCERATOPS_EGG.get());
        helper.eggLike(ModBlocks.RUGOPS_EGG.get());
        helper.eggLike(ModBlocks.SHANTUNGOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.STEGOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.STYRACOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.THERIZINOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.DISTORTUS_REX_EGG.get());
        helper.eggLike(ModBlocks.ALLOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.ALVAREZSAURUS_EGG.get());
        helper.eggLike(ModBlocks.ANKYLOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.ARAMBOURGIANIA_EGG.get());
        helper.eggLike(ModBlocks.CARCHARODONTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.CEARADACTYLUS_EGG.get());
        helper.eggLike(ModBlocks.CHASMOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.COELOPHYSIS_EGG.get());
        helper.eggLike(ModBlocks.COELURUS_EGG.get());
        helper.eggLike(ModBlocks.CORYTHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.DIMORPHODON_EGG.get());
        helper.eggLike(ModBlocks.DRYOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.GEOSTERNBERGIA_EGG.get());
        helper.eggLike(ModBlocks.GUIDRACO_EGG.get());
        helper.eggLike(ModBlocks.HADROSAURUS_EGG.get());
        helper.eggLike(ModBlocks.HYPSILOPHODON_EGG.get());
        helper.eggLike(ModBlocks.INDORAPTOR_EGG.get());
        helper.eggLike(ModBlocks.INOSTRANCEVIA_EGG.get());
        helper.eggLike(ModBlocks.LAMBEOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.LUDODACTYLUS_EGG.get());
        helper.eggLike(ModBlocks.MAMENCHISAURUS_EGG.get());
        helper.eggLike(ModBlocks.METRIACANTHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.MOGANOPTERUS_EGG.get());
        helper.eggLike(ModBlocks.NYCTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.ORNITHOLESTES_EGG.get());
        helper.eggLike(ModBlocks.ORNITHOMIMUS_EGG.get());
        helper.eggLike(ModBlocks.OVIRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.PACHYCEPHALOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.PROCERATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.PTERANODON_EGG.get());
        helper.eggLike(ModBlocks.PTERODAUSTRO_EGG.get());
        helper.eggLike(ModBlocks.QUETZALCOATLUS_EGG.get());
        helper.eggLike(ModBlocks.RAJASAURUS_EGG.get());
        helper.eggLike(ModBlocks.SEGISAURUS_EGG.get());
        helper.eggLike(ModBlocks.TAPEJARA_EGG.get());
        helper.eggLike(ModBlocks.TITANOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.TROODON_EGG.get());
        helper.eggLike(ModBlocks.TROPEOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.TUPUXUARA_EGG.get());
        helper.eggLike(ModBlocks.UTAHRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.ZHENYUANOPTERUS_EGG.get());
        helper.eggLike(ModBlocks.ACHILLOBATOR_EGG.get());
        helper.eggLike(ModBlocks.SUCHOMIMUS_EGG.get());
        helper.eggLike(ModBlocks.CHILESAURUS_EGG.get());
        helper.eggLike(ModBlocks.MUSSASAURUS_EGG.get());
        helper.eggLike(ModBlocks.THESCELOSAURUS_EGG.get());

        helper.eggLike(ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TRICERATOPS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_SPINOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_OURANOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_GALLIMIMUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DIPLODOCUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CERATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_APATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_BARYONYX_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CARNOTAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CONCAVENATOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DEINONYCHUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_EDMONTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_GUANLONG_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_HERRERASAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_MAJUNGASAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PROTOCERATOPS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_RUGOPS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_STEGOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_STYRACOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_THERIZINOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DISTORTUS_REX_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ALLOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ALVAREZSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ANKYLOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ARAMBOURGIANIA_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CARCHARODONTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CEARADACTYLUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CHASMOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_COELOPHYSIS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_COELURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CORYTHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DIMORPHODON_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_DRYOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_GEOSTERNBERGIA_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_GUIDRACO_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_HADROSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_HYPSILOPHODON_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_INDORAPTOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_INOSTRANCEVIA_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_LAMBEOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_LUDODACTYLUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_MAMENCHISAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_METRIACANTHOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_MOGANOPTERUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_NYCTOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ORNITHOLESTES_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ORNITHOMIMUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_OVIRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PACHYCEPHALOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PROCERATOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PTERANODON_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_PTERODAUSTRO_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_QUETZALCOATLUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_RAJASAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_SEGISAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TAPEJARA_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TITANOSAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TROODON_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TROPEOGNATHUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_TUPUXUARA_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_UTAHRAPTOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ZHENYUANOPTERUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_ACHILLOBATOR_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_SUCHOMIMUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_CHILESAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_MUSSASAURUS_EGG.get());
        helper.eggLike(ModBlocks.INCUBATED_THESCELOSAURUS_EGG.get());

        helper.customFenceMultipart(
                ModBlocks.LOW_SECURITY_FENCE_POLE.get(),
                "low_security_fence_pole",
                "low_security_fence_pole_part",
                "low_security_fence_pole_diag_part",
                FencePoleBlock.NE,
                FencePoleBlock.SE,
                FencePoleBlock.SW,
                FencePoleBlock.NW
        );

        helper.customFenceMultipart(
                ModBlocks.LOW_SECURITY_FENCE_WIRE.get(),
                "low_security_fence_wire",
                "low_security_fence_wire_part",
                "low_security_fence_wire_diag_part",
                FenceWireBlock.NE,
                FenceWireBlock.SE,
                FenceWireBlock.SW,
                FenceWireBlock.NW
        );

        helper.customFenceMultipart(
                ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get(),
                "medium_security_fence_pole",
                "medium_security_fence_pole_part",
                "medium_security_fence_pole_diag_part",
                FencePoleBlock.NE,
                FencePoleBlock.SE,
                FencePoleBlock.SW,
                FencePoleBlock.NW
        );

        helper.customFenceMultipart(
                ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get(),
                "medium_security_fence_wire",
                "medium_security_fence_wire_part",
                "medium_security_fence_wire_diag_part",
                FenceWireBlock.NE,
                FenceWireBlock.SE,
                FenceWireBlock.SW,
                FenceWireBlock.NW
        );

        helper.pipeMultipartWithItem(ModBlocks.ITEM_PIPE.get(), "item_pipe");
        helper.pipeMultipartWithItem(ModBlocks.FLUID_PIPE.get(), "fluid_pipe");
        helper.pipeMultipartWithItem(ModBlocks.POWER_PIPE.get(), "power_pipe");
    }
}