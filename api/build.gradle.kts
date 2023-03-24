plugins {
    `maven-publish`
}

group = "io.github.slaxnetwork"
version = "0.0.1"

dependencies {
    implementation("io.github.slaxnetwork:kyouko-wrapper:0.0.1")
}

publishing {
    publications {
        create<MavenPublication>(project.name.toLowerCase()) {
            groupId = "io.github.slaxnetwork"
            artifactId = "bukkit-core-api"
            version = "${project.version}"

            from(components["java"])
        }
    }
}