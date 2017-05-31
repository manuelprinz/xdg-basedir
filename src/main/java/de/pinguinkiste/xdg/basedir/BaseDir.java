/*
 * Copyright © 2017 Manuel Prinz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.pinguinkiste.xdg.basedir;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Basic accessor class to the base directories defined in the XDG Base Directory Specification.
 *
 * <p>This class provides simple access to the base directories in accordance with the spec. It only
 * provides the paths as {@link String}s. There is no further check for existence of the paths, nor
 * any form of error checking considering e.g. permissions as defined in the spec.
 */
public class BaseDir {

  /** An instance of a wrapper to retrieve the values of environment variables. */
  private final Environment environment;

  /** Create a new instance of the accessor class. */
  public BaseDir() {
    this(new Environment());
  }

  /**
   * Create a new instance of the accessor class using a custom environment wrapper.
   *
   * <p>This constructor is supposed to be only used as a seam for testing.
   *
   * @param environment An alternative implementation of the environment wrapper for testing (stub).
   */
  BaseDir(Environment environment) {
    this.environment = environment;
  }

  /**
   * Get the base directory relative to which user-specific data files should be written.
   *
   * @return The value of <code>XDG_DATA_HOME</code> if set or non-empty, <code>$HOME/.local/share
   *     </code> otherwise.
   */
  public String getUserDataDir() {
    return defaultValueIfNotSet("XDG_DATA_HOME", ".local/share");
  }

  /**
   * Get the base directory relative to which user-specific configuration files should be written.
   *
   * @return The value of <code>XDG_CONFIG_HOME</code> if set or non-empty, <code>$HOME/.config
   *     </code> otherwise.
   */
  public String getUserConfigDir() {
    return defaultValueIfNotSet("XDG_CONFIG_HOME", ".config");
  }

  /**
   * Get the base directory relative to which user-specific non-essential (cached) data should be
   * written.
   *
   * @return The value of <code>XDG_CACHE_HOME</code> if set or non-empty, <code>$HOME/.cache</code>
   *     otherwise.
   */
  public String getUserCacheDir() {
    return defaultValueIfNotSet("XDG_CACHE_HOME", ".cache");
  }

  /**
   * Get the base directories relative to which data files should be searched (preference-ordered).
   *
   * @return The list of directories in <code>XDG_DATA_DIRS</code> (colon-separated) if set or
   *     non-empty, or a list containing <code>["/usr/local/share", "/usr/share"]</code> otherwise.
   */
  public List<String> getDataDirs() {
    return defaultListIfNotSet("XDG_DATA_DIRS", "/usr/local/share:/usr/share");
  }

  /**
   * Get the base directories relative to which configuration files should be searched
   * (preference-ordered).
   *
   * @return The list of directories in <code>XDG_CONFIG_DIRS</code> (colon-separated) if set or
   *     non-empty, or a list containing <code>["/etc/xdg"]</code> otherwise.
   */
  public List<String> getConfigDirs() {
    return defaultListIfNotSet("XDG_CONFIG_DIRS", "/etc/xdg");
  }

  /**
   * Helper method to retrieve the value of the <code>HOME</code> environment variable.
   *
   * @throws IllegalStateException Thrown if <code>HOME</code> is not set. This should never happen
   *     in environments that run "desktop" applications as it is set on login.
   * @return The value of the <code>HOME</code> environment variable.
   */
  @NotNull
  private String getValueOfHome() throws IllegalStateException {
    Optional<String> home = environment.valueOf("HOME");
    if (!home.isPresent()) {
      throw new IllegalStateException(
          "The HOME environment variable is not set. This should not happen.");
    }
    return home.get();
  }

  /**
   * Helper method that constructs and returns the default value for the given environment variable
   * if that is not set or empty.
   *
   * @param envVar The environment variable to check.
   * @param postfix The postfix for the default value. It is prepended with the value of the <code>
   *     HOME</code> environment variable.
   * @return The value of <code>$HOME/$postfix</code> if {@code envVar} is not set or empty, or the
   *     value of {@code envVar} otherwise.
   */
  private String defaultValueIfNotSet(String envVar, String postfix) {
    String value = environment.valueOf(envVar).orElse("");
    if (!value.equals("")) {
      return value;
    }
    return getValueOfHome() + "/" + postfix;
  }

  /**
   * Helper method that constructs and returns the list of default paths for the given environment
   * variable if that is not set or empty.
   *
   * @param envVar The environment variable to check.
   * @param defaultList The default list of values (colon-separated).
   * @return The {@link List} of default paths in the order they were provided.
   */
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
  private boolean isNotEmptyString(@Nullable String string) {
    return !Objects.equals(string, "");
  }

  /**
   * Helper method to parse a {@link String} into a {@link List} of paths. Empty values are filtered
   * from the list.
   *
   * @param string The list of paths as a colon-separated {@link String}.
   * @return The {@link List} of paths in the order given without empty values, or an empty list if
   *     no valid entries were found.
   */
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
