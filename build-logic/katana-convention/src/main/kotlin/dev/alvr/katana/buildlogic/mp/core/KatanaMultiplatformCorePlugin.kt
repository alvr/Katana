package dev.alvr.katana.buildlogic.mp.core

import com.android.build.gradle.LibraryExtension
import dev.alvr.katana.buildlogic.configureAndroid
import dev.alvr.katana.buildlogic.fullPackageName
import dev.alvr.katana.buildlogic.mp.commonConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

internal class KatanaMultiplatformCorePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = "com.android.library")
        commonConfiguration()

        extensions.configure<LibraryExtension> { configureAndroid(project.fullPackageName) }
    }
}
