package dev.alvr.katana.features.home.ui.screens

import co.touchlab.kermit.Logger
import dev.alvr.katana.features.home.ui.navigation.HomeNavigator
import dev.alvr.katana.features.home.ui.viewmodel.HomeEffect
import dev.alvr.katana.features.home.ui.viewmodel.PlatformHomeEffect

internal actual fun HomeEffect.handleHomeEffect(
    homeNavigator: HomeNavigator
) {

    @Suppress("UseIfInsteadOfWhen")
    when (this) {
        PlatformHomeEffect.ShowTokenInputDialog -> homeNavigator.showTokenInputDialog()
        else -> Logger.d(LogTag) { "Unhandled effect: $this" }
    }
}

private const val LogTag = "HomeScreen"
