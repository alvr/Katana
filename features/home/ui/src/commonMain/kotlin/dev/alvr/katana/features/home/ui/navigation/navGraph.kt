package dev.alvr.katana.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.alvr.katana.core.ui.navigation.viewModelStoreOwner
import dev.alvr.katana.core.ui.utils.navDeepLink
import dev.alvr.katana.features.home.ui.LOGIN_DEEP_LINK
import dev.alvr.katana.features.home.ui.screens.HomeScreen
import dev.alvr.katana.features.home.ui.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

internal expect fun NavGraphBuilder.tokenInputDialog(homeNavigator: HomeNavigator)

fun NavGraphBuilder.home(homeNavigator: HomeNavigator) {
    navigation<HomeDestination.Root>(
        startDestination = HomeDestination.Home(),
    ) {
        composable<HomeDestination.Home>(
            deepLinks = listOf(navDeepLink { setUriPattern(LOGIN_DEEP_LINK) }),
        ) { homeBackStackEntry ->
            val viewModel = koinViewModel<HomeViewModel>(
                viewModelStoreOwner = homeNavigator.viewModelStoreOwner,
                extras = homeBackStackEntry.defaultViewModelCreationExtras,
            )

            HomeScreen(
                homeNavigator = homeNavigator,
                viewModel = viewModel,
            )
        }

        tokenInputDialog(homeNavigator)
    }
}
