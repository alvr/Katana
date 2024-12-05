package dev.alvr.katana.features.lists.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.overridden
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface MangaListsNavigator : BaseNavigator {
    fun mangaEntryDetails(id: Int)
    fun editMangaEntry(id: Int)
}

private class KatanaMangaListsNavigator : MangaListsNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun mangaEntryDetails(id: Int) {
        Logger.d(LOG_TAG) { "Entry details $id" }
    }

    override fun editMangaEntry(id: Int) {
        Logger.d(LOG_TAG) { "Edit entry $id" }
    }
}

@Composable
fun rememberKatanaMangaListsNavigator(): MangaListsNavigator = rememberKatanaNavigator { _ ->
    KatanaMangaListsNavigator()
}

private const val LOG_TAG = "MangaListsNavigator"
