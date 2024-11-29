package dev.alvr.katana.features.lists.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface MangaListsNavigator : BaseNavigator {
    fun mangaEntryDetails(id: Int)
    fun editMangaEntry(id: Int)
}

private class KatanaMangaListsNavigator : MangaListsNavigator {
    override fun navigateBack() {
        Logger.i { "Implementation done in KatanaNavigator" }
    }

    override fun mangaEntryDetails(id: Int) {
        Logger.d { "Entry details $id" }
    }

    override fun editMangaEntry(id: Int) {
        Logger.d { "Edit entry $id" }
    }
}

@Composable
fun rememberKatanaMangaListsNavigator(): MangaListsNavigator = rememberKatanaNavigator { _ ->
    KatanaMangaListsNavigator()
}
