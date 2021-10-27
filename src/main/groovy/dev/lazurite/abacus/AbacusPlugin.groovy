package dev.lazurite.abacus

import dev.lazurite.abacus.project.AbacusProjectPlugin
import dev.lazurite.abacus.settings.AbacusSettingsPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

abstract class AbacusPlugin<T> implements Plugin<T> {

    protected static final def ABACUS_PROPERTIES = new HashMap<String, String>()

    @Override
    void apply(T t) {
        if (t instanceof Settings) new AbacusSettingsPlugin().apply(t as Settings);
        else if (t instanceof Project) new AbacusProjectPlugin().apply(t as Project);
    }

}
