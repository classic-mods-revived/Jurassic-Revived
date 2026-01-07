package net.cmr.jurassicrevived.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JRConfigManager {
    private static JRConfig config = new JRConfig();

    private JRConfigManager() {}

    public static JRConfig get() {
        return config;
    }

    /**
     * Loader modules should call this with the platform's config directory.
     */
    public static void load(Path configDir) {
        Path file = configDir.resolve("jurassicrevived.json");
        if (!Files.exists(file)) {
            save(configDir);
            return;
        }

        // Minimal placeholder IO (swap to Gson/Jackson later if you want)
        // For now: keep it simple and non-fatal.
        try {
            String text = Files.readString(file, StandardCharsets.UTF_8);
            // TODO: parse JSON into config (Gson recommended)
            // config = parsed;
        } catch (IOException e) {
            // Keep defaults if load fails
        }
    }

    public static void save(Path configDir) {
        try {
            Files.createDirectories(configDir);
            Path file = configDir.resolve("jurassicrevived.json");

            // TODO: write JSON (Gson recommended)
            String text = "{\n" +
                    "  \"enableDinosaurs\": " + config.enableDinosaurs + ",\n" +
                    "  \"spawnWeight\": " + config.spawnWeight + "\n" +
                    "}\n";

            Files.writeString(file, text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // ignore / log via your logger if desired
        }
    }
}
