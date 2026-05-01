plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val projectArtifactId = providers.gradleProperty("projectArtifactId").orElse("trakt-bot-template")

rootProject.name = projectArtifactId.get()
