val kyouko_wrapper_version: String by project

plugins {
    `maven-publish`
}

val githubActor = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
val githubToken = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")

group = "io.github.slaxnetwork.bukkitcore"
version = "0.0.2"

dependencies {
    compileOnly(project(":api"))
    compileOnly("io.github.slaxnetwork:kyouko-wrapper:$kyouko_wrapper_version")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SlaxNetwork/bukkit-core")
            credentials {
                username = githubActor
                password = githubToken
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