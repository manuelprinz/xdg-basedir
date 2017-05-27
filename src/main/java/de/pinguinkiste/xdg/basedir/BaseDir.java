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
        String dataHome = environment.valueOf("XDG_DATA_HOME").orElse("");
        if (!dataHome.equals("")) {
            return dataHome;
        }
        return getValueOfHome() + "/.local/share";
    }

    public String getUserConfigDir() {
        String dataHome = environment.valueOf("XDG_CONFIG_HOME").orElse("");
        if (!dataHome.equals("")) {
            return dataHome;
        }
        return getValueOfHome() + "/.config";
    }

    private String getValueOfHome() {
        Optional<String> home = environment.valueOf("HOME");
        if (!home.isPresent()) {
            throw new IllegalStateException("The HOME environment variable is not set. This should not happen.");
        }
        return home.get();
    }
}
