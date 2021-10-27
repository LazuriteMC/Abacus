package dev.lazurite.abacus.settings

import dev.lazurite.abacus.AbacusPlugin
import org.gradle.api.initialization.Settings

final class AbacusSettingsPlugin extends AbacusPlugin<Settings> {

    @Override
    void apply(Settings target) {
        target.extensions.create('abacus', AbacusSettingsExtension)

        target.gradle.settingsEvaluated {
            for (property in target.extensions.abacus.all) {
                ABACUS_PROPERTIES.put property, target.abacus.getProperty(property)
            }
        }
    }

}
