package net.cmr.jurassicrevived.sound;

import dev.architectury.registry.registries.DeferredRegister;
import net.cmr.jurassicrevived.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Constants.MOD_ID, Registries.SOUND_EVENT);

    public static final Supplier<SoundEvent> MACHINE_HUM_SOUND = registerSoundEvent("machine_hum");

    public static final Supplier<SoundEvent> MEDIUM_THEROPOD_ATTACK = registerSoundEvent("medium_theropod_attack");
    public static final Supplier<SoundEvent> LARGE_THEROPOD_ATTACK = registerSoundEvent("large_theropod_attack");
    public static final Supplier<SoundEvent> TAIL_WHIP = registerSoundEvent("tail_whip");
    public static final Supplier<SoundEvent> TAIL_STRIKE = registerSoundEvent("tail_strike");
    public static final Supplier<SoundEvent> STOMP_ATTACK = registerSoundEvent("stomp_attack");
    public static final Supplier<SoundEvent> BEAK_ATTACK = registerSoundEvent("beak_attack");

    public static final Supplier<SoundEvent> CROCODILE = registerSoundEvent("crocodile");
    public static final Supplier<SoundEvent> CROW = registerSoundEvent("crow");

    public static final Supplier<SoundEvent> GOAT_CALL = registerSoundEvent("goat_call");
    public static final Supplier<SoundEvent> GOAT_HURT = registerSoundEvent("goat_hurt");
    public static final Supplier<SoundEvent> GOAT_DEATH = registerSoundEvent("goat_death");

    public static final Supplier<SoundEvent> TREE_FROG_CALL = registerSoundEvent("tree_frog_call");
    public static final Supplier<SoundEvent> TREE_FROG_HURT = registerSoundEvent("tree_frog_hurt");
    public static final Supplier<SoundEvent> TREE_FROG_DEATH = registerSoundEvent("tree_frog_death");

    public static final Supplier<SoundEvent> OSTRICH_CALL = registerSoundEvent("ostrich_call");
    public static final Supplier<SoundEvent> OSTRICH_HURT = registerSoundEvent("ostrich_hurt");
    public static final Supplier<SoundEvent> OSTRICH_DEATH = registerSoundEvent("ostrich_death");

    public static final Supplier<SoundEvent> STOMP = registerSoundEvent("stomp");

    public static final Supplier<SoundEvent> ALBERTOSAURUS_CALL = registerSoundEvent("albertosaurus_call");
    public static final Supplier<SoundEvent> ALBERTOSAURUS_HURT = registerSoundEvent("albertosaurus_hurt");
    public static final Supplier<SoundEvent> ALBERTOSAURUS_DEATH = registerSoundEvent("albertosaurus_death");

    public static final Supplier<SoundEvent> ALLOSAURUS_CALL = registerSoundEvent("allosaurus_call");
    public static final Supplier<SoundEvent> ALLOSAURUS_HURT = registerSoundEvent("allosaurus_hurt");
    public static final Supplier<SoundEvent> ALLOSAURUS_DEATH = registerSoundEvent("allosaurus_death");

    public static final Supplier<SoundEvent> ALVAREZSAURUS_CALL = registerSoundEvent("alvarezsaurus_call");
    public static final Supplier<SoundEvent> ALVAREZSAURUS_HURT = registerSoundEvent("alvarezsaurus_hurt");
    public static final Supplier<SoundEvent> ALVAREZSAURUS_DEATH = registerSoundEvent("alvarezsaurus_death");

    public static final Supplier<SoundEvent> ANKYLOSAURUS_CALL = registerSoundEvent("ankylosaurus_call");
    public static final Supplier<SoundEvent> ANKYLOSAURUS_HURT = registerSoundEvent("ankylosaurus_hurt");
    public static final Supplier<SoundEvent> ANKYLOSAURUS_DEATH = registerSoundEvent("ankylosaurus_death");

    public static final Supplier<SoundEvent> APATOSAURUS_CALL = registerSoundEvent("apatosaurus_call");
    public static final Supplier<SoundEvent> APATOSAURUS_HURT = registerSoundEvent("apatosaurus_hurt");
    public static final Supplier<SoundEvent> APATOSAURUS_DEATH = registerSoundEvent("apatosaurus_death");

    public static final Supplier<SoundEvent> ARAMBOURGIANIA_CALL = registerSoundEvent("arambourgiania_call");
    public static final Supplier<SoundEvent> ARAMBOURGIANIA_HURT = registerSoundEvent("arambourgiania_hurt");
    public static final Supplier<SoundEvent> ARAMBOURGIANIA_DEATH = registerSoundEvent("arambourgiania_death");

    public static final Supplier<SoundEvent> BARYONYX_CALL = registerSoundEvent("baryonyx_call");
    public static final Supplier<SoundEvent> BARYONYX_ATTACK = registerSoundEvent("baryonyx_attack");
    public static final Supplier<SoundEvent> BARYONYX_HURT = registerSoundEvent("baryonyx_hurt");
    public static final Supplier<SoundEvent> BARYONYX_DEATH = registerSoundEvent("baryonyx_death");

    public static final Supplier<SoundEvent> BRACHIOSAURUS_CALL = registerSoundEvent("brachiosaurus_call");
    public static final Supplier<SoundEvent> BRACHIOSAURUS_HURT = registerSoundEvent("brachiosaurus_hurt");
    public static final Supplier<SoundEvent> BRACHIOSAURUS_DEATH = registerSoundEvent("brachiosaurus_death");

    public static final Supplier<SoundEvent> CARCHARODONTOSAURUS_CALL = registerSoundEvent("carcharodontosaurus_call");
    public static final Supplier<SoundEvent> CARCHARODONTOSAURUS_HURT = registerSoundEvent("carcharodontosaurus_hurt");
    public static final Supplier<SoundEvent> CARCHARODONTOSAURUS_DEATH = registerSoundEvent("carcharodontosaurus_death");

    public static final Supplier<SoundEvent> CARNOTAURUS_CALL = registerSoundEvent("carnotaurus_call");
    public static final Supplier<SoundEvent> CARNOTAURUS_ATTACK = registerSoundEvent("carnotaurus_attack");
    public static final Supplier<SoundEvent> CARNOTAURUS_HURT = registerSoundEvent("carnotaurus_hurt");
    public static final Supplier<SoundEvent> CARNOTAURUS_DEATH = registerSoundEvent("carnotaurus_death");

    public static final Supplier<SoundEvent> CEARADACTYLUS_CALL = registerSoundEvent("cearadactylus_call");
    public static final Supplier<SoundEvent> CEARADACTYLUS_HURT = registerSoundEvent("cearadactylus_hurt");
    public static final Supplier<SoundEvent> CEARADACTYLUS_DEATH = registerSoundEvent("cearadactylus_death");

    public static final Supplier<SoundEvent> CERATOSAURUS_CALL = registerSoundEvent("ceratosaurus_call");
    public static final Supplier<SoundEvent> CERATOSAURUS_HURT = registerSoundEvent("ceratosaurus_hurt");
    public static final Supplier<SoundEvent> CERATOSAURUS_DEATH = registerSoundEvent("ceratosaurus_death");

    public static final Supplier<SoundEvent> CHASMOSAURUS_CALL = registerSoundEvent("chasmosaurus_call");
    public static final Supplier<SoundEvent> CHASMOSAURUS_ATTACK = registerSoundEvent("chasmosaurus_attack");////////////
    public static final Supplier<SoundEvent> CHASMOSAURUS_HURT = registerSoundEvent("chasmosaurus_hurt");
    public static final Supplier<SoundEvent> CHASMOSAURUS_DEATH = registerSoundEvent("chasmosaurus_death");

    public static final Supplier<SoundEvent> CHICKENOSAURUS_CALL = registerSoundEvent("chickenosaurus_call");
    public static final Supplier<SoundEvent> CHICKENOSAURUS_HURT = registerSoundEvent("chickenosaurus_hurt");
    public static final Supplier<SoundEvent> CHICKENOSAURUS_DEATH = registerSoundEvent("chickenosaurus_death");

    public static final Supplier<SoundEvent> COELOPHYSIS_CALL = registerSoundEvent("coelophysis_call");
    public static final Supplier<SoundEvent> COELOPHYSIS_HURT = registerSoundEvent("coelophysis_hurt");
    public static final Supplier<SoundEvent> COELOPHYSIS_DEATH = registerSoundEvent("coelophysis_death");

    public static final Supplier<SoundEvent> COELURUS_CALL = registerSoundEvent("coelurus_call");
    public static final Supplier<SoundEvent> COELURUS_HURT = registerSoundEvent("coelurus_hurt");
    public static final Supplier<SoundEvent> COELURUS_DEATH = registerSoundEvent("coelurus_death");

    public static final Supplier<SoundEvent> COMPSOGNATHUS_CALL = registerSoundEvent("compsognathus_call");
    public static final Supplier<SoundEvent> COMPSOGNATHUS_HURT = registerSoundEvent("compsognathus_hurt");
    public static final Supplier<SoundEvent> COMPSOGNATHUS_DEATH = registerSoundEvent("compsognathus_death");

    public static final Supplier<SoundEvent> CONCAVENATOR_CALL = registerSoundEvent("concavenator_call");
    public static final Supplier<SoundEvent> CONCAVENATOR_HURT = registerSoundEvent("concavenator_hurt");
    public static final Supplier<SoundEvent> CONCAVENATOR_DEATH = registerSoundEvent("concavenator_death");

    public static final Supplier<SoundEvent> CORYTHOSAURUS_CALL = registerSoundEvent("corythosaurus_call");
    public static final Supplier<SoundEvent> CORYTHOSAURUS_HURT = registerSoundEvent("corythosaurus_hurt");
    public static final Supplier<SoundEvent> CORYTHOSAURUS_DEATH = registerSoundEvent("corythosaurus_death");

    public static final Supplier<SoundEvent> DEINONYCHUS_CALL = registerSoundEvent("deinonychus_call");
    public static final Supplier<SoundEvent> DEINONYCHUS_HURT = registerSoundEvent("deinonychus_hurt");
    public static final Supplier<SoundEvent> DEINONYCHUS_DEATH = registerSoundEvent("deinonychus_death");

    public static final Supplier<SoundEvent> DILOPHOSAURUS_CALL = registerSoundEvent("dilophosaurus_call");
    public static final Supplier<SoundEvent> DILOPHOSAURUS_ATTACK = registerSoundEvent("dilophosaurus_attack");
    public static final Supplier<SoundEvent> DILOPHOSAURUS_HURT = registerSoundEvent("dilophosaurus_hurt");
    public static final Supplier<SoundEvent> DILOPHOSAURUS_DEATH = registerSoundEvent("dilophosaurus_death");

    public static final Supplier<SoundEvent> DIMORPHODON_CALL = registerSoundEvent("dimorphodon_call");
    public static final Supplier<SoundEvent> DIMORPHODON_HURT = registerSoundEvent("dimorphodon_hurt");
    public static final Supplier<SoundEvent> DIMORPHODON_DEATH = registerSoundEvent("dimorphodon_death");

    public static final Supplier<SoundEvent> DIPLODOCUS_CALL = registerSoundEvent("diplodocus_call");
    public static final Supplier<SoundEvent> DIPLODOCUS_HURT = registerSoundEvent("diplodocus_hurt");
    public static final Supplier<SoundEvent> DIPLODOCUS_DEATH = registerSoundEvent("diplodocus_death");

    public static final Supplier<SoundEvent> DISTORTUS_REX_CALL = registerSoundEvent("distortus_rex_call");
    public static final Supplier<SoundEvent> DISTORTUS_REX_ATTACK = registerSoundEvent("distortus_rex_attack");
    public static final Supplier<SoundEvent> DISTORTUS_REX_HURT = registerSoundEvent("distortus_rex_hurt");
    public static final Supplier<SoundEvent> DISTORTUS_REX_DEATH = registerSoundEvent("distortus_rex_death");

    public static final Supplier<SoundEvent> DRYOSAURUS_CALL = registerSoundEvent("dryosaurus_call");
    public static final Supplier<SoundEvent> DRYOSAURUS_HURT = registerSoundEvent("dryosaurus_hurt");
    public static final Supplier<SoundEvent> DRYOSAURUS_DEATH = registerSoundEvent("dryosaurus_death");

    public static final Supplier<SoundEvent> EDMONTOSAURUS_CALL = registerSoundEvent("edmontosaurus_call");
    public static final Supplier<SoundEvent> EDMONTOSAURUS_HURT = registerSoundEvent("edmontosaurus_hurt");
    public static final Supplier<SoundEvent> EDMONTOSAURUS_DEATH = registerSoundEvent("edmontosaurus_death");

    public static final Supplier<SoundEvent> FDUCK_CALL = registerSoundEvent("fduck_call");
    public static final Supplier<SoundEvent> FDUCK_HURT = registerSoundEvent("fduck_hurt");
    public static final Supplier<SoundEvent> FDUCK_DEATH = registerSoundEvent("fduck_death");

    public static final Supplier<SoundEvent> GALLIMIMUS_CALL = registerSoundEvent("gallimimus_call");
    public static final Supplier<SoundEvent> GALLIMIMUS_HURT = registerSoundEvent("gallimimus_hurt");
    public static final Supplier<SoundEvent> GALLIMIMUS_DEATH = registerSoundEvent("gallimimus_death");

    public static final Supplier<SoundEvent> GEOSTERNBERGIA_CALL = registerSoundEvent("geosternbergia_call");
    public static final Supplier<SoundEvent> GEOSTERNBERGIA_HURT = registerSoundEvent("geosternbergia_hurt");
    public static final Supplier<SoundEvent> GEOSTERNBERGIA_DEATH = registerSoundEvent("geosternbergia_death");

    public static final Supplier<SoundEvent> GIGANOTOSAURUS_CALL = registerSoundEvent("giganotosaurus_call");
    public static final Supplier<SoundEvent> GIGANOTOSAURUS_ATTACK = registerSoundEvent("giganotosaurus_attack");
    public static final Supplier<SoundEvent> GIGANOTOSAURUS_HURT = registerSoundEvent("giganotosaurus_hurt");
    public static final Supplier<SoundEvent> GIGANOTOSAURUS_DEATH = registerSoundEvent("giganotosaurus_death");

    public static final Supplier<SoundEvent> GUANLONG_CALL = registerSoundEvent("guanlong_call");
    public static final Supplier<SoundEvent> GUANLONG_HURT = registerSoundEvent("guanlong_hurt");
    public static final Supplier<SoundEvent> GUANLONG_DEATH = registerSoundEvent("guanlong_death");

    public static final Supplier<SoundEvent> GUIDRACO_CALL = registerSoundEvent("guidraco_call");
    public static final Supplier<SoundEvent> GUIDRACO_HURT = registerSoundEvent("guidraco_hurt");
    public static final Supplier<SoundEvent> GUIDRACO_DEATH = registerSoundEvent("guidraco_death");

    public static final Supplier<SoundEvent> HADROSAURUS_CALL = registerSoundEvent("hadrosaurus_call");
    public static final Supplier<SoundEvent> HADROSAURUS_HURT = registerSoundEvent("hadrosaurus_hurt");
    public static final Supplier<SoundEvent> HADROSAURUS_DEATH = registerSoundEvent("hadrosaurus_death");

    public static final Supplier<SoundEvent> HERRERASAURUS_CALL = registerSoundEvent("herrerasaurus_call");
    public static final Supplier<SoundEvent> HERRERASAURUS_ATTACK = registerSoundEvent("herrerasaurus_attack");
    public static final Supplier<SoundEvent> HERRERASAURUS_HURT = registerSoundEvent("herrerasaurus_hurt");
    public static final Supplier<SoundEvent> HERRERASAURUS_DEATH = registerSoundEvent("herrerasaurus_death");

    public static final Supplier<SoundEvent> HYPSILOPHODON_CALL = registerSoundEvent("hypsilophodon_call");
    public static final Supplier<SoundEvent> HYPSILOPHODON_HURT = registerSoundEvent("hypsilophodon_hurt");
    public static final Supplier<SoundEvent> HYPSILOPHODON_DEATH = registerSoundEvent("hypsilophodon_death");

    public static final Supplier<SoundEvent> INDOMINUS_REX_CALL = registerSoundEvent("indominus_rex_call");
    public static final Supplier<SoundEvent> INDOMINUS_REX_ATTACK = registerSoundEvent("indominus_rex_attack");
    public static final Supplier<SoundEvent> INDOMINUS_REX_HURT = registerSoundEvent("indominus_rex_hurt");
    public static final Supplier<SoundEvent> INDOMINUS_REX_DEATH = registerSoundEvent("indominus_rex_death");

    public static final Supplier<SoundEvent> INDORAPTOR_CALL = registerSoundEvent("indoraptor_call");
    public static final Supplier<SoundEvent> INDORAPTOR_ATTACK = registerSoundEvent("indoraptor_attack");
    public static final Supplier<SoundEvent> INDORAPTOR_HURT = registerSoundEvent("indoraptor_hurt");
    public static final Supplier<SoundEvent> INDORAPTOR_DEATH = registerSoundEvent("indoraptor_death");

    public static final Supplier<SoundEvent> INOSTRANCEVIA_CALL = registerSoundEvent("inostrancevia_call");
    public static final Supplier<SoundEvent> INOSTRANCEVIA_ATTACK = registerSoundEvent("inostrancevia_attack");
    public static final Supplier<SoundEvent> INOSTRANCEVIA_HURT = registerSoundEvent("inostrancevia_hurt");
    public static final Supplier<SoundEvent> INOSTRANCEVIA_DEATH = registerSoundEvent("inostrancevia_death");

    public static final Supplier<SoundEvent> LAMBEOSAURUS_CALL = registerSoundEvent("lambeosaurus_call");
    public static final Supplier<SoundEvent> LAMBEOSAURUS_HURT = registerSoundEvent("lambeosaurus_hurt");
    public static final Supplier<SoundEvent> LAMBEOSAURUS_DEATH = registerSoundEvent("lambeosaurus_death");

    public static final Supplier<SoundEvent> LUDODACTYLUS_CALL = registerSoundEvent("ludodactylus_call");
    public static final Supplier<SoundEvent> LUDODACTYLUS_HURT = registerSoundEvent("ludodactylus_hurt");
    public static final Supplier<SoundEvent> LUDODACTYLUS_DEATH = registerSoundEvent("ludodactylus_death");

    public static final Supplier<SoundEvent> MAJUNGASAURUS_CALL = registerSoundEvent("majungasaurus_call");
    public static final Supplier<SoundEvent> MAJUNGASAURUS_HURT = registerSoundEvent("majungasaurus_hurt");
    public static final Supplier<SoundEvent> MAJUNGASAURUS_DEATH = registerSoundEvent("majungasaurus_death");

    public static final Supplier<SoundEvent> MAMENCHISAURUS_CALL = registerSoundEvent("mamenchisaurus_call");
    public static final Supplier<SoundEvent> MAMENCHISAURUS_HURT = registerSoundEvent("mamenchisaurus_hurt");
    public static final Supplier<SoundEvent> MAMENCHISAURUS_DEATH = registerSoundEvent("mamenchisaurus_death");

    public static final Supplier<SoundEvent> METRIACANTHOSAURUS_CALL = registerSoundEvent("metriacanthosaurus_call");
    public static final Supplier<SoundEvent> METRIACANTHOSAURUS_HURT = registerSoundEvent("metriacanthosaurus_hurt");
    public static final Supplier<SoundEvent> METRIACANTHOSAURUS_DEATH = registerSoundEvent("metriacanthosaurus_death");

    public static final Supplier<SoundEvent> MOGANOPTERUS_CALL = registerSoundEvent("moganopterus_call");
    public static final Supplier<SoundEvent> MOGANOPTERUS_HURT = registerSoundEvent("moganopterus_hurt");
    public static final Supplier<SoundEvent> MOGANOPTERUS_DEATH = registerSoundEvent("moganopterus_death");

    public static final Supplier<SoundEvent> NYCTOSAURUS_CALL = registerSoundEvent("nyctosaurus_call");
    public static final Supplier<SoundEvent> NYCTOSAURUS_HURT = registerSoundEvent("nyctosaurus_hurt");
    public static final Supplier<SoundEvent> NYCTOSAURUS_DEATH = registerSoundEvent("nyctosaurus_death");

    public static final Supplier<SoundEvent> ORNITHOLESTES_CALL = registerSoundEvent("ornitholestes_call");
    public static final Supplier<SoundEvent> ORNITHOLESTES_HURT = registerSoundEvent("ornitholestes_hurt");
    public static final Supplier<SoundEvent> ORNITHOLESTES_DEATH = registerSoundEvent("ornitholestes_death");

    public static final Supplier<SoundEvent> ORNITHOMIMUS_CALL = registerSoundEvent("ornithomimus_call");
    public static final Supplier<SoundEvent> ORNITHOMIMUS_HURT = registerSoundEvent("ornithomimus_hurt");
    public static final Supplier<SoundEvent> ORNITHOMIMUS_DEATH = registerSoundEvent("ornithomimus_death");

    public static final Supplier<SoundEvent> OURANOSAURUS_CALL = registerSoundEvent("ouranosaurus_call");
    public static final Supplier<SoundEvent> OURANOSAURUS_HURT = registerSoundEvent("ouranosaurus_hurt");
    public static final Supplier<SoundEvent> OURANOSAURUS_DEATH = registerSoundEvent("ouranosaurus_death");

    public static final Supplier<SoundEvent> OVIRAPTOR_CALL = registerSoundEvent("oviraptor_call");
    public static final Supplier<SoundEvent> OVIRAPTOR_HURT = registerSoundEvent("oviraptor_hurt");
    public static final Supplier<SoundEvent> OVIRAPTOR_DEATH = registerSoundEvent("oviraptor_death");

    public static final Supplier<SoundEvent> PACHYCEPHALOSAURUS_CALL = registerSoundEvent("pachycephalosaurus_call");
    public static final Supplier<SoundEvent> PACHYCEPHALOSAURUS_HURT = registerSoundEvent("pachycephalosaurus_hurt");
    public static final Supplier<SoundEvent> PACHYCEPHALOSAURUS_DEATH = registerSoundEvent("pachycephalosaurus_death");

    public static final Supplier<SoundEvent> PARASAUROLOPHUS_CALL = registerSoundEvent("parasaurolophus_call");
    public static final Supplier<SoundEvent> PARASAUROLOPHUS_HURT = registerSoundEvent("parasaurolophus_hurt");
    public static final Supplier<SoundEvent> PARASAUROLOPHUS_DEATH = registerSoundEvent("parasaurolophus_death");

    public static final Supplier<SoundEvent> PROCERATOSAURUS_CALL = registerSoundEvent("proceratosaurus_call");
    public static final Supplier<SoundEvent> PROCERATOSAURUS_HURT = registerSoundEvent("proceratosaurus_hurt");
    public static final Supplier<SoundEvent> PROCERATOSAURUS_DEATH = registerSoundEvent("proceratosaurus_death");

    public static final Supplier<SoundEvent> PROCOMPSOGNATHUS_CALL = registerSoundEvent("procompsognathus_call");
    public static final Supplier<SoundEvent> PROCOMPSOGNATHUS_HURT = registerSoundEvent("procompsognathus_hurt");
    public static final Supplier<SoundEvent> PROCOMPSOGNATHUS_DEATH = registerSoundEvent("procompsognathus_death");

    public static final Supplier<SoundEvent> PROTOCERATOPS_CALL = registerSoundEvent("protoceratops_call");
    public static final Supplier<SoundEvent> PROTOCERATOPS_HURT = registerSoundEvent("protoceratops_hurt");
    public static final Supplier<SoundEvent> PROTOCERATOPS_DEATH = registerSoundEvent("protoceratops_death");

    public static final Supplier<SoundEvent> PTERANODON_CALL = registerSoundEvent("pteranodon_call");
    public static final Supplier<SoundEvent> PTERANODON_HURT = registerSoundEvent("pteranodon_hurt");
    public static final Supplier<SoundEvent> PTERANODON_DEATH = registerSoundEvent("pteranodon_death");

    public static final Supplier<SoundEvent> PTERODAUSTRO_CALL = registerSoundEvent("pterodaustro_call");
    public static final Supplier<SoundEvent> PTERODAUSTRO_HURT = registerSoundEvent("pterodaustro_hurt");
    public static final Supplier<SoundEvent> PTERODAUSTRO_DEATH = registerSoundEvent("pterodaustro_death");

    public static final Supplier<SoundEvent> QUETZALCOATLUS_CALL = registerSoundEvent("quetzalcoatlus_call");
    public static final Supplier<SoundEvent> QUETZALCOATLUS_HURT = registerSoundEvent("quetzalcoatlus_hurt");
    public static final Supplier<SoundEvent> QUETZALCOATLUS_DEATH = registerSoundEvent("quetzalcoatlus_death");

    public static final Supplier<SoundEvent> RAJASAURUS_CALL = registerSoundEvent("rajasaurus_call");
    public static final Supplier<SoundEvent> RAJASAURUS_HURT = registerSoundEvent("rajasaurus_hurt");
    public static final Supplier<SoundEvent> RAJASAURUS_DEATH = registerSoundEvent("rajasaurus_death");

    public static final Supplier<SoundEvent> RUGOPS_CALL = registerSoundEvent("rugops_call");
    public static final Supplier<SoundEvent> RUGOPS_HURT = registerSoundEvent("rugops_hurt");
    public static final Supplier<SoundEvent> RUGOPS_DEATH = registerSoundEvent("rugops_death");

    public static final Supplier<SoundEvent> SEGISAURUS_CALL = registerSoundEvent("segisaurus_call");
    public static final Supplier<SoundEvent> SEGISAURUS_HURT = registerSoundEvent("segisaurus_hurt");
    public static final Supplier<SoundEvent> SEGISAURUS_DEATH = registerSoundEvent("segisaurus_death");

    public static final Supplier<SoundEvent> SHANTUNGOSAURUS_CALL = registerSoundEvent("shantungosaurus_call");
    public static final Supplier<SoundEvent> SHANTUNGOSAURUS_HURT = registerSoundEvent("shantungosaurus_hurt");
    public static final Supplier<SoundEvent> SHANTUNGOSAURUS_DEATH = registerSoundEvent("shantungosaurus_death");

    public static final Supplier<SoundEvent> SPINOSAURUS_CALL = registerSoundEvent("spinosaurus_call");
    public static final Supplier<SoundEvent> SPINOSAURUS_ATTACK = registerSoundEvent("spinosaurus_attack");
    public static final Supplier<SoundEvent> SPINOSAURUS_HURT = registerSoundEvent("spinosaurus_hurt");
    public static final Supplier<SoundEvent> SPINOSAURUS_DEATH = registerSoundEvent("spinosaurus_death");

    public static final Supplier<SoundEvent> STEGOSAURUS_CALL = registerSoundEvent("stegosaurus_call");
    public static final Supplier<SoundEvent> STEGOSAURUS_HURT = registerSoundEvent("stegosaurus_hurt");
    public static final Supplier<SoundEvent> STEGOSAURUS_DEATH = registerSoundEvent("stegosaurus_death");

    public static final Supplier<SoundEvent> STYRACOSAURUS_CALL = registerSoundEvent("styracosaurus_call");
    public static final Supplier<SoundEvent> STYRACOSAURUS_HURT = registerSoundEvent("styracosaurus_hurt");
    public static final Supplier<SoundEvent> STYRACOSAURUS_DEATH = registerSoundEvent("styracosaurus_death");

    public static final Supplier<SoundEvent> TAPEJARA_CALL = registerSoundEvent("tapejara_call");
    public static final Supplier<SoundEvent> TAPEJARA_HURT = registerSoundEvent("tapejara_hurt");
    public static final Supplier<SoundEvent> TAPEJARA_DEATH = registerSoundEvent("tapejara_death");

    public static final Supplier<SoundEvent> THERIZINOSAURUS_CALL = registerSoundEvent("therizinosaurus_call");
    public static final Supplier<SoundEvent> THERIZINOSAURUS_HURT = registerSoundEvent("therizinosaurus_hurt");
    public static final Supplier<SoundEvent> THERIZINOSAURUS_DEATH = registerSoundEvent("therizinosaurus_death");

    public static final Supplier<SoundEvent> TITANOSAURUS_CALL = registerSoundEvent("titanosaurus_call");
    public static final Supplier<SoundEvent> TITANOSAURUS_HURT = registerSoundEvent("titanosaurus_hurt");
    public static final Supplier<SoundEvent> TITANOSAURUS_DEATH = registerSoundEvent("titanosaurus_death");

    public static final Supplier<SoundEvent> TRICERATOPS_CALL = registerSoundEvent("triceratops_call");
    public static final Supplier<SoundEvent> TRICERATOPS_HURT = registerSoundEvent("triceratops_hurt");
    public static final Supplier<SoundEvent> TRICERATOPS_DEATH = registerSoundEvent("triceratops_death");

    public static final Supplier<SoundEvent> TROODON_CALL = registerSoundEvent("troodon_call");
    public static final Supplier<SoundEvent> TROODON_HURT = registerSoundEvent("troodon_hurt");
    public static final Supplier<SoundEvent> TROODON_DEATH = registerSoundEvent("troodon_death");

    public static final Supplier<SoundEvent> TROPEOGNATHUS_CALL = registerSoundEvent("tropeognathus_call");
    public static final Supplier<SoundEvent> TROPEOGNATHUS_HURT = registerSoundEvent("tropeognathus_hurt");
    public static final Supplier<SoundEvent> TROPEOGNATHUS_DEATH = registerSoundEvent("tropeognathus_death");

    public static final Supplier<SoundEvent> TUPUXUARA_CALL = registerSoundEvent("tupuxuara_call");
    public static final Supplier<SoundEvent> TUPUXUARA_HURT = registerSoundEvent("tupuxuara_hurt");
    public static final Supplier<SoundEvent> TUPUXUARA_DEATH = registerSoundEvent("tupuxuara_death");

    public static final Supplier<SoundEvent> TYRANNOSAURUS_REX_CALL = registerSoundEvent("tyrannosaurus_rex_call");
    public static final Supplier<SoundEvent> TYRANNOSAURUS_REX_ATTACK = registerSoundEvent("tyrannosaurus_rex_attack");
    public static final Supplier<SoundEvent> TYRANNOSAURUS_REX_HURT = registerSoundEvent("tyrannosaurus_rex_hurt");
    public static final Supplier<SoundEvent> TYRANNOSAURUS_REX_DEATH = registerSoundEvent("tyrannosaurus_rex_death");

    public static final Supplier<SoundEvent> UTAHRAPTOR_CALL = registerSoundEvent("utahraptor_call");
    public static final Supplier<SoundEvent> UTAHRAPTOR_HURT = registerSoundEvent("utahraptor_hurt");
    public static final Supplier<SoundEvent> UTAHRAPTOR_DEATH = registerSoundEvent("utahraptor_death");

    public static final Supplier<SoundEvent> VELOCIRAPTOR_CALL = registerSoundEvent("velociraptor_call");
    public static final Supplier<SoundEvent> VELOCIRAPTOR_ATTACK = registerSoundEvent("velociraptor_attack");
    public static final Supplier<SoundEvent> VELOCIRAPTOR_HURT = registerSoundEvent("velociraptor_hurt");
    public static final Supplier<SoundEvent> VELOCIRAPTOR_DEATH = registerSoundEvent("velociraptor_death");

    public static final Supplier<SoundEvent> ZHENYUANOPTERUS_CALL = registerSoundEvent("zhenyuanopterus_call");
    public static final Supplier<SoundEvent> ZHENYUANOPTERUS_HURT = registerSoundEvent("zhenyuanopterus_hurt");
    public static final Supplier<SoundEvent> ZHENYUANOPTERUS_DEATH = registerSoundEvent("zhenyuanopterus_death");


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = Constants.rl(name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        SOUND_EVENTS.register();
    }
}
