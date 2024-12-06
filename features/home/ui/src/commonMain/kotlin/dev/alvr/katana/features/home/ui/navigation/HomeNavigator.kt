package dev.alvr.katana.features.home.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.overridden
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface HomeNavigator : BaseNavigator {
    val homeNavController: NavHostController

    fun navigateToAnimeLists()
    fun navigateToMangaLists()
}

private class KatanaHomeNavigator(
    override val homeNavController: NavHostController,
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
}

@Composable
fun rememberKatanaHomeNavigator(): HomeNavigator = rememberKatanaNavigator { navController ->
    KatanaHomeNavigator(navController)
}
