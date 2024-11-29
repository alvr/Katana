package dev.alvr.katana.features.home.ui.navigation

import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlinx.serialization.Serializable

sealed interface HomeDestination : KatanaDestination {

    @Serializable
    data object Root : HomeDestination

    @Serializable
    data object Home : HomeDestination
}
