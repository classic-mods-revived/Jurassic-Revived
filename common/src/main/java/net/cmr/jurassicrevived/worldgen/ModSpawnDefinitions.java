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
		spawn("albertosaurus", ModEntities.ALBERTOSAURUS, 12, 1, 2, BiomeTags.IS_TAIGA),
		spawn("allosaurus", ModEntities.ALLOSAURUS, 10, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("alvarezsaurus", ModEntities.ALVAREZSAURUS, 28, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("ankylosaurus", ModEntities.ANKYLOSAURUS, 14, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("apatosaurus", ModEntities.APATOSAURUS, 10, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("arambourgiania", ModEntities.ARAMBOURGIANIA, 6, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("baryonyx", ModEntities.BARYONYX, 8, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("brachiosaurus", ModEntities.BRACHIOSAURUS, 7, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("carcharodontosaurus", ModEntities.CARCHARODONTOSAURUS, 6, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("carnotaurus", ModEntities.CARNOTAURUS, 11, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("cearadactylus", ModEntities.CEARADACTYLUS, 6, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ceratosaurus", ModEntities.CERATOSAURUS, 9, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("chasmosaurus", ModEntities.CHASMOSAURUS, 18, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("coelophysis", ModEntities.COELOPHYSIS, 30, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("coelurus", ModEntities.COELURUS, 28, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("compsognathus", ModEntities.COMPSOGNATHUS, 36, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("concavenator", ModEntities.CONCAVENATOR, 10, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("corythosaurus", ModEntities.CORYTHOSAURUS, 24, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("deinonychus", ModEntities.DEINONYCHUS, 14, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("dilophosaurus", ModEntities.DILOPHOSAURUS, 22, 2, 3, BiomeTags.IS_JUNGLE),
		spawn("dimorphodon", ModEntities.DIMORPHODON, 7, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("diplodocus", ModEntities.DIPLODOCUS, 8, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("dryosaurus", ModEntities.DRYOSAURUS, 32, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("edmontosaurus", ModEntities.EDMONTOSAURUS, 22, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("gallimimus", ModEntities.GALLIMIMUS, 36, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("geosternbergia", ModEntities.GEOSTERNBERGIA, 7, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("giganotosaurus", ModEntities.GIGANOTOSAURUS, 3, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guanlong", ModEntities.GUANLONG, 20, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guidraco", ModEntities.GUIDRACO, 7, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("hadrosaurus", ModEntities.HADROSAURUS, 26, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("herrerasaurus", ModEntities.HERRERASAURUS, 24, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("hypsilophodon", ModEntities.HYPSILOPHODON, 34, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("inostrancevia", ModEntities.INOSTRANCEVIA, 5, 2, 3, BiomeTags.IS_TAIGA),
		spawn("lambeosaurus", ModEntities.LAMBEOSAURUS, 24, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ludodactylus", ModEntities.LUDODACTYLUS, 6, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("majungasaurus", ModEntities.MAJUNGASAURUS, 8, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("mamenchisaurus", ModEntities.MAMENCHISAURUS, 7, 1, 2, BiomeTags.IS_JUNGLE),
		spawn("metriacanthosaurus", ModEntities.METRIACANTHOSAURUS, 10, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("moganopterus", ModEntities.MOGANOPTERUS, 7, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("nyctosaurus", ModEntities.NYCTOSAURUS, 6, 2, 3, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ornitholestes", ModEntities.ORNITHOLESTES, 30, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ornithomimus", ModEntities.ORNITHOMIMUS, 30, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("ouranosaurus", ModEntities.OURANOSAURUS, 22, 3, 5, BiomeTags.IS_OVERWORLD),
		spawn("oviraptor", ModEntities.OVIRAPTOR, 34, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pachycephalosaurus", ModEntities.PACHYCEPHALOSAURUS, 22, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("parasaurolophus", ModEntities.PARASAUROLOPHUS, 22, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("proceratosaurus", ModEntities.PROCERATOSAURUS, 24, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("procompsognathus", ModEntities.PROCOMPSOGNATHUS, 34, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("protoceratops", ModEntities.PROTOCERATOPS, 28, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pteranodon", ModEntities.PTERANODON, 8, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("pterodaustro", ModEntities.PTERODAUSTRO, 12, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_OVERWORLD),
		spawn("quetzalcoatlus", ModEntities.QUETZALCOATLUS, 4, 1, 2, BiomeTags.IS_MOUNTAIN),
		spawn("rajasaurus", ModEntities.RAJASAURUS, 10, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("rugops", ModEntities.RUGOPS, 10, 2, 3, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("segisaurus", ModEntities.SEGISAURUS, 36, 3, 6, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("shantungosaurus", ModEntities.SHANTUNGOSAURUS, 8, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("spinosaurus", ModEntities.SPINOSAURUS, 3, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("stegosaurus", ModEntities.STEGOSAURUS, 14, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("styracosaurus", ModEntities.STYRACOSAURUS, 22, 2, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tapejara", ModEntities.TAPEJARA, 6, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("therizinosaurus", ModEntities.THERIZINOSAURUS, 8, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("titanosaurus", ModEntities.TITANOSAURUS, 7, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("triceratops", ModEntities.TRICERATOPS, 20, 3, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("troodon", ModEntities.TROODON, 28, 3, 6, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tropeognathus", ModEntities.TROPEOGNATHUS, 6, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("tupuxuara", ModEntities.TUPUXUARA, 6, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("tyrannosaurus_rex", ModEntities.TYRANNOSAURUS_REX, 5, 1, 2, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("utahraptor", ModEntities.UTAHRAPTOR, 16, 1, 3, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("velociraptor", ModEntities.VELOCIRAPTOR, 26, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("zhenyuanopterus", ModEntities.ZHENYUANOPTERUS, 7, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("achillobator", ModEntities.ACHILLOBATOR, 12, 1, 2, BiomeTags.IS_TAIGA)
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
