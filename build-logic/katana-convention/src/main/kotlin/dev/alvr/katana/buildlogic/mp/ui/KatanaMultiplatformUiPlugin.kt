package dev.alvr.katana.buildlogic.mp.ui

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

internal class KatanaMultiplatformUiPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = "katana.multiplatform.core")
        apply(plugin = "katana.multiplatform.compose")
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    }
}
