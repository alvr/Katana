plugins {
    id("katana.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.remote)

            implementation(projects.features.home.domain)
        }

        commonTest.dependencies { implementation(projects.core.tests) }
    }
}
