package dev.alvr.katana.features.lists.ui.viewmodel

import dev.alvr.katana.core.common.empty
import dev.alvr.katana.core.ui.viewmodel.UiState
import dev.alvr.katana.features.lists.ui.entities.ListsCollection
import dev.alvr.katana.features.lists.ui.entities.MediaListItem
import dev.alvr.katana.features.lists.ui.entities.mappers.toUserList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

internal data class ListsState<T : MediaListItem>(
    val type: ListType,
    val collection: ListsCollection<T> = persistentMapOf(),
    val items: ImmutableList<T> = persistentListOf(),
    val selectedList: String = String.empty,
    val error: Boolean = false,
    val loading: Boolean = true,
) : UiState {

    val empty get() = items.isEmpty()

    val entries get() = collection.getOrElse(selectedList) { persistentListOf() }

    val lists get() = collection.toUserList()

    enum class ListType {
        Anime,
        Manga,
    }
}
