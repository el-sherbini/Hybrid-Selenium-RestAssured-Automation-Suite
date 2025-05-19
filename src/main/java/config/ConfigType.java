package config;

public enum ConfigType {
    UI("ui/config.properties"),
    API("api/config.properties");

    private final String path;

    ConfigType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
