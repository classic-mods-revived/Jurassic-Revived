package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class ModRecipeProvider {

    public interface RecipeHelper {
        void buildShaped(RecipeCategory category, ItemLike result, int count, String[] patterns, Object... keys);
        void buildShapeless(RecipeCategory category, ItemLike result, int count, ItemLike... ingredients);
        void buildShapeless(RecipeCategory category, ItemLike result, int count, String name, ItemLike... ingredients);
        void buildSmelting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group);
        void buildBlasting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group);
        void buildSmelting(TagKey<Item> tag, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group);
        void buildBlasting(TagKey<Item> tag, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group);

        // Custom
        void dnaExtracting(ItemLike testTube, ItemLike tissue, ItemLike dna, int count);
        void dnaAnalyzing(ItemLike testTube, ItemLike material, ItemLike dna, int count);
        void dnaHybridizing(ItemLike result, int count, ItemLike catalyst, ItemLike... ingredients);
        void embryonicMachine(ItemLike syringe, ItemLike dna, ItemLike catalyst, ItemLike result, int count);
        void embryoCalcification(ItemLike syringe, ItemLike egg, ItemLike result, int count);
        void incubating(ItemLike egg, ItemLike result, int count);
        void fossilGrinding(ItemLike fossil, ItemLike tissue, int count);
        void skullToTissue(ItemLike skull, ItemLike tissue, int count);
        void fossilCleaning(ItemLike fossilBlock, ItemLike result, int count);
        
        // Special cases
        void amberRandomDNA(ItemLike testTube, ItemLike amber, ItemLike defaultDna, int count);
    }

    public static void registerRecipes(RecipeHelper helper) {
        List<ItemLike> GYPSUM_COBBLESTONE_SMELTABLES = List.of(ModBlocks.GYPSUM_COBBLESTONE.get());
        helper.buildSmelting(GYPSUM_COBBLESTONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 200, "jr_gypsum_stone");
        helper.buildBlasting(GYPSUM_COBBLESTONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 100, "jr_gypsum_stone");

        List<ItemLike> GYPSUM_STONE_SMELTABLES = List.of(ModBlocks.GYPSUM_STONE.get());
        helper.buildSmelting(GYPSUM_STONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.SMOOTH_GYPSUM_STONE.get(), 0.25f, 200, "jr_smooth_gypsum_stone");
        helper.buildBlasting(GYPSUM_STONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.SMOOTH_GYPSUM_STONE.get(), 0.25f, 100, "jr_smooth_gypsum_stone");

        TagKey<Item> CHARRED_TERRACOTTA_SMELTABLES = ItemTags.TERRACOTTA;
        helper.buildSmelting(CHARRED_TERRACOTTA_SMELTABLES, RecipeCategory.MISC, ModBlocks.CHARRED_TERRACOTTA.get(), 0.25f, 200, "jr_charred_terracotta");
        helper.buildBlasting(CHARRED_TERRACOTTA_SMELTABLES, RecipeCategory.MISC, ModBlocks.CHARRED_TERRACOTTA.get(), 0.25f, 100, "jr_charred_terracotta");

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_STAIRS.get(), 4, new String[]{"A  ", "AA ", "AAA"}, 'A', ModBlocks.GYPSUM_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_SLAB.get(), 6, new String[]{"AAA"}, 'A', ModBlocks.GYPSUM_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_WALL.get(), 6, new String[]{"AAA", "AAA"}, 'A', ModBlocks.GYPSUM_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.CHISELED_GYPSUM_STONE.get(), 1, new String[]{"A", "A"}, 'A', ModBlocks.GYPSUM_BRICK_SLAB.get());

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_STAIRS.get(), 4, new String[]{"A  ", "AA ", "AAA"}, 'A', ModBlocks.REINFORCED_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_SLAB.get(), 6, new String[]{"AAA"}, 'A', ModBlocks.REINFORCED_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_WALL.get(), 6, new String[]{"AAA", "AAA"}, 'A', ModBlocks.REINFORCED_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.CHISELED_REINFORCED_STONE.get(), 1, new String[]{"A", "A"}, 'A', ModBlocks.REINFORCED_BRICK_SLAB.get());

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.TRASH_CAN.get(), 1, new String[]{"AAA", "A A", "AAA"}, 'A', Items.IRON_INGOT);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.BENCH.get(), 1, new String[]{"A  ", "AAA", "BBB"}, 'A', ItemTags.PLANKS, 'B', ModBlocks.REINFORCED_STONE_BRICKS.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.FENCE_LIGHT.get(), 1, new String[]{"A", "B"}, 'A', Blocks.GLOWSTONE, 'B', Items.IRON_INGOT);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.LIGHT_POST.get(), 1, new String[]{"A", "B"}, 'A', Blocks.GLOWSTONE, 'B', ModBlocks.GYPSUM_STONE.get());

        helper.buildShaped(RecipeCategory.MISC, ModItems.TEST_TUBE.get(), 3, new String[]{"  A", " B ", "B  "}, 'A', Items.IRON_INGOT, 'B', Blocks.GLASS);
        helper.buildShaped(RecipeCategory.MISC, ModItems.SYRINGE.get(), 3, new String[]{"  A", " B ", "C  "}, 'A', Items.IRON_INGOT, 'B', Blocks.GLASS, 'C', Items.IRON_NUGGET);
        helper.buildShaped(RecipeCategory.MISC, ModItems.CABLE.get(), 4, new String[]{" BA", "BAB", "AB "}, 'A', Items.COPPER_INGOT, 'B', Items.IRON_NUGGET);
        helper.buildShaped(RecipeCategory.MISC, ModItems.SCREEN.get(), 2, new String[]{"ABA", "ABA", " C "}, 'A', Items.IRON_INGOT, 'B', Blocks.REDSTONE_LAMP, 'C', ModItems.CABLE.get());
        helper.buildShaped(RecipeCategory.MISC, ModItems.PROCESSOR.get(), 1, new String[]{"ABA", "BCB", "ABA"}, 'A', Items.GOLD_NUGGET, 'B', Items.IRON_INGOT, 'C', Items.REDSTONE);
        helper.buildShaped(RecipeCategory.MISC, ModItems.TIRE.get(), 1, new String[]{"AAA", "ABA", "AAA"}, 'A', Items.INK_SAC, 'B', Items.IRON_INGOT);
        helper.buildShaped(RecipeCategory.MISC, ModItems.CUTTING_BLADES.get(), 4, new String[]{"A A", " A ", "A A"}, 'A', Items.IRON_INGOT);

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.GYPSUM_STONE_BRICKS.get(), 4, new String[]{"AA", "AA"}, 'A', ModBlocks.GYPSUM_STONE.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE.get(), 6, new String[]{"AAA", "AAA", "AAA"}, 'A', Blocks.STONE);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE_BRICKS.get(), 6, new String[]{"AAA", "AAA", "AAA"}, 'A', Blocks.STONE_BRICKS);

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.LOW_SECURITY_FENCE_POLE.get(), 8, new String[]{"ABA", " B ", "ABA"}, 'A', Items.IRON_NUGGET, 'B', Items.IRON_INGOT);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.LOW_SECURITY_FENCE_WIRE.get(), 16, new String[]{"AAA", " B ", "AAA"}, 'A', Items.IRON_INGOT, 'B', Items.REDSTONE);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get(), 8, new String[]{"ABA", "ABA", "ABA"}, 'A', Items.IRON_INGOT, 'B', Items.IRON_NUGGET);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get(), 16, new String[]{"AAA", "BBB", "AAA"}, 'A', Items.IRON_INGOT, 'B', Items.REDSTONE);

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.TANK.get(), 1, new String[]{"AAA", "ABA", "AAA"}, 'A', Items.IRON_INGOT, 'B', Items.BUCKET);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.POWER_CELL.get(), 1, new String[]{"AAA", "ABA", "AAA"}, 'A', Items.IRON_INGOT, 'B', ModBlocks.POWER_PIPE.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.WOOD_CRATE.get(), 1, new String[]{"AAA", "ABA", "AAA"}, 'A', ItemTags.PLANKS, 'B', Blocks.CHEST);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.IRON_CRATE.get(), 1, new String[]{"AAA", "ABA", "AAA"}, 'A', Items.IRON_INGOT, 'B', Blocks.CHEST);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.CAT_PLUSHIE.get(), 1, new String[]{"ABA", "CBD", "BBA"}, 'A', Blocks.BLACK_WOOL, 'B', Blocks.WHITE_WOOL, 'C', Blocks.GREEN_WOOL, 'D', Blocks.GRAY_WOOL);

        helper.buildShaped(RecipeCategory.MISC, ModBlocks.GENERATOR.get(), 1, new String[]{"ABA", "CDE", "ABA"}, 'A', Blocks.IRON_BLOCK, 'B', ModItems.CABLE.get(), 'C', Blocks.REDSTONE_BLOCK, 'D', ModItems.PROCESSOR.get(), 'E', Items.COPPER_INGOT);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.DNA_EXTRACTOR.get(), 1, new String[]{"AAA", "BCD", "AAA"}, 'A', Items.LAPIS_LAZULI, 'B', ModItems.SCREEN.get(), 'C', ModItems.CABLE.get(), 'D', ModItems.PROCESSOR.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.DNA_ANALYZER.get(), 1, new String[]{"AAA", "BCD", "EEE"}, 'A', ModItems.TEST_TUBE.get(), 'B', ModItems.SCREEN.get(), 'C', ModItems.CABLE.get(), 'D', ModItems.PROCESSOR.get(), 'E', ModItems.SYRINGE.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.FOSSIL_GRINDER.get(), 1, new String[]{"ABA", "CDC", "AEA"}, 'A', Items.IRON_INGOT, 'B', Blocks.GLASS, 'C', Items.LAPIS_LAZULI, 'D', ModItems.CUTTING_BLADES.get(), 'E', Items.WATER_BUCKET);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.FOSSIL_CLEANER.get(), 1, new String[]{"ABA", "ACA", "AAA"}, 'A', Items.IRON_INGOT, 'B', Items.IRON_NUGGET, 'C', Blocks.GLASS);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.DNA_HYBRIDIZER.get(), 1, new String[]{"ABA", "CDC", "BEB"}, 'A', ModItems.SCREEN.get(), 'B', Items.IRON_INGOT, 'C', ModItems.CABLE.get(), 'D', ModItems.PROCESSOR.get(), 'E', Items.REDSTONE);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.EMBRYONIC_MACHINE.get(), 1, new String[]{"AAA", "BCB", "ADA"}, 'A', Items.IRON_INGOT, 'B', ModItems.TEST_TUBE.get(), 'C', Items.IRON_NUGGET, 'D', Items.REDSTONE);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(), 1, new String[]{"AB ", "CDE", "FAF"}, 'A', Items.IRON_INGOT, 'B', ModItems.SYRINGE.get(), 'C', ModItems.SCREEN.get(), 'D', ModItems.CABLE.get(), 'E', ModItems.PROCESSOR.get(), 'F', Items.FLINT);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.INCUBATOR.get(), 1, new String[]{"AAA", "BCB", "DED"}, 'A', Blocks.GLASS, 'B', Items.COPPER_INGOT, 'C', Blocks.HAY_BLOCK, 'D', Items.IRON_INGOT, 'E', ModItems.CABLE.get());
        helper.buildShaped(RecipeCategory.MISC, ModItems.WRENCH.get(), 1, new String[]{" A ", " BA", "B  "}, 'A', Items.IRON_INGOT, 'B', Items.IRON_NUGGET);
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.ITEM_PIPE.get(), 8, new String[]{"AAA"}, 'A', ModItems.CABLE.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.FLUID_PIPE.get(), 8, new String[]{" A ", "BBB", " A "}, 'A', Items.WATER_BUCKET, 'B', ModItems.CABLE.get());
        helper.buildShaped(RecipeCategory.MISC, ModBlocks.POWER_PIPE.get(), 8, new String[]{" A ", "BBB", " A "}, 'A', Items.REDSTONE, 'B', ModItems.CABLE.get());

        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_GENERATOR.get(), 1, "white_generator_from_generator", ModBlocks.GENERATOR.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.GENERATOR.get(), 1, "generator_from_white_generator", ModBlocks.WHITE_GENERATOR.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_DNA_EXTRACTOR.get(), 1, "white_dna_extractor_from_dna_extractor", ModBlocks.DNA_EXTRACTOR.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.DNA_EXTRACTOR.get(), 1, "dna_extractor_from_white_dna_extractor", ModBlocks.WHITE_DNA_EXTRACTOR.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_DNA_ANALYZER.get(), 1, "white_dna_analyzer_from_dna_analyzer", ModBlocks.DNA_ANALYZER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.DNA_ANALYZER.get(), 1, "dna_analyzer_from_white_dna_analyzer", ModBlocks.WHITE_DNA_ANALYZER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_FOSSIL_GRINDER.get(), 1, "white_fossil_grinder_from_fossil_grinder", ModBlocks.FOSSIL_GRINDER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.FOSSIL_GRINDER.get(), 1, "fossil_grinder_from_white_fossil_grinder", ModBlocks.WHITE_FOSSIL_GRINDER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_FOSSIL_CLEANER.get(), 1, "white_fossil_cleaner_from_fossil_cleaner", ModBlocks.FOSSIL_CLEANER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.FOSSIL_CLEANER.get(), 1, "fossil_cleaner_from_white_fossil_cleaner", ModBlocks.WHITE_FOSSIL_CLEANER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_DNA_HYBRIDIZER.get(), 1, "white_dna_hybridizer_from_dna_hybridizer", ModBlocks.DNA_HYBRIDIZER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.DNA_HYBRIDIZER.get(), 1, "dna_hybridizer_from_white_dna_hybridizer", ModBlocks.WHITE_DNA_HYBRIDIZER.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_EMBRYONIC_MACHINE.get(), 1, "white_embryonic_machine_from_embryonic_machine", ModBlocks.EMBRYONIC_MACHINE.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.EMBRYONIC_MACHINE.get(), 1, "embryonic_machine_from_white_embryonic_machine", ModBlocks.WHITE_EMBRYONIC_MACHINE.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get(), 1, "white_embryo_calcification_machine_from_embryo_calcification_machine", ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(), 1, "embryo_calcification_machine_from_white_embryo_calcification_machine", ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.WHITE_INCUBATOR.get(), 1, "white_incubator_from_incubator", ModBlocks.INCUBATOR.get());
        helper.buildShapeless(RecipeCategory.MISC, ModBlocks.INCUBATOR.get(), 1, "incubator_from_white_incubator", ModBlocks.WHITE_INCUBATOR.get());

        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.APATOSAURUS_TISSUE.get(), ModItems.APATOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ALBERTOSAURUS_TISSUE.get(), ModItems.ALBERTOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.BRACHIOSAURUS_TISSUE.get(), ModItems.BRACHIOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CERATOSAURUS_TISSUE.get(), ModItems.CERATOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.COMPSOGNATHUS_TISSUE.get(), ModItems.COMPSOGNATHUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DILOPHOSAURUS_TISSUE.get(), ModItems.DILOPHOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DIPLODOCUS_TISSUE.get(), ModItems.DIPLODOCUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.GALLIMIMUS_TISSUE.get(), ModItems.GALLIMIMUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.INDOMINUS_REX_TISSUE.get(), ModItems.INDOMINUS_REX_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.OURANOSAURUS_TISSUE.get(), ModItems.OURANOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PARASAUROLOPHUS_TISSUE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.SPINOSAURUS_TISSUE.get(), ModItems.SPINOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TRICERATOPS_TISSUE.get(), ModItems.TRICERATOPS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.VELOCIRAPTOR_TISSUE.get(), ModItems.VELOCIRAPTOR_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.BARYONYX_TISSUE.get(), ModItems.BARYONYX_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CARNOTAURUS_TISSUE.get(), ModItems.CARNOTAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CONCAVENATOR_TISSUE.get(), ModItems.CONCAVENATOR_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DEINONYCHUS_TISSUE.get(), ModItems.DEINONYCHUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.EDMONTOSAURUS_TISSUE.get(), ModItems.EDMONTOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.GIGANOTOSAURUS_TISSUE.get(), ModItems.GIGANOTOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.GUANLONG_TISSUE.get(), ModItems.GUANLONG_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.HERRERASAURUS_TISSUE.get(), ModItems.HERRERASAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.MAJUNGASAURUS_TISSUE.get(), ModItems.MAJUNGASAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PROCOMPSOGNATHUS_TISSUE.get(), ModItems.PROCOMPSOGNATHUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PROTOCERATOPS_TISSUE.get(), ModItems.PROTOCERATOPS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.RUGOPS_TISSUE.get(), ModItems.RUGOPS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.SHANTUNGOSAURUS_TISSUE.get(), ModItems.SHANTUNGOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.STEGOSAURUS_TISSUE.get(), ModItems.STEGOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.STYRACOSAURUS_TISSUE.get(), ModItems.STYRACOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.THERIZINOSAURUS_TISSUE.get(), ModItems.THERIZINOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DISTORTUS_REX_TISSUE.get(), ModItems.DISTORTUS_REX_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ALLOSAURUS_TISSUE.get(), ModItems.ALLOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ALVAREZSAURUS_TISSUE.get(), ModItems.ALVAREZSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ANKYLOSAURUS_TISSUE.get(), ModItems.ANKYLOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ARAMBOURGIANIA_TISSUE.get(), ModItems.ARAMBOURGIANIA_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CARCHARODONTOSAURUS_TISSUE.get(), ModItems.CARCHARODONTOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CEARADACTYLUS_TISSUE.get(), ModItems.CEARADACTYLUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CHASMOSAURUS_TISSUE.get(), ModItems.CHASMOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.COELOPHYSIS_TISSUE.get(), ModItems.COELOPHYSIS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.COELURUS_TISSUE.get(), ModItems.COELURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.CORYTHOSAURUS_TISSUE.get(), ModItems.CORYTHOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DIMORPHODON_TISSUE.get(), ModItems.DIMORPHODON_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.DRYOSAURUS_TISSUE.get(), ModItems.DRYOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.GEOSTERNBERGIA_TISSUE.get(), ModItems.GEOSTERNBERGIA_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.GUIDRACO_TISSUE.get(), ModItems.GUIDRACO_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.HADROSAURUS_TISSUE.get(), ModItems.HADROSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.HYPSILOPHODON_TISSUE.get(), ModItems.HYPSILOPHODON_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.INDORAPTOR_TISSUE.get(), ModItems.INDORAPTOR_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.INOSTRANCEVIA_TISSUE.get(), ModItems.INOSTRANCEVIA_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.LAMBEOSAURUS_TISSUE.get(), ModItems.LAMBEOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.LUDODACTYLUS_TISSUE.get(), ModItems.LUDODACTYLUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.MAMENCHISAURUS_TISSUE.get(), ModItems.MAMENCHISAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.METRIACANTHOSAURUS_TISSUE.get(), ModItems.METRIACANTHOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.MOGANOPTERUS_TISSUE.get(), ModItems.MOGANOPTERUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.NYCTOSAURUS_TISSUE.get(), ModItems.NYCTOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ORNITHOLESTES_TISSUE.get(), ModItems.ORNITHOLESTES_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ORNITHOMIMUS_TISSUE.get(), ModItems.ORNITHOMIMUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.OVIRAPTOR_TISSUE.get(), ModItems.OVIRAPTOR_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PACHYCEPHALOSAURUS_TISSUE.get(), ModItems.PACHYCEPHALOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PROCERATOSAURUS_TISSUE.get(), ModItems.PROCERATOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PTERANODON_TISSUE.get(), ModItems.PTERANODON_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.PTERODAUSTRO_TISSUE.get(), ModItems.PTERODAUSTRO_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.QUETZALCOATLUS_TISSUE.get(), ModItems.QUETZALCOATLUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.RAJASAURUS_TISSUE.get(), ModItems.RAJASAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.SEGISAURUS_TISSUE.get(), ModItems.SEGISAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TAPEJARA_TISSUE.get(), ModItems.TAPEJARA_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TITANOSAURUS_TISSUE.get(), ModItems.TITANOSAURUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TROODON_TISSUE.get(), ModItems.TROODON_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TROPEOGNATHUS_TISSUE.get(), ModItems.TROPEOGNATHUS_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.TUPUXUARA_TISSUE.get(), ModItems.TUPUXUARA_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.UTAHRAPTOR_TISSUE.get(), ModItems.UTAHRAPTOR_DNA.get(), 1);
        helper.dnaExtracting(ModItems.TEST_TUBE.get(), ModItems.ZHENYUANOPTERUS_TISSUE.get(), ModItems.ZHENYUANOPTERUS_DNA.get(), 1);

        helper.fossilCleaning(ModBlocks.STONE_FOSSIL.get(), ModItems.APATOSAURUS_SKULL_FOSSIL.get(), 1);
        helper.fossilCleaning(ModBlocks.DEEPSLATE_FOSSIL.get(), ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), 1);

        helper.dnaHybridizing(ModItems.INDOMINUS_REX_DNA.get(), 1, ModItems.FROG_DNA.get(),
                ModItems.TYRANNOSAURUS_REX_DNA.get(),
                ModItems.VELOCIRAPTOR_DNA.get(),
                ModItems.CARNOTAURUS_DNA.get(),
                ModItems.THERIZINOSAURUS_DNA.get(),
                ModItems.MAJUNGASAURUS_DNA.get(),
                ModItems.RUGOPS_DNA.get(),
                ModItems.GIGANOTOSAURUS_DNA.get());

        helper.dnaHybridizing(ModItems.DISTORTUS_REX_DNA.get(), 1, ModItems.FROG_DNA.get(),
                ModItems.TYRANNOSAURUS_REX_DNA.get(),
                ModItems.BRACHIOSAURUS_DNA.get(),
                ModItems.VELOCIRAPTOR_DNA.get());

        helper.dnaHybridizing(ModItems.INDORAPTOR_DNA.get(), 1, ModItems.FROG_DNA.get(),
                ModItems.INDOMINUS_REX_DNA.get(),
                ModItems.VELOCIRAPTOR_DNA.get());

        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.APATOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.APATOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ALBERTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ALBERTOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.BRACHIOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.BRACHIOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CERATOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CERATOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.COMPSOGNATHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.COMPSOGNATHUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DILOPHOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DILOPHOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DIPLODOCUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DIPLODOCUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.GALLIMIMUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GALLIMIMUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.INDOMINUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.INDOMINUS_REX_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.OURANOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.OURANOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PARASAUROLOPHUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.SPINOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.SPINOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TRICERATOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TRICERATOPS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TYRANNOSAURUS_REX_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.VELOCIRAPTOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.VELOCIRAPTOR_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.BARYONYX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.BARYONYX_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CARNOTAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CARNOTAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CONCAVENATOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CONCAVENATOR_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DEINONYCHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DEINONYCHUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.EDMONTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.EDMONTOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.GIGANOTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GIGANOTOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.GUANLONG_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GUANLONG_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.HERRERASAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.HERRERASAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.MAJUNGASAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.MAJUNGASAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PROCOMPSOGNATHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PROCOMPSOGNATHUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PROTOCERATOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PROTOCERATOPS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.RUGOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.RUGOPS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.SHANTUNGOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.SHANTUNGOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.STEGOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.STEGOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.STYRACOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.STYRACOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.THERIZINOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.THERIZINOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DISTORTUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DISTORTUS_REX_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ALLOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ALLOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ALVAREZSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ALVAREZSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ANKYLOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ANKYLOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ARAMBOURGIANIA_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ARAMBOURGIANIA_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CARCHARODONTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CARCHARODONTOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CEARADACTYLUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CEARADACTYLUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CHASMOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CHASMOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.COELOPHYSIS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.COELOPHYSIS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.COELURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.COELURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.CORYTHOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CORYTHOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DIMORPHODON_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DIMORPHODON_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.DRYOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DRYOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.GEOSTERNBERGIA_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GEOSTERNBERGIA_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.GUIDRACO_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GUIDRACO_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.HADROSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.HADROSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.HYPSILOPHODON_DNA.get(), ModItems.FROG_DNA.get(), ModItems.HYPSILOPHODON_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.INDORAPTOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.INDORAPTOR_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.INOSTRANCEVIA_DNA.get(), ModItems.FROG_DNA.get(), ModItems.INOSTRANCEVIA_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.LAMBEOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.LAMBEOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.LUDODACTYLUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.LUDODACTYLUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.MAMENCHISAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.MAMENCHISAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.METRIACANTHOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.METRIACANTHOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.MOGANOPTERUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.MOGANOPTERUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.NYCTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.NYCTOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ORNITHOLESTES_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ORNITHOLESTES_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ORNITHOMIMUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ORNITHOMIMUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.OVIRAPTOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.OVIRAPTOR_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PACHYCEPHALOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PACHYCEPHALOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PROCERATOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PROCERATOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PTERANODON_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PTERANODON_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.PTERODAUSTRO_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PTERODAUSTRO_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.QUETZALCOATLUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.QUETZALCOATLUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.RAJASAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.RAJASAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.SEGISAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.SEGISAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TAPEJARA_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TAPEJARA_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TITANOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TITANOSAURUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TROODON_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TROODON_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TROPEOGNATHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TROPEOGNATHUS_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.TUPUXUARA_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TUPUXUARA_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.UTAHRAPTOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.UTAHRAPTOR_SYRINGE.get(), 1);
        helper.embryonicMachine(ModItems.SYRINGE.get(), ModItems.ZHENYUANOPTERUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ZHENYUANOPTERUS_SYRINGE.get(), 1);

        helper.amberRandomDNA(ModItems.TEST_TUBE.get(), ModItems.MOSQUITO_IN_AMBER.get(), ModItems.FROG_DNA.get(), 1);
    }
}
