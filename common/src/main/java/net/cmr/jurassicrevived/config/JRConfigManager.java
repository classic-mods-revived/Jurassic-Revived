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
            JRConfig loaded = new JRConfig();

            loaded.requirePower = readBoolean(text, "requirePower", loaded.requirePower);
            loaded.naturallySpawning = readBoolean(text, "naturallySpawning", loaded.naturallySpawning);
            loaded.hungerConsumption = readBoolean(text, "hungerConsumption", loaded.hungerConsumption);
            loaded.waterConsumption = readBoolean(text, "waterConsumption", loaded.waterConsumption);
            loaded.fePerSecond = readPositiveInt(text, "fePerSecond", loaded.fePerSecond);
            loaded.itemsPerSecond = readPositiveInt(text, "itemsPerSecond", loaded.itemsPerSecond);
            loaded.milliBucketsPerSecond = readPositiveInt(text, "milliBucketsPerSecond", loaded.milliBucketsPerSecond);

            config = loaded;
            save(configDir);
        } catch (IOException e) {
            // Keep defaults if load fails
        }
    }

    public static void save(Path configDir) {
        try {
            Files.createDirectories(configDir);
            Path file = configDir.resolve("jurassicrevived.json");

            String text = "{\n" +
                    "  \"requirePower\": " + config.requirePower + ",\n" +
                    "  \"naturallySpawning\": " + config.naturallySpawning + ",\n" +
                    "  \"hungerConsumption\": " + config.hungerConsumption + ",\n" +
                    "  \"waterConsumption\": " + config.waterConsumption + ",\n" +
                    "  \"fePerSecond\": " + config.fePerSecond + ",\n" +
                    "  \"itemsPerSecond\": " + config.itemsPerSecond + ",\n" +
                    "  \"milliBucketsPerSecond\": " + config.milliBucketsPerSecond + "\n" +
                    "}\n";

            Files.writeString(file, text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // ignore / log via your logger if desired
        }
    }

    private static boolean readBoolean(String text, String key, boolean fallback) {
        String value = readRawValue(text, key);
        if ("true".equalsIgnoreCase(value)) return true;
        if ("false".equalsIgnoreCase(value)) return false;
        return fallback;
    }

    private static int readPositiveInt(String text, String key, int fallback) {
        String value = readRawValue(text, key);
        if (value == null) return fallback;

        try {
            return Math.max(1, Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static String readRawValue(String text, String key) {
        String quotedKey = "\"" + key + "\"";
        int keyIndex = text.indexOf(quotedKey);
        if (keyIndex < 0) return null;

        int colonIndex = text.indexOf(':', keyIndex + quotedKey.length());
        if (colonIndex < 0) return null;

        int valueStart = colonIndex + 1;
        while (valueStart < text.length() && Character.isWhitespace(text.charAt(valueStart))) {
            valueStart++;
        }

        int valueEnd = valueStart;
        while (valueEnd < text.length()) {
            char c = text.charAt(valueEnd);
            if (c == ',' || c == '\n' || c == '\r' || c == '}') break;
            valueEnd++;
        }

        return text.substring(valueStart, valueEnd).trim();
    }
}
