package dev.alvr.katana.features.explore.ui.navigation

import androidx.compose.runtime.Composable
import dev.alvr.katana.core.ui.navigation.KatanaNavigator
import dev.alvr.katana.core.ui.navigation.overridden
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface ExploreNavigator : KatanaNavigator

private class KatanaExploreNavigator : ExploreNavigator {
    override fun navigateBack() {
        overridden()
    }
}

@Composable
fun rememberKatanaExploreNavigator(): ExploreNavigator = rememberKatanaNavigator { _ ->
    KatanaExploreNavigator()
}
