plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.0"

    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
//    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    group = "io.github.slaxnetwork.bukkitcore"
    version = "0.0.1"

    repositories {
        mavenCentral()
        mavenLocal()

        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        implementation("io.github.slaxnetwork:kyouko-wrapper:0.0.1")

        compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    }
}

dependencies {
    implementation(project(":api"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")

    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.11.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.11.0")

    implementation("net.kyori:adventure-api:4.12.0")
}

bukkit {
    name = "bukkit-core"
    apiVersion = "1.19"
    authors = listOf("Tech")
    main = "io.github.slaxnetwork.BukkitCore"
    commands {
        register("language")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}