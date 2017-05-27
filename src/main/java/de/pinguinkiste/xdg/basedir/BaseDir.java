package de.pinguinkiste.xdg.basedir;

import java.util.Optional;

public class BaseDir {

    private final Environment environment;

    public BaseDir() {
        this(new Environment());
    }

    protected BaseDir(Environment environment) {
        this.environment = environment;
    }

    public String getUserDataDir() {
        return defaultValueIfNotSet("XDG_DATA_HOME", ".local/share");
    }

    public String getUserConfigDir() {
        return defaultValueIfNotSet("XDG_CONFIG_HOME", ".config");
    }

    private String getValueOfHome() {
        Optional<String> home = environment.valueOf("HOME");
        if (!home.isPresent()) {
            throw new IllegalStateException("The HOME environment variable is not set. This should not happen.");
        }
        return home.get();
    }

    private String defaultValueIfNotSet(String envVar, String postfix) {
        String value = environment.valueOf(envVar).orElse("");
        if (!value.equals("")) {
            return value;
        }
        return getValueOfHome() + "/" + postfix;
    }
}
