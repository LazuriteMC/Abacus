package dev.lazurite.abacus

import dev.lazurite.abacus.project.AbacusProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginAware

final class AbacusPlugin implements Plugin<PluginAware> {

    // Was used to delegate apply to either a gradle, settings, or project object.
    // Keeping here because I'm still testing with that.
    @Override
    void apply(PluginAware pluginAware) {
        new AbacusProjectPlugin().apply(pluginAware as Project);
    }

}
