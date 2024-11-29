package dev.alvr.katana.features.home.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface HomeNavigator : BaseNavigator

private class KatanaHomeNavigator : HomeNavigator {
    override fun navigateBack() {
        Logger.i { "Implementation done in KatanaNavigator" }
    }
}

@Composable
fun rememberKatanaHomeNavigator(): HomeNavigator = rememberKatanaNavigator { _ ->
    KatanaHomeNavigator()
}
