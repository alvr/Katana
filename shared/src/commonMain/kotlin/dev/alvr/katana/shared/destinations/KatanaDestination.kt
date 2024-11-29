package dev.alvr.katana.shared.destinations

import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlinx.serialization.Serializable

sealed interface KatanaDestination : KatanaDestination {
    @Serializable
    data object ExpiredSessionDialog : KatanaDestination
}
