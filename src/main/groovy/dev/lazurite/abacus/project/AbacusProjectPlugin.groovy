package dev.lazurite.abacus.project

import dev.lazurite.abacus.AbacusPlugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginInstantiationException
import org.gradle.jvm.toolchain.JavaLanguageVersion

final class AbacusProjectPlugin extends AbacusPlugin<Project> {

    @Override
    void apply(Project target) {
        // abacus expects loom to be applied to the project first
        if (!target.pluginManager.hasPlugin('fabric-loom')) throw new PluginInstantiationException('Plugin \'fabric-loom\' must be applied to project before \'dev.lazurite.abacus\'!')

        // register the project extension
        target.extensions.create('abacus', AbacusProjectExtension)

        // configure the project
        configureProject(target)
    }

    private void configureProject(Project target) {
        target.group 'dev.lazurite'

        target.java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(16)
            }

            withSourcesJar()
        }

        target.jar {
            from 'LICENSE'
        }

        target.dependencies {
            mappings target.loom.officialMojangMappings()
            minecraft "com.mojang:minecraft:" + ABACUS_PROPERTIES.get('minecraftVersion').get()
            modImplementation "net.fabricmc:fabric-loader:" + ABACUS_PROPERTIES.get('fabricLoaderVersion').get()
        }
    }

}
