package dev.alvr.katana.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.alvr.katana.core.ui.utils.navDeepLink
import dev.alvr.katana.features.home.ui.LOGIN_DEEP_LINK
import dev.alvr.katana.features.home.ui.screens.HomeScreen

fun NavGraphBuilder.home(homeNavigator: HomeNavigator) {
    composable<HomeDestination.Root> {
        NavHost(
            navController = homeNavigator.homeNavController,
            startDestination = HomeDestination.Home(),
        ) {
            composable<HomeDestination.Home>(
                deepLinks = listOf(navDeepLink { setUriPattern(LOGIN_DEEP_LINK) }),
            ) {
                HomeScreen(homeNavigator)
            }
        }
    }
}
