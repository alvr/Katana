@file:Suppress("NoUnusedImports", "UnusedImports")

package dev.alvr.katana.buildlogic.mp

import dev.alvr.katana.buildlogic.catalogBundle
import dev.alvr.katana.buildlogic.commonExtensions
import dev.alvr.katana.buildlogic.commonTasks
import dev.alvr.katana.buildlogic.kspDependencies
import io.sentry.kotlin.multiplatform.gradle.SentryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.commonConfiguration() {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "com.google.devtools.ksp")
    apply(plugin = "io.kotest.multiplatform")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "dev.mokkery")
    apply(plugin = "io.sentry.kotlin.multiplatform.gradle")

    with(extensions) {
        commonExtensions()
        configure<KotlinMultiplatformExtension> { configureMultiplatform(project) }
        configure<SentryExtension> { configureSentryMultiplatform() }
    }

    tasks.commonTasks()
}

private fun KotlinMultiplatformExtension.configureMultiplatform(project: Project) {
    hierarchy()
    configureSourceSets()

    kspDependencies(project, "core")
}

private fun KotlinMultiplatformExtension.configureSourceSets() {
    sourceSets {
        commonMain.dependencies {
            implementation(catalogBundle("core-common"))
        }
        androidMain.dependencies {
            implementation(catalogBundle("core-android"))
        }
        iosMain.dependencies {
            implementation(catalogBundle("core-ios"))
        }
        desktopMain.dependencies {
            implementation(catalogBundle("core-desktop"))
        }
        jvmBasedMain.dependencies {
            implementation(catalogBundle("core-jvm"))
        }

        commonTest.dependencies {
            implementation(catalogBundle("core-common-test"))
        }
        androidUnitTest.dependencies {
            implementation(catalogBundle("core-android-test"))
        }
        iosTest.dependencies {
            implementation(catalogBundle("core-ios-test"))
        }
        desktopTest.dependencies {
            implementation(catalogBundle("core-desktop-test"))
        }
        jvmBasedTest.dependencies {
            implementation(catalogBundle("core-jvm-test"))
        }
    }
}

private fun SentryExtension.configureSentryMultiplatform() {
    autoInstall.enabled = false
    autoInstall.commonMain.enabled = false
    autoInstall.cocoapods.enabled = false
}
