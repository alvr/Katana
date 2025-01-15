package dev.alvr.katana.features.lists.ui.navigation

import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.KatanaNavigator
import dev.alvr.katana.core.ui.navigation.overridden

interface AnimeListsNavigator : KatanaNavigator {
    fun animeEntryDetails(id: Int)
    fun editAnimeEntry(id: Int)
}

private class KatanaAnimeListsNavigator(
    override val navController: NavHostController,
) : AnimeListsNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun animeEntryDetails(id: Int) {
        Logger.d(LogTag) { "Entry details $id" }
    }

    override fun editAnimeEntry(id: Int) {
        Logger.d(LogTag) { "Edit entry $id" }
    }
}

fun katanaAnimeListsNavigator(navController: NavHostController): AnimeListsNavigator =
    KatanaAnimeListsNavigator(navController)

private const val LogTag = "AnimeListsNavigator"
