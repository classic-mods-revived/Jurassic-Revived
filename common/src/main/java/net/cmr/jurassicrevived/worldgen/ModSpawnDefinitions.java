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
		spawn("albertosaurus", ModEntities.ALBERTOSAURUS, 3, 1, 2, BiomeTags.IS_TAIGA),
		spawn("allosaurus", ModEntities.ALLOSAURUS, 2, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("alvarezsaurus", ModEntities.ALVAREZSAURUS, 6, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("ankylosaurus", ModEntities.ANKYLOSAURUS, 4, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("apatosaurus", ModEntities.APATOSAURUS, 3, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("arambourgiania", ModEntities.ARAMBOURGIANIA, 2, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("baryonyx", ModEntities.BARYONYX, 2, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("suchomimus", ModEntities.SUCHOMIMUS, 2, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("brachiosaurus", ModEntities.BRACHIOSAURUS, 2, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("carcharodontosaurus", ModEntities.CARCHARODONTOSAURUS, 2, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("carnotaurus", ModEntities.CARNOTAURUS, 3, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("cearadactylus", ModEntities.CEARADACTYLUS, 2, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ceratosaurus", ModEntities.CERATOSAURUS, 2, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("chasmosaurus", ModEntities.CHASMOSAURUS, 3, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("coelophysis", ModEntities.COELOPHYSIS, 5, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("coelurus", ModEntities.COELURUS, 6, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("compsognathus", ModEntities.COMPSOGNATHUS, 6, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("concavenator", ModEntities.CONCAVENATOR, 3, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("corythosaurus", ModEntities.CORYTHOSAURUS, 4, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("deinonychus", ModEntities.DEINONYCHUS, 3, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("dilophosaurus", ModEntities.DILOPHOSAURUS, 5, 2, 3, BiomeTags.IS_JUNGLE),
		spawn("dimorphodon", ModEntities.DIMORPHODON, 2, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("diplodocus", ModEntities.DIPLODOCUS, 3, 1, 2, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("dryosaurus", ModEntities.DRYOSAURUS, 6, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("thescelosaurus", ModEntities.THESCELOSAURUS, 6, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("chilesaurus", ModEntities.CHILESAURUS, 6, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("edmontosaurus", ModEntities.EDMONTOSAURUS, 4, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("gallimimus", ModEntities.GALLIMIMUS, 6, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("geosternbergia", ModEntities.GEOSTERNBERGIA, 2, 2, 4, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("giganotosaurus", ModEntities.GIGANOTOSAURUS, 1, 1, 2, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guanlong", ModEntities.GUANLONG, 4, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("guidraco", ModEntities.GUIDRACO, 2, 2, 3, BiomeTags.IS_MOUNTAIN),
		spawn("hadrosaurus", ModEntities.HADROSAURUS, 4, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("herrerasaurus", ModEntities.HERRERASAURUS, 4, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("hypsilophodon", ModEntities.HYPSILOPHODON, 6, 3, 6, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("inostrancevia", ModEntities.INOSTRANCEVIA, 2, 2, 3, BiomeTags.IS_TAIGA),
		spawn("lambeosaurus", ModEntities.LAMBEOSAURUS, 4, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ludodactylus", ModEntities.LUDODACTYLUS, 2, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("majungasaurus", ModEntities.MAJUNGASAURUS, 3, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("mamenchisaurus", ModEntities.MAMENCHISAURUS, 2, 1, 2, BiomeTags.IS_JUNGLE),
		spawn("metriacanthosaurus", ModEntities.METRIACANTHOSAURUS, 3, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("moganopterus", ModEntities.MOGANOPTERUS, 2, 2, 3, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("nyctosaurus", ModEntities.NYCTOSAURUS, 2, 2, 3, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("ornitholestes", ModEntities.ORNITHOLESTES, 5, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("ornithomimus", ModEntities.ORNITHOMIMUS, 5, 3, 6, BiomeTags.IS_OVERWORLD),
		spawn("ouranosaurus", ModEntities.OURANOSAURUS, 4, 3, 5, BiomeTags.IS_OVERWORLD),
		spawn("oviraptor", ModEntities.OVIRAPTOR, 6, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pachycephalosaurus", ModEntities.PACHYCEPHALOSAURUS, 4, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("parasaurolophus", ModEntities.PARASAUROLOPHUS, 4, 3, 5, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("proceratosaurus", ModEntities.PROCERATOSAURUS, 4, 2, 4, BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA),
		spawn("procompsognathus", ModEntities.PROCOMPSOGNATHUS, 5, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("protoceratops", ModEntities.PROTOCERATOPS, 5, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("mussasaurus", ModEntities.MUSSASAURUS, 5, 3, 5, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("pteranodon", ModEntities.PTERANODON, 3, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("pterodaustro", ModEntities.PTERODAUSTRO, 3, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_OVERWORLD),
		spawn("quetzalcoatlus", ModEntities.QUETZALCOATLUS, 2, 1, 2, BiomeTags.IS_MOUNTAIN),
		spawn("rajasaurus", ModEntities.RAJASAURUS, 3, 2, 3, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("rugops", ModEntities.RUGOPS, 3, 2, 3, BiomeTags.IS_FOREST, BiomeTags.IS_OVERWORLD),
		spawn("segisaurus", ModEntities.SEGISAURUS, 6, 3, 6, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("shantungosaurus", ModEntities.SHANTUNGOSAURUS, 2, 2, 4, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("spinosaurus", ModEntities.SPINOSAURUS, 2, 1, 2, BiomeTags.IS_OVERWORLD),
		spawn("stegosaurus", ModEntities.STEGOSAURUS, 3, 2, 4, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("styracosaurus", ModEntities.STYRACOSAURUS, 4, 2, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tapejara", ModEntities.TAPEJARA, 2, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("therizinosaurus", ModEntities.THERIZINOSAURUS, 3, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_FOREST),
		spawn("titanosaurus", ModEntities.TITANOSAURUS, 2, 1, 2, BiomeTags.IS_JUNGLE, BiomeTags.IS_OVERWORLD),
		spawn("triceratops", ModEntities.TRICERATOPS, 4, 3, 5, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("troodon", ModEntities.TROODON, 5, 3, 6, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("tropeognathus", ModEntities.TROPEOGNATHUS, 2, 2, 4, BiomeTags.IS_MOUNTAIN),
		spawn("tupuxuara", ModEntities.TUPUXUARA, 2, 2, 5, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN),
		spawn("tyrannosaurus_rex", ModEntities.TYRANNOSAURUS_REX, 3, 1, 2, BiomeTags.IS_TAIGA, BiomeTags.IS_OVERWORLD),
		spawn("utahraptor", ModEntities.UTAHRAPTOR, 4, 1, 3, BiomeTags.IS_TAIGA, BiomeTags.IS_FOREST),
		spawn("velociraptor", ModEntities.VELOCIRAPTOR, 5, 2, 4, BiomeTags.IS_BADLANDS, BiomeTags.IS_OVERWORLD),
		spawn("zhenyuanopterus", ModEntities.ZHENYUANOPTERUS, 2, 2, 5, BiomeTags.IS_BEACH, BiomeTags.IS_MOUNTAIN),
		spawn("achillobator", ModEntities.ACHILLOBATOR, 3, 1, 2, BiomeTags.IS_TAIGA)
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
