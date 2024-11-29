package dev.alvr.katana.shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.alvr.katana.core.ui.navigation.KatanaNavigationBarItem.Companion.hasRoute
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator
import dev.alvr.katana.features.account.ui.navigation.AccountNavigator
import dev.alvr.katana.features.account.ui.navigation.rememberKatanaAccountNavigator
import dev.alvr.katana.features.explore.ui.navigation.ExploreNavigator
import dev.alvr.katana.features.explore.ui.navigation.rememberKatanaExploreNavigator
import dev.alvr.katana.features.home.ui.navigation.HomeNavigator
import dev.alvr.katana.features.home.ui.navigation.rememberKatanaHomeNavigator
import dev.alvr.katana.features.lists.ui.navigation.AnimeListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.MangaListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.rememberKatanaAnimeListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.rememberKatanaMangaListsNavigator
import dev.alvr.katana.shared.destinations.KatanaDestination

internal sealed interface KatanaNavigator :
    HomeNavigator,
    AnimeListsNavigator,
    MangaListsNavigator,
    ExploreNavigator,
    AccountNavigator {
    val navController: NavHostController

    fun onNavigationBarItemClicked(item: MainNavigationBarItem)

    fun onSessionExpired()
}

private class DefaultKatanaNavigator(
    override val navController: NavHostController,
    homeNavigator: HomeNavigator,
    animeListsNavigator: AnimeListsNavigator,
    mangaListsNavigator: MangaListsNavigator,
    exploreNavigator: ExploreNavigator,
    accountNavigator: AccountNavigator,
) : KatanaNavigator,
    HomeNavigator by homeNavigator,
    AnimeListsNavigator by animeListsNavigator,
    MangaListsNavigator by mangaListsNavigator,
    ExploreNavigator by exploreNavigator,
    AccountNavigator by accountNavigator {

    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun onNavigationBarItemClicked(item: MainNavigationBarItem) {
        val hasItemRoute = navController.currentBackStackEntry.hasRoute(item)

        if (!hasItemRoute) {
            navController.navigate(item.screen) {
                popUpTo(navController.graph.startDestinationRoute.orEmpty()) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    override fun onSessionExpired() {
        navController.navigate(KatanaDestination.ExpiredSessionDialog)
    }
}

@Composable
internal fun rememberKatanaNavigator(): KatanaNavigator {
    val homeNavigator = rememberKatanaHomeNavigator()
    val animeListsNavigator = rememberKatanaAnimeListsNavigator()
    val mangaListsNavigator = rememberKatanaMangaListsNavigator()
    val exploreNavigator = rememberKatanaExploreNavigator()
    val accountNavigator = rememberKatanaAccountNavigator()

    return rememberKatanaNavigator { navController ->
        DefaultKatanaNavigator(
            navController = navController,
            homeNavigator = homeNavigator,
            animeListsNavigator = animeListsNavigator,
            mangaListsNavigator = mangaListsNavigator,
            exploreNavigator = exploreNavigator,
            accountNavigator = accountNavigator,
        )
    }
}
