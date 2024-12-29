plugins {
    id("katana.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.domain)

            implementation(libs.coil.ktor)
            implementation(libs.compose.placeholder)
            implementation(libs.compose.windowsize)
            implementation(libs.haze.materials)
            implementation(libs.koin)
            implementation(libs.kotlinx.atomicfu)
            implementation(libs.materialkolor)
        }

        androidMain.dependencies {
            implementation(libs.sentry.compose)
        }

        commonTest.dependencies { implementation(projects.core.tests) }
    }
}
