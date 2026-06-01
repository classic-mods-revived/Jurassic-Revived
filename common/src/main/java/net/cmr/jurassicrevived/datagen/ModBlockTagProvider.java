package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlockTagProvider {

    public interface BlockTagHelper {
        void tag(net.minecraft.tags.TagKey<Block> tag, Block... blocks);
    }

    public static void registerBlockTags(BlockTagHelper helper) {
        helper.tag(BlockTags.MINEABLE_WITH_PICKAXE,
                ModBlocks.CAT_PLUSHIE.get(),
                ModBlocks.TRASH_CAN.get(),
                ModBlocks.BENCH.get(),
                ModBlocks.CHARRED_TERRACOTTA.get(),
                ModBlocks.FENCE_LIGHT.get(),
                ModBlocks.LIGHT_POST.get(),
                ModBlocks.GYPSUM_STONE.get(),
                ModBlocks.GYPSUM_COBBLESTONE.get(),
                ModBlocks.GYPSUM_STONE_BRICKS.get(),
                ModBlocks.SMOOTH_GYPSUM_STONE.get(),
                ModBlocks.CHISELED_GYPSUM_STONE.get(),
                ModBlocks.GYPSUM_BRICK_STAIRS.get(),
                ModBlocks.GYPSUM_BRICK_SLAB.get(),
                ModBlocks.GYPSUM_BRICK_WALL.get(),
                ModBlocks.REINFORCED_STONE.get(),
                ModBlocks.REINFORCED_STONE_BRICKS.get(),
                ModBlocks.CHISELED_REINFORCED_STONE.get(),
                ModBlocks.REINFORCED_BRICK_STAIRS.get(),
                ModBlocks.REINFORCED_BRICK_SLAB.get(),
                ModBlocks.REINFORCED_BRICK_WALL.get(),
                ModBlocks.STONE_FOSSIL.get(),
                ModBlocks.DEEPSLATE_FOSSIL.get(),
                ModBlocks.AMBER_ORE.get(),
                ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(),
                ModBlocks.LOW_SECURITY_FENCE_POLE.get(),
                ModBlocks.LOW_SECURITY_FENCE_WIRE.get(),
                ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get(),
                ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get(),
                ModBlocks.GENERATOR.get(),
                ModBlocks.DNA_EXTRACTOR.get(),
                ModBlocks.DNA_ANALYZER.get(),
                ModBlocks.FOSSIL_CLEANER.get(),
                ModBlocks.FOSSIL_GRINDER.get(),
                ModBlocks.DNA_HYBRIDIZER.get(),
                ModBlocks.EMBRYONIC_MACHINE.get(),
                ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(),
                ModBlocks.INCUBATOR.get(),
                ModBlocks.WHITE_GENERATOR.get(),
                ModBlocks.WHITE_DNA_EXTRACTOR.get(),
                ModBlocks.WHITE_DNA_ANALYZER.get(),
                ModBlocks.WHITE_FOSSIL_CLEANER.get(),
                ModBlocks.WHITE_FOSSIL_GRINDER.get(),
                ModBlocks.WHITE_DNA_HYBRIDIZER.get(),
                ModBlocks.WHITE_EMBRYONIC_MACHINE.get(),
                ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get(),
                ModBlocks.WHITE_INCUBATOR.get(),
                ModBlocks.TANK.get(),
                ModBlocks.POWER_CELL.get(),
                ModBlocks.IRON_CRATE.get()
        );

        helper.tag(BlockTags.MINEABLE_WITH_AXE,
                ModBlocks.WOOD_CRATE.get()
        );

        helper.tag(ModTags.Blocks.INCUBATED_EGGS,
                ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get(),
                ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get(),
                ModBlocks.INCUBATED_TRICERATOPS_EGG.get(),
                ModBlocks.INCUBATED_SPINOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_OURANOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get(),
                ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get(),
                ModBlocks.INCUBATED_GALLIMIMUS_EGG.get(),
                ModBlocks.INCUBATED_DIPLODOCUS_EGG.get(),
                ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get(),
                ModBlocks.INCUBATED_CERATOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_APATOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_BARYONYX_EGG.get(),
                ModBlocks.INCUBATED_CARNOTAURUS_EGG.get(),
                ModBlocks.INCUBATED_CONCAVENATOR_EGG.get(),
                ModBlocks.INCUBATED_DEINONYCHUS_EGG.get(),
                ModBlocks.INCUBATED_EDMONTOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_GUANLONG_EGG.get(),
                ModBlocks.INCUBATED_HERRERASAURUS_EGG.get(),
                ModBlocks.INCUBATED_MAJUNGASAURUS_EGG.get(),
                ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG.get(),
                ModBlocks.INCUBATED_PROTOCERATOPS_EGG.get(),
                ModBlocks.INCUBATED_RUGOPS_EGG.get(),
                ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_STEGOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_STYRACOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_THERIZINOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_DISTORTUS_REX_EGG.get(),
                ModBlocks.INCUBATED_ALLOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_ALVAREZSAURUS_EGG.get(),
                ModBlocks.INCUBATED_ANKYLOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_ARAMBOURGIANIA_EGG.get(),
                ModBlocks.INCUBATED_CARCHARODONTOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_CEARADACTYLUS_EGG.get(),
                ModBlocks.INCUBATED_CHASMOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_COELOPHYSIS_EGG.get(),
                ModBlocks.INCUBATED_COELURUS_EGG.get(),
                ModBlocks.INCUBATED_CORYTHOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_DIMORPHODON_EGG.get(),
                ModBlocks.INCUBATED_DRYOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_GEOSTERNBERGIA_EGG.get(),
                ModBlocks.INCUBATED_GUIDRACO_EGG.get(),
                ModBlocks.INCUBATED_HADROSAURUS_EGG.get(),
                ModBlocks.INCUBATED_HYPSILOPHODON_EGG.get(),
                ModBlocks.INCUBATED_INDORAPTOR_EGG.get(),
                ModBlocks.INCUBATED_INOSTRANCEVIA_EGG.get(),
                ModBlocks.INCUBATED_LAMBEOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_LUDODACTYLUS_EGG.get(),
                ModBlocks.INCUBATED_MAMENCHISAURUS_EGG.get(),
                ModBlocks.INCUBATED_METRIACANTHOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_MOGANOPTERUS_EGG.get(),
                ModBlocks.INCUBATED_NYCTOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_ORNITHOLESTES_EGG.get(),
                ModBlocks.INCUBATED_ORNITHOMIMUS_EGG.get(),
                ModBlocks.INCUBATED_OVIRAPTOR_EGG.get(),
                ModBlocks.INCUBATED_PACHYCEPHALOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_PROCERATOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_PTERANODON_EGG.get(),
                ModBlocks.INCUBATED_PTERODAUSTRO_EGG.get(),
                ModBlocks.INCUBATED_QUETZALCOATLUS_EGG.get(),
                ModBlocks.INCUBATED_RAJASAURUS_EGG.get(),
                ModBlocks.INCUBATED_SEGISAURUS_EGG.get(),
                ModBlocks.INCUBATED_TAPEJARA_EGG.get(),
                ModBlocks.INCUBATED_TITANOSAURUS_EGG.get(),
                ModBlocks.INCUBATED_TROODON_EGG.get(),
                ModBlocks.INCUBATED_TROPEOGNATHUS_EGG.get(),
                ModBlocks.INCUBATED_TUPUXUARA_EGG.get(),
                ModBlocks.INCUBATED_UTAHRAPTOR_EGG.get(),
                ModBlocks.INCUBATED_ZHENYUANOPTERUS_EGG.get(),
                ModBlocks.INCUBATED_ACHILLOBATOR_EGG.get()
        );

        helper.tag(BlockTags.MINEABLE_WITH_SHOVEL);

        helper.tag(BlockTags.NEEDS_STONE_TOOL,
                ModBlocks.STONE_FOSSIL.get(),
                ModBlocks.AMBER_ORE.get()
        );

        helper.tag(BlockTags.NEEDS_IRON_TOOL,
                ModBlocks.DEEPSLATE_FOSSIL.get(),
                ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get()
        );

        helper.tag(ModTags.Blocks.AQUATIC_PLACEMENT_REPLACEABLES,
                Blocks.STONE,
                Blocks.GRANITE,
                Blocks.DIORITE,
                Blocks.ANDESITE,
                Blocks.GRAVEL,
                Blocks.DIRT,
                Blocks.SAND,
                Blocks.CLAY
        );

        helper.tag(BlockTags.WALLS,
                ModBlocks.GYPSUM_BRICK_WALL.get(),
                ModBlocks.REINFORCED_BRICK_WALL.get()
        );
    }
}
