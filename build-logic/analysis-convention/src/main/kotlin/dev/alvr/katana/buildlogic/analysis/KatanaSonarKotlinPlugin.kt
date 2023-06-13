package dev.alvr.katana.buildlogic.analysis

import dev.alvr.katana.buildlogic.ConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarExtension

internal class KatanaSonarKotlinPlugin : ConventionPlugin {
    override fun Project.configure() {
        extensions.configure<SonarExtension> {
            properties {
                property(
                    "sonar.coverage.jacoco.xmlReportPaths",
                    "$buildDir/reports/kover/report.xml",
                )
                property("sonar.java.binaries", "$buildDir/classes/kotlin")
                property("sonar.junit.reportPaths", "$buildDir/test-results/test")
                property(
                    "sonar.sources",
                    listOf(
                        "$projectDir/src/commonMain/kotlin",
                        "$projectDir/src/jvmMain/kotlin",
                        "$projectDir/src/iosMain/kotlin",
                    ),
                )
                property(
                    "sonar.tests",
                    listOf(
                        "$projectDir/src/commonTest/kotlin",
                        "$projectDir/src/jvmTest/kotlin",
                        "$projectDir/src/iosTest/kotlin",
                    ),
                )
            }
        }
    }
}