package dev.alvr.katana.features.lists.ui.navigation

import dev.alvr.katana.core.ui.navigation.KatanaNavigator

sealed interface ListsNavigator : KatanaNavigator {
    fun navigateToEntryDetails(id: Int)
    fun showEditEntry(id: Int)
}
