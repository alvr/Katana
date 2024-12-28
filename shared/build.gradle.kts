plugins {
    id("katana.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.session.data)
            implementation(projects.common.session.domain)
            implementation(projects.common.user.data)
            implementation(projects.common.user.domain)

            api(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.preferences)
            implementation(projects.core.remote)
            implementation(projects.core.ui)

            implementation(projects.features.account.data)
            implementation(projects.features.account.domain)
            implementation(projects.features.account.ui)

            implementation(projects.features.explore.data)
            implementation(projects.features.explore.domain)
            implementation(projects.features.explore.ui)

            implementation(projects.features.home.data)
            implementation(projects.features.home.domain)
            implementation(projects.features.home.ui)

            implementation(projects.features.lists.data)
            implementation(projects.features.lists.domain)
            implementation(projects.features.lists.ui)

            implementation(libs.sentry.multiplatform)
        }

        commonTest.dependencies { implementation(projects.core.tests) }
    }
}
