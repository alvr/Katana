package dev.alvr.katana.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import dev.alvr.katana.features.home.ui.screens.HomeScreen

fun NavGraphBuilder.home(homeNavigator: HomeNavigator) {
    navigation<HomeDestination.Root>(
        startDestination = HomeDestination.Home,
    ) {
        composable<HomeDestination.Home> {
            HomeScreen(homeNavigator)
        }
    }
}
