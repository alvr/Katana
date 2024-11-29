package dev.alvr.katana.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.common.KatanaBuildConfig

@Stable
interface BaseNavigator {
    fun navigateBack()
}

@Composable
fun <T : BaseNavigator> rememberKatanaNavigator(factory: (NavHostController) -> T): T {
    val navigator = rememberNavController().sentryObserver().loggerObserver()
    return remember(navigator) { factory(navigator) }
}

@Composable
internal expect fun NavHostController.sentryObserver(): NavHostController

@Composable
private fun NavHostController.loggerObserver() = apply {
    if (KatanaBuildConfig.DEBUG) {
        DisposableEffect(this, LocalLifecycleOwner.current.lifecycle) {
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                Logger.d { "Navigating to route ${destination.route}" }
            }

            addOnDestinationChangedListener(listener)
            onDispose { removeOnDestinationChangedListener(listener) }
        }
    }
}
