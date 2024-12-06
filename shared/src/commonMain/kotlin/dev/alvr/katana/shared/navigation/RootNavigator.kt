package dev.alvr.katana.shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.alvr.katana.core.ui.navigation.KatanaDestination
import dev.alvr.katana.core.ui.navigation.KatanaNavigationBarItem.Companion.hasRoute
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator
import dev.alvr.katana.core.ui.utils.hasRoute
import dev.alvr.katana.features.account.ui.navigation.AccountNavigator
import dev.alvr.katana.features.account.ui.navigation.rememberKatanaAccountNavigator
import dev.alvr.katana.features.explore.ui.navigation.ExploreNavigator
import dev.alvr.katana.features.explore.ui.navigation.rememberKatanaExploreNavigator
import dev.alvr.katana.features.home.ui.navigation.HomeDestination
import dev.alvr.katana.features.home.ui.navigation.HomeNavigator
import dev.alvr.katana.features.home.ui.navigation.rememberKatanaHomeNavigator
import dev.alvr.katana.features.lists.ui.navigation.AnimeListsDestination
import dev.alvr.katana.features.lists.ui.navigation.AnimeListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.MangaListsDestination
import dev.alvr.katana.features.lists.ui.navigation.MangaListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.rememberKatanaAnimeListsNavigator
import dev.alvr.katana.features.lists.ui.navigation.rememberKatanaMangaListsNavigator
import dev.alvr.katana.shared.destinations.RootDestination

internal sealed interface RootNavigator :
    HomeNavigator,
    AnimeListsNavigator,
    MangaListsNavigator,
    ExploreNavigator,
    AccountNavigator {
    val navController: NavHostController

    fun navigateToHome()

    fun onNavigationBarItemClicked(item: MainNavigationBarItem)

    fun onSessionExpired()
}

private class KatanaRootNavigator(
    override val navController: NavHostController,
    homeNavigator: HomeNavigator,
    animeListsNavigator: AnimeListsNavigator,
    mangaListsNavigator: MangaListsNavigator,
    exploreNavigator: ExploreNavigator,
    accountNavigator: AccountNavigator,
) : RootNavigator,
    HomeNavigator by homeNavigator,
    AnimeListsNavigator by animeListsNavigator,
    MangaListsNavigator by mangaListsNavigator,
    ExploreNavigator by exploreNavigator,
    AccountNavigator by accountNavigator {

    // region [KatanaNavigator]
    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateToHome() {
        onNavigationBarItemClicked(HomeDestination.Root)
    }

    override fun onNavigationBarItemClicked(item: MainNavigationBarItem) {
        onNavigationBarItemClicked(item.screen)
    }

    override fun onSessionExpired() {
        navController.navigate(RootDestination.ExpiredSessionDialog)
    }
    // endregion [KatanaNavigator]

    // region [HomeNavigator]
    override fun navigateToAnimeLists() {
        onNavigationBarItemClicked(AnimeListsDestination.Root)
    }

    override fun navigateToMangaLists() {
        onNavigationBarItemClicked(MangaListsDestination.Root)
    }
    // endregion [HomeNavigator]

    private fun onNavigationBarItemClicked(screen: KatanaDestination) {
        val hasItemRoute = navController.currentBackStackEntry.hasRoute(screen::class)

        if (!hasItemRoute) {
            navController.navigate(screen) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
internal fun rememberKatanaNavigator(): RootNavigator {
    val homeNavigator = rememberKatanaHomeNavigator()
    val animeListsNavigator = rememberKatanaAnimeListsNavigator()
    val mangaListsNavigator = rememberKatanaMangaListsNavigator()
    val exploreNavigator = rememberKatanaExploreNavigator()
    val accountNavigator = rememberKatanaAccountNavigator()

    return rememberKatanaNavigator { navController ->
        KatanaRootNavigator(
            navController = navController,
            homeNavigator = homeNavigator,
            animeListsNavigator = animeListsNavigator,
            mangaListsNavigator = mangaListsNavigator,
            exploreNavigator = exploreNavigator,
            accountNavigator = accountNavigator,
        )
    }
}
