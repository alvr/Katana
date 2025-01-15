package dev.alvr.katana.features.home.ui.navigation

import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlinx.serialization.Serializable

expect sealed interface PlatformHomeDestination : HomeDestination

interface HomeDestination : KatanaDestination {

    @Serializable
    data object Root : HomeDestination

    @Serializable
    data class Home(val token: String? = null) : HomeDestination
}
