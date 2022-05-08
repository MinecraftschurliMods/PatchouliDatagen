PatchouliDatagen
============

Adds a data generator for [Patchouli](https://github.com/VazkiiMods/Patchouli).

## Installation

First add the maven repository in your repositories block.

~~~
repositories {
    ...
    maven {
        name = 'MinecraftschurliMods'
        url = 'https://minecraftschurli.ddns.net/repository/maven-public/'
    }
    ...
}
~~~

Then add the dependency for your modloader to your dependencies and replace `<version>` with the version you want (current: 1.0-SNAPSHOT).

### Forge
~~~gradle
dependencies {
    ...
    implementation fg.deobf("com.github.mincraftschurli.patchouli_datagen:PatchouliDatagen-forge-1.18.2:<version>")
    ...
}
~~~

### Fabric
~~~gradle
dependencies {
    ...
    modImplementation "com.github.mincraftschurli.patchouli_datagen:PatchouliDatagen-fabric-1.18.2:<version>"
    ...
}
~~~
