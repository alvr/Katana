package dev.alvr.katana.features.lists.ui.navigation

import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.KatanaNavigator
import dev.alvr.katana.core.ui.navigation.overridden

interface MangaListsNavigator : KatanaNavigator {
    fun mangaEntryDetails(id: Int)
    fun editMangaEntry(id: Int)
}

private class KatanaMangaListsNavigator(
    override val navController: NavHostController,
) : MangaListsNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun mangaEntryDetails(id: Int) {
        Logger.d(LogTag) { "Entry details $id" }
    }

    override fun editMangaEntry(id: Int) {
        Logger.d(LogTag) { "Edit entry $id" }
    }
}

fun katanaMangaListsNavigator(navController: NavHostController): MangaListsNavigator =
    KatanaMangaListsNavigator(navController)

private const val LogTag = "MangaListsNavigator"
