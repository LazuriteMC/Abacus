package dev.lazurite.abacus.settings

import dev.lazurite.abacus.AbacusExtension
import org.gradle.api.provider.Property

abstract class AbacusSettingsExtension extends AbacusExtension {
    abstract Property<String> getMinecraftVersion()
    abstract Property<String> getFabricLoaderVersion()
}
