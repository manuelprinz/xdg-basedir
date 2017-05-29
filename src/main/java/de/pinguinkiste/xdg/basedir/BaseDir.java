package de.pinguinkiste.xdg.basedir;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    return defaultListIfNotSet("XDG_DATA_DIRS", "/usr/local/share:/usr/share");
  }

  public List<String> getConfigDirs() {
    return defaultListIfNotSet("XDG_CONFIG_DIRS", "/etc/xdg");
  }

  @NotNull
  private String getValueOfHome() {
    Optional<String> home = environment.valueOf("HOME");
    if (!home.isPresent()) {
      throw new IllegalStateException(
          "The HOME environment variable is not set. This should not happen.");
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

  @NotNull
  private List<String> defaultListIfNotSet(String envVar, String defaultList) {
    Optional<String> dataDirsValue = environment.valueOf(envVar);
    String dataDirs = defaultList;
    if (dataDirsValue.isPresent() && isNotEmptyString(dataDirsValue.get())) {
      dataDirs = dataDirsValue.get();
    }
    return listFromColonSeparatedString(dataDirs);
  }

  @Contract(pure = true)
  private boolean isNotEmptyString(String string) {
    return !string.equals("");
  }

  @NotNull
  private List<String> listFromColonSeparatedString(@NotNull String string) {
    List<String> result = new ArrayList<>();
    for (String element : string.split(":")) {
      if (isNotEmptyString(element)) {
        result.add(element);
      }
    }
    return unmodifiableList(result);
  }
}
