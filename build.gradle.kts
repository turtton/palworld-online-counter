plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jmailen.kotlinter") version "4.2.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "net.turtton"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.kord:kord-core:0.13.1")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("net.turtton.MainKt")
}