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

    void 'When XDG_CONFIG_HOME is set, return its value'() {
        given:
        def env = Stub(Environment) {
            valueOf('XDG_CONFIG_HOME') >> Optional.of('/some/arbitrary/directory')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userConfigDir == '/some/arbitrary/directory'
    }

    void 'When XDG_CONFIG_HOME is not set, return default value ($HOME/.config)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_CONFIG_HOME') >> Optional.empty()
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userConfigDir == '/foo/bar/.config'
    }

    void 'When XDG_CONFIG_HOME is empty, return default value ($HOME/.config)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_CONFIG_HOME') >> Optional.of('')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userConfigDir == '/foo/bar/.config'
    }

    void 'When XDG_CACHE_HOME is set, return its value'() {
        given:
        def env = Stub(Environment) {
            valueOf('XDG_CACHE_HOME') >> Optional.of('/some/arbitrary/directory')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userCacheDir == '/some/arbitrary/directory'
    }

    void 'When XDG_CACHE_HOME is not set, return default value ($HOME/.cache)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_CACHE_HOME') >> Optional.empty()
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userCacheDir == '/foo/bar/.cache'
    }

    void 'When XDG_CACHE_HOME is empty, return default value ($HOME/.cache)'() {
        given:
        def env = Stub(Environment) {
            valueOf('HOME') >> Optional.of('/foo/bar')
            valueOf('XDG_CACHE_HOME') >> Optional.of('')
        }
        baseDir = new BaseDir(env)

        expect:
        baseDir.userCacheDir == '/foo/bar/.cache'
    }
}
