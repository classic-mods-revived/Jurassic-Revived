package net.cmr.jurassicrevived.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.custom.CustomGenderedSpawnEggItem;
import net.cmr.jurassicrevived.item.custom.FrogSyringeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);

	public static final RegistrySupplier<Item> FROG_MATERIAL = ITEMS.register("frog_material", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FROG_DNA = ITEMS.register("frog_dna", () -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistrySupplier<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties().stacksTo(1)));
	//? if >1.20.1 {
	/*public static final RegistrySupplier<Item> MAC_N_CHEESE = ITEMS.register("mac_n_cheese", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.6f).build())));
	public static final RegistrySupplier<Item> WALNUT_PUMPKIN_PIE = ITEMS.register("walnut_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.6f).build())));
	public static final RegistrySupplier<Item> BANANA_NUT_COOKIE = ITEMS.register("banana_nut_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.6f).build())));
	*///?} else {
	public static final RegistrySupplier<Item> MAC_N_CHEESE = ITEMS.register("mac_n_cheese", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));
	public static final RegistrySupplier<Item> WALNUT_PUMPKIN_PIE = ITEMS.register("walnut_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));
	public static final RegistrySupplier<Item> BANANA_NUT_COOKIE = ITEMS.register("banana_nut_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));
	//?}

	public static final RegistrySupplier<Item> APATOSAURUS_SPAWN_EGG = ITEMS.register("apatosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.APATOSAURUS, 0xff7f7d6f, 0xff36373b, new Item.Properties()));
	public static final RegistrySupplier<Item> ALBERTOSAURUS_SPAWN_EGG = ITEMS.register("albertosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ALBERTOSAURUS, 0xff2b2315, 0xff7a442d, new Item.Properties()));
	public static final RegistrySupplier<Item> BRACHIOSAURUS_SPAWN_EGG = ITEMS.register("brachiosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.BRACHIOSAURUS, 0xff95846D, 0xff4B4236, new Item.Properties()));
	public static final RegistrySupplier<Item> CERATOSAURUS_SPAWN_EGG = ITEMS.register("ceratosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CERATOSAURUS, 0xff954846, 0xff221F1D, new Item.Properties()));
	public static final RegistrySupplier<Item> COMPSOGNATHUS_SPAWN_EGG = ITEMS.register("compsognathus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.COMPSOGNATHUS, 0xff676D24, 0xff373E16, new Item.Properties()));
	public static final RegistrySupplier<Item> DIPLODOCUS_SPAWN_EGG = ITEMS.register("diplodocus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DIPLODOCUS, 0xffbf9a5e, 0xff624d2c, new Item.Properties()));
	public static final RegistrySupplier<Item> DILOPHOSAURUS_SPAWN_EGG = ITEMS.register("dilophosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DILOPHOSAURUS, 0xff575D32, 0xff16191C, new Item.Properties()));
	public static final RegistrySupplier<Item> FDUCK_SPAWN_EGG = ITEMS.register("fduck_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.FDUCK, 0xffff8800, 0xff421111, new Item.Properties()));
	public static final RegistrySupplier<Item> GALLIMIMUS_SPAWN_EGG = ITEMS.register("gallimimus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.GALLIMIMUS, 0xffAD7341, 0xff5C3925, new Item.Properties()));
	public static final RegistrySupplier<Item> INDOMINUS_REX_SPAWN_EGG = ITEMS.register("indominus_rex_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.INDOMINUS_REX, 0xff9C9B99, 0xff60605F, new Item.Properties()));
	public static final RegistrySupplier<Item> OURANOSAURUS_SPAWN_EGG = ITEMS.register("ouranosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.OURANOSAURUS, 0xff5e6e49, 0xff6c511c, new Item.Properties()));
	public static final RegistrySupplier<Item> PARASAUROLOPHUS_SPAWN_EGG = ITEMS.register("parasaurolophus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PARASAUROLOPHUS, 0xff856836, 0xff442911, new Item.Properties()));
	public static final RegistrySupplier<Item> SPINOSAURUS_SPAWN_EGG = ITEMS.register("spinosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.SPINOSAURUS, 0xff685E5A, 0xff5D3831, new Item.Properties()));
	public static final RegistrySupplier<Item> TRICERATOPS_SPAWN_EGG = ITEMS.register("triceratops_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TRICERATOPS, 0xff353A30, 0xff121212, new Item.Properties()));
	public static final RegistrySupplier<Item> TYRANNOSAURUS_REX_SPAWN_EGG = ITEMS.register("tyrannosaurus_rex_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TYRANNOSAURUS_REX, 0xff4C3C2D, 0xff241F1E, new Item.Properties()));
	public static final RegistrySupplier<Item> VELOCIRAPTOR_SPAWN_EGG = ITEMS.register("velociraptor_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.VELOCIRAPTOR, 0xff8A5837, 0xff45220D, new Item.Properties()));
	public static final RegistrySupplier<Item> BARYONYX_SPAWN_EGG = ITEMS.register("baryonyx_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.BARYONYX, 0xff2e5325, 0xff7dcf35, new Item.Properties()));
	public static final RegistrySupplier<Item> CARNOTAURUS_SPAWN_EGG = ITEMS.register("carnotaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CARNOTAURUS, 0xffa6996e, 0xffc36e60, new Item.Properties()));
	public static final RegistrySupplier<Item> CONCAVENATOR_SPAWN_EGG = ITEMS.register("concavenator_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CONCAVENATOR, 0xffa6a49b, 0xff964b22, new Item.Properties()));
	public static final RegistrySupplier<Item> DEINONYCHUS_SPAWN_EGG = ITEMS.register("deinonychus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DEINONYCHUS, 0xff8d7d51, 0xff95c9a2, new Item.Properties()));
	public static final RegistrySupplier<Item> DISTORTUS_REX_SPAWN_EGG = ITEMS.register("distortus_rex_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DISTORTUS_REX, 0xff59422b, 0xff3f2e1a, new Item.Properties()));
	public static final RegistrySupplier<Item> EDMONTOSAURUS_SPAWN_EGG = ITEMS.register("edmontosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.EDMONTOSAURUS, 0xffeaa569, 0xffbe783e, new Item.Properties()));
	public static final RegistrySupplier<Item> GIGANOTOSAURUS_SPAWN_EGG = ITEMS.register("giganotosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.GIGANOTOSAURUS, 0xff5c483b, 0xff2d2b30, new Item.Properties()));
	public static final RegistrySupplier<Item> GUANLONG_SPAWN_EGG = ITEMS.register("guanlong_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.GUANLONG, 0xffefe7cb, 0xff624e18, new Item.Properties()));
	public static final RegistrySupplier<Item> HERRERASAURUS_SPAWN_EGG = ITEMS.register("herrerasaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.HERRERASAURUS, 0xff93211e, 0xff987839, new Item.Properties()));
	public static final RegistrySupplier<Item> MAJUNGASAURUS_SPAWN_EGG = ITEMS.register("majungasaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.MAJUNGASAURUS, 0xff657774, 0xffce8039, new Item.Properties()));
	public static final RegistrySupplier<Item> PROCOMPSOGNATHUS_SPAWN_EGG = ITEMS.register("procompsognathus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PROCOMPSOGNATHUS, 0xffe3c775, 0xff362c18, new Item.Properties()));
	public static final RegistrySupplier<Item> PROTOCERATOPS_SPAWN_EGG = ITEMS.register("protoceratops_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PROTOCERATOPS, 0xfffccdb4, 0xfffdc079, new Item.Properties()));
	public static final RegistrySupplier<Item> ARAMBOURGIANIA_SPAWN_EGG = ITEMS.register("arambourgiania_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ARAMBOURGIANIA, 0xffd2c294, 0xff95b2c2, new Item.Properties()));
	public static final RegistrySupplier<Item> CEARADACTYLUS_SPAWN_EGG = ITEMS.register("cearadactylus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CEARADACTYLUS, 0xff68594e, 0xff64a0b3, new Item.Properties()));
	public static final RegistrySupplier<Item> DIMORPHODON_SPAWN_EGG = ITEMS.register("dimorphodon_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DIMORPHODON, 0xffb4aba0, 0xff674a43, new Item.Properties()));
	public static final RegistrySupplier<Item> GEOSTERNBERGIA_SPAWN_EGG = ITEMS.register("geosternbergia_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.GEOSTERNBERGIA, 0xffd8cb8b, 0xff3e677f, new Item.Properties()));
	public static final RegistrySupplier<Item> GUIDRACO_SPAWN_EGG = ITEMS.register("guidraco_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.GUIDRACO, 0xff19132e, 0xff572749, new Item.Properties()));
	public static final RegistrySupplier<Item> LUDODACTYLUS_SPAWN_EGG = ITEMS.register("ludodactylus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.LUDODACTYLUS, 0xff303133, 0xff72502b, new Item.Properties()));
	public static final RegistrySupplier<Item> MOGANOPTERUS_SPAWN_EGG = ITEMS.register("moganopterus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.MOGANOPTERUS, 0xffdeb7ab, 0xff8d5a47, new Item.Properties()));
	public static final RegistrySupplier<Item> NYCTOSAURUS_SPAWN_EGG = ITEMS.register("nyctosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.NYCTOSAURUS, 0xfff3f1e9, 0xff2087b3, new Item.Properties()));
	public static final RegistrySupplier<Item> PTERANODON_SPAWN_EGG = ITEMS.register("pteranodon_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PTERANODON, 0xff4b4541, 0xff173d4a, new Item.Properties()));
	public static final RegistrySupplier<Item> PTERODAUSTRO_SPAWN_EGG = ITEMS.register("pterodaustro_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PTERODAUSTRO, 0xff2f2f36, 0xfff5d33c, new Item.Properties()));
	public static final RegistrySupplier<Item> QUETZALCOATLUS_SPAWN_EGG = ITEMS.register("quetzalcoatlus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.QUETZALCOATLUS, 0xff1c1b1a, 0xffa6a69f, new Item.Properties()));
	public static final RegistrySupplier<Item> TAPEJARA_SPAWN_EGG = ITEMS.register("tapejara_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TAPEJARA, 0xffd8dbe4, 0xff710b0c, new Item.Properties()));
	public static final RegistrySupplier<Item> TROPEOGNATHUS_SPAWN_EGG = ITEMS.register("tropeognathus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TROPEOGNATHUS, 0xff4e6067, 0xff483141, new Item.Properties()));
	public static final RegistrySupplier<Item> TUPUXUARA_SPAWN_EGG = ITEMS.register("tupuxuara_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TUPUXUARA, 0xff6b4e40, 0xff3a647e, new Item.Properties()));
	public static final RegistrySupplier<Item> ZHENYUANOPTERUS_SPAWN_EGG = ITEMS.register("zhenyuanopterus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ZHENYUANOPTERUS, 0xff485654, 0xffd1cdb6, new Item.Properties()));
	public static final RegistrySupplier<Item> RUGOPS_SPAWN_EGG = ITEMS.register("rugops_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.RUGOPS, 0xffd3ecf0, 0xffafef5a, new Item.Properties()));
	public static final RegistrySupplier<Item> SHANTUNGOSAURUS_SPAWN_EGG = ITEMS.register("shantungosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.SHANTUNGOSAURUS, 0xff272727, 0xffb57942, new Item.Properties()));
	public static final RegistrySupplier<Item> STEGOSAURUS_SPAWN_EGG = ITEMS.register("stegosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.STEGOSAURUS, 0xff6b6e29, 0xff441500, new Item.Properties()));
	public static final RegistrySupplier<Item> STYRACOSAURUS_SPAWN_EGG = ITEMS.register("styracosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.STYRACOSAURUS, 0xff813b2b, 0xff6a342c, new Item.Properties()));
	public static final RegistrySupplier<Item> THERIZINOSAURUS_SPAWN_EGG = ITEMS.register("therizinosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.THERIZINOSAURUS, 0xff787878, 0xff454545, new Item.Properties()));
	public static final RegistrySupplier<Item> CHICKENOSAURUS_SPAWN_EGG = ITEMS.register("chickenosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CHICKENOSAURUS, 0xff5d3c11, 0xff3a2934, new Item.Properties()));
	public static final RegistrySupplier<Item> ALLOSAURUS_SPAWN_EGG = ITEMS.register("allosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ALLOSAURUS, 0xffc0a086, 0xff653333, new Item.Properties()));
	public static final RegistrySupplier<Item> ALVAREZSAURUS_SPAWN_EGG = ITEMS.register("alvarezsaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ALVAREZSAURUS, 0xffa8a8a8, 0xfff3962a, new Item.Properties()));
	public static final RegistrySupplier<Item> ANKYLOSAURUS_SPAWN_EGG = ITEMS.register("ankylosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ANKYLOSAURUS, 0xffaf9f86, 0xffa55d52, new Item.Properties()));
	public static final RegistrySupplier<Item> CARCHARODONTOSAURUS_SPAWN_EGG = ITEMS.register("carcharodontosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CARCHARODONTOSAURUS, 0xff1b1613, 0xff9d321d, new Item.Properties()));
	public static final RegistrySupplier<Item> CHASMOSAURUS_SPAWN_EGG = ITEMS.register("chasmosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CHASMOSAURUS, 0xffbab697, 0xff825038, new Item.Properties()));
	public static final RegistrySupplier<Item> COELOPHYSIS_SPAWN_EGG = ITEMS.register("coelophysis_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.COELOPHYSIS, 0xff95a248, 0xffa55031, new Item.Properties()));
	public static final RegistrySupplier<Item> COELURUS_SPAWN_EGG = ITEMS.register("coelurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.COELURUS, 0xff9c7219, 0xff2d1b06, new Item.Properties()));
	public static final RegistrySupplier<Item> CORYTHOSAURUS_SPAWN_EGG = ITEMS.register("corythosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CORYTHOSAURUS, 0xffa2926b, 0xffe9c451, new Item.Properties()));
	public static final RegistrySupplier<Item> DRYOSAURUS_SPAWN_EGG = ITEMS.register("dryosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.DRYOSAURUS, 0xffb8992b, 0xff271c03, new Item.Properties()));
	public static final RegistrySupplier<Item> HADROSAURUS_SPAWN_EGG = ITEMS.register("hadrosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.HADROSAURUS, 0xff95b18f, 0xffd2ce55, new Item.Properties()));
	public static final RegistrySupplier<Item> HYPSILOPHODON_SPAWN_EGG = ITEMS.register("hypsilophodon_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.HYPSILOPHODON, 0xff1d1e1f, 0xff9277f0, new Item.Properties()));
	public static final RegistrySupplier<Item> INDORAPTOR_SPAWN_EGG = ITEMS.register("indoraptor_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.INDORAPTOR, 0xff070707, 0xffcfac1c, new Item.Properties()));
	public static final RegistrySupplier<Item> INOSTRANCEVIA_SPAWN_EGG = ITEMS.register("inostrancevia_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.INOSTRANCEVIA, 0xff6a6b57, 0xff484330, new Item.Properties()));
	public static final RegistrySupplier<Item> LAMBEOSAURUS_SPAWN_EGG = ITEMS.register("lambeosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.LAMBEOSAURUS, 0xff6f8765, 0xff5d2855, new Item.Properties()));
	public static final RegistrySupplier<Item> MAMENCHISAURUS_SPAWN_EGG = ITEMS.register("mamenchisaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.MAMENCHISAURUS, 0xffe1c77a, 0xff979d16, new Item.Properties()));
	public static final RegistrySupplier<Item> METRIACANTHOSAURUS_SPAWN_EGG = ITEMS.register("metriacanthosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.METRIACANTHOSAURUS, 0xffb15e1b, 0xffe7e92f, new Item.Properties()));
	public static final RegistrySupplier<Item> ORNITHOLESTES_SPAWN_EGG = ITEMS.register("ornitholestes_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ORNITHOLESTES, 0xff7ac7e6, 0xff091d07, new Item.Properties()));
	public static final RegistrySupplier<Item> ORNITHOMIMUS_SPAWN_EGG = ITEMS.register("ornithomimus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ORNITHOMIMUS, 0xff8ea4d3, 0xff7ac7e6, new Item.Properties()));
	public static final RegistrySupplier<Item> OVIRAPTOR_SPAWN_EGG = ITEMS.register("oviraptor_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.OVIRAPTOR, 0xffddd9c3, 0xff6c3545, new Item.Properties()));
	public static final RegistrySupplier<Item> PACHYCEPHALOSAURUS_SPAWN_EGG = ITEMS.register("pachycephalosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PACHYCEPHALOSAURUS, 0xff8a7e61, 0xff495156, new Item.Properties()));
	public static final RegistrySupplier<Item> PROCERATOSAURUS_SPAWN_EGG = ITEMS.register("proceratosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.PROCERATOSAURUS, 0xff8f8e8a, 0xff040203, new Item.Properties()));
	public static final RegistrySupplier<Item> RAJASAURUS_SPAWN_EGG = ITEMS.register("rajasaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.RAJASAURUS, 0xff3f3a32, 0xff62c6ce, new Item.Properties()));
	public static final RegistrySupplier<Item> SEGISAURUS_SPAWN_EGG = ITEMS.register("segisaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.SEGISAURUS, 0xff72383a, 0xff69abcc, new Item.Properties()));
	public static final RegistrySupplier<Item> TITANOSAURUS_SPAWN_EGG = ITEMS.register("titanosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TITANOSAURUS, 0xff6f6960, 0xffd43d13, new Item.Properties()));
	public static final RegistrySupplier<Item> TROODON_SPAWN_EGG = ITEMS.register("troodon_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.TROODON, 0xff414632, 0xff640600, new Item.Properties()));
	public static final RegistrySupplier<Item> UTAHRAPTOR_SPAWN_EGG = ITEMS.register("utahraptor_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.UTAHRAPTOR, 0xff474131, 0xffdad8db, new Item.Properties()));
	public static final RegistrySupplier<Item> ACHILLOBATOR_SPAWN_EGG = ITEMS.register("achillobator_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.ACHILLOBATOR, 0xffcbb791, 0xff8e4338, new Item.Properties()));
	public static final RegistrySupplier<Item> SUCHOMIMUS_SPAWN_EGG = ITEMS.register("suchomimus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.SUCHOMIMUS, 0xff455565, 0xffccb674, new Item.Properties()));
	public static final RegistrySupplier<Item> CHILESAURUS_SPAWN_EGG = ITEMS.register("chilesaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.CHILESAURUS, 0xff4fa0ba, 0xff7e9148, new Item.Properties()));
	public static final RegistrySupplier<Item> THESCELOSAURUS_SPAWN_EGG = ITEMS.register("thescelosaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.THESCELOSAURUS, 0xff6d7a83, 0xffbac6d1, new Item.Properties()));
	public static final RegistrySupplier<Item> MUSSASAURUS_SPAWN_EGG = ITEMS.register("mussasaurus_spawn_egg",
		() -> new CustomGenderedSpawnEggItem(ModEntities.MUSSASAURUS, 0xff6c6724, 0xff222611, new Item.Properties()));


	public static final RegistrySupplier<Item> TEST_TUBE = ITEMS.register("test_tube", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SYRINGE = ITEMS.register("syringe", () -> new FrogSyringeItem(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> MOSQUITO_IN_AMBER = ITEMS.register("mosquito_in_amber", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> FROZEN_BONE = ITEMS.register("frozen_bone", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CRUSHED_FOSSIL = ITEMS.register("crushed_fossil", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> FROZEN_LEECH = ITEMS.register("frozen_leech", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CABLE = ITEMS.register("cable", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SCREEN = ITEMS.register("screen", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistrySupplier<Item> PROCESSOR = ITEMS.register("processor", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistrySupplier<Item> TIRE = ITEMS.register("tire", () -> new Item(new Item.Properties().stacksTo(4)));
	public static final RegistrySupplier<Item> CUTTING_BLADES = ITEMS.register("cutting_blades", () -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistrySupplier<Item> VELOCIRAPTOR_SKULL_FOSSIL = ITEMS.register("velociraptor_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TYRANNOSAURUS_REX_SKULL_FOSSIL = ITEMS.register("tyrannosaurus_rex_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TRICERATOPS_SKULL_FOSSIL = ITEMS.register("triceratops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SPINOSAURUS_SKULL_FOSSIL = ITEMS.register("spinosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PARASAUROLOPHUS_SKULL_FOSSIL = ITEMS.register("parasaurolophus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> OURANOSAURUS_SKULL_FOSSIL = ITEMS.register("ouranosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> GALLIMIMUS_SKULL_FOSSIL = ITEMS.register("gallimimus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> DIPLODOCUS_SKULL_FOSSIL = ITEMS.register("diplodocus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> DILOPHOSAURUS_SKULL_FOSSIL = ITEMS.register("dilophosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> COMPSOGNATHUS_SKULL_FOSSIL = ITEMS.register("compsognathus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CERATOSAURUS_SKULL_FOSSIL = ITEMS.register("ceratosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> BRACHIOSAURUS_SKULL_FOSSIL = ITEMS.register("brachiosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ALBERTOSAURUS_SKULL_FOSSIL = ITEMS.register("albertosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> APATOSAURUS_SKULL_FOSSIL = ITEMS.register("apatosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> BARYONYX_SKULL_FOSSIL = ITEMS.register("baryonyx_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CARNOTAURUS_SKULL_FOSSIL = ITEMS.register("carnotaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CONCAVENATOR_SKULL_FOSSIL = ITEMS.register("concavenator_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> DEINONYCHUS_SKULL_FOSSIL = ITEMS.register("deinonychus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> EDMONTOSAURUS_SKULL_FOSSIL = ITEMS.register("edmontosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> GIGANOTOSAURUS_SKULL_FOSSIL = ITEMS.register("giganotosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> GUANLONG_SKULL_FOSSIL = ITEMS.register("guanlong_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> HERRERASAURUS_SKULL_FOSSIL = ITEMS.register("herrerasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> MAJUNGASAURUS_SKULL_FOSSIL = ITEMS.register("majungasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PROCOMPSOGNATHUS_SKULL_FOSSIL = ITEMS.register("procompsognathus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PROTOCERATOPS_SKULL_FOSSIL = ITEMS.register("protoceratops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> RUGOPS_SKULL_FOSSIL = ITEMS.register("rugops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SHANTUNGOSAURUS_SKULL_FOSSIL = ITEMS.register("shantungosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> STEGOSAURUS_SKULL_FOSSIL = ITEMS.register("stegosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> STYRACOSAURUS_SKULL_FOSSIL = ITEMS.register("styracosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> THERIZINOSAURUS_SKULL_FOSSIL = ITEMS.register("therizinosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ALLOSAURUS_SKULL_FOSSIL = ITEMS.register("allosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ALVAREZSAURUS_SKULL_FOSSIL = ITEMS.register("alvarezsaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ANKYLOSAURUS_SKULL_FOSSIL = ITEMS.register("ankylosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ARAMBOURGIANIA_SKULL_FOSSIL = ITEMS.register("arambourgiania_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CARCHARODONTOSAURUS_SKULL_FOSSIL = ITEMS.register("carcharodontosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CEARADACTYLUS_SKULL_FOSSIL = ITEMS.register("cearadactylus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CHASMOSAURUS_SKULL_FOSSIL = ITEMS.register("chasmosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> COELOPHYSIS_SKULL_FOSSIL = ITEMS.register("coelophysis_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> COELURUS_SKULL_FOSSIL = ITEMS.register("coelurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CORYTHOSAURUS_SKULL_FOSSIL = ITEMS.register("corythosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> DIMORPHODON_SKULL_FOSSIL = ITEMS.register("dimorphodon_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> DRYOSAURUS_SKULL_FOSSIL = ITEMS.register("dryosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> GEOSTERNBERGIA_SKULL_FOSSIL = ITEMS.register("geosternbergia_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> GUIDRACO_SKULL_FOSSIL = ITEMS.register("guidraco_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> HADROSAURUS_SKULL_FOSSIL = ITEMS.register("hadrosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> HYPSILOPHODON_SKULL_FOSSIL = ITEMS.register("hypsilophodon_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> INOSTRANCEVIA_SKULL_FOSSIL = ITEMS.register("inostrancevia_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> LAMBEOSAURUS_SKULL_FOSSIL = ITEMS.register("lambeosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> LUDODACTYLUS_SKULL_FOSSIL = ITEMS.register("ludodactylus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> MAMENCHISAURUS_SKULL_FOSSIL = ITEMS.register("mamenchisaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> METRIACANTHOSAURUS_SKULL_FOSSIL = ITEMS.register("metriacanthosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> MOGANOPTERUS_SKULL_FOSSIL = ITEMS.register("moganopterus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> NYCTOSAURUS_SKULL_FOSSIL = ITEMS.register("nyctosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ORNITHOLESTES_SKULL_FOSSIL = ITEMS.register("ornitholestes_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ORNITHOMIMUS_SKULL_FOSSIL = ITEMS.register("ornithomimus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> OVIRAPTOR_SKULL_FOSSIL = ITEMS.register("oviraptor_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PACHYCEPHALOSAURUS_SKULL_FOSSIL = ITEMS.register("pachycephalosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PROCERATOSAURUS_SKULL_FOSSIL = ITEMS.register("proceratosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PTERANODON_SKULL_FOSSIL = ITEMS.register("pteranodon_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> PTERODAUSTRO_SKULL_FOSSIL = ITEMS.register("pterodaustro_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> QUETZALCOATLUS_SKULL_FOSSIL = ITEMS.register("quetzalcoatlus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> RAJASAURUS_SKULL_FOSSIL = ITEMS.register("rajasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SEGISAURUS_SKULL_FOSSIL = ITEMS.register("segisaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TAPEJARA_SKULL_FOSSIL = ITEMS.register("tapejara_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TITANOSAURUS_SKULL_FOSSIL = ITEMS.register("titanosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TROODON_SKULL_FOSSIL = ITEMS.register("troodon_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TROPEOGNATHUS_SKULL_FOSSIL = ITEMS.register("tropeognathus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> TUPUXUARA_SKULL_FOSSIL = ITEMS.register("tupuxuara_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> UTAHRAPTOR_SKULL_FOSSIL = ITEMS.register("utahraptor_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ZHENYUANOPTERUS_SKULL_FOSSIL = ITEMS.register("zhenyuanopterus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> ACHILLOBATOR_SKULL_FOSSIL = ITEMS.register("achillobator_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> SUCHOMIMUS_SKULL_FOSSIL = ITEMS.register("suchomimus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> CHILESAURUS_SKULL_FOSSIL = ITEMS.register("chilesaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> THESCELOSAURUS_SKULL_FOSSIL = ITEMS.register("thescelosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> MUSSASAURUS_SKULL_FOSSIL = ITEMS.register("mussasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));


	public static final RegistrySupplier<Item> FRESH_VELOCIRAPTOR_SKULL = ITEMS.register("fresh_velociraptor_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TYRANNOSAURUS_REX_SKULL = ITEMS.register("fresh_tyrannosaurus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TRICERATOPS_SKULL = ITEMS.register("fresh_triceratops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_SPINOSAURUS_SKULL = ITEMS.register("fresh_spinosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PARASAUROLOPHUS_SKULL = ITEMS.register("fresh_parasaurolophus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_OURANOSAURUS_SKULL = ITEMS.register("fresh_ouranosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_INDOMINUS_REX_SKULL = ITEMS.register("fresh_indominus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_GALLIMIMUS_SKULL = ITEMS.register("fresh_gallimimus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DIPLODOCUS_SKULL = ITEMS.register("fresh_diplodocus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DILOPHOSAURUS_SKULL = ITEMS.register("fresh_dilophosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_COMPSOGNATHUS_SKULL = ITEMS.register("fresh_compsognathus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CERATOSAURUS_SKULL = ITEMS.register("fresh_ceratosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_BRACHIOSAURUS_SKULL = ITEMS.register("fresh_brachiosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ALBERTOSAURUS_SKULL = ITEMS.register("fresh_albertosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_APATOSAURUS_SKULL = ITEMS.register("fresh_apatosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_BARYONYX_SKULL = ITEMS.register("fresh_baryonyx_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CARNOTAURUS_SKULL = ITEMS.register("fresh_carnotaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CONCAVENATOR_SKULL = ITEMS.register("fresh_concavenator_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DEINONYCHUS_SKULL = ITEMS.register("fresh_deinonychus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_EDMONTOSAURUS_SKULL = ITEMS.register("fresh_edmontosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_GIGANOTOSAURUS_SKULL = ITEMS.register("fresh_giganotosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_GUANLONG_SKULL = ITEMS.register("fresh_guanlong_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_HERRERASAURUS_SKULL = ITEMS.register("fresh_herrerasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_MAJUNGASAURUS_SKULL = ITEMS.register("fresh_majungasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PROTOCERATOPS_SKULL = ITEMS.register("fresh_protoceratops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PROCOMPSOGNATHUS_SKULL = ITEMS.register("fresh_procompsognathus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_RUGOPS_SKULL = ITEMS.register("fresh_rugops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_SHANTUNGOSAURUS_SKULL = ITEMS.register("fresh_shantungosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_STEGOSAURUS_SKULL = ITEMS.register("fresh_stegosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_STYRACOSAURUS_SKULL = ITEMS.register("fresh_styracosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_THERIZINOSAURUS_SKULL = ITEMS.register("fresh_therizinosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DISTORTUS_REX_SKULL = ITEMS.register("fresh_distortus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ALLOSAURUS_SKULL = ITEMS.register("fresh_allosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ALVAREZSAURUS_SKULL = ITEMS.register("fresh_alvarezsaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ANKYLOSAURUS_SKULL = ITEMS.register("fresh_ankylosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ARAMBOURGIANIA_SKULL = ITEMS.register("fresh_arambourgiania_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CARCHARODONTOSAURUS_SKULL = ITEMS.register("fresh_carcharodontosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CEARADACTYLUS_SKULL = ITEMS.register("fresh_cearadactylus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CHASMOSAURUS_SKULL = ITEMS.register("fresh_chasmosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_COELOPHYSIS_SKULL = ITEMS.register("fresh_coelophysis_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_COELURUS_SKULL = ITEMS.register("fresh_coelurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CORYTHOSAURUS_SKULL = ITEMS.register("fresh_corythosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DIMORPHODON_SKULL = ITEMS.register("fresh_dimorphodon_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_DRYOSAURUS_SKULL = ITEMS.register("fresh_dryosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_GEOSTERNBERGIA_SKULL = ITEMS.register("fresh_geosternbergia_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_GUIDRACO_SKULL = ITEMS.register("fresh_guidraco_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_HADROSAURUS_SKULL = ITEMS.register("fresh_hadrosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_HYPSILOPHODON_SKULL = ITEMS.register("fresh_hypsilophodon_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_INDORAPTOR_SKULL = ITEMS.register("fresh_indoraptor_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_INOSTRANCEVIA_SKULL = ITEMS.register("fresh_inostrancevia_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_LAMBEOSAURUS_SKULL = ITEMS.register("fresh_lambeosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_LUDODACTYLUS_SKULL = ITEMS.register("fresh_ludodactylus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_MAMENCHISAURUS_SKULL = ITEMS.register("fresh_mamenchisaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_METRIACANTHOSAURUS_SKULL = ITEMS.register("fresh_metriacanthosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_MOGANOPTERUS_SKULL = ITEMS.register("fresh_moganopterus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_NYCTOSAURUS_SKULL = ITEMS.register("fresh_nyctosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ORNITHOLESTES_SKULL = ITEMS.register("fresh_ornitholestes_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ORNITHOMIMUS_SKULL = ITEMS.register("fresh_ornithomimus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_OVIRAPTOR_SKULL = ITEMS.register("fresh_oviraptor_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PACHYCEPHALOSAURUS_SKULL = ITEMS.register("fresh_pachycephalosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PROCERATOSAURUS_SKULL = ITEMS.register("fresh_proceratosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PTERANODON_SKULL = ITEMS.register("fresh_pteranodon_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_PTERODAUSTRO_SKULL = ITEMS.register("fresh_pterodaustro_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_QUETZALCOATLUS_SKULL = ITEMS.register("fresh_quetzalcoatlus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_RAJASAURUS_SKULL = ITEMS.register("fresh_rajasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_SEGISAURUS_SKULL = ITEMS.register("fresh_segisaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TAPEJARA_SKULL = ITEMS.register("fresh_tapejara_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TITANOSAURUS_SKULL = ITEMS.register("fresh_titanosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TROODON_SKULL = ITEMS.register("fresh_troodon_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TROPEOGNATHUS_SKULL = ITEMS.register("fresh_tropeognathus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_TUPUXUARA_SKULL = ITEMS.register("fresh_tupuxuara_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_UTAHRAPTOR_SKULL = ITEMS.register("fresh_utahraptor_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ZHENYUANOPTERUS_SKULL = ITEMS.register("fresh_zhenyuanopterus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_ACHILLOBATOR_SKULL = ITEMS.register("fresh_achillobator_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_SUCHOMIMUS_SKULL = ITEMS.register("fresh_suchomimus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_CHILESAURUS_SKULL = ITEMS.register("fresh_chilesaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_THESCELOSAURUS_SKULL = ITEMS.register("fresh_thescelosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistrySupplier<Item> FRESH_MUSSASAURUS_SKULL = ITEMS.register("fresh_mussasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));


	// Tissue group
	public static final RegistrySupplier<Item> VELOCIRAPTOR_TISSUE = ITEMS.register("velociraptor_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TYRANNOSAURUS_REX_TISSUE = ITEMS.register("tyrannosaurus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TRICERATOPS_TISSUE = ITEMS.register("triceratops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> SPINOSAURUS_TISSUE = ITEMS.register("spinosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PARASAUROLOPHUS_TISSUE = ITEMS.register("parasaurolophus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> OURANOSAURUS_TISSUE = ITEMS.register("ouranosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> INDOMINUS_REX_TISSUE = ITEMS.register("indominus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> GALLIMIMUS_TISSUE = ITEMS.register("gallimimus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DIPLODOCUS_TISSUE = ITEMS.register("diplodocus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DILOPHOSAURUS_TISSUE = ITEMS.register("dilophosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> COMPSOGNATHUS_TISSUE = ITEMS.register("compsognathus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CERATOSAURUS_TISSUE = ITEMS.register("ceratosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> BRACHIOSAURUS_TISSUE = ITEMS.register("brachiosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ALBERTOSAURUS_TISSUE = ITEMS.register("albertosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> APATOSAURUS_TISSUE = ITEMS.register("apatosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> BARYONYX_TISSUE = ITEMS.register("baryonyx_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CARNOTAURUS_TISSUE = ITEMS.register("carnotaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CONCAVENATOR_TISSUE = ITEMS.register("concavenator_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DEINONYCHUS_TISSUE = ITEMS.register("deinonychus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> EDMONTOSAURUS_TISSUE = ITEMS.register("edmontosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> GIGANOTOSAURUS_TISSUE = ITEMS.register("giganotosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> GUANLONG_TISSUE = ITEMS.register("guanlong_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> HERRERASAURUS_TISSUE = ITEMS.register("herrerasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> MAJUNGASAURUS_TISSUE = ITEMS.register("majungasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PROCOMPSOGNATHUS_TISSUE = ITEMS.register("procompsognathus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PROTOCERATOPS_TISSUE = ITEMS.register("protoceratops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> RUGOPS_TISSUE = ITEMS.register("rugops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> SHANTUNGOSAURUS_TISSUE = ITEMS.register("shantungosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> STEGOSAURUS_TISSUE = ITEMS.register("stegosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> STYRACOSAURUS_TISSUE = ITEMS.register("styracosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> THERIZINOSAURUS_TISSUE = ITEMS.register("therizinosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DISTORTUS_REX_TISSUE = ITEMS.register("distortus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ALLOSAURUS_TISSUE = ITEMS.register("allosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ALVAREZSAURUS_TISSUE = ITEMS.register("alvarezsaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ANKYLOSAURUS_TISSUE = ITEMS.register("ankylosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ARAMBOURGIANIA_TISSUE = ITEMS.register("arambourgiania_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CARCHARODONTOSAURUS_TISSUE = ITEMS.register("carcharodontosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CEARADACTYLUS_TISSUE = ITEMS.register("cearadactylus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CHASMOSAURUS_TISSUE = ITEMS.register("chasmosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> COELOPHYSIS_TISSUE = ITEMS.register("coelophysis_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> COELURUS_TISSUE = ITEMS.register("coelurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CORYTHOSAURUS_TISSUE = ITEMS.register("corythosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DIMORPHODON_TISSUE = ITEMS.register("dimorphodon_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> DRYOSAURUS_TISSUE = ITEMS.register("dryosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> GEOSTERNBERGIA_TISSUE = ITEMS.register("geosternbergia_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> GUIDRACO_TISSUE = ITEMS.register("guidraco_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> HADROSAURUS_TISSUE = ITEMS.register("hadrosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> HYPSILOPHODON_TISSUE = ITEMS.register("hypsilophodon_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> INDORAPTOR_TISSUE = ITEMS.register("indoraptor_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> INOSTRANCEVIA_TISSUE = ITEMS.register("inostrancevia_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> LAMBEOSAURUS_TISSUE = ITEMS.register("lambeosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> LUDODACTYLUS_TISSUE = ITEMS.register("ludodactylus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> MAMENCHISAURUS_TISSUE = ITEMS.register("mamenchisaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> METRIACANTHOSAURUS_TISSUE = ITEMS.register("metriacanthosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> MOGANOPTERUS_TISSUE = ITEMS.register("moganopterus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> NYCTOSAURUS_TISSUE = ITEMS.register("nyctosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ORNITHOLESTES_TISSUE = ITEMS.register("ornitholestes_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ORNITHOMIMUS_TISSUE = ITEMS.register("ornithomimus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> OVIRAPTOR_TISSUE = ITEMS.register("oviraptor_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PACHYCEPHALOSAURUS_TISSUE = ITEMS.register("pachycephalosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PROCERATOSAURUS_TISSUE = ITEMS.register("proceratosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PTERANODON_TISSUE = ITEMS.register("pteranodon_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> PTERODAUSTRO_TISSUE = ITEMS.register("pterodaustro_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> QUETZALCOATLUS_TISSUE = ITEMS.register("quetzalcoatlus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> RAJASAURUS_TISSUE = ITEMS.register("rajasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> SEGISAURUS_TISSUE = ITEMS.register("segisaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TAPEJARA_TISSUE = ITEMS.register("tapejara_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TITANOSAURUS_TISSUE = ITEMS.register("titanosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TROODON_TISSUE = ITEMS.register("troodon_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TROPEOGNATHUS_TISSUE = ITEMS.register("tropeognathus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> TUPUXUARA_TISSUE = ITEMS.register("tupuxuara_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> UTAHRAPTOR_TISSUE = ITEMS.register("utahraptor_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ZHENYUANOPTERUS_TISSUE = ITEMS.register("zhenyuanopterus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> ACHILLOBATOR_TISSUE = ITEMS.register("achillobator_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> SUCHOMIMUS_TISSUE = ITEMS.register("suchomimus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> CHILESAURUS_TISSUE = ITEMS.register("chilesaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> THESCELOSAURUS_TISSUE = ITEMS.register("thescelosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistrySupplier<Item> MUSSASAURUS_TISSUE = ITEMS.register("mussasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));


	// DNA group
	public static final RegistrySupplier<Item> VELOCIRAPTOR_DNA = ITEMS.register("velociraptor_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TYRANNOSAURUS_REX_DNA = ITEMS.register("tyrannosaurus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TRICERATOPS_DNA = ITEMS.register("triceratops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> SPINOSAURUS_DNA = ITEMS.register("spinosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PARASAUROLOPHUS_DNA = ITEMS.register("parasaurolophus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> OURANOSAURUS_DNA = ITEMS.register("ouranosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> INDOMINUS_REX_DNA = ITEMS.register("indominus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> GALLIMIMUS_DNA = ITEMS.register("gallimimus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DIPLODOCUS_DNA = ITEMS.register("diplodocus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DILOPHOSAURUS_DNA = ITEMS.register("dilophosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> COMPSOGNATHUS_DNA = ITEMS.register("compsognathus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CERATOSAURUS_DNA = ITEMS.register("ceratosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> BRACHIOSAURUS_DNA = ITEMS.register("brachiosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ALBERTOSAURUS_DNA = ITEMS.register("albertosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> APATOSAURUS_DNA = ITEMS.register("apatosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> BARYONYX_DNA = ITEMS.register("baryonyx_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CARNOTAURUS_DNA = ITEMS.register("carnotaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CONCAVENATOR_DNA = ITEMS.register("concavenator_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DEINONYCHUS_DNA = ITEMS.register("deinonychus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> EDMONTOSAURUS_DNA = ITEMS.register("edmontosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> GIGANOTOSAURUS_DNA = ITEMS.register("giganotosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> GUANLONG_DNA = ITEMS.register("guanlong_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> HERRERASAURUS_DNA = ITEMS.register("herrerasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> MAJUNGASAURUS_DNA = ITEMS.register("majungasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PROCOMPSOGNATHUS_DNA = ITEMS.register("procompsognathus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PROTOCERATOPS_DNA = ITEMS.register("protoceratops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> RUGOPS_DNA = ITEMS.register("rugops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> SHANTUNGOSAURUS_DNA = ITEMS.register("shantungosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> STEGOSAURUS_DNA = ITEMS.register("stegosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> STYRACOSAURUS_DNA = ITEMS.register("styracosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> THERIZINOSAURUS_DNA = ITEMS.register("therizinosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DISTORTUS_REX_DNA = ITEMS.register("distortus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ALLOSAURUS_DNA = ITEMS.register("allosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ALVAREZSAURUS_DNA = ITEMS.register("alvarezsaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ANKYLOSAURUS_DNA = ITEMS.register("ankylosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ARAMBOURGIANIA_DNA = ITEMS.register("arambourgiania_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CARCHARODONTOSAURUS_DNA = ITEMS.register("carcharodontosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CEARADACTYLUS_DNA = ITEMS.register("cearadactylus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CHASMOSAURUS_DNA = ITEMS.register("chasmosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> COELOPHYSIS_DNA = ITEMS.register("coelophysis_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> COELURUS_DNA = ITEMS.register("coelurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CORYTHOSAURUS_DNA = ITEMS.register("corythosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DIMORPHODON_DNA = ITEMS.register("dimorphodon_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> DRYOSAURUS_DNA = ITEMS.register("dryosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> GEOSTERNBERGIA_DNA = ITEMS.register("geosternbergia_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> GUIDRACO_DNA = ITEMS.register("guidraco_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> HADROSAURUS_DNA = ITEMS.register("hadrosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> HYPSILOPHODON_DNA = ITEMS.register("hypsilophodon_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> INDORAPTOR_DNA = ITEMS.register("indoraptor_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> INOSTRANCEVIA_DNA = ITEMS.register("inostrancevia_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> LAMBEOSAURUS_DNA = ITEMS.register("lambeosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> LUDODACTYLUS_DNA = ITEMS.register("ludodactylus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> MAMENCHISAURUS_DNA = ITEMS.register("mamenchisaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> METRIACANTHOSAURUS_DNA = ITEMS.register("metriacanthosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> MOGANOPTERUS_DNA = ITEMS.register("moganopterus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> NYCTOSAURUS_DNA = ITEMS.register("nyctosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ORNITHOLESTES_DNA = ITEMS.register("ornitholestes_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ORNITHOMIMUS_DNA = ITEMS.register("ornithomimus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> OVIRAPTOR_DNA = ITEMS.register("oviraptor_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PACHYCEPHALOSAURUS_DNA = ITEMS.register("pachycephalosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PROCERATOSAURUS_DNA = ITEMS.register("proceratosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PTERANODON_DNA = ITEMS.register("pteranodon_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> PTERODAUSTRO_DNA = ITEMS.register("pterodaustro_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> QUETZALCOATLUS_DNA = ITEMS.register("quetzalcoatlus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> RAJASAURUS_DNA = ITEMS.register("rajasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> SEGISAURUS_DNA = ITEMS.register("segisaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TAPEJARA_DNA = ITEMS.register("tapejara_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TITANOSAURUS_DNA = ITEMS.register("titanosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TROODON_DNA = ITEMS.register("troodon_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TROPEOGNATHUS_DNA = ITEMS.register("tropeognathus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> TUPUXUARA_DNA = ITEMS.register("tupuxuara_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> UTAHRAPTOR_DNA = ITEMS.register("utahraptor_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ZHENYUANOPTERUS_DNA = ITEMS.register("zhenyuanopterus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> ACHILLOBATOR_DNA = ITEMS.register("achillobator_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> SUCHOMIMUS_DNA = ITEMS.register("suchomimus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> CHILESAURUS_DNA = ITEMS.register("chilesaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> THESCELOSAURUS_DNA = ITEMS.register("thescelosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
	public static final RegistrySupplier<Item> MUSSASAURUS_DNA = ITEMS.register("mussasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));


	// Syringe group
	public static final RegistrySupplier<Item> VELOCIRAPTOR_SYRINGE = ITEMS.register("velociraptor_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TYRANNOSAURUS_REX_SYRINGE = ITEMS.register("tyrannosaurus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TRICERATOPS_SYRINGE = ITEMS.register("triceratops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> SPINOSAURUS_SYRINGE = ITEMS.register("spinosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PARASAUROLOPHUS_SYRINGE = ITEMS.register("parasaurolophus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> OURANOSAURUS_SYRINGE = ITEMS.register("ouranosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> INDOMINUS_REX_SYRINGE = ITEMS.register("indominus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> GALLIMIMUS_SYRINGE = ITEMS.register("gallimimus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DIPLODOCUS_SYRINGE = ITEMS.register("diplodocus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DILOPHOSAURUS_SYRINGE = ITEMS.register("dilophosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> COMPSOGNATHUS_SYRINGE = ITEMS.register("compsognathus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CERATOSAURUS_SYRINGE = ITEMS.register("ceratosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> BRACHIOSAURUS_SYRINGE = ITEMS.register("brachiosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ALBERTOSAURUS_SYRINGE = ITEMS.register("albertosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> APATOSAURUS_SYRINGE = ITEMS.register("apatosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> BARYONYX_SYRINGE = ITEMS.register("baryonyx_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CARNOTAURUS_SYRINGE = ITEMS.register("carnotaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CONCAVENATOR_SYRINGE = ITEMS.register("concavenator_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DEINONYCHUS_SYRINGE = ITEMS.register("deinonychus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> EDMONTOSAURUS_SYRINGE = ITEMS.register("edmontosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> GIGANOTOSAURUS_SYRINGE = ITEMS.register("giganotosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> GUANLONG_SYRINGE = ITEMS.register("guanlong_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> HERRERASAURUS_SYRINGE = ITEMS.register("herrerasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> MAJUNGASAURUS_SYRINGE = ITEMS.register("majungasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PROCOMPSOGNATHUS_SYRINGE = ITEMS.register("procompsognathus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PROTOCERATOPS_SYRINGE = ITEMS.register("protoceratops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> RUGOPS_SYRINGE = ITEMS.register("rugops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> SHANTUNGOSAURUS_SYRINGE = ITEMS.register("shantungosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> STEGOSAURUS_SYRINGE = ITEMS.register("stegosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> STYRACOSAURUS_SYRINGE = ITEMS.register("styracosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> THERIZINOSAURUS_SYRINGE = ITEMS.register("therizinosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DISTORTUS_REX_SYRINGE = ITEMS.register("distortus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ALLOSAURUS_SYRINGE = ITEMS.register("allosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ALVAREZSAURUS_SYRINGE = ITEMS.register("alvarezsaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ANKYLOSAURUS_SYRINGE = ITEMS.register("ankylosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ARAMBOURGIANIA_SYRINGE = ITEMS.register("arambourgiania_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CARCHARODONTOSAURUS_SYRINGE = ITEMS.register("carcharodontosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CEARADACTYLUS_SYRINGE = ITEMS.register("cearadactylus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CHASMOSAURUS_SYRINGE = ITEMS.register("chasmosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> COELOPHYSIS_SYRINGE = ITEMS.register("coelophysis_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> COELURUS_SYRINGE = ITEMS.register("coelurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CORYTHOSAURUS_SYRINGE = ITEMS.register("corythosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DIMORPHODON_SYRINGE = ITEMS.register("dimorphodon_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> DRYOSAURUS_SYRINGE = ITEMS.register("dryosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> GEOSTERNBERGIA_SYRINGE = ITEMS.register("geosternbergia_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> GUIDRACO_SYRINGE = ITEMS.register("guidraco_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> HADROSAURUS_SYRINGE = ITEMS.register("hadrosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> HYPSILOPHODON_SYRINGE = ITEMS.register("hypsilophodon_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> INDORAPTOR_SYRINGE = ITEMS.register("indoraptor_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> INOSTRANCEVIA_SYRINGE = ITEMS.register("inostrancevia_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> LAMBEOSAURUS_SYRINGE = ITEMS.register("lambeosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> LUDODACTYLUS_SYRINGE = ITEMS.register("ludodactylus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> MAMENCHISAURUS_SYRINGE = ITEMS.register("mamenchisaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> METRIACANTHOSAURUS_SYRINGE = ITEMS.register("metriacanthosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> MOGANOPTERUS_SYRINGE = ITEMS.register("moganopterus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> NYCTOSAURUS_SYRINGE = ITEMS.register("nyctosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ORNITHOLESTES_SYRINGE = ITEMS.register("ornitholestes_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ORNITHOMIMUS_SYRINGE = ITEMS.register("ornithomimus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> OVIRAPTOR_SYRINGE = ITEMS.register("oviraptor_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PACHYCEPHALOSAURUS_SYRINGE = ITEMS.register("pachycephalosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PROCERATOSAURUS_SYRINGE = ITEMS.register("proceratosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PTERANODON_SYRINGE = ITEMS.register("pteranodon_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> PTERODAUSTRO_SYRINGE = ITEMS.register("pterodaustro_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> QUETZALCOATLUS_SYRINGE = ITEMS.register("quetzalcoatlus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> RAJASAURUS_SYRINGE = ITEMS.register("rajasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> SEGISAURUS_SYRINGE = ITEMS.register("segisaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TAPEJARA_SYRINGE = ITEMS.register("tapejara_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TITANOSAURUS_SYRINGE = ITEMS.register("titanosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TROODON_SYRINGE = ITEMS.register("troodon_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TROPEOGNATHUS_SYRINGE = ITEMS.register("tropeognathus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> TUPUXUARA_SYRINGE = ITEMS.register("tupuxuara_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> UTAHRAPTOR_SYRINGE = ITEMS.register("utahraptor_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ZHENYUANOPTERUS_SYRINGE = ITEMS.register("zhenyuanopterus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> ACHILLOBATOR_SYRINGE = ITEMS.register("achillobator_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> SUCHOMIMUS_SYRINGE = ITEMS.register("suchomimus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> CHILESAURUS_SYRINGE = ITEMS.register("chilesaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> THESCELOSAURUS_SYRINGE = ITEMS.register("thescelosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistrySupplier<Item> MUSSASAURUS_SYRINGE = ITEMS.register("mussasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));


	public static void register() {
		ITEMS.register();
	}
}