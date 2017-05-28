package de.pinguinkiste.xdg.basedir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

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

    public String getUserCacheDir() {
        return defaultValueIfNotSet("XDG_CACHE_HOME", ".cache");
    }

    public List<String> getDataDirs() {
        Optional<String> dataDirsValue = environment.valueOf("XDG_DATA_DIRS");
        String dataDirs = "/usr/local/share:/usr/share";
        if (dataDirsValue.isPresent() && isNotEmptyString(dataDirsValue.get())) {
            dataDirs = dataDirsValue.get();
        }
        return listFromColonSeparatedString(dataDirs);
    }

    public List<String> getConfigDirs() {
        Optional<String> configDirsValue = environment.valueOf("XDG_CONFIG_DIRS");
        String dataDirs = "/etc/xdg";
        if (configDirsValue.isPresent() && isNotEmptyString(configDirsValue.get())) {
            dataDirs = configDirsValue.get();
        }
        return listFromColonSeparatedString(dataDirs);
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

    private boolean isNotEmptyString(String string) {
        return !string.equals("");
    }

    private List<String> listFromColonSeparatedString(String string) {
        List<String> result = new ArrayList<>();
        for (String element : string.split(":")) {
            if (isNotEmptyString(element))
                result.add(element);
        }
        return unmodifiableList(result);
    }
}
