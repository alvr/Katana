package dev.alvr.katana.features.lists.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.BaseNavigator
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface AnimeListsNavigator : BaseNavigator {
    fun animeEntryDetails(id: Int)
    fun editAnimeEntry(id: Int)
}

private class KatanaAnimeListsNavigator : AnimeListsNavigator {
    override fun navigateBack() {
        Logger.i { "Implementation done in KatanaNavigator" }
    }

    override fun animeEntryDetails(id: Int) {
        Logger.d { "Entry details $id" }
    }

    override fun editAnimeEntry(id: Int) {
        Logger.d { "Edit entry $id" }
    }
}

@Composable
fun rememberKatanaAnimeListsNavigator(): AnimeListsNavigator = rememberKatanaNavigator { _ ->
    KatanaAnimeListsNavigator()
}
