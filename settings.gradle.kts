
pluginManagement {
    // Versions are declared in 'gradle.properties' file
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    val shadowVersion: String by settings
    val micronautApplicationVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
        id("com.github.johnrengelman.shadow") version shadowVersion
        id("io.micronaut.application") version micronautApplicationVersion
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        id("maven-publish")
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "super-engine"

include(":books")
include(":books-analytics")
include("event")
