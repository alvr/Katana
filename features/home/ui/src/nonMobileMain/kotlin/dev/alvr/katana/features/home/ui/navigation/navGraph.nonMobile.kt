package dev.alvr.katana.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import dev.alvr.katana.core.ui.components.login.KatanaLoginDialog
import dev.alvr.katana.core.ui.navigation.viewModelStoreOwner
import dev.alvr.katana.features.home.ui.viewmodel.HomeViewModel
import dev.alvr.katana.features.home.ui.viewmodel.PlatformHomeIntent
import org.koin.compose.viewmodel.koinViewModel

internal actual fun NavGraphBuilder.tokenInputDialog(homeNavigator: HomeNavigator) {
    dialog<PlatformHomeDestination.TokenInputDialog> {
        val viewModel = koinViewModel<HomeViewModel>(
            viewModelStoreOwner = homeNavigator.viewModelStoreOwner,
        )

        KatanaLoginDialog(
            onDismiss = homeNavigator::closeTokenInputDialog,
            onLogin = { token ->
                viewModel.intent(PlatformHomeIntent.TokenInputIntent.SaveTokenInput(token))
                homeNavigator.closeTokenInputDialog()
            },
        )
    }
}
