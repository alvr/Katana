package dev.alvr.katana.features.explore.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface ExploreNavigator : BaseNavigator

private class KatanaExploreNavigator : ExploreNavigator {
    override fun navigateBack() {
        Logger.i { "Implementation done in KatanaNavigator" }
    }
}

@Composable
fun rememberKatanaExploreNavigator(): ExploreNavigator = rememberKatanaNavigator { _ ->
    KatanaExploreNavigator()
}
