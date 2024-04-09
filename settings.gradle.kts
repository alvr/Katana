rootProject.name = "Katana"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("com.gradle.enterprise") version "3.17.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

// Keep in sync with build-logic/settings.gradle.kts
buildCache {
    local {
        directory = rootDir.resolve(".gradle/build-cache")
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

include(":app-android", ":shared")
includes("common", "core", "features", maxDepth = 2)

fun includes(vararg directories: String, maxDepth: Int = 1) {
    directories.forEach { topDir ->
        rootDir.resolve(topDir)
            .walkTopDown()
            .maxDepth(maxDepth)
            .filter { file ->
                file.isDirectory && file.resolve("build.gradle.kts").exists()
            }.forEach { module ->
                include(":${module.relativeTo(rootDir).path.replace(File.separatorChar, ':')}")
            }
    }
}

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
