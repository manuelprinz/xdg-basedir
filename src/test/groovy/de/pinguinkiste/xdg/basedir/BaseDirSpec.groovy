package de.pinguinkiste.xdg.basedir

import spock.lang.Specification

class BaseDirSpec extends Specification {

    def baseDir = new BaseDir()

    void 'When XDG_DATA_HOME is set, return its value'() {
        given:
        def env = Stub(Environment) {
            valueOf('XDG_DATA_HOME') >> Optional.of('/some/arbitrary/directory')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userDataDir == '/some/arbitrary/directory'
    }

    void 'When XDG_DATA_HOME is not set, return default value ($HOME/.local/share)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_DATA_HOME') >> Optional.empty()
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userDataDir == '/foo/bar/.local/share'
    }

    void 'When XDG_DATA_HOME is empty, return default value ($HOME/.local/share)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_DATA_HOME') >> Optional.of('')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userDataDir == '/foo/bar/.local/share'
    }

}
