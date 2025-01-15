package dev.alvr.katana.features.home.ui.navigation

import dev.alvr.katana.core.ui.navigation.KatanaNavigator

actual sealed interface PlatformHomeNavigator : KatanaNavigator {
    fun showTokenInputDialog() {
        navController.navigate(PlatformHomeDestination.TokenInputDialog)
    }

    fun closeTokenInputDialog() {
        navController.popBackStack<PlatformHomeDestination.TokenInputDialog>(
            inclusive = true,
            saveState = false,
        )
    }
}
