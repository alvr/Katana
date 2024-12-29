package dev.alvr.katana.features.lists.ui.entities

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

internal typealias ListsCollection<T> = ImmutableMap<String, ImmutableList<T>>
