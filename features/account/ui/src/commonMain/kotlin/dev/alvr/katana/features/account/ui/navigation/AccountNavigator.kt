package dev.alvr.katana.features.account.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface AccountNavigator : BaseNavigator

private class KatanaAccountNavigator : AccountNavigator {
    override fun navigateBack() {
        Logger.i { "Implementation done in KatanaNavigator" }
    }
}

@Composable
fun rememberKatanaAccountNavigator(): AccountNavigator = rememberKatanaNavigator { _ ->
    KatanaAccountNavigator()
}
