val kyouko_wrapper_version: String by project

plugins {
    `maven-publish`
}

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
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
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