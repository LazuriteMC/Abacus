package dev.lazurite.abacus

import org.gradle.api.provider.Property

abstract class AbacusExtension {
    // As far as I could tell there's no way to collect all properties provided by an extension?
    String[] getAll() {
        return this.class.methods.findAll({ it.returnType == Property })
                .collect({ it.name })
                .collect({ it.substring(3) }) // remove 'get'
                .collect({ it.replace(it[0], it[0].toLowerCase()) }) // change 'Foo' to 'foo'
    }
}