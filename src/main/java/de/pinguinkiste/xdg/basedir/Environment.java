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

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Thin wrapper class to retrieve environment values.
 *
 * <p>This class is intended as a <em>seam</em> for testing and should be considered an
 * implementation detail. The concept of <em>seams</em> is introduced in the book "Working
 * Effectively with Legacy Code" by Michael Feathers.
 */
class Environment {
  /**
   * Wrapper method around {@link System#getenv(String)}.
   *
   * <p>It can be used by stub during testing.
   *
   * @param name The name of the environment variable. Must no be {@code null}.
   * @return An {@link Optional} containing the result of the lookup.
   */
  public Optional<String> valueOf(@NotNull String name) {
    return Optional.ofNullable(System.getenv(name));
  }
}
