@file:Suppress("NoUnusedImports", "UnusedImports")

package dev.alvr.katana.buildlogic.common

import dev.alvr.katana.buildlogic.isRelease
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

internal class KatanaCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(tasks) {
                register<TestReport>("unitTests") {
                    val testTasks = subprojects.map { p ->
                        p.tasks.withType<Test>().matching { t -> !t.isRelease }
                    }

                    mustRunAfter(testTasks)
                    destinationDirectory = file("${layout.buildDirectory.asFile.get()}/reports/allTests")
                    testResults.setFrom(testTasks)
                }
            }
        }
    }
}
