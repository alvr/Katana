package dev.alvr.katana.buildlogic.mp.core

import com.android.build.gradle.LibraryExtension
import dev.alvr.katana.buildlogic.catalogBundle
import dev.alvr.katana.buildlogic.commonTasks
import dev.alvr.katana.buildlogic.configureAndroid
import dev.alvr.katana.buildlogic.fullPackageName
import dev.alvr.katana.buildlogic.mp.desktopMain
import dev.alvr.katana.buildlogic.mp.hierarchy
import dev.alvr.katana.buildlogic.mp.jvmBasedMain
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class KatanaMultiplatformTestsPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
        apply(plugin = "com.android.library")

        with(extensions) {
            configure<KotlinMultiplatformExtension> { configureMultiplatform() }
            configure<LibraryExtension> { configureAndroid(project.fullPackageName) }
        }

        tasks.commonTasks()
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform() {
        hierarchy()
        configureSourceSets()
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        sourceSets {
            commonMain.dependencies {
                implementation(catalogBundle("core-common-test"))
                implementation(catalogBundle("data-preferences-common-test"))
                implementation(catalogBundle("data-remote-common-test"))
                implementation(catalogBundle("ui-common-test"))
            }
            androidMain.dependencies {
                implementation(catalogBundle("core-android-test"))
                implementation(catalogBundle("data-preferences-android-test"))
                implementation(catalogBundle("data-remote-android-test"))
                implementation(catalogBundle("ui-android-test"))
            }
            iosMain.dependencies {
                implementation(catalogBundle("core-ios-test"))
                implementation(catalogBundle("data-preferences-ios-test"))
                implementation(catalogBundle("data-remote-ios-test"))
                implementation(catalogBundle("ui-ios-test"))
            }
            desktopMain.dependencies {
                implementation(catalogBundle("core-desktop-test"))
                implementation(catalogBundle("data-preferences-desktop-test"))
                implementation(catalogBundle("data-remote-desktop-test"))
                implementation(catalogBundle("ui-desktop-test"))
            }
            jvmBasedMain.dependencies {
                implementation(catalogBundle("core-jvm-test"))
                implementation(catalogBundle("data-preferences-jvm-test"))
                implementation(catalogBundle("data-remote-jvm-test"))
                implementation(catalogBundle("ui-jvm-test"))
            }
        }
    }
}
