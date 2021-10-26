package dev.lazurite.abacus.project

import org.gradle.api.provider.Property

interface AbacusProjectExtension  {
    Property<String> getMinecraftVersion()
    Property<String> getFabricLoaderVersion()
}
