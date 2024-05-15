plugins {
    id("katana.app.android")
}

dependencies {
    implementation(projects.shared)
    coreLibraryDesugaring(libs.desugaring)
}
