package dev.alvr.katana.shared.navigation

import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlinx.serialization.Serializable

sealed interface RootDestination : KatanaDestination {
    @Serializable
    data object ExpiredSessionDialog : KatanaDestination
}
