buildscript {
    configurations.classpath {
        resolutionStrategy {
            force(
                "com.pinterest.ktlint:ktlint-rule-engine:1.0.0",
                "com.pinterest.ktlint:ktlint-rule-engine-core:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-core:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-checkstyle:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-json:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-html:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-plain:1.0.0",
                "com.pinterest.ktlint:ktlint-cli-reporter-sarif:1.0.0",
                "com.pinterest.ktlint:ktlint-ruleset-standard:1.0.0",
            )
        }
    }
}

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
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")
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
