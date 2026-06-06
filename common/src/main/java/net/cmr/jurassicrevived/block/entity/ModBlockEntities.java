package net.cmr.jurassicrevived.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
		DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

	public static final RegistrySupplier<BlockEntityType<CrateBlockEntity>> CRATE_BE =
		BLOCK_ENTITIES.register("crate", () ->
			BlockEntityType.Builder.of(
				(pos, state) -> new CrateBlockEntity(pos, state, 9),
				ModBlocks.WOOD_CRATE.get(), ModBlocks.IRON_CRATE.get()
			).build(null)
		);

	public static final RegistrySupplier<BlockEntityType<TankBlockEntity>> TANK_BE =
		BLOCK_ENTITIES.register("tank_be", () -> BlockEntityType.Builder.of(
			TankBlockEntity::new, ModBlocks.TANK.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<PowerCellBlockEntity>> POWER_CELL_BE =
		BLOCK_ENTITIES.register("power_cell_be", () -> BlockEntityType.Builder.of(
			PowerCellBlockEntity::new, ModBlocks.POWER_CELL.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<EggBlockEntity>> EGG_BE =
		BLOCK_ENTITIES.register("egg_be", () ->
			BlockEntityType.Builder.of(EggBlockEntity::new,
				ModBlocks.APATOSAURUS_EGG.get(),
				ModBlocks.ALBERTOSAURUS_EGG.get(),
				ModBlocks.VELOCIRAPTOR_EGG.get(),
				ModBlocks.TYRANNOSAURUS_REX_EGG.get(),
				ModBlocks.TRICERATOPS_EGG.get(),
				ModBlocks.SPINOSAURUS_EGG.get(),
				ModBlocks.PARASAUROLOPHUS_EGG.get(),
				ModBlocks.INDOMINUS_REX_EGG.get(),
				ModBlocks.GALLIMIMUS_EGG.get(),
				ModBlocks.DIPLODOCUS_EGG.get(),
				ModBlocks.OURANOSAURUS_EGG.get(),
				ModBlocks.DILOPHOSAURUS_EGG.get(),
				ModBlocks.COMPSOGNATHUS_EGG.get(),
				ModBlocks.CERATOSAURUS_EGG.get(),
				ModBlocks.BRACHIOSAURUS_EGG.get(),
				ModBlocks.BARYONYX_EGG.get(),
				ModBlocks.CARNOTAURUS_EGG.get(),
				ModBlocks.CONCAVENATOR_EGG.get(),
				ModBlocks.DEINONYCHUS_EGG.get(),
				ModBlocks.EDMONTOSAURUS_EGG.get(),
				ModBlocks.GIGANOTOSAURUS_EGG.get(),
				ModBlocks.GUANLONG_EGG.get(),
				ModBlocks.HERRERASAURUS_EGG.get(),
				ModBlocks.MAJUNGASAURUS_EGG.get(),
				ModBlocks.PROCOMPSOGNATHUS_EGG.get(),
				ModBlocks.PROTOCERATOPS_EGG.get(),
				ModBlocks.RUGOPS_EGG.get(),
				ModBlocks.SHANTUNGOSAURUS_EGG.get(),
				ModBlocks.STEGOSAURUS_EGG.get(),
				ModBlocks.STYRACOSAURUS_EGG.get(),
				ModBlocks.THERIZINOSAURUS_EGG.get(),
				ModBlocks.DISTORTUS_REX_EGG.get(),
				ModBlocks.ALLOSAURUS_EGG.get(),
				ModBlocks.ALVAREZSAURUS_EGG.get(),
				ModBlocks.ANKYLOSAURUS_EGG.get(),
				ModBlocks.ARAMBOURGIANIA_EGG.get(),
				ModBlocks.CARCHARODONTOSAURUS_EGG.get(),
				ModBlocks.CEARADACTYLUS_EGG.get(),
				ModBlocks.CHASMOSAURUS_EGG.get(),
				ModBlocks.COELOPHYSIS_EGG.get(),
				ModBlocks.COELURUS_EGG.get(),
				ModBlocks.CORYTHOSAURUS_EGG.get(),
				ModBlocks.DIMORPHODON_EGG.get(),
				ModBlocks.DRYOSAURUS_EGG.get(),
				ModBlocks.GEOSTERNBERGIA_EGG.get(),
				ModBlocks.GUIDRACO_EGG.get(),
				ModBlocks.HADROSAURUS_EGG.get(),
				ModBlocks.HYPSILOPHODON_EGG.get(),
				ModBlocks.INDORAPTOR_EGG.get(),
				ModBlocks.INOSTRANCEVIA_EGG.get(),
				ModBlocks.LAMBEOSAURUS_EGG.get(),
				ModBlocks.LUDODACTYLUS_EGG.get(),
				ModBlocks.MAMENCHISAURUS_EGG.get(),
				ModBlocks.METRIACANTHOSAURUS_EGG.get(),
				ModBlocks.MOGANOPTERUS_EGG.get(),
				ModBlocks.NYCTOSAURUS_EGG.get(),
				ModBlocks.ORNITHOLESTES_EGG.get(),
				ModBlocks.ORNITHOMIMUS_EGG.get(),
				ModBlocks.OVIRAPTOR_EGG.get(),
				ModBlocks.PACHYCEPHALOSAURUS_EGG.get(),
				ModBlocks.PROCERATOSAURUS_EGG.get(),
				ModBlocks.PTERANODON_EGG.get(),
				ModBlocks.PTERODAUSTRO_EGG.get(),
				ModBlocks.QUETZALCOATLUS_EGG.get(),
				ModBlocks.RAJASAURUS_EGG.get(),
				ModBlocks.SEGISAURUS_EGG.get(),
				ModBlocks.TAPEJARA_EGG.get(),
				ModBlocks.TITANOSAURUS_EGG.get(),
				ModBlocks.TROODON_EGG.get(),
				ModBlocks.TROPEOGNATHUS_EGG.get(),
				ModBlocks.TUPUXUARA_EGG.get(),
				ModBlocks.UTAHRAPTOR_EGG.get(),
				ModBlocks.ZHENYUANOPTERUS_EGG.get(),
				ModBlocks.ACHILLOBATOR_EGG.get(),
				ModBlocks.SUCHOMIMUS_EGG.get(),
				ModBlocks.CHILESAURUS_EGG.get(),
				ModBlocks.MUSSASAURUS_EGG.get(),
				ModBlocks.THESCELOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_APATOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get(),
				ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get(),
				ModBlocks.INCUBATED_TRICERATOPS_EGG.get(),
				ModBlocks.INCUBATED_SPINOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get(),
				ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get(),
				ModBlocks.INCUBATED_GALLIMIMUS_EGG.get(),
				ModBlocks.INCUBATED_DIPLODOCUS_EGG.get(),
				ModBlocks.INCUBATED_OURANOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get(),
				ModBlocks.INCUBATED_CERATOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get(),
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
				ModBlocks.INCUBATED_ACHILLOBATOR_EGG.get(),
				ModBlocks.INCUBATED_SUCHOMIMUS_EGG.get(),
				ModBlocks.INCUBATED_MUSSASAURUS_EGG.get(),
				ModBlocks.INCUBATED_THESCELOSAURUS_EGG.get(),
				ModBlocks.INCUBATED_CHILESAURUS_EGG.get()
			).build(null));

	public static final RegistrySupplier<BlockEntityType<DNAExtractorBlockEntity>> DNA_EXTRACTOR_BE =
		BLOCK_ENTITIES.register("dna_extractor_be", () -> BlockEntityType.Builder.of(
			DNAExtractorBlockEntity::new, ModBlocks.DNA_EXTRACTOR.get(), ModBlocks.WHITE_DNA_EXTRACTOR.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<DNAAnalyzerBlockEntity>> DNA_ANALYZER_BE =
		BLOCK_ENTITIES.register("dna_analyzer_be", () -> BlockEntityType.Builder.of(
			DNAAnalyzerBlockEntity::new, ModBlocks.DNA_ANALYZER.get(), ModBlocks.WHITE_DNA_ANALYZER.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<FossilGrinderBlockEntity>> FOSSIL_GRINDER_BE =
		BLOCK_ENTITIES.register("fossil_grinder_be", () -> BlockEntityType.Builder.of(
			FossilGrinderBlockEntity::new, ModBlocks.FOSSIL_GRINDER.get(), ModBlocks.WHITE_FOSSIL_GRINDER.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<FossilCleanerBlockEntity>> FOSSIL_CLEANER_BE =
		BLOCK_ENTITIES.register("fossil_cleaner_be", () -> BlockEntityType.Builder.of(
			FossilCleanerBlockEntity::new, ModBlocks.FOSSIL_CLEANER.get(), ModBlocks.WHITE_FOSSIL_CLEANER.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<PipeBlockEntity>> ITEM_PIPE_BE =
		BLOCK_ENTITIES.register("item_pipe_be", () -> BlockEntityType.Builder.of(
			PipeBlockEntity::new, ModBlocks.ITEM_PIPE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<PipeBlockEntity>> FLUID_PIPE_BE =
		BLOCK_ENTITIES.register("fluid_pipe_be", () -> BlockEntityType.Builder.of(
			PipeBlockEntity::new, ModBlocks.FLUID_PIPE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<PipeBlockEntity>> POWER_PIPE_BE =
		BLOCK_ENTITIES.register("power_pipe_be", () -> BlockEntityType.Builder.of(
			PipeBlockEntity::new, ModBlocks.POWER_PIPE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE =
		BLOCK_ENTITIES.register("generator_be", () -> BlockEntityType.Builder.of(
			GeneratorBlockEntity::new, ModBlocks.GENERATOR.get(), ModBlocks.WHITE_GENERATOR.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<DNAHybridizerBlockEntity>> DNA_HYBRIDIZER_BE =
		BLOCK_ENTITIES.register("dna_hybridizer_be", () -> BlockEntityType.Builder.of(
			DNAHybridizerBlockEntity::new, ModBlocks.DNA_HYBRIDIZER.get(), ModBlocks.WHITE_DNA_HYBRIDIZER.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<EmbryonicMachineBlockEntity>> EMBRYONIC_MACHINE_BE =
		BLOCK_ENTITIES.register("embryonic_machine_be", () -> BlockEntityType.Builder.of(
			EmbryonicMachineBlockEntity::new, ModBlocks.EMBRYONIC_MACHINE.get(), ModBlocks.WHITE_EMBRYONIC_MACHINE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<EmbryoCalcificationMachineBlockEntity>> EMBRYO_CALCIFICATION_MACHINE_BE =
		BLOCK_ENTITIES.register("embryo_calcification_machine_be", () -> BlockEntityType.Builder.of(
			EmbryoCalcificationMachineBlockEntity::new, ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(), ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get()).build(null));

	public static final RegistrySupplier<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_BE =
		BLOCK_ENTITIES.register("incubator_be", () -> BlockEntityType.Builder.of(
			IncubatorBlockEntity::new, ModBlocks.INCUBATOR.get(), ModBlocks.WHITE_INCUBATOR.get()).build(null));

	public static void register() {
		BLOCK_ENTITIES.register();
	}
}