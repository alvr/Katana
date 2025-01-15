package dev.alvr.katana.features.home.ui.navigation

import kotlinx.serialization.Serializable

actual sealed interface PlatformHomeDestination : HomeDestination {

    @Serializable
    data object TokenInputDialog : PlatformHomeDestination
}
