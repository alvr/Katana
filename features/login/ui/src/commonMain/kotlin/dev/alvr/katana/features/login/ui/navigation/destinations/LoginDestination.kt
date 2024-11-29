package dev.alvr.katana.features.login.ui.navigation.destinations

import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlinx.serialization.Serializable

sealed interface LoginDestination : KatanaDestination {
    @Serializable
    data class Login(val token: String? = null) : LoginDestination
}
