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

package de.pinguinkiste.xdg.basedir

import spock.lang.Specification

class EnvironmentSpec extends Specification {

    static final String DEFINED_ENV_VAR = 'HOME'
    static final String UNDEFINED_ENV_VAR = '_NON_EXISTING_ENV_VAR'

    void 'Retrieving the value of an environment variable that is set should return that value'() {
        given:
        def env = new Environment()

        expect: 'environment variable is defined (pre-condition)'
        System.getenv(DEFINED_ENV_VAR) != null

        when:
        def result = env.valueOf(DEFINED_ENV_VAR)

        then:
        result.isPresent()
        result.get() == System.getenv(DEFINED_ENV_VAR)
    }

    void 'Retrieving the value of an environment variable that is not set should return an empty value'() {
        given:
        def env = new Environment()

        expect: 'environment variable is not defined (pre-condition)'
        System.getenv(UNDEFINED_ENV_VAR) == null

        when:
        def result = env.valueOf(UNDEFINED_ENV_VAR)

        then:
        !result.isPresent()
    }

    void 'Passing a null value to valueOf() should throw an exception'() {
        when:
        new Environment().valueOf(null)

        then:
        thrown(NullPointerException)
    }
}
