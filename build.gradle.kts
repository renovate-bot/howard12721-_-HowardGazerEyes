plugins {
    application
    alias(libs.plugins.kotlin.jvm)
}

group = providers.gradleProperty("projectGroup").get()
version = providers.gradleProperty("projectVersion").get()

val projectMainClass = providers.gradleProperty("projectMainClass").get()

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.trakt.bot)
    implementation(libs.kotlinx.coroutines.core)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
        freeCompilerArgs.add("-opt-in=kotlin.uuid.ExperimentalUuidApi")
        freeCompilerArgs.add("-Xcontext-parameters")
    }
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

tasks.run {
    standardInput = System.`in`
}

application {
    mainClass = projectMainClass
    applicationName = rootProject.name
}
