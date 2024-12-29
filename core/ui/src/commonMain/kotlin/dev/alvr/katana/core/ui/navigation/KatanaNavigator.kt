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
interface KatanaNavigator {
    fun navigateBack()
}

@Suppress("UnusedReceiverParameter")
fun KatanaNavigator.overridden(
    navigator: String = "KatanaRootNavigator",
) {
    Logger.i(LogTag) { "Implementation overridden in $navigator" }
}

@Composable
fun <T : KatanaNavigator> rememberKatanaNavigator(factory: (NavHostController) -> T): T {
    val navigator = rememberNavController().sentryObserver().loggerObserver()
    return remember(navigator) { factory(navigator) }
}

@Composable
internal expect fun NavHostController.sentryObserver(): NavHostController

@Composable
private fun NavHostController.loggerObserver() = apply {
    if (KatanaBuildConfig.DEBUG) {
        DisposableEffect(this, LocalLifecycleOwner.current.lifecycle) {
            val listener = NavController.OnDestinationChangedListener { navController, destination, args ->
                Logger.d(LogTag) {
                    buildString {
                        append("Navigating to route ${destination.route}")

                        navController.previousBackStackEntry?.destination?.route?.let { previousRoute ->
                            append(" from $previousRoute")
                        }
                    }
                }
            }

            addOnDestinationChangedListener(listener)
            onDispose { removeOnDestinationChangedListener(listener) }
        }
    }
}

private const val LogTag = "KatanaNavigator"
