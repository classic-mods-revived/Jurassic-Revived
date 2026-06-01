package net.cmr.jurassicrevived.worldgen;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Supplier;

public final class ModSpawnDefinitions {
	private ModSpawnDefinitions() {
	}

	public static final List<SpawnDefinition> NATURAL_SPAWNS = List.of(
		spawn("albertosaurus", ModEntities.ALBERTOSAURUS, 9, 1, 2, BiomeTags.IS_TAIGA),
		spawn("allosaurus", ModEntities.ALLOSAURUS, 8, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("alvarezsaurus", ModEntities.ALVAREZSAURUS, 21, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("ankylosaurus", ModEntities.ANKYLOSAURUS, 11, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("apatosaurus", ModEntities.APATOSAURUS, 8, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("arambourgiania", ModEntities.ARAMBOURGIANIA, 4, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("baryonyx", ModEntities.BARYONYX, 5, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("brachiosaurus", ModEntities.BRACHIOSAURUS, 4, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("carcharodontosaurus", ModEntities.CARCHARODONTOSAURUS, 4, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("carnotaurus", ModEntities.CARNOTAURUS, 8, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("cearadactylus", ModEntities.CEARADACTYLUS, 4, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ceratosaurus", ModEntities.CERATOSAURUS, 6, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("chasmosaurus", ModEntities.CHASMOSAURUS, 12, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("coelophysis", ModEntities.COELOPHYSIS, 20, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("coelurus", ModEntities.COELURUS, 21, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("compsognathus", ModEntities.COMPSOGNATHUS, 27, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("concavenator", ModEntities.CONCAVENATOR, 7, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("corythosaurus", ModEntities.CORYTHOSAURUS, 18, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("deinonychus", ModEntities.DEINONYCHUS, 10, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("dilophosaurus", ModEntities.DILOPHOSAURUS, 17, 2, 3, BiomeTags.IS_JUNGLE),
		spawn("dimorphodon", ModEntities.DIMORPHODON, 4, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("diplodocus", ModEntities.DIPLODOCUS, 6, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("dryosaurus", ModEntities.DRYOSAURUS, 24, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("edmontosaurus", ModEntities.EDMONTOSAURUS, 17, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("gallimimus", ModEntities.GALLIMIMUS, 27, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("geosternbergia", ModEntities.GEOSTERNBERGIA, 4, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("giganotosaurus", ModEntities.GIGANOTOSAURUS, 2, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guanlong", ModEntities.GUANLONG, 15, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guidraco", ModEntities.GUIDRACO, 4, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("hadrosaurus", ModEntities.HADROSAURUS, 17, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("herrerasaurus", ModEntities.HERRERASAURUS, 18, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("hypsilophodon", ModEntities.HYPSILOPHODON, 25, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("inostrancevia", ModEntities.INOSTRANCEVIA, 3, 2, 3, BiomeTags.IS_TAIGA),
		spawn("lambeosaurus", ModEntities.LAMBEOSAURUS, 18, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ludodactylus", ModEntities.LUDODACTYLUS, 4, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("majungasaurus", ModEntities.MAJUNGASAURUS, 5, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("mamenchisaurus", ModEntities.MAMENCHISAURUS, 4, 1, 2, BiomeTags.IS_JUNGLE),
		spawn("metriacanthosaurus", ModEntities.METRIACANTHOSAURUS, 7, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("moganopterus", ModEntities.MOGANOPTERUS, 4, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("nyctosaurus", ModEntities.NYCTOSAURUS, 4, 2, 3, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ornitholestes", ModEntities.ORNITHOLESTES, 20, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ornithomimus", ModEntities.ORNITHOMIMUS, 20, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("ouranosaurus", ModEntities.OURANOSAURUS, 17, 3, 5, BiomeTags.IS_OVERWORLD),
		spawn("oviraptor", ModEntities.OVIRAPTOR, 25, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pachycephalosaurus", ModEntities.PACHYCEPHALOSAURUS, 17, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("parasaurolophus", ModEntities.PARASAUROLOPHUS, 17, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("proceratosaurus", ModEntities.PROCERATOSAURUS, 18, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("procompsognathus", ModEntities.PROCOMPSOGNATHUS, 25, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("protoceratops", ModEntities.PROTOCERATOPS, 21, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pteranodon", ModEntities.PTERANODON, 5, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("pterodaustro", ModEntities.PTERODAUSTRO, 9, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_OVERWORLD),
		spawn("quetzalcoatlus", ModEntities.QUETZALCOATLUS, 2, 1, 2, BiomeTags.IS_MOUNTAIN),
		spawn("rajasaurus", ModEntities.RAJASAURUS, 7, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("rugops", ModEntities.RUGOPS, 7, 2, 3, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("segisaurus", ModEntities.SEGISAURUS, 27, 3, 6, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("shantungosaurus", ModEntities.SHANTUNGOSAURUS, 5, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("spinosaurus", ModEntities.SPINOSAURUS, 2, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("stegosaurus", ModEntities.STEGOSAURUS, 10, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("styracosaurus", ModEntities.STYRACOSAURUS, 17, 2, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tapejara", ModEntities.TAPEJARA, 4, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("therizinosaurus", ModEntities.THERIZINOSAURUS, 5, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("titanosaurus", ModEntities.TITANOSAURUS, 4, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("triceratops", ModEntities.TRICERATOPS, 15, 3, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("troodon", ModEntities.TROODON, 21, 3, 6, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tropeognathus", ModEntities.TROPEOGNATHUS, 4, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("tupuxuara", ModEntities.TUPUXUARA, 4, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("tyrannosaurus_rex", ModEntities.TYRANNOSAURUS_REX, 3, 1, 2, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("utahraptor", ModEntities.UTAHRAPTOR, 12, 1, 3, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("velociraptor", ModEntities.VELOCIRAPTOR, 18, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("zhenyuanopterus", ModEntities.ZHENYUANOPTERUS, 4, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("achillobator", ModEntities.ACHILLOBATOR, 9, 1, 2, BiomeTags.IS_TAIGA)
	);

	@SafeVarargs
	private static SpawnDefinition spawn(String name, Supplier<? extends EntityType<?>> entityType, int weight, int minCount, int maxCount, TagKey<Biome>... biomeTags) {
		return new SpawnDefinition(name, entityType, weight, minCount, maxCount, List.of(biomeTags));
	}

	public record SpawnDefinition(
		String name,
		Supplier<? extends EntityType<?>> entityType,
		int weight,
		int minCount,
		int maxCount,
		List<TagKey<Biome>> biomeTags
	) {
	}
}
