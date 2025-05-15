group = "org.example"
version = "0.0.1"

plugins {
    kotlin("jvm") version "2.1.21"
    id("io.ktor.plugin") version "3.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:3.1.3")
    implementation("io.ktor:ktor-server-netty-jvm:3.1.3")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.3")
    implementation("io.ktor:ktor-server-status-pages:3.1.3")
    implementation("io.ktor:ktor-server-cors:3.1.3")
    implementation("io.ktor:ktor-server-default-headers:3.1.3")
    implementation("io.ktor:ktor-server-forwarded-header:3.1.3")
    implementation("io.ktor:ktor-server-call-logging:3.1.3")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Koin
    implementation("io.insert-koin:koin-ktor:3.5.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.3")

    // Log4j
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")

    // Testing
    testImplementation("io.ktor:ktor-server-tests:3.1.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.1.21")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveFileName.set("app.jar")
    mergeServiceFiles()
    manifest {
        attributes(mapOf("Main-Class" to "io.ktor.server.netty.EngineMain"))
    }
}
