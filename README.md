# Abacus

[![GitHub](https://img.shields.io/github/license/LazuriteMC/Abacus?color=A31F34&label=License&labelColor=8A8B8C)](https://github.com/LazuriteMC/Abacus/blob/main/LICENSE)
[![Discord](https://img.shields.io/discord/719662192601071747?color=7289DA&label=Discord&labelColor=2C2F33&logo=Discord)](https://discord.gg/NNPPHN7b3P)

The Lazurite Team's Gradle plugin. Requires [loom](https://github.com/FabricMC/fabric-loom) to be applied first.

## Features

When applied to the `settings.gradle`:

- Adds an `abacus` extension with the following properties:

    - `minecraftVersion`: Specifies the version of Minecraft to be applied as a `minecraft` dependency. See [here](https://fabricmc.net/versions.html) for version details.
    - `fabricLoaderVersion`: Specifies the version of the Fabric Loader to be applied as a `modImplementation` dependency. See [here](https://fabricmc.net/versions.html) for version details.

When applied to a `build.gradle`:

- Sets `group` to `'dev.lazurite'`.
- Sets `java.toolchain.languageVersion` to `JavaLanguageVersion.of(16)`.
- Adds `java.withSourcesJar()`.
- Adds `jar.from 'LICENSE'`.
- Applies `mappings loom.officialMojangMappings()` as a dependency.
- Applies `minecraft 'com.mojang:minecraft:VERSION'` as a dependency where `VERSION` is specified using the `abacus` extension from `settings.gradle`.
- Applies `modImplementation 'net.fabricmc:fabric-loader:VERSION'` as  a dependency where `VERSION` is specified using the `abacus` extension from `settings.gradle`.
