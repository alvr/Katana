import dev.alvr.katana.buildlogic.KatanaConfiguration

plugins {
    id("katana.android.library")
    alias(libs.plugins.apollo)
}

val pkg = "${KatanaConfiguration.PackageName}.data.remote.account"

android.namespace = pkg

apollo {
    service("anilist") {
        generateAsInternal.set(true)
        generateDataBuilders.set(true)
        packageName.set(pkg)
    }
}

dependencies {
    apolloMetadata(projects.data.remote.base)

    implementation(projects.data.remote.base)
    implementation(projects.domain.account)
    implementation(libs.bundles.data.remote)

    testImplementation(projects.common.tests)
    testImplementation(libs.bundles.test.data.remote)
}
