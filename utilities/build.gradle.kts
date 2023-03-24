plugins {
    `maven-publish`
}

group = "io.github.slaxnetwork.bukkitcore"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(project(":api"))
}

publishing {
    publications {
        create<MavenPublication>(project.name.toLowerCase()) {
            groupId = "io.github.slaxnetwork"
            artifactId = "bukkit-utilities"
            version = "${project.version}"

            from(components["java"])
        }
    }
}