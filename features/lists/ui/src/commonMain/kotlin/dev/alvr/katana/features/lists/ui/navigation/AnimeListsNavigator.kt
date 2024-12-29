package dev.alvr.katana.features.lists.ui.navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.ui.navigation.KatanaNavigator
import dev.alvr.katana.core.ui.navigation.overridden
import dev.alvr.katana.core.ui.navigation.rememberKatanaNavigator

interface AnimeListsNavigator : KatanaNavigator {
    fun animeEntryDetails(id: Int)
    fun editAnimeEntry(id: Int)
}

private class KatanaAnimeListsNavigator : AnimeListsNavigator {
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

@Composable
fun rememberKatanaAnimeListsNavigator(): AnimeListsNavigator = rememberKatanaNavigator { _ ->
    KatanaAnimeListsNavigator()
}

private const val LogTag = "AnimeListsNavigator"
