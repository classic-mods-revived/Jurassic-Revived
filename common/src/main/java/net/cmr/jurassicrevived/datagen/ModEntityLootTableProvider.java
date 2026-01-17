package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModEntityLootTableProvider {

    public interface EntityLootHelper {
        void add(EntityType<?> type, LootTable.Builder builder);
    }

    public static void registerEntityLootTables(EntityLootHelper helper) {
        helper.add(ModEntities.FDUCK.get(), LootTable.lootTable());
        helper.add(ModEntities.CHICKENOSAURUS.get(), LootTable.lootTable());

        // Albertosaurus
        helper.add(ModEntities.ALBERTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ALBERTOSAURUS_SKULL.get()))));

// Apatosaurus
        helper.add(ModEntities.APATOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_APATOSAURUS_SKULL.get()))));

// Baryonyx
        helper.add(ModEntities.BARYONYX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_BARYONYX_SKULL.get()))));

// Brachiosaurus
        helper.add(ModEntities.BRACHIOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_BRACHIOSAURUS_SKULL.get()))));

// Carnotaurus
        helper.add(ModEntities.CARNOTAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CARNOTAURUS_SKULL.get()))));

// Ceratosaurus
        helper.add(ModEntities.CERATOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CERATOSAURUS_SKULL.get()))));

// Compsognathus
        helper.add(ModEntities.COMPSOGNATHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_COMPSOGNATHUS_SKULL.get()))));

// Concavenator
        helper.add(ModEntities.CONCAVENATOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CONCAVENATOR_SKULL.get()))));

// Deinonychus
        helper.add(ModEntities.DEINONYCHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DEINONYCHUS_SKULL.get()))));

// Dilophosaurus
        helper.add(ModEntities.DILOPHOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DILOPHOSAURUS_SKULL.get()))));

// Diplodocus
        helper.add(ModEntities.DIPLODOCUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DIPLODOCUS_SKULL.get()))));

// D Rex -> Distortus Rex
        helper.add(ModEntities.DISTORTUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DISTORTUS_REX_SKULL.get()))));

// Edmontosaurus
        helper.add(ModEntities.EDMONTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_EDMONTOSAURUS_SKULL.get()))));

// Gallimimus
        helper.add(ModEntities.GALLIMIMUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GALLIMIMUS_SKULL.get()))));

// Giganotosaurus
        helper.add(ModEntities.GIGANOTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GIGANOTOSAURUS_SKULL.get()))));

// Guanlong
        helper.add(ModEntities.GUANLONG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GUANLONG_SKULL.get()))));

// Herrerasaurus
        helper.add(ModEntities.HERRERASAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_HERRERASAURUS_SKULL.get()))));

// Indominus Rex
        helper.add(ModEntities.INDOMINUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_INDOMINUS_REX_SKULL.get()))));

// Majungasaurus
        helper.add(ModEntities.MAJUNGASAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_MAJUNGASAURUS_SKULL.get()))));

// Ouranosaurus
        helper.add(ModEntities.OURANOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_OURANOSAURUS_SKULL.get()))));

// Parasaurolophus
        helper.add(ModEntities.PARASAUROLOPHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get()))));

// Procompsognathus
        helper.add(ModEntities.PROCOMPSOGNATHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PROCOMPSOGNATHUS_SKULL.get()))));

// Protoceratops
        helper.add(ModEntities.PROTOCERATOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PROTOCERATOPS_SKULL.get()))));

// Rugops
        helper.add(ModEntities.RUGOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_RUGOPS_SKULL.get()))));

// Shantungosaurus
        helper.add(ModEntities.SHANTUNGOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_SHANTUNGOSAURUS_SKULL.get()))));

// Spinosaurus
        helper.add(ModEntities.SPINOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_SPINOSAURUS_SKULL.get()))));

// Stegosaurus
        helper.add(ModEntities.STEGOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_STEGOSAURUS_SKULL.get()))));

// Styracosaurus
        helper.add(ModEntities.STYRACOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_STYRACOSAURUS_SKULL.get()))));

// Therizinosaurus
        helper.add(ModEntities.THERIZINOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_THERIZINOSAURUS_SKULL.get()))));

// Triceratops
        helper.add(ModEntities.TRICERATOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TRICERATOPS_SKULL.get()))));

// Tyrannosaurus Rex
        helper.add(ModEntities.TYRANNOSAURUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get()))));

// Velociraptor
        helper.add(ModEntities.VELOCIRAPTOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_VELOCIRAPTOR_SKULL.get()))));

        helper.add(ModEntities.ALLOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ALLOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.ALVAREZSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ALVAREZSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.ANKYLOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ANKYLOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.ARAMBOURGIANIA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ARAMBOURGIANIA_SKULL.get()))
                )
        );

        helper.add(ModEntities.CARCHARODONTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CARCHARODONTOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.CEARADACTYLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CEARADACTYLUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.CHASMOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CHASMOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.COELOPHYSIS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_COELOPHYSIS_SKULL.get()))
                )
        );

        helper.add(ModEntities.COELURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_COELURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.CORYTHOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CORYTHOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.DIMORPHODON.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DIMORPHODON_SKULL.get()))
                )
        );

        helper.add(ModEntities.DRYOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DRYOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.GEOSTERNBERGIA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GEOSTERNBERGIA_SKULL.get()))
                )
        );

        helper.add(ModEntities.GUIDRACO.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GUIDRACO_SKULL.get()))
                )
        );

        helper.add(ModEntities.HADROSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_HADROSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.HYPSILOPHODON.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_HYPSILOPHODON_SKULL.get()))
                )
        );

        helper.add(ModEntities.INDORAPTOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_INDORAPTOR_SKULL.get()))
                )
        );

        helper.add(ModEntities.INOSTRANCEVIA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_INOSTRANCEVIA_SKULL.get()))
                )
        );

        helper.add(ModEntities.LAMBEOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_LAMBEOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.LUDODACTYLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_LUDODACTYLUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.MAMENCHISAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_MAMENCHISAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.METRIACANTHOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_METRIACANTHOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.MOGANOPTERUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_MOGANOPTERUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.NYCTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_NYCTOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.ORNITHOLESTES.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ORNITHOLESTES_SKULL.get()))
                )
        );

        helper.add(ModEntities.ORNITHOMIMUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ORNITHOMIMUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.OVIRAPTOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_OVIRAPTOR_SKULL.get()))
                )
        );

        helper.add(ModEntities.PACHYCEPHALOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PACHYCEPHALOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.PROCERATOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PROCERATOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.PTERANODON.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PTERANODON_SKULL.get()))
                )
        );

        helper.add(ModEntities.PTERODAUSTRO.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PTERODAUSTRO_SKULL.get()))
                )
        );

        helper.add(ModEntities.QUETZALCOATLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_QUETZALCOATLUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.RAJASAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_RAJASAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.SEGISAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_SEGISAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.TAPEJARA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TAPEJARA_SKULL.get()))
                )
        );

        helper.add(ModEntities.TITANOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TITANOSAURUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.TROODON.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TROODON_SKULL.get()))
                )
        );

        helper.add(ModEntities.TROPEOGNATHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TROPEOGNATHUS_SKULL.get()))
                )
        );

        helper.add(ModEntities.TUPUXUARA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TUPUXUARA_SKULL.get()))
                )
        );

        helper.add(ModEntities.UTAHRAPTOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_UTAHRAPTOR_SKULL.get()))
                )
        );

        helper.add(ModEntities.ZHENYUANOPTERUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ZHENYUANOPTERUS_SKULL.get()))
                )
        );
    }
}
