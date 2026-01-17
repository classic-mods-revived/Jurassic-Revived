package net.cmr.jurassicrevived;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.renderer.TankBlockEntityRenderer;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.client.*;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.screen.custom.*;
import net.cmr.jurassicrevived.sound.MachineHumSoundHandler;
import net.cmr.jurassicrevived.util.FenceClimbClientHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;

public class CommonClientClass {
	public static void init() {
		// Initialize client-only systems
		MachineHumSoundHandler.init();
		FenceClimbClientHandler.register();

		// Register Entity Renderers (Architectury handles suppliers here)
		EntityRendererRegistry.register(ModEntities.SEAT, NoopRenderer::new);
		EntityRendererRegistry.register(ModEntities.APATOSAURUS, ApatosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.ALBERTOSAURUS, AlbertosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.BARYONYX, BaryonyxRenderer::new);
		EntityRendererRegistry.register(ModEntities.BRACHIOSAURUS, BrachiosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARNOTAURUS, CarnotaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CERATOSAURUS, CeratosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.COMPSOGNATHUS, CompsognathusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CONCAVENATOR, ConcavenatorRenderer::new);
		EntityRendererRegistry.register(ModEntities.DEINONYCHUS, DeinonychusRenderer::new);
		EntityRendererRegistry.register(ModEntities.DILOPHOSAURUS, DilophosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.DIPLODOCUS, DiplodocusRenderer::new);
		EntityRendererRegistry.register(ModEntities.DISTORTUS_REX, DistortusRexRenderer::new);
		EntityRendererRegistry.register(ModEntities.EDMONTOSAURUS, EdmontosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.FDUCK, FDuckRenderer::new);
		EntityRendererRegistry.register(ModEntities.GALLIMIMUS, GallimimusRenderer::new);
		EntityRendererRegistry.register(ModEntities.GIGANOTOSAURUS, GiganotosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.GUANLONG, GuanlongRenderer::new);
		EntityRendererRegistry.register(ModEntities.HERRERASAURUS, HerrerasaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.INDOMINUS_REX, IndominusRexRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAJUNGASAURUS, MajungasaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.OURANOSAURUS, OuranosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.PARASAUROLOPHUS, ParasaurolophusRenderer::new);
		EntityRendererRegistry.register(ModEntities.PROCOMPSOGNATHUS, ProcompsognathusRenderer::new);
		EntityRendererRegistry.register(ModEntities.PROTOCERATOPS, ProtoceratopsRenderer::new);
		EntityRendererRegistry.register(ModEntities.ARAMBOURGIANIA, ArambourgianiaRenderer::new);
		EntityRendererRegistry.register(ModEntities.CEARADACTYLUS, CearadactylusRenderer::new);
		EntityRendererRegistry.register(ModEntities.DIMORPHODON, DimorphodonRenderer::new);
		EntityRendererRegistry.register(ModEntities.GEOSTERNBERGIA, GeosternbergiaRenderer::new);
		EntityRendererRegistry.register(ModEntities.GUIDRACO, GuidracoRenderer::new);
		EntityRendererRegistry.register(ModEntities.LUDODACTYLUS, LudodactylusRenderer::new);
		EntityRendererRegistry.register(ModEntities.MOGANOPTERUS, MoganopterusRenderer::new);
		EntityRendererRegistry.register(ModEntities.NYCTOSAURUS, NyctosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.PTERANODON, PteranodonRenderer::new);
		EntityRendererRegistry.register(ModEntities.PTERODAUSTRO, PterodaustroRenderer::new);
		EntityRendererRegistry.register(ModEntities.QUETZALCOATLUS, QuetzalcoatlusRenderer::new);
		EntityRendererRegistry.register(ModEntities.TAPEJARA, TapejaraRenderer::new);
		EntityRendererRegistry.register(ModEntities.TROPEOGNATHUS, TropeognathusRenderer::new);
		EntityRendererRegistry.register(ModEntities.TUPUXUARA, TupuxuaraRenderer::new);
		EntityRendererRegistry.register(ModEntities.ZHENYUANOPTERUS, ZhenyuanopterusRenderer::new);
		EntityRendererRegistry.register(ModEntities.RUGOPS, RugopsRenderer::new);
		EntityRendererRegistry.register(ModEntities.SHANTUNGOSAURUS, ShantungosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.SPINOSAURUS, SpinosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.STEGOSAURUS, StegosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.STYRACOSAURUS, StyracosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.THERIZINOSAURUS, TherizinosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.TRICERATOPS, TriceratopsRenderer::new);
		EntityRendererRegistry.register(ModEntities.TYRANNOSAURUS_REX, TyrannosaurusRexRenderer::new);
		EntityRendererRegistry.register(ModEntities.VELOCIRAPTOR, VelociraptorRenderer::new);
		EntityRendererRegistry.register(ModEntities.CHICKENOSAURUS, ChickenosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.ALLOSAURUS, AllosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.ALVAREZSAURUS, AlvarezsaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.ANKYLOSAURUS, AnkylosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CARCHARODONTOSAURUS, CarcharodontosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CHASMOSAURUS, ChasmosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.COELOPHYSIS, CoelophysisRenderer::new);
		EntityRendererRegistry.register(ModEntities.COELURUS, CoelurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.CORYTHOSAURUS, CorythosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.DRYOSAURUS, DryosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.HADROSAURUS, HadrosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.HYPSILOPHODON, HypsilophodonRenderer::new);
		EntityRendererRegistry.register(ModEntities.INDORAPTOR, IndoraptorRenderer::new);
		EntityRendererRegistry.register(ModEntities.INOSTRANCEVIA, InostranceviaRenderer::new);
		EntityRendererRegistry.register(ModEntities.LAMBEOSAURUS, LambeosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAMENCHISAURUS, MamenchisaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.METRIACANTHOSAURUS, MetriacanthosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.ORNITHOLESTES, OrnitholestesRenderer::new);
		EntityRendererRegistry.register(ModEntities.ORNITHOMIMUS, OrnithomimusRenderer::new);
		EntityRendererRegistry.register(ModEntities.OVIRAPTOR, OviraptorRenderer::new);
		EntityRendererRegistry.register(ModEntities.PACHYCEPHALOSAURUS, PachycephalosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.PROCERATOSAURUS, ProceratosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.RAJASAURUS, RajasaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.SEGISAURUS, SegisaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.TITANOSAURUS, TitanosaurusRenderer::new);
		EntityRendererRegistry.register(ModEntities.TROODON, TroodonRenderer::new);
		EntityRendererRegistry.register(ModEntities.UTAHRAPTOR, UtahraptorRenderer::new);

        //? if <=1.20.1 {
		LifecycleEvent.SETUP.register(() -> {
			MenuRegistry.registerScreenFactory(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DNAExtractorScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_ANALYZER_MENU.get(), DNAAnalyzerScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.FOSSIL_GRINDER_MENU.get(), FossilGrinderScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.FOSSIL_CLEANER_MENU.get(), FossilCleanerScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.DNA_HYBRIDIZER_MENU.get(), DNAHybridizerScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(), EmbryonicMachineScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.EMBRYO_CALCIFICATION_MACHINE_MENU.get(), EmbryoCalcificationMachineScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.INCUBATOR_MENU.get(), IncubatorScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.TANK_MENU.get(), TankScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.POWER_CELL_MENU.get(), PowerCellScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.WOOD_CRATE_MENU.get(), CrateScreen::new);
			MenuRegistry.registerScreenFactory(ModMenuTypes.IRON_CRATE_MENU.get(), CrateScreen::new);
		});
        //?}

		if (Platform.isFabric()) {
			registerSpawnEggColors();
		} else {
			ClientLifecycleEvent.CLIENT_SETUP.register(mc -> registerSpawnEggColors());
		}

        LifecycleEvent.SETUP.register(() -> {
            BlockEntityRendererRegistry.register(ModBlockEntities.TANK_BE.get(), TankBlockEntityRenderer::new);
			registerRenderTypes();
		});
    }

