package net.cmr.jurassicrevived.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FabricModModelProvider::new);
        pack.addProvider(FabricBlockTagProvider::new);
        pack.addProvider(FabricItemTagProvider::new);
		pack.addProvider(FabricEntityTagProvider::new);
        pack.addProvider(FabricBlockLootTableProvider::new);
        pack.addProvider(FabricEntityLootTableProvider::new);
		pack.addProvider(FabricRecipeProvider::new);
		pack.addProvider((output, registriesFuture) -> new ModWorldgenProvider(output));
    }
}
