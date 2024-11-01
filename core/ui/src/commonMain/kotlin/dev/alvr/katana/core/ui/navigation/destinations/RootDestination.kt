package dev.alvr.katana.core.ui.navigation.destinations

import kotlinx.serialization.Serializable

sealed interface RootDestination : KatanaDestination {
    @Serializable
    data object Auth : RootDestination

    @Serializable
    data object Home : RootDestination
}
