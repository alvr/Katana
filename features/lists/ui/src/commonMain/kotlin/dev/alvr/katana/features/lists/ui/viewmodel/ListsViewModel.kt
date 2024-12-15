package dev.alvr.katana.features.lists.ui.viewmodel

import androidx.compose.runtime.Stable
import co.touchlab.kermit.Logger
import dev.alvr.katana.core.common.empty
import dev.alvr.katana.core.domain.usecases.FlowEitherUseCase
import dev.alvr.katana.core.ui.viewmodel.KatanaBaseViewModel
import dev.alvr.katana.features.lists.domain.models.MediaCollection
import dev.alvr.katana.features.lists.domain.models.entries.MediaEntry
import dev.alvr.katana.features.lists.domain.models.lists.MediaListGroup
import dev.alvr.katana.features.lists.domain.usecases.UpdateListUseCase
import dev.alvr.katana.features.lists.ui.entities.MediaListItem
import dev.alvr.katana.features.lists.ui.entities.mappers.toMediaList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

@Stable
internal sealed class ListsViewModel<E : MediaEntry, I : MediaListItem>(
    type: ListsState.ListType,
    private val updateListUseCase: UpdateListUseCase,
) : KatanaBaseViewModel<ListsState<I>, ListsEffect, ListsIntent>(ListsState(type)) {
    protected abstract val observeListUseCase: FlowEitherUseCase<Unit, MediaCollection<E>>

    protected abstract fun List<MediaListGroup<E>>.entryMap(): ImmutableList<I>

    override fun init() {
        observeLists()
    }

    override fun handleIntent(intent: ListsIntent) {
        when (intent) {
            is ListsIntent.Refresh -> observeLists()
            is ListsIntent.AddPlusOne -> addPlusOne(intent.id)
            is ListsIntent.SelectList -> selectList(intent.name)
            is ListsIntent.Search -> search(intent.search)
        }
    }

    private fun observeLists() {
        state {
            Logger.d("Loading lists")
            copy(loading = true)
        }
        execute(
            useCase = observeListUseCase,
            params = Unit,
            onFailure = {
                state {
                    copy(
                        collection = persistentMapOf(),
                        items = persistentListOf(),
                        selectedList = String.empty,
                        error = true,
                        loading = false,
                    )
                }
                effect(ListsEffect.LoadingListsFailure)
            },
            onSuccess = { media ->
                val collection = media.lists
                    .groupBy { it.name }
                    .mapValues { it.value.entryMap() }
                    .toImmutableMap()

                state {
                    val selectedList = selectedList.ifEmpty { collection.keys.firstOrNull().orEmpty() }

                    copy(
                        collection = collection,
                        items = collection.getOrElse(selectedList) { persistentListOf() },
                        selectedList = selectedList,
                        error = false,
                        loading = false,
                    )
                }
            },
        )
    }

    private fun addPlusOne(id: Int) {
        val listItem = currentState.items.firstOrNull { it.entryId == id } ?: return
        val entry = listItem.toMediaList().copy(progress = listItem.progress.inc())

        execute(
            useCase = updateListUseCase,
            params = entry,
            onSuccess = { /* no-op */ },
            onFailure = { effect(ListsEffect.AddPlusOneFailure) },
        )
    }

    private fun selectList(name: String) {
        state {
            copy(
                items = collection.getOrElse(name) { persistentListOf() },
                selectedList = name,
            )
        }
    }

    private fun search(search: String) {
        state {
            val filtered = entries.filter { item ->
                item.title.contains(search, ignoreCase = true)
            }.toImmutableList()

            copy(items = filtered)
        }
    }
}
