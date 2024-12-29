package dev.alvr.katana.features.lists.ui.viewmodel

import dev.alvr.katana.core.ui.viewmodel.UiIntent

internal sealed interface ListsIntent : UiIntent {
    data object Refresh : ListsIntent
    data class AddPlusOne(val id: Int) : ListsIntent
    data class SelectList(val name: String) : ListsIntent
    data class Search(val search: String) : ListsIntent
}
