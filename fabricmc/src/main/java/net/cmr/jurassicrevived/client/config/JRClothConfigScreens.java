package net.cmr.jurassicrevived.client.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.cmr.jurassicrevived.config.JRConfig;
import net.cmr.jurassicrevived.config.JRConfigManager;
import net.cmr.jurassicrevived.platform.Services;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class JRClothConfigScreens {
    private JRClothConfigScreens() {}

    public static Screen create(Screen parent) {
        JRConfig cfg = JRConfigManager.get();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Jurassic Revived Config"));

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        ConfigEntryBuilder eb = builder.entryBuilder();

        general.addEntry(
                eb.startBooleanToggle(Component.literal("Enable Dinosaurs"), cfg.enableDinosaurs)
                        .setDefaultValue(true)
                        .setSaveConsumer(v -> cfg.enableDinosaurs = v)
                        .build()
        );

        general.addEntry(
                eb.startIntField(Component.literal("Spawn Weight"), cfg.spawnWeight)
                        .setDefaultValue(10)
                        .setMin(0)
                        .setMax(1000)
                        .setSaveConsumer(v -> cfg.spawnWeight = v)
                        .build()
        );

        builder.setSavingRunnable(() -> JRConfigManager.save(Services.PLATFORM.getConfigDir()));

        return builder.build();
    }
}
