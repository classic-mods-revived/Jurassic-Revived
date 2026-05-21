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
                eb.startBooleanToggle(Component.literal("Require Power"), cfg.requirePower)
                        .setDefaultValue(false)
                        .setTooltip(Component.literal("When disabled, machines do not consume power, hide power bars, and energy pipes do not connect to machines."))
                        .setSaveConsumer(v -> cfg.requirePower = v)
						.requireRestart()
                        .build()
        );

        general.addEntry(
                eb.startBooleanToggle(Component.literal("Naturally Spawning Dinosaurs"), cfg.naturallySpawning)
                        .setDefaultValue(false)
                        .setTooltip(Component.literal("Controls whether dinosaurs naturally spawn once entity spawning is implemented."))
                        .setSaveConsumer(v -> cfg.naturallySpawning = v)
						.requireRestart()
                        .build()
        );

        general.addEntry(
                eb.startIntField(Component.literal("FE Per Second"), cfg.fePerSecond)
                        .setDefaultValue(1000)
                        .setMin(1)
                        .setMax(Integer.MAX_VALUE)
                        .setTooltip(Component.literal("Energy pipe transfer rate in FE per second."))
                        .setSaveConsumer(v -> cfg.fePerSecond = Math.max(1, v))
						.requireRestart()
                        .build()
        );

        general.addEntry(
                eb.startIntField(Component.literal("Items Per Second"), cfg.itemsPerSecond)
                        .setDefaultValue(100)
                        .setMin(1)
                        .setMax(Integer.MAX_VALUE)
                        .setTooltip(Component.literal("Item pipe transfer rate in items per second."))
                        .setSaveConsumer(v -> cfg.itemsPerSecond = Math.max(1, v))
						.requireRestart()
                        .build()
        );

        general.addEntry(
                eb.startIntField(Component.literal("MilliBuckets Per Second"), cfg.milliBucketsPerSecond)
                        .setDefaultValue(1000)
                        .setMin(1)
                        .setMax(Integer.MAX_VALUE)
                        .setTooltip(Component.literal("Fluid pipe transfer rate in milliBuckets per second."))
                        .setSaveConsumer(v -> cfg.milliBucketsPerSecond = Math.max(1, v))
						.requireRestart()
                        .build()
        );

        builder.setSavingRunnable(() -> JRConfigManager.save(Services.PLATFORM.getConfigDir()));

        return builder.build();
    }
}
