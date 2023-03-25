val kyouko_wrapper_version: String by project

plugins {
    `maven-publish`
}

val githubActor = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
val githubToken = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")

group = "io.github.slaxnetwork"
version = "0.0.1"

dependencies {
    implementation("io.github.slaxnetwork:kyouko-wrapper:$kyouko_wrapper_version")
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
            artifactId = "bukkit-core-api"
            version = "${project.version}"

            from(components["java"])
        }
    }
}