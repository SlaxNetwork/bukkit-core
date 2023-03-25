import java.net.URI

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
    compileOnly("io.github.slaxnetwork:kyouko-wrapper:0.0.1")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI.create("https://maven.pkg.github.com/SlaxNetwork/bukkit-core")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>(project.name.toLowerCase()) {
            groupId = "io.github.slaxnetwork"
            artifactId = "bukkit-utilities"
            version = "${project.version}"

            from(components["java"])
        }
    }
}