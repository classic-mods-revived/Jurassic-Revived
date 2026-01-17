package net.cmr.jurassicrevived.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.custom.*;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.mixin.FlowerPotBlockAccessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS =
		DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK);

	public static final RegistrySupplier<Block> CAT_PLUSHIE = registerBlock("cat_plushie",
		() -> new DecoBlock(BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.WOOL)));

	public static final RegistrySupplier<Block> TRASH_CAN = registerBlock("trash_can",
		() -> new TrashBlock(BlockBehaviour.Properties.of().noOcclusion()));

	public static final RegistrySupplier<Block> BENCH = registerBlock("bench",
		() -> new BenchBlock(BlockBehaviour.Properties.of().noOcclusion()));

	public static final RegistrySupplier<Block> CHARRED_TERRACOTTA = registerBlock("charred_terracotta",
		() -> new Block(BlockBehaviour.Properties.of().noOcclusion()));

	public static final RegistrySupplier<Block> LIGHT_POST = registerBlock("light_post",
		() -> new LightPostBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel(state -> 15)));

	public static final RegistrySupplier<Block> ITEM_PIPE = registerBlock("item_pipe",
		() -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.ITEMS));

	public static final RegistrySupplier<Block> FLUID_PIPE = registerBlock("fluid_pipe",
		() -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.FLUIDS));

	public static final RegistrySupplier<Block> POWER_PIPE = registerBlock("power_pipe",
		() -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.ENERGY));

	public static final RegistrySupplier<Block> TANK = registerBlock("tank",
		() -> new TankBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

	public static final RegistrySupplier<Block> POWER_CELL = registerBlock("power_cell",
		() -> new PowerCellBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

	public static final RegistrySupplier<Block> WOOD_CRATE = registerBlock("wood_crate",
		() -> new CrateBlock(BlockBehaviour.Properties.of().strength(2.0f).noOcclusion().noLootTable().sound(SoundType.WOOD), 9));
	public static final RegistrySupplier<Block> IRON_CRATE = registerBlock("iron_crate",
		() -> new CrateBlock(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().noOcclusion().noLootTable(), 18));

	public static final RegistrySupplier<Block> GENERATOR = registerBlock("generator",
		() -> new GeneratorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
		() -> new DNAExtractorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> DNA_ANALYZER = registerBlock("dna_analyzer",
		() -> new DNAAnalyzerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> FOSSIL_GRINDER = registerBlock("fossil_grinder",
		() -> new FossilGrinderBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> FOSSIL_CLEANER = registerBlock("fossil_cleaner",
		() -> new FossilCleanerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> DNA_HYBRIDIZER = registerBlock("dna_hybridizer",
		() -> new DNAHybridizerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> EMBRYONIC_MACHINE = registerBlock("embryonic_machine",
		() -> new EmbryonicMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> EMBRYO_CALCIFICATION_MACHINE = registerBlock("embryo_calcification_machine",
		() -> new EmbryoCalcificationMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> INCUBATOR = registerBlock("incubator",
		() -> new IncubatorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_GENERATOR = registerBlock("white_generator",
		() -> new GeneratorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_DNA_EXTRACTOR = registerBlock("white_dna_extractor",
		() -> new DNAExtractorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_DNA_ANALYZER = registerBlock("white_dna_analyzer",
		() -> new DNAAnalyzerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_FOSSIL_GRINDER = registerBlock("white_fossil_grinder",
		() -> new FossilGrinderBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_FOSSIL_CLEANER = registerBlock("white_fossil_cleaner",
		() -> new FossilCleanerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_DNA_HYBRIDIZER = registerBlock("white_dna_hybridizer",
		() -> new DNAHybridizerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_EMBRYONIC_MACHINE = registerBlock("white_embryonic_machine",
		() -> new EmbryonicMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_EMBRYO_CALCIFICATION_MACHINE = registerBlock("white_embryo_calcification_machine",
		() -> new EmbryoCalcificationMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
	public static final RegistrySupplier<Block> WHITE_INCUBATOR = registerBlock("white_incubator",
		() -> new IncubatorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));

	//? if >1.20.1 {
	/*public static final RegistrySupplier<Block> ROYAL_FERN = registerBlock("royal_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_ROYAL_FERN = BLOCKS.register("potted_royal_fern",
		() -> new FlowerPotBlock(ROYAL_FERN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> HORSETAIL_FERN = registerBlock("horsetail_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_HORSETAIL_FERN = BLOCKS.register("potted_horsetail_fern",
		() -> new FlowerPotBlock(HORSETAIL_FERN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> WESTERN_SWORD_FERN = registerBlock("western_sword_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_WESTERN_SWORD_FERN = BLOCKS.register("potted_western_sword_fern",
		() -> new FlowerPotBlock(WESTERN_SWORD_FERN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> ONYCHIOPSIS = registerBlock("onychiopsis",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_ONYCHIOPSIS = BLOCKS.register("potted_onychiopsis",
		() -> new FlowerPotBlock(ONYCHIOPSIS.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));
	*///?} else {
	public static final RegistrySupplier<Block> ROYAL_FERN = registerBlock("royal_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_ROYAL_FERN = BLOCKS.register("potted_royal_fern",
		() -> new FlowerPotBlock(ROYAL_FERN.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> HORSETAIL_FERN = registerBlock("horsetail_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_HORSETAIL_FERN = BLOCKS.register("potted_horsetail_fern",
		() -> new FlowerPotBlock(HORSETAIL_FERN.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> WESTERN_SWORD_FERN = registerBlock("western_sword_fern",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_WESTERN_SWORD_FERN = BLOCKS.register("potted_western_sword_fern",
		() -> new FlowerPotBlock(WESTERN_SWORD_FERN.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

	public static final RegistrySupplier<Block> ONYCHIOPSIS = registerBlock("onychiopsis",
		() -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
	public static final RegistrySupplier<Block> POTTED_ONYCHIOPSIS = BLOCKS.register("potted_onychiopsis",
		() -> new FlowerPotBlock(ONYCHIOPSIS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));
	//?}

	public static void setupPots() {
		Map<Block, Block> map = null;

		// Attempt Mixin cast safely
		try {
			Object pot = Blocks.FLOWER_POT;
			if (pot instanceof FlowerPotBlockAccessor accessor) {
				map = accessor.getPottedByContent();
			}
		} catch (Throwable ignored) {}

		// Fallback to Reflection if Mixin didn't apply (common on early Fabric loading)
		if (map == null) {
			try {
				java.lang.reflect.Field field = FlowerPotBlock.class.getDeclaredField("POTTED_BY_CONTENT");
				field.setAccessible(true);
				map = (Map<Block, Block>) field.get(null);
			} catch (Exception e) {
				Constants.LOG.error("Could not access FlowerPotBlock.POTTED_BY_CONTENT", e);
			}
		}

		if (map != null) {
			if (ROYAL_FERN.isPresent()) map.put(ROYAL_FERN.get(), POTTED_ROYAL_FERN.get());
			if (HORSETAIL_FERN.isPresent()) map.put(HORSETAIL_FERN.get(), POTTED_HORSETAIL_FERN.get());
			if (WESTERN_SWORD_FERN.isPresent()) map.put(WESTERN_SWORD_FERN.get(), POTTED_WESTERN_SWORD_FERN.get());
			if (ONYCHIOPSIS.isPresent()) map.put(ONYCHIOPSIS.get(), POTTED_ONYCHIOPSIS.get());
		}
	}

	public static final RegistrySupplier<Block> GYPSUM_STONE = registerBlock("gypsum_stone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> GYPSUM_COBBLESTONE = registerBlock("gypsum_cobblestone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> GYPSUM_STONE_BRICKS = registerBlock("gypsum_stone_bricks",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> SMOOTH_GYPSUM_STONE = registerBlock("smooth_gypsum_stone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> CHISELED_GYPSUM_STONE = registerBlock("chiseled_gypsum_stone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

	public static final RegistrySupplier<Block> GYPSUM_BRICK_STAIRS = registerBlock("gypsum_brick_stairs",
		() -> new StairBlock(ModBlocks.GYPSUM_STONE_BRICKS.get().defaultBlockState(),
			BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> GYPSUM_BRICK_SLAB = registerBlock("gypsum_brick_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> GYPSUM_BRICK_WALL = registerBlock("gypsum_brick_wall",
		() -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

	public static final RegistrySupplier<Block> FENCE_LIGHT = registerBlock("fence_light",
		() -> new FenceLightBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().lightLevel(state -> 15)));

	public static final RegistrySupplier<Block> LOW_SECURITY_FENCE_POLE = registerBlock("low_security_fence_pole",
		() -> new FencePoleBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().requiresCorrectToolForDrops(), FencePoleBlock.Tier.LOW));
	public static final RegistrySupplier<Block> LOW_SECURITY_FENCE_WIRE = registerBlock("low_security_fence_wire",
		() -> new FenceWireBlock(BlockBehaviour.Properties.of().strength(0.5F).noOcclusion().requiresCorrectToolForDrops(), FenceWireBlock.Tier.LOW));
	public static final RegistrySupplier<Block> MEDIUM_SECURITY_FENCE_POLE = registerBlock("medium_security_fence_pole",
		() -> new FencePoleBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().requiresCorrectToolForDrops(), FencePoleBlock.Tier.MEDIUM));
	public static final RegistrySupplier<Block> MEDIUM_SECURITY_FENCE_WIRE = registerBlock("medium_security_fence_wire",
		() -> new FenceWireBlock(BlockBehaviour.Properties.of().strength(0.5F).noOcclusion().requiresCorrectToolForDrops(), FenceWireBlock.Tier.MEDIUM));



	public static final RegistrySupplier<Block> STONE_FOSSIL = registerBlock("stone_fossil",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> AMBER_ORE = registerBlock("amber_ore",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> DEEPSLATE_ICE_SHARD_ORE = registerBlock("deepslate_ice_shard_ore",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

	public static final RegistrySupplier<Block> REINFORCED_STONE = registerBlock("reinforced_stone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> REINFORCED_STONE_BRICKS = registerBlock("reinforced_stone_bricks",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> CHISELED_REINFORCED_STONE = registerBlock("chiseled_reinforced_stone",
		() -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

	public static final RegistrySupplier<Block> REINFORCED_BRICK_STAIRS = registerBlock("reinforced_brick_stairs",
		() -> new StairBlock(ModBlocks.REINFORCED_STONE_BRICKS.get().defaultBlockState(),
			BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> REINFORCED_BRICK_SLAB = registerBlock("reinforced_brick_slab",
		() -> new SlabBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
	public static final RegistrySupplier<Block> REINFORCED_BRICK_WALL = registerBlock("reinforced_brick_wall",
		() -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

	public static final RegistrySupplier<Block> ALBERTOSAURUS_EGG = registerBlock("albertosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALBERTOSAURUS));

	public static final RegistrySupplier<Block> APATOSAURUS_EGG = registerBlock("apatosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.APATOSAURUS));

	public static final RegistrySupplier<Block> BRACHIOSAURUS_EGG = registerBlock("brachiosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BRACHIOSAURUS));

	public static final RegistrySupplier<Block> CERATOSAURUS_EGG = registerBlock("ceratosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CERATOSAURUS));

	public static final RegistrySupplier<Block> COMPSOGNATHUS_EGG = registerBlock("compsognathus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COMPSOGNATHUS));

	public static final RegistrySupplier<Block> DILOPHOSAURUS_EGG = registerBlock("dilophosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DILOPHOSAURUS));

	public static final RegistrySupplier<Block> DIPLODOCUS_EGG = registerBlock("diplodocus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIPLODOCUS));

	public static final RegistrySupplier<Block> GALLIMIMUS_EGG = registerBlock("gallimimus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GALLIMIMUS));

	public static final RegistrySupplier<Block> INDOMINUS_REX_EGG = registerBlock("indominus_rex_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDOMINUS_REX));

	public static final RegistrySupplier<Block> OURANOSAURUS_EGG = registerBlock("ouranosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OURANOSAURUS));

	public static final RegistrySupplier<Block> PARASAUROLOPHUS_EGG = registerBlock("parasaurolophus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PARASAUROLOPHUS));

	public static final RegistrySupplier<Block> SPINOSAURUS_EGG = registerBlock("spinosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SPINOSAURUS));

	public static final RegistrySupplier<Block> TRICERATOPS_EGG = registerBlock("triceratops_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TRICERATOPS));

	public static final RegistrySupplier<Block> TYRANNOSAURUS_REX_EGG = registerBlock("tyrannosaurus_rex_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TYRANNOSAURUS_REX));

	public static final RegistrySupplier<Block> VELOCIRAPTOR_EGG = registerBlock("velociraptor_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.VELOCIRAPTOR));

	public static final RegistrySupplier<Block> BARYONYX_EGG = registerBlock("baryonyx_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BARYONYX));

	public static final RegistrySupplier<Block> CARNOTAURUS_EGG = registerBlock("carnotaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARNOTAURUS));

	public static final RegistrySupplier<Block> CONCAVENATOR_EGG = registerBlock("concavenator_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CONCAVENATOR));

	public static final RegistrySupplier<Block> DEINONYCHUS_EGG = registerBlock("deinonychus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DEINONYCHUS));

	public static final RegistrySupplier<Block> EDMONTOSAURUS_EGG = registerBlock("edmontosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.EDMONTOSAURUS));

	public static final RegistrySupplier<Block> GIGANOTOSAURUS_EGG = registerBlock("giganotosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GIGANOTOSAURUS));

	public static final RegistrySupplier<Block> GUANLONG_EGG = registerBlock("guanlong_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUANLONG));

	public static final RegistrySupplier<Block> HERRERASAURUS_EGG = registerBlock("herrerasaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HERRERASAURUS));

	public static final RegistrySupplier<Block> MAJUNGASAURUS_EGG = registerBlock("majungasaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAJUNGASAURUS));

	public static final RegistrySupplier<Block> PROCOMPSOGNATHUS_EGG = registerBlock("procompsognathus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCOMPSOGNATHUS));

	public static final RegistrySupplier<Block> PROTOCERATOPS_EGG = registerBlock("protoceratops_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROTOCERATOPS));

	public static final RegistrySupplier<Block> RUGOPS_EGG = registerBlock("rugops_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RUGOPS));

	public static final RegistrySupplier<Block> SHANTUNGOSAURUS_EGG = registerBlock("shantungosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SHANTUNGOSAURUS));

	public static final RegistrySupplier<Block> STEGOSAURUS_EGG = registerBlock("stegosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STEGOSAURUS));

	public static final RegistrySupplier<Block> STYRACOSAURUS_EGG = registerBlock("styracosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STYRACOSAURUS));

	public static final RegistrySupplier<Block> THERIZINOSAURUS_EGG = registerBlock("therizinosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.THERIZINOSAURUS));

	public static final RegistrySupplier<Block> DISTORTUS_REX_EGG = registerBlock("distortus_rex_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DISTORTUS_REX));

	public static final RegistrySupplier<Block> ALLOSAURUS_EGG = registerBlock("allosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALLOSAURUS));

	public static final RegistrySupplier<Block> ALVAREZSAURUS_EGG = registerBlock("alvarezsaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALVAREZSAURUS));

	public static final RegistrySupplier<Block> ANKYLOSAURUS_EGG = registerBlock("ankylosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ANKYLOSAURUS));

	public static final RegistrySupplier<Block> ARAMBOURGIANIA_EGG = registerBlock("arambourgiania_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ARAMBOURGIANIA));

	public static final RegistrySupplier<Block> CARCHARODONTOSAURUS_EGG = registerBlock("carcharodontosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARCHARODONTOSAURUS));

	public static final RegistrySupplier<Block> CEARADACTYLUS_EGG = registerBlock("cearadactylus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CEARADACTYLUS));

	public static final RegistrySupplier<Block> CHASMOSAURUS_EGG = registerBlock("chasmosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CHASMOSAURUS));

	public static final RegistrySupplier<Block> COELOPHYSIS_EGG = registerBlock("coelophysis_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COELOPHYSIS));

	public static final RegistrySupplier<Block> COELURUS_EGG = registerBlock("coelurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COELURUS));

	public static final RegistrySupplier<Block> CORYTHOSAURUS_EGG = registerBlock("corythosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CORYTHOSAURUS));

	public static final RegistrySupplier<Block> DIMORPHODON_EGG = registerBlock("dimorphodon_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIMORPHODON));

	public static final RegistrySupplier<Block> DRYOSAURUS_EGG = registerBlock("dryosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DRYOSAURUS));

	public static final RegistrySupplier<Block> GEOSTERNBERGIA_EGG = registerBlock("geosternbergia_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GEOSTERNBERGIA));

	public static final RegistrySupplier<Block> GUIDRACO_EGG = registerBlock("guidraco_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUIDRACO));

	public static final RegistrySupplier<Block> HADROSAURUS_EGG = registerBlock("hadrosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HADROSAURUS));

	public static final RegistrySupplier<Block> HYPSILOPHODON_EGG = registerBlock("hypsilophodon_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HYPSILOPHODON));

	public static final RegistrySupplier<Block> INDORAPTOR_EGG = registerBlock("indoraptor_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDORAPTOR));

	public static final RegistrySupplier<Block> INOSTRANCEVIA_EGG = registerBlock("inostrancevia_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INOSTRANCEVIA));

	public static final RegistrySupplier<Block> LAMBEOSAURUS_EGG = registerBlock("lambeosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.LAMBEOSAURUS));

	public static final RegistrySupplier<Block> LUDODACTYLUS_EGG = registerBlock("ludodactylus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.LUDODACTYLUS));

	public static final RegistrySupplier<Block> MAMENCHISAURUS_EGG = registerBlock("mamenchisaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAMENCHISAURUS));

	public static final RegistrySupplier<Block> METRIACANTHOSAURUS_EGG = registerBlock("metriacanthosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.METRIACANTHOSAURUS));

	public static final RegistrySupplier<Block> MOGANOPTERUS_EGG = registerBlock("moganopterus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MOGANOPTERUS));

	public static final RegistrySupplier<Block> NYCTOSAURUS_EGG = registerBlock("nyctosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.NYCTOSAURUS));

	public static final RegistrySupplier<Block> ORNITHOLESTES_EGG = registerBlock("ornitholestes_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ORNITHOLESTES));

	public static final RegistrySupplier<Block> ORNITHOMIMUS_EGG = registerBlock("ornithomimus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ORNITHOMIMUS));

	public static final RegistrySupplier<Block> OVIRAPTOR_EGG = registerBlock("oviraptor_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OVIRAPTOR));

	public static final RegistrySupplier<Block> PACHYCEPHALOSAURUS_EGG = registerBlock("pachycephalosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PACHYCEPHALOSAURUS));

	public static final RegistrySupplier<Block> PROCERATOSAURUS_EGG = registerBlock("proceratosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCERATOSAURUS));

	public static final RegistrySupplier<Block> PTERANODON_EGG = registerBlock("pteranodon_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PTERANODON));

	public static final RegistrySupplier<Block> PTERODAUSTRO_EGG = registerBlock("pterodaustro_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PTERODAUSTRO));

	public static final RegistrySupplier<Block> QUETZALCOATLUS_EGG = registerBlock("quetzalcoatlus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.QUETZALCOATLUS));

	public static final RegistrySupplier<Block> RAJASAURUS_EGG = registerBlock("rajasaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RAJASAURUS));

	public static final RegistrySupplier<Block> SEGISAURUS_EGG = registerBlock("segisaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SEGISAURUS));

	public static final RegistrySupplier<Block> TAPEJARA_EGG = registerBlock("tapejara_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TAPEJARA));

	public static final RegistrySupplier<Block> TITANOSAURUS_EGG = registerBlock("titanosaurus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TITANOSAURUS));

	public static final RegistrySupplier<Block> TROODON_EGG = registerBlock("troodon_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TROODON));

	public static final RegistrySupplier<Block> TROPEOGNATHUS_EGG = registerBlock("tropeognathus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TROPEOGNATHUS));

	public static final RegistrySupplier<Block> TUPUXUARA_EGG = registerBlock("tupuxuara_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TUPUXUARA));

	public static final RegistrySupplier<Block> UTAHRAPTOR_EGG = registerBlock("utahraptor_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.UTAHRAPTOR));

	public static final RegistrySupplier<Block> ZHENYUANOPTERUS_EGG = registerBlock("zhenyuanopterus_egg",
		() -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ZHENYUANOPTERUS));



	public static final RegistrySupplier<Block> INCUBATED_APATOSAURUS_EGG = registerBlock("incubated_apatosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.APATOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_ALBERTOSAURUS_EGG = registerBlock("incubated_albertosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALBERTOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_VELOCIRAPTOR_EGG = registerBlock("incubated_velociraptor_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.VELOCIRAPTOR));

	public static final RegistrySupplier<Block> INCUBATED_TYRANNOSAURUS_REX_EGG = registerBlock("incubated_tyrannosaurus_rex_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TYRANNOSAURUS_REX));

	public static final RegistrySupplier<Block> INCUBATED_TRICERATOPS_EGG = registerBlock("incubated_triceratops_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TRICERATOPS));

	public static final RegistrySupplier<Block> INCUBATED_SPINOSAURUS_EGG = registerBlock("incubated_spinosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SPINOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_PARASAUROLOPHUS_EGG = registerBlock("incubated_parasaurolophus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PARASAUROLOPHUS));

	public static final RegistrySupplier<Block> INCUBATED_INDOMINUS_REX_EGG = registerBlock("incubated_indominus_rex_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDOMINUS_REX));

	public static final RegistrySupplier<Block> INCUBATED_GALLIMIMUS_EGG = registerBlock("incubated_gallimimus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GALLIMIMUS));

	public static final RegistrySupplier<Block> INCUBATED_DIPLODOCUS_EGG = registerBlock("incubated_diplodocus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIPLODOCUS));

	public static final RegistrySupplier<Block> INCUBATED_OURANOSAURUS_EGG = registerBlock("incubated_ouranosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OURANOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_DILOPHOSAURUS_EGG = registerBlock("incubated_dilophosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DILOPHOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_COMPSOGNATHUS_EGG = registerBlock("incubated_compsognathus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COMPSOGNATHUS));

	public static final RegistrySupplier<Block> INCUBATED_CERATOSAURUS_EGG = registerBlock("incubated_ceratosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CERATOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_BRACHIOSAURUS_EGG = registerBlock("incubated_brachiosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BRACHIOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_BARYONYX_EGG = registerBlock("incubated_baryonyx_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BARYONYX));

	public static final RegistrySupplier<Block> INCUBATED_CARNOTAURUS_EGG = registerBlock("incubated_carnotaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARNOTAURUS));

	public static final RegistrySupplier<Block> INCUBATED_CONCAVENATOR_EGG = registerBlock("incubated_concavenator_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CONCAVENATOR));

	public static final RegistrySupplier<Block> INCUBATED_DEINONYCHUS_EGG = registerBlock("incubated_deinonychus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DEINONYCHUS));

	public static final RegistrySupplier<Block> INCUBATED_EDMONTOSAURUS_EGG = registerBlock("incubated_edmontosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.EDMONTOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_GIGANOTOSAURUS_EGG = registerBlock("incubated_giganotosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GIGANOTOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_GUANLONG_EGG = registerBlock("incubated_guanlong_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUANLONG));

	public static final RegistrySupplier<Block> INCUBATED_HERRERASAURUS_EGG = registerBlock("incubated_herrerasaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HERRERASAURUS));

	public static final RegistrySupplier<Block> INCUBATED_MAJUNGASAURUS_EGG = registerBlock("incubated_majungasaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAJUNGASAURUS));

	public static final RegistrySupplier<Block> INCUBATED_PROCOMPSOGNATHUS_EGG = registerBlock("incubated_procompsognathus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCOMPSOGNATHUS));

	public static final RegistrySupplier<Block> INCUBATED_PROTOCERATOPS_EGG = registerBlock("incubated_protoceratops_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROTOCERATOPS));

	public static final RegistrySupplier<Block> INCUBATED_RUGOPS_EGG = registerBlock("incubated_rugops_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RUGOPS));

	public static final RegistrySupplier<Block> INCUBATED_SHANTUNGOSAURUS_EGG = registerBlock("incubated_shantungosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SHANTUNGOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_STEGOSAURUS_EGG = registerBlock("incubated_stegosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STEGOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_STYRACOSAURUS_EGG = registerBlock("incubated_styracosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STYRACOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_THERIZINOSAURUS_EGG = registerBlock("incubated_therizinosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.THERIZINOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_DISTORTUS_REX_EGG = registerBlock("incubated_distortus_rex_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DISTORTUS_REX));

	public static final RegistrySupplier<Block> INCUBATED_ALLOSAURUS_EGG = registerBlock("incubated_allosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALLOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_ALVAREZSAURUS_EGG = registerBlock("incubated_alvarezsaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALVAREZSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_ANKYLOSAURUS_EGG = registerBlock("incubated_ankylosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ANKYLOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_ARAMBOURGIANIA_EGG = registerBlock("incubated_arambourgiania_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ARAMBOURGIANIA));

	public static final RegistrySupplier<Block> INCUBATED_CARCHARODONTOSAURUS_EGG = registerBlock("incubated_carcharodontosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARCHARODONTOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_CEARADACTYLUS_EGG = registerBlock("incubated_cearadactylus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CEARADACTYLUS));

	public static final RegistrySupplier<Block> INCUBATED_CHASMOSAURUS_EGG = registerBlock("incubated_chasmosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CHASMOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_COELOPHYSIS_EGG = registerBlock("incubated_coelophysis_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COELOPHYSIS));

	public static final RegistrySupplier<Block> INCUBATED_COELURUS_EGG = registerBlock("incubated_coelurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COELURUS));

	public static final RegistrySupplier<Block> INCUBATED_CORYTHOSAURUS_EGG = registerBlock("incubated_corythosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CORYTHOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_DIMORPHODON_EGG = registerBlock("incubated_dimorphodon_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIMORPHODON));

	public static final RegistrySupplier<Block> INCUBATED_DRYOSAURUS_EGG = registerBlock("incubated_dryosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DRYOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_GEOSTERNBERGIA_EGG = registerBlock("incubated_geosternbergia_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GEOSTERNBERGIA));

	public static final RegistrySupplier<Block> INCUBATED_GUIDRACO_EGG = registerBlock("incubated_guidraco_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUIDRACO));

	public static final RegistrySupplier<Block> INCUBATED_HADROSAURUS_EGG = registerBlock("incubated_hadrosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HADROSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_HYPSILOPHODON_EGG = registerBlock("incubated_hypsilophodon_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HYPSILOPHODON));

	public static final RegistrySupplier<Block> INCUBATED_INDORAPTOR_EGG = registerBlock("incubated_indoraptor_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDORAPTOR));

	public static final RegistrySupplier<Block> INCUBATED_INOSTRANCEVIA_EGG = registerBlock("incubated_inostrancevia_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INOSTRANCEVIA));

	public static final RegistrySupplier<Block> INCUBATED_LAMBEOSAURUS_EGG = registerBlock("incubated_lambeosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.LAMBEOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_LUDODACTYLUS_EGG = registerBlock("incubated_ludodactylus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.LUDODACTYLUS));

	public static final RegistrySupplier<Block> INCUBATED_MAMENCHISAURUS_EGG = registerBlock("incubated_mamenchisaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAMENCHISAURUS));

	public static final RegistrySupplier<Block> INCUBATED_METRIACANTHOSAURUS_EGG = registerBlock("incubated_metriacanthosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.METRIACANTHOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_MOGANOPTERUS_EGG = registerBlock("incubated_moganopterus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MOGANOPTERUS));

	public static final RegistrySupplier<Block> INCUBATED_NYCTOSAURUS_EGG = registerBlock("incubated_nyctosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.NYCTOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_ORNITHOLESTES_EGG = registerBlock("incubated_ornitholestes_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ORNITHOLESTES));

	public static final RegistrySupplier<Block> INCUBATED_ORNITHOMIMUS_EGG = registerBlock("incubated_ornithomimus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ORNITHOMIMUS));

	public static final RegistrySupplier<Block> INCUBATED_OVIRAPTOR_EGG = registerBlock("incubated_oviraptor_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OVIRAPTOR));

	public static final RegistrySupplier<Block> INCUBATED_PACHYCEPHALOSAURUS_EGG = registerBlock("incubated_pachycephalosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PACHYCEPHALOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_PROCERATOSAURUS_EGG = registerBlock("incubated_proceratosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCERATOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_PTERANODON_EGG = registerBlock("incubated_pteranodon_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PTERANODON));

	public static final RegistrySupplier<Block> INCUBATED_PTERODAUSTRO_EGG = registerBlock("incubated_pterodaustro_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PTERODAUSTRO));

	public static final RegistrySupplier<Block> INCUBATED_QUETZALCOATLUS_EGG = registerBlock("incubated_quetzalcoatlus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.QUETZALCOATLUS));

	public static final RegistrySupplier<Block> INCUBATED_RAJASAURUS_EGG = registerBlock("incubated_rajasaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RAJASAURUS));

	public static final RegistrySupplier<Block> INCUBATED_SEGISAURUS_EGG = registerBlock("incubated_segisaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SEGISAURUS));

	public static final RegistrySupplier<Block> INCUBATED_TAPEJARA_EGG = registerBlock("incubated_tapejara_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TAPEJARA));

	public static final RegistrySupplier<Block> INCUBATED_TITANOSAURUS_EGG = registerBlock("incubated_titanosaurus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TITANOSAURUS));

	public static final RegistrySupplier<Block> INCUBATED_TROODON_EGG = registerBlock("incubated_troodon_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TROODON));

	public static final RegistrySupplier<Block> INCUBATED_TROPEOGNATHUS_EGG = registerBlock("incubated_tropeognathus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TROPEOGNATHUS));

	public static final RegistrySupplier<Block> INCUBATED_TUPUXUARA_EGG = registerBlock("incubated_tupuxuara_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TUPUXUARA));

	public static final RegistrySupplier<Block> INCUBATED_UTAHRAPTOR_EGG = registerBlock("incubated_utahraptor_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.UTAHRAPTOR));

	public static final RegistrySupplier<Block> INCUBATED_ZHENYUANOPTERUS_EGG = registerBlock("incubated_zhenyuanopterus_egg",
		() -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ZHENYUANOPTERUS));


	// --- Helper Methods ---

	/**
	 * Registers a block and a corresponding BlockItem.
	 */

	private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
		RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistrySupplier<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register() {
		BLOCKS.register();
	}
}