	private static void registerSpawnEggColors() {
		ModItems.ITEMS.forEach(itemSupplier -> {
			Item item = itemSupplier.get();
			if (item instanceof SpawnEggItem) {
				ColorHandlerRegistry.registerItemColors(
					(stack, tintIndex) -> ((SpawnEggItem) stack.getItem()).getColor(tintIndex) | 0xFF000000,
					item
				);
			}
		});
	}

	private static void registerRenderTypes() {
		RenderTypeRegistry.register(RenderType.cutout(),
			ModBlocks.ROYAL_FERN.get(),
			ModBlocks.HORSETAIL_FERN.get(),
			ModBlocks.WESTERN_SWORD_FERN.get(),
			ModBlocks.ONYCHIOPSIS.get(),
			ModBlocks.POTTED_ROYAL_FERN.get(),
			ModBlocks.POTTED_HORSETAIL_FERN.get(),
			ModBlocks.POTTED_WESTERN_SWORD_FERN.get(),
			ModBlocks.POTTED_ONYCHIOPSIS.get()
		);
		RenderTypeRegistry.register(RenderType.translucent(),
			ModBlocks.FLUID_PIPE.get(),
			ModBlocks.POWER_PIPE.get(),
			ModBlocks.ITEM_PIPE.get()
		);
	}
}
