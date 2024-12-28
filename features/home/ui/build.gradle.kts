plugins {
    id("katana.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.session.domain)

            implementation(projects.core.common)
            implementation(projects.core.ui)

            implementation(projects.features.home.domain)
        }

        commonTest.dependencies {
            implementation(projects.core.tests)
            implementation(projects.core.tests.ui)
        }
    }
}
