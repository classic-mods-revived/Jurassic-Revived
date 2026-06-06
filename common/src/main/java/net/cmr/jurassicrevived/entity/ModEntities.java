package net.cmr.jurassicrevived.entity;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
/*? if >1.20.1 {*/
/*import net.minecraft.world.entity.SpawnPlacementTypes;
*//*?} else {*/
import net.minecraft.world.entity.SpawnPlacements;
/*?}*/
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES =
		DeferredRegister.create(Constants.MOD_ID, Registries.ENTITY_TYPE);

	public static final RegistrySupplier<EntityType<SeatEntity>> SEAT =
		ENTITIES.register("seat", () ->
			EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
				.sized(0.001f, 0.001f)
				.clientTrackingRange(16)
				.updateInterval(1)
				.build("seat")
		);

	public static final RegistrySupplier<EntityType<AlbertosaurusEntity>> ALBERTOSAURUS =
		ENTITIES.register("albertosaurus", () -> EntityType.Builder.of(AlbertosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("albertosaurus"));

	public static final RegistrySupplier<EntityType<ApatosaurusEntity>> APATOSAURUS =
		ENTITIES.register("apatosaurus", () -> EntityType.Builder.of(ApatosaurusEntity::new, MobCategory.CREATURE)
			.sized(3.0f, 5.0f).build("apatosaurus"));

	public static final RegistrySupplier<EntityType<BrachiosaurusEntity>> BRACHIOSAURUS =
		ENTITIES.register("brachiosaurus", () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE)
			.sized(4.0f, 7.0f).build("brachiosaurus"));

	public static final RegistrySupplier<EntityType<CeratosaurusEntity>> CERATOSAURUS =
		ENTITIES.register("ceratosaurus", () -> EntityType.Builder.of(CeratosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("ceratosaurus"));

	public static final RegistrySupplier<EntityType<CompsognathusEntity>> COMPSOGNATHUS =
		ENTITIES.register("compsognathus", () -> EntityType.Builder.of(CompsognathusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("compsognathus"));

	public static final RegistrySupplier<EntityType<DilophosaurusEntity>> DILOPHOSAURUS =
		ENTITIES.register("dilophosaurus", () -> EntityType.Builder.of(DilophosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("dilophosaurus"));

	public static final RegistrySupplier<EntityType<DiplodocusEntity>> DIPLODOCUS =
		ENTITIES.register("diplodocus", () -> EntityType.Builder.of(DiplodocusEntity::new, MobCategory.CREATURE)
			.sized(3.0f, 6.0f).build("diplodocus"));

	public static final RegistrySupplier<EntityType<FDuckEntity>> FDUCK =
		ENTITIES.register("fduck", () -> EntityType.Builder.of(FDuckEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("fduck"));

	public static final RegistrySupplier<EntityType<GallimimusEntity>> GALLIMIMUS =
		ENTITIES.register("gallimimus", () -> EntityType.Builder.of(GallimimusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.5f).build("gallimimus"));

	public static final RegistrySupplier<EntityType<IndominusRexEntity>> INDOMINUS_REX =
		ENTITIES.register("indominus_rex", () -> EntityType.Builder.of(IndominusRexEntity::new, MobCategory.CREATURE)
			.sized(3.0f, 5.5f).build("indominus_rex"));

	public static final RegistrySupplier<EntityType<OuranosaurusEntity>> OURANOSAURUS =
		ENTITIES.register("ouranosaurus", () -> EntityType.Builder.of(OuranosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 3.0f).build("ouranosaurus"));

	public static final RegistrySupplier<EntityType<ParasaurolophusEntity>> PARASAUROLOPHUS =
		ENTITIES.register("parasaurolophus", () -> EntityType.Builder.of(ParasaurolophusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("parasaurolophus"));

	public static final RegistrySupplier<EntityType<SpinosaurusEntity>> SPINOSAURUS =
		ENTITIES.register("spinosaurus", () -> EntityType.Builder.of(SpinosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.5f, 5.0f).build("spinosaurus"));

	public static final RegistrySupplier<EntityType<TriceratopsEntity>> TRICERATOPS =
		ENTITIES.register("triceratops", () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE)
			.sized(2.5f, 3.5f).build("triceratops"));

	public static final RegistrySupplier<EntityType<TyrannosaurusRexEntity>> TYRANNOSAURUS_REX =
		ENTITIES.register("tyrannosaurus_rex", () -> EntityType.Builder.of(TyrannosaurusRexEntity::new, MobCategory.CREATURE)
			.sized(2.5f, 5.0f).build("tyrannosaurus_rex"));

	public static final RegistrySupplier<EntityType<VelociraptorEntity>> VELOCIRAPTOR =
		ENTITIES.register("velociraptor", () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("velociraptor"));

	public static final RegistrySupplier<EntityType<BaryonyxEntity>> BARYONYX =
		ENTITIES.register("baryonyx", () -> EntityType.Builder.of(BaryonyxEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("baryonyx"));

	public static final RegistrySupplier<EntityType<CarnotaurusEntity>> CARNOTAURUS =
		ENTITIES.register("carnotaurus", () -> EntityType.Builder.of(CarnotaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("carnotaurus"));

	public static final RegistrySupplier<EntityType<ConcavenatorEntity>> CONCAVENATOR =
		ENTITIES.register("concavenator", () -> EntityType.Builder.of(ConcavenatorEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("concavenator"));

	public static final RegistrySupplier<EntityType<DeinonychusEntity>> DEINONYCHUS =
		ENTITIES.register("deinonychus", () -> EntityType.Builder.of(DeinonychusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("deinonychus"));

	public static final RegistrySupplier<EntityType<EdmontosaurusEntity>> EDMONTOSAURUS =
		ENTITIES.register("edmontosaurus", () -> EntityType.Builder.of(EdmontosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("edmontosaurus"));

	public static final RegistrySupplier<EntityType<GiganotosaurusEntity>> GIGANOTOSAURUS =
		ENTITIES.register("giganotosaurus", () -> EntityType.Builder.of(GiganotosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.5f, 5.0f).build("giganotosaurus"));

	public static final RegistrySupplier<EntityType<GuanlongEntity>> GUANLONG =
		ENTITIES.register("guanlong", () -> EntityType.Builder.of(GuanlongEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("guanlong"));

	public static final RegistrySupplier<EntityType<HerrerasaurusEntity>> HERRERASAURUS =
		ENTITIES.register("herrerasaurus", () -> EntityType.Builder.of(HerrerasaurusEntity::new, MobCategory.CREATURE)
			.sized(1.5f, 2.5f).build("herrerasaurus"));

	public static final RegistrySupplier<EntityType<MajungasaurusEntity>> MAJUNGASAURUS =
		ENTITIES.register("majungasaurus", () -> EntityType.Builder.of(MajungasaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.5f).build("majungasaurus"));

	public static final RegistrySupplier<EntityType<ProcompsognathusEntity>> PROCOMPSOGNATHUS =
		ENTITIES.register("procompsognathus", () -> EntityType.Builder.of(ProcompsognathusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("procompsognathus"));

	public static final RegistrySupplier<EntityType<ProtoceratopsEntity>> PROTOCERATOPS =
		ENTITIES.register("protoceratops", () -> EntityType.Builder.of(ProtoceratopsEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("protoceratops"));

	public static final RegistrySupplier<EntityType<ArambourgianiaEntity>> ARAMBOURGIANIA =
		ENTITIES.register("arambourgiania", () -> EntityType.Builder.of(ArambourgianiaEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("arambourgiania"));

	public static final RegistrySupplier<EntityType<CearadactylusEntity>> CEARADACTYLUS =
		ENTITIES.register("cearadactylus", () -> EntityType.Builder.of(CearadactylusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("cearadactylus"));

	public static final RegistrySupplier<EntityType<DimorphodonEntity>> DIMORPHODON =
		ENTITIES.register("dimorphodon", () -> EntityType.Builder.of(DimorphodonEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("dimorphodon"));

	public static final RegistrySupplier<EntityType<GeosternbergiaEntity>> GEOSTERNBERGIA =
		ENTITIES.register("geosternbergia", () -> EntityType.Builder.of(GeosternbergiaEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.0f).build("geosternbergia"));

	public static final RegistrySupplier<EntityType<GuidracoEntity>> GUIDRACO =
		ENTITIES.register("guidraco", () -> EntityType.Builder.of(GuidracoEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("guidraco"));

	public static final RegistrySupplier<EntityType<LudodactylusEntity>> LUDODACTYLUS =
		ENTITIES.register("ludodactylus", () -> EntityType.Builder.of(LudodactylusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.0f).build("ludodactylus"));

	public static final RegistrySupplier<EntityType<MoganopterusEntity>> MOGANOPTERUS =
		ENTITIES.register("moganopterus", () -> EntityType.Builder.of(MoganopterusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("moganopterus"));

	public static final RegistrySupplier<EntityType<NyctosaurusEntity>> NYCTOSAURUS =
		ENTITIES.register("nyctosaurus", () -> EntityType.Builder.of(NyctosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("nyctosaurus"));

	public static final RegistrySupplier<EntityType<PteranodonEntity>> PTERANODON =
		ENTITIES.register("pteranodon", () -> EntityType.Builder.of(PteranodonEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.0f).build("pteranodon"));

	public static final RegistrySupplier<EntityType<PterodaustroEntity>> PTERODAUSTRO =
		ENTITIES.register("pterodaustro", () -> EntityType.Builder.of(PterodaustroEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("pterodaustro"));

	public static final RegistrySupplier<EntityType<QuetzalcoatlusEntity>> QUETZALCOATLUS =
		ENTITIES.register("quetzalcoatlus", () -> EntityType.Builder.of(QuetzalcoatlusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("quetzalcoatlus"));

	public static final RegistrySupplier<EntityType<TapejaraEntity>> TAPEJARA =
		ENTITIES.register("tapejara", () -> EntityType.Builder.of(TapejaraEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("tapejara"));

	public static final RegistrySupplier<EntityType<TropeognathusEntity>> TROPEOGNATHUS =
		ENTITIES.register("tropeognathus", () -> EntityType.Builder.of(TropeognathusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.0f).build("tropeognathus"));

	public static final RegistrySupplier<EntityType<TupuxuaraEntity>> TUPUXUARA =
		ENTITIES.register("tupuxuara", () -> EntityType.Builder.of(TupuxuaraEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("tupuxuara"));

	public static final RegistrySupplier<EntityType<ZhenyuanopterusEntity>> ZHENYUANOPTERUS =
		ENTITIES.register("zhenyuanopterus", () -> EntityType.Builder.of(ZhenyuanopterusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("zhenyuanopterus"));

	public static final RegistrySupplier<EntityType<RugopsEntity>> RUGOPS =
		ENTITIES.register("rugops", () -> EntityType.Builder.of(RugopsEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("rugops"));

	public static final RegistrySupplier<EntityType<ShantungosaurusEntity>> SHANTUNGOSAURUS =
		ENTITIES.register("shantungosaurus", () -> EntityType.Builder.of(ShantungosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 5.5f).build("shantungosaurus"));

	public static final RegistrySupplier<EntityType<StegosaurusEntity>> STEGOSAURUS =
		ENTITIES.register("stegosaurus", () -> EntityType.Builder.of(StegosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.5f).build("stegosaurus"));

	public static final RegistrySupplier<EntityType<StyracosaurusEntity>> STYRACOSAURUS =
		ENTITIES.register("styracosaurus", () -> EntityType.Builder.of(StyracosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("styracosaurus"));

	public static final RegistrySupplier<EntityType<TherizinosaurusEntity>> THERIZINOSAURUS =
		ENTITIES.register("therizinosaurus", () -> EntityType.Builder.of(TherizinosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("therizinosaurus"));

	public static final RegistrySupplier<EntityType<DistortusRexEntity>> DISTORTUS_REX =
		ENTITIES.register("distortus_rex", () -> EntityType.Builder.of(DistortusRexEntity::new, MobCategory.CREATURE)
			.sized(4.0f, 7.0f).build("distortus_rex"));

	public static final RegistrySupplier<EntityType<ChickenosaurusEntity>> CHICKENOSAURUS =
		ENTITIES.register("chickenosaurus", () -> EntityType.Builder.of(ChickenosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("chickenosaurus"));

	public static final RegistrySupplier<EntityType<AllosaurusEntity>> ALLOSAURUS =
		ENTITIES.register("allosaurus", () -> EntityType.Builder.of(AllosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("allosaurus"));

	public static final RegistrySupplier<EntityType<AlvarezsaurusEntity>> ALVAREZSAURUS =
		ENTITIES.register("alvarezsaurus", () -> EntityType.Builder.of(AlvarezsaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("alvarezsaurus"));

	public static final RegistrySupplier<EntityType<AnkylosaurusEntity>> ANKYLOSAURUS =
		ENTITIES.register("ankylosaurus", () -> EntityType.Builder.of(AnkylosaurusEntity::new, MobCategory.CREATURE)
			.sized(3.0f, 3.0f).build("ankylosaurus"));

	public static final RegistrySupplier<EntityType<CarcharodontosaurusEntity>> CARCHARODONTOSAURUS =
		ENTITIES.register("carcharodontosaurus", () -> EntityType.Builder.of(CarcharodontosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("carcharodontosaurus"));

	public static final RegistrySupplier<EntityType<ChasmosaurusEntity>> CHASMOSAURUS =
		ENTITIES.register("chasmosaurus", () -> EntityType.Builder.of(ChasmosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.0f).build("chasmosaurus"));

	public static final RegistrySupplier<EntityType<CoelophysisEntity>> COELOPHYSIS =
		ENTITIES.register("coelophysis", () -> EntityType.Builder.of(CoelophysisEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("coelophysis"));

	public static final RegistrySupplier<EntityType<CoelurusEntity>> COELURUS =
		ENTITIES.register("coelurus", () -> EntityType.Builder.of(CoelurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("coelurus"));

	public static final RegistrySupplier<EntityType<CorythosaurusEntity>> CORYTHOSAURUS =
		ENTITIES.register("corythosaurus", () -> EntityType.Builder.of(CorythosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("corythosaurus"));

	public static final RegistrySupplier<EntityType<DryosaurusEntity>> DRYOSAURUS =
		ENTITIES.register("dryosaurus", () -> EntityType.Builder.of(DryosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("dryosaurus"));

	public static final RegistrySupplier<EntityType<ThescelosaurusEntity>> THESCELOSAURUS =
		ENTITIES.register("thescelosaurus", () -> EntityType.Builder.of(ThescelosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("thescelosaurus"));

	public static final RegistrySupplier<EntityType<MussasaurusEntity>> MUSSASAURUS =
		ENTITIES.register("mussasaurus", () -> EntityType.Builder.of(MussasaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("mussasaurus"));

	public static final RegistrySupplier<EntityType<ChilesaurusEntity>> CHILESAURUS =
		ENTITIES.register("chilesaurus", () -> EntityType.Builder.of(ChilesaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("chilesaurus"));

	public static final RegistrySupplier<EntityType<HadrosaurusEntity>> HADROSAURUS =
		ENTITIES.register("hadrosaurus", () -> EntityType.Builder.of(HadrosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("hadrosaurus"));

	public static final RegistrySupplier<EntityType<HypsilophodonEntity>> HYPSILOPHODON =
		ENTITIES.register("hypsilophodon", () -> EntityType.Builder.of(HypsilophodonEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("hypsilophodon"));

	public static final RegistrySupplier<EntityType<IndoraptorEntity>> INDORAPTOR =
		ENTITIES.register("indoraptor", () -> EntityType.Builder.of(IndoraptorEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("indoraptor"));

	public static final RegistrySupplier<EntityType<InostranceviaEntity>> INOSTRANCEVIA =
		ENTITIES.register("inostrancevia", () -> EntityType.Builder.of(InostranceviaEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("inostrancevia"));

	public static final RegistrySupplier<EntityType<LambeosaurusEntity>> LAMBEOSAURUS =
		ENTITIES.register("lambeosaurus", () -> EntityType.Builder.of(LambeosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 4.0f).build("lambeosaurus"));

	public static final RegistrySupplier<EntityType<MamenchisaurusEntity>> MAMENCHISAURUS =
		ENTITIES.register("mamenchisaurus", () -> EntityType.Builder.of(MamenchisaurusEntity::new, MobCategory.CREATURE)
			.sized(3.0f, 5.5f).build("mamenchisaurus"));

	public static final RegistrySupplier<EntityType<MetriacanthosaurusEntity>> METRIACANTHOSAURUS =
		ENTITIES.register("metriacanthosaurus", () -> EntityType.Builder.of(MetriacanthosaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("metriacanthosaurus"));

	public static final RegistrySupplier<EntityType<OrnitholestesEntity>> ORNITHOLESTES =
		ENTITIES.register("ornitholestes", () -> EntityType.Builder.of(OrnitholestesEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("ornitholestes"));

	public static final RegistrySupplier<EntityType<OrnithomimusEntity>> ORNITHOMIMUS =
		ENTITIES.register("ornithomimus", () -> EntityType.Builder.of(OrnithomimusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("ornithomimus"));

	public static final RegistrySupplier<EntityType<OviraptorEntity>> OVIRAPTOR =
		ENTITIES.register("oviraptor", () -> EntityType.Builder.of(OviraptorEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("oviraptor"));

	public static final RegistrySupplier<EntityType<PachycephalosaurusEntity>> PACHYCEPHALOSAURUS =
		ENTITIES.register("pachycephalosaurus", () -> EntityType.Builder.of(PachycephalosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("pachycephalosaurus"));

	public static final RegistrySupplier<EntityType<ProceratosaurusEntity>> PROCERATOSAURUS =
		ENTITIES.register("proceratosaurus", () -> EntityType.Builder.of(ProceratosaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("proceratosaurus"));

	public static final RegistrySupplier<EntityType<RajasaurusEntity>> RAJASAURUS =
		ENTITIES.register("rajasaurus", () -> EntityType.Builder.of(RajasaurusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 2.5f).build("rajasaurus"));

	public static final RegistrySupplier<EntityType<SegisaurusEntity>> SEGISAURUS =
		ENTITIES.register("segisaurus", () -> EntityType.Builder.of(SegisaurusEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.5f).build("segisaurus"));

	public static final RegistrySupplier<EntityType<TitanosaurusEntity>> TITANOSAURUS =
		ENTITIES.register("titanosaurus", () -> EntityType.Builder.of(TitanosaurusEntity::new, MobCategory.CREATURE)
			.sized(4.0f, 8.0f).build("titanosaurus"));

	public static final RegistrySupplier<EntityType<TroodonEntity>> TROODON =
		ENTITIES.register("troodon", () -> EntityType.Builder.of(TroodonEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 1.0f).build("troodon"));

	public static final RegistrySupplier<EntityType<UtahraptorEntity>> UTAHRAPTOR =
		ENTITIES.register("utahraptor", () -> EntityType.Builder.of(UtahraptorEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("utahraptor"));

	public static final RegistrySupplier<EntityType<AchillobatorEntity>> ACHILLOBATOR =
		ENTITIES.register("achillobator", () -> EntityType.Builder.of(AchillobatorEntity::new, MobCategory.CREATURE)
			.sized(1.0f, 2.0f).build("achillobator"));

	public static final RegistrySupplier<EntityType<SuchomimusEntity>> SUCHOMIMUS =
		ENTITIES.register("suchomimus", () -> EntityType.Builder.of(SuchomimusEntity::new, MobCategory.CREATURE)
			.sized(2.0f, 3.0f).build("suchomimus"));

	public static void registerAttributes() {
		EntityAttributeRegistry.register(APATOSAURUS, ApatosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(ALBERTOSAURUS, AlbertosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(BARYONYX, BaryonyxEntity::createAttributes);
		EntityAttributeRegistry.register(BRACHIOSAURUS, BrachiosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(CARNOTAURUS, CarnotaurusEntity::createAttributes);
		EntityAttributeRegistry.register(CERATOSAURUS, CeratosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(COMPSOGNATHUS, CompsognathusEntity::createAttributes);
		EntityAttributeRegistry.register(CONCAVENATOR, ConcavenatorEntity::createAttributes);
		EntityAttributeRegistry.register(DEINONYCHUS, DeinonychusEntity::createAttributes);
		EntityAttributeRegistry.register(DILOPHOSAURUS, DilophosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(DIPLODOCUS, DiplodocusEntity::createAttributes);
		EntityAttributeRegistry.register(DISTORTUS_REX, DistortusRexEntity::createAttributes);
		EntityAttributeRegistry.register(EDMONTOSAURUS, EdmontosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(FDUCK, FDuckEntity::createAttributes);
		EntityAttributeRegistry.register(GALLIMIMUS, GallimimusEntity::createAttributes);
		EntityAttributeRegistry.register(GIGANOTOSAURUS, GiganotosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(GUANLONG, GuanlongEntity::createAttributes);
		EntityAttributeRegistry.register(HERRERASAURUS, HerrerasaurusEntity::createAttributes);
		EntityAttributeRegistry.register(INDOMINUS_REX, IndominusRexEntity::createAttributes);
		EntityAttributeRegistry.register(MAJUNGASAURUS, MajungasaurusEntity::createAttributes);
		EntityAttributeRegistry.register(OURANOSAURUS, OuranosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(PARASAUROLOPHUS, ParasaurolophusEntity::createAttributes);
		EntityAttributeRegistry.register(PROCOMPSOGNATHUS, ProcompsognathusEntity::createAttributes);
		EntityAttributeRegistry.register(PROTOCERATOPS, ProtoceratopsEntity::createAttributes);
		EntityAttributeRegistry.register(ARAMBOURGIANIA, ArambourgianiaEntity::createAttributes);
		EntityAttributeRegistry.register(CEARADACTYLUS, CearadactylusEntity::createAttributes);
		EntityAttributeRegistry.register(DIMORPHODON, DimorphodonEntity::createAttributes);
		EntityAttributeRegistry.register(GEOSTERNBERGIA, GeosternbergiaEntity::createAttributes);
		EntityAttributeRegistry.register(GUIDRACO, GuidracoEntity::createAttributes);
		EntityAttributeRegistry.register(LUDODACTYLUS, LudodactylusEntity::createAttributes);
		EntityAttributeRegistry.register(MOGANOPTERUS, MoganopterusEntity::createAttributes);
		EntityAttributeRegistry.register(NYCTOSAURUS, NyctosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(PTERANODON, PteranodonEntity::createAttributes);
		EntityAttributeRegistry.register(PTERODAUSTRO, PterodaustroEntity::createAttributes);
		EntityAttributeRegistry.register(QUETZALCOATLUS, QuetzalcoatlusEntity::createAttributes);
		EntityAttributeRegistry.register(TAPEJARA, TapejaraEntity::createAttributes);
		EntityAttributeRegistry.register(TROPEOGNATHUS, TropeognathusEntity::createAttributes);
		EntityAttributeRegistry.register(TUPUXUARA, TupuxuaraEntity::createAttributes);
		EntityAttributeRegistry.register(ZHENYUANOPTERUS, ZhenyuanopterusEntity::createAttributes);
		EntityAttributeRegistry.register(RUGOPS, RugopsEntity::createAttributes);
		EntityAttributeRegistry.register(SHANTUNGOSAURUS, ShantungosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(SPINOSAURUS, SpinosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(STEGOSAURUS, StegosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(STYRACOSAURUS, StyracosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(THERIZINOSAURUS, TherizinosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(TRICERATOPS, TriceratopsEntity::createAttributes);
		EntityAttributeRegistry.register(TYRANNOSAURUS_REX, TyrannosaurusRexEntity::createAttributes);
		EntityAttributeRegistry.register(VELOCIRAPTOR, VelociraptorEntity::createAttributes);
		EntityAttributeRegistry.register(CHICKENOSAURUS, ChickenosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(ALLOSAURUS, AllosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(ALVAREZSAURUS, AlvarezsaurusEntity::createAttributes);
		EntityAttributeRegistry.register(ANKYLOSAURUS, AnkylosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(CARCHARODONTOSAURUS, CarcharodontosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(CHASMOSAURUS, ChasmosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(COELOPHYSIS, CoelophysisEntity::createAttributes);
		EntityAttributeRegistry.register(COELURUS, CoelurusEntity::createAttributes);
		EntityAttributeRegistry.register(CORYTHOSAURUS, CorythosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(DRYOSAURUS, DryosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(HADROSAURUS, HadrosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(HYPSILOPHODON, HypsilophodonEntity::createAttributes);
		EntityAttributeRegistry.register(INDORAPTOR, IndoraptorEntity::createAttributes);
		EntityAttributeRegistry.register(INOSTRANCEVIA, InostranceviaEntity::createAttributes);
		EntityAttributeRegistry.register(LAMBEOSAURUS, LambeosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(MAMENCHISAURUS, MamenchisaurusEntity::createAttributes);
		EntityAttributeRegistry.register(METRIACANTHOSAURUS, MetriacanthosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(ORNITHOLESTES, OrnitholestesEntity::createAttributes);
		EntityAttributeRegistry.register(ORNITHOMIMUS, OrnithomimusEntity::createAttributes);
		EntityAttributeRegistry.register(OVIRAPTOR, OviraptorEntity::createAttributes);
		EntityAttributeRegistry.register(PACHYCEPHALOSAURUS, PachycephalosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(PROCERATOSAURUS, ProceratosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(RAJASAURUS, RajasaurusEntity::createAttributes);
		EntityAttributeRegistry.register(SEGISAURUS, SegisaurusEntity::createAttributes);
		EntityAttributeRegistry.register(TITANOSAURUS, TitanosaurusEntity::createAttributes);
		EntityAttributeRegistry.register(TROODON, TroodonEntity::createAttributes);
		EntityAttributeRegistry.register(UTAHRAPTOR, UtahraptorEntity::createAttributes);
		EntityAttributeRegistry.register(ACHILLOBATOR, AchillobatorEntity::createAttributes);
		EntityAttributeRegistry.register(SUCHOMIMUS, SuchomimusEntity::createAttributes);
		EntityAttributeRegistry.register(CHILESAURUS, ChilesaurusEntity::createAttributes);
		EntityAttributeRegistry.register(MUSSASAURUS, MussasaurusEntity::createAttributes);
		EntityAttributeRegistry.register(THESCELOSAURUS, ThescelosaurusEntity::createAttributes);
	}

	public static void registerSpawnPlacements() {
		Constants.LOG.info("Natural dinosaur spawn placement config loaded as: {}", JRConfigManager.get().naturallySpawning);

		if (!JRConfigManager.get().naturallySpawning) {
			Constants.LOG.info("Skipping dinosaur spawn placement registration");
			return;
		}

		Constants.LOG.info("Registering dinosaur spawn placements");

		registerGroundAnimalSpawn(ALBERTOSAURUS);
		registerGroundAnimalSpawn(ALLOSAURUS);
		registerGroundAnimalSpawn(ALVAREZSAURUS);
		registerGroundAnimalSpawn(ANKYLOSAURUS);
		registerGroundAnimalSpawn(APATOSAURUS);
		registerGroundAnimalSpawn(ARAMBOURGIANIA);
		registerGroundAnimalSpawn(BARYONYX);
		registerGroundAnimalSpawn(BRACHIOSAURUS);
		registerGroundAnimalSpawn(CARCHARODONTOSAURUS);
		registerGroundAnimalSpawn(CARNOTAURUS);
		registerGroundAnimalSpawn(CEARADACTYLUS);
		registerGroundAnimalSpawn(CERATOSAURUS);
		registerGroundAnimalSpawn(CHASMOSAURUS);
		registerGroundAnimalSpawn(COELOPHYSIS);
		registerGroundAnimalSpawn(COELURUS);
		registerGroundAnimalSpawn(COMPSOGNATHUS);
		registerGroundAnimalSpawn(CONCAVENATOR);
		registerGroundAnimalSpawn(CORYTHOSAURUS);
		registerGroundAnimalSpawn(DEINONYCHUS);
		registerGroundAnimalSpawn(DILOPHOSAURUS);
		registerGroundAnimalSpawn(DIMORPHODON);
		registerGroundAnimalSpawn(DIPLODOCUS);
		registerGroundAnimalSpawn(DRYOSAURUS);
		registerGroundAnimalSpawn(EDMONTOSAURUS);
		registerGroundAnimalSpawn(GALLIMIMUS);
		registerGroundAnimalSpawn(GEOSTERNBERGIA);
		registerGroundAnimalSpawn(GIGANOTOSAURUS);
		registerGroundAnimalSpawn(GUANLONG);
		registerGroundAnimalSpawn(GUIDRACO);
		registerGroundAnimalSpawn(HADROSAURUS);
		registerGroundAnimalSpawn(HERRERASAURUS);
		registerGroundAnimalSpawn(HYPSILOPHODON);
		registerGroundAnimalSpawn(INOSTRANCEVIA);
		registerGroundAnimalSpawn(LAMBEOSAURUS);
		registerGroundAnimalSpawn(LUDODACTYLUS);
		registerGroundAnimalSpawn(MAJUNGASAURUS);
		registerGroundAnimalSpawn(MAMENCHISAURUS);
		registerGroundAnimalSpawn(METRIACANTHOSAURUS);
		registerGroundAnimalSpawn(MOGANOPTERUS);
		registerGroundAnimalSpawn(NYCTOSAURUS);
		registerGroundAnimalSpawn(ORNITHOLESTES);
		registerGroundAnimalSpawn(ORNITHOMIMUS);
		registerGroundAnimalSpawn(OURANOSAURUS);
		registerGroundAnimalSpawn(OVIRAPTOR);
		registerGroundAnimalSpawn(PACHYCEPHALOSAURUS);
		registerGroundAnimalSpawn(PARASAUROLOPHUS);
		registerGroundAnimalSpawn(PROCERATOSAURUS);
		registerGroundAnimalSpawn(PROCOMPSOGNATHUS);
		registerGroundAnimalSpawn(PROTOCERATOPS);
		registerGroundAnimalSpawn(PTERANODON);
		registerGroundAnimalSpawn(PTERODAUSTRO);
		registerGroundAnimalSpawn(QUETZALCOATLUS);
		registerGroundAnimalSpawn(RAJASAURUS);
		registerGroundAnimalSpawn(RUGOPS);
		registerGroundAnimalSpawn(SEGISAURUS);
		registerGroundAnimalSpawn(SHANTUNGOSAURUS);
		registerGroundAnimalSpawn(SPINOSAURUS);
		registerGroundAnimalSpawn(STEGOSAURUS);
		registerGroundAnimalSpawn(STYRACOSAURUS);
		registerGroundAnimalSpawn(TAPEJARA);
		registerGroundAnimalSpawn(THERIZINOSAURUS);
		registerGroundAnimalSpawn(TITANOSAURUS);
		registerGroundAnimalSpawn(TRICERATOPS);
		registerGroundAnimalSpawn(TROODON);
		registerGroundAnimalSpawn(TROPEOGNATHUS);
		registerGroundAnimalSpawn(TUPUXUARA);
		registerGroundAnimalSpawn(TYRANNOSAURUS_REX);
		registerGroundAnimalSpawn(UTAHRAPTOR);
		registerGroundAnimalSpawn(VELOCIRAPTOR);
		registerGroundAnimalSpawn(ZHENYUANOPTERUS);
		registerGroundAnimalSpawn(ACHILLOBATOR);
		registerGroundAnimalSpawn(SUCHOMIMUS);
		registerGroundAnimalSpawn(CHILESAURUS);
		registerGroundAnimalSpawn(MUSSASAURUS);
		registerGroundAnimalSpawn(THESCELOSAURUS);
	}

	private static <T extends Animal> void registerGroundAnimalSpawn(RegistrySupplier<EntityType<T>> entityType) {
/*? if >1.20.1 {*/
		/*SpawnPlacementsRegistry.register(entityType, SpawnPlacementTypes.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, level, reason, pos, random) -> true);
*//*?} else {*/
		SpawnPlacementsRegistry.register(entityType, SpawnPlacements.Type.ON_GROUND,
			Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, level, reason, pos, random) -> true);
/*?}*/
	}

	public static void register() {
		ENTITIES.register();
	}
}