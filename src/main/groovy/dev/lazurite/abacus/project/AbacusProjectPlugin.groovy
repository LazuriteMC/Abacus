package dev.lazurite.abacus.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

final class AbacusProjectPlugin implements Plugin<Project> {

    private Map<String, String> abacusProperties = new HashMap<>()

    @Override
    void apply(Project target) {
        // extension... will implement later
//        target.extensions.create('abacus', AbacusProjectExtension)

        setProperties(target)
        applyPlugins(target)
        setupProject(target)
        processResources(target)
        setupPublishing(target)
    }

    private void setProperties(Project project) {
        for (property in project.properties.findAll { (it.key as String).startsWith 'abacus' } ) {
            String[] splits = (property.key as String).split '_'

            // splits.length != 3 because 'abacus_version' (yes I hate it too)
            // matches all 'abacus_projectName_propertyName' and 'abacus_parentProjectName_propertyName'
            // only supports subproject depth of 1
            if (splits.length != 3 || project.name != splits[1] || (project.parent != null && project.parent.name != splits[1])) continue

            if (project.hasProperty(splits[2])) {
                abacusProperties.put splits[2], property.value
                project.setProperty splits[2], property.value
            }
        }
    }

    private void applyPlugins(Project target) {
        // I can't seem to apply third-party plugins (loom)
        // to external build scripts/plugins (this).
        // Instead I'll apply the plugins from loom that I need here.
        // Note that abacus still makes the assumption that loom has already been applied.
        // This isn't safe in any way but makes me slightly happier.
        target.apply plugin: 'java-library'
        target.apply plugin: 'maven-publish'
    }

    private void setupProject(Project target) {
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
            // potential kaboom point
            mappings target.loom.officialMojangMappings()
            minecraft "com.mojang:minecraft:" + target.property('abacus_minecraft_version')
            modImplementation "net.fabricmc:fabric-loader:" + target.property('abacus_loader_version')
        }
    }

    private void processResources(Project target) {
        target.afterEvaluate {
            target.sourceSets.each {
                final def sourceSetName = (it.name == 'main') ? '' : it.name.capitalize()

                target.tasks.getByName('process' + sourceSetName + 'Resources') {
                    // declare input properties before expanding
                    for (prop in abacusProperties) {
                        inputs.property prop.key, prop.value
                    }

                    for (prop in abacusProperties) {
                        filesMatching 'fabric.mod.json', {
                            expand Map.of(prop.key, prop.value)
                        }
                    }
                }
            }
        }
    }

    private void setupPublishing(Project target) {
        final def lazuriteMavenUsername = 'abacus_lazurite_maven_username'
        final def lazuriteMavenPassword = 'abacus_lazurite_maven_password'

        if (target.hasProperty(lazuriteMavenUsername) && target.hasProperty(lazuriteMavenPassword)) {
            target.publishing.repositories {
                maven {
                    name 'Lazurite'
                    url 'https://lazurite.dev/maven/releases'

                    credentials {
                        username target.property(lazuriteMavenUsername)
                        password target.property(lazuriteMavenPassword)
                    }
                }
            }
        }
    }

}
