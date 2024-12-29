package dev.alvr.katana.features.lists.ui.entities.mappers

import dev.alvr.katana.features.lists.ui.entities.ListsCollection
import dev.alvr.katana.features.lists.ui.entities.MediaListItem
import dev.alvr.katana.features.lists.ui.entities.UserList
import kotlinx.collections.immutable.toImmutableList

internal fun <I : MediaListItem> ListsCollection<I>.toUserList() =
    entries.map { entry -> UserList(entry.key to entry.value.size) }.toImmutableList()
