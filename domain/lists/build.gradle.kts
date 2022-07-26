plugins {
    modules.`kotlin-library`
}

dependencies {
    api(projects.domain.base)

    implementation(libs.bundles.common)

    kapt(libs.bundles.kapt)

    testImplementation(projects.common.tests)
    testImplementation(libs.bundles.test)
}
