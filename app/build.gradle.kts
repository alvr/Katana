plugins {
    id("katana.app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.shared)
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugaring)
}
