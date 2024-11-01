package dev.alvr.katana.features.home.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.destinations.HomeDestination
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator
import dev.alvr.katana.features.account.ui.navigation.AccountNavigator
import dev.alvr.katana.features.explore.ui.navigation.ExploreNavigator
import dev.alvr.katana.features.home.ui.navigation.HomeNavigationBarItem.Companion.hasRoute
import dev.alvr.katana.features.lists.ui.navigation.AnimeListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.MangaListsNavigator
import dev.alvr.katana.features.social.ui.navigation.SocialNavigator

interface HomeNavigator :
    AnimeListsNavigator,
    MangaListsNavigator,
    ExploreNavigator,
    SocialNavigator,
    AccountNavigator {
    val homeNavController: NavHostController

    override fun navigateToLogin()

    fun onSessionExpired()

    fun onHomeNavigationBarItemClicked(item: HomeNavigationBarItem)
}

private class KatanaHomeNavigator(
    override val homeNavController: NavHostController,
) : HomeNavigator {

    override fun navigateBack() {
        homeNavController.navigateUp()
    }

    override fun onSessionExpired() {
        homeNavController.navigate(HomeDestination.ExpiredSessionDialog)
    }

    override fun onHomeNavigationBarItemClicked(item: HomeNavigationBarItem) {
        val hasItemRoute = homeNavController.currentBackStackEntry.hasRoute(item)

        if (!hasItemRoute) {
            homeNavController.navigate(item.screen) {
                popUpTo(homeNavController.graph.startDestinationRoute.orEmpty()) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    override fun navigateToLogin() {
        Logger.i { "Implementation overridden in KatanaRootNavigator" }
    }

    override fun navigateToEntryDetails(id: Int) {
        Logger.d { "Entry details $id" }
    }

    override fun showEditEntry(id: Int) {
        Logger.d { "Edit entry $id" }
    }
}

@Composable
fun rememberKatanaHomeNavigator(): HomeNavigator = rememberKatanaNavigator { navController ->
    KatanaHomeNavigator(homeNavController = navController)
}
