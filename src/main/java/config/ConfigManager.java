package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    private static final Map<ConfigType, Properties> configCache = new EnumMap<>(ConfigType.class);

    public static String getProperty(ConfigType type, String key) {
        Properties props = configCache.computeIfAbsent(type, ConfigManager::loadProperties);
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property '" + key + "' not found in " + type.name() + " config file.");
        }
        return value;
    }

    private static Properties loadProperties(ConfigType type) {
        Properties props = new Properties();
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(type.getPath())) {
            if (is == null) {
                throw new IOException("Configuration file not found: " + type.getPath());
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + type.getPath(), e);
        }
        return props;
    }
}
