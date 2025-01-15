package dev.alvr.katana.features.home.ui.navigation

import androidx.navigation.NavHostController
import dev.alvr.katana.core.ui.navigation.KatanaNavigator
import dev.alvr.katana.core.ui.navigation.overridden

expect sealed interface PlatformHomeNavigator : KatanaNavigator

interface HomeNavigator : PlatformHomeNavigator, KatanaNavigator {
    fun navigateToAnimeLists()
    fun navigateToMangaLists()
    fun navigateToTrending()
    fun navigateToPopular()
    fun navigateToUpcoming()
}

private class KatanaHomeNavigator(
    override val navController: NavHostController,
) : HomeNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToAnimeLists() {
        overridden()
    }

    override fun navigateToMangaLists() {
        overridden()
    }

    override fun navigateToUpcoming() {
        overridden()
    }

    override fun navigateToPopular() {
        overridden()
    }

    override fun navigateToTrending() {
        overridden()
    }
}

fun katanaHomeNavigator(navController: NavHostController): HomeNavigator =
    KatanaHomeNavigator(navController)
