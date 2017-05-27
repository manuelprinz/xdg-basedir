package de.pinguinkiste.xdg.basedir

import org.valid4j.errors.RequireViolation
import spock.lang.Specification

class EnvironmentSpec extends Specification {

    void 'Passing a null value to valueOf() should throw an exception'() {
        when:
        new Environment().valueOf(null)

        then:
        def exception = thrown(RequireViolation)
        exception.message =~ /but: was null/
    }
}
