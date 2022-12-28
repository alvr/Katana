package dev.alvr.katana.ui.lists.view.components

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.alvr.katana.common.core.zero
import dev.alvr.katana.ui.base.components.KatanaEmptyState
import dev.alvr.katana.ui.base.components.KatanaErrorState
import dev.alvr.katana.ui.base.components.home.KatanaHomeScaffold
import dev.alvr.katana.ui.base.components.home.rememberKatanaHomeScaffoldState
import dev.alvr.katana.ui.lists.R
import dev.alvr.katana.ui.lists.entities.MediaListItem
import dev.alvr.katana.ui.lists.navigation.ListsNavigator
import dev.alvr.katana.ui.lists.view.destinations.ChangeListSheetDestination
import dev.alvr.katana.ui.lists.view.destinations.EditEntrySheetDestination
import dev.alvr.katana.ui.lists.viewmodel.AnimeListsViewModel
import dev.alvr.katana.ui.lists.viewmodel.ListsViewModel
import dev.alvr.katana.ui.lists.viewmodel.MangaListsViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
internal fun ListScreen(
    vm: ListsViewModel<*, *>,
    navigator: ListsNavigator,
    @StringRes title: Int,
    @StringRes emptyStateRes: Int,
    backContent: @Composable () -> Unit,
    changeListResult: ResultRecipient<ChangeListSheetDestination, String>,
    editEntryResult: ResultRecipient<EditEntrySheetDestination, MediaListItem>,
    modifier: Modifier = Modifier,
) {
    val state by vm.collectAsState()
    val katanaScaffoldState = rememberKatanaHomeScaffoldState()
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    changeListResult.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> vm.selectList(result.value).also {
                coroutineScope.launch { lazyGridState.scrollToItem(Int.zero) }
                katanaScaffoldState.resetToolbar()
            }
        }
    }

    editEntryResult.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> vm.updateEntry(result.value)
        }
    }

    val searchPlaceholder = when (vm) {
        is AnimeListsViewModel -> R.string.lists_toolbar_search_anime_placeholder
        is MangaListsViewModel -> R.string.lists_toolbar_search_manga_placeholder
    }.let { stringResource(it) }

    val buttonsVisible by remember(state.isError) {
        derivedStateOf(structuralEqualityPolicy()) { !state.isError }
    }
    katanaScaffoldState.showTopAppBarActions = buttonsVisible

    KatanaHomeScaffold(
        katanaScaffoldState = katanaScaffoldState,
        title = title,
        subtitle = state.name,
        searchPlaceholder = searchPlaceholder,
        onSearch = vm::search,
        backContent = backContent,
        fab = {
            ChangeListButton(visible = buttonsVisible && vm.userLists.isNotEmpty()) {
                navigator.listSelector(vm.userLists, state.name.orEmpty())
            }
        },
    ) { paddingValues ->
        with(state) {
            when {
                isError -> KatanaErrorState(
                    modifier = modifier.padding(paddingValues),
                    text = stringResource(R.string.lists_error_message),
                    onRetry = {
                        vm.refreshList()
                        katanaScaffoldState.resetToolbar()
                    },
                    loading = state.isLoading,
                )
                isEmpty && !isLoading -> KatanaEmptyState(
                    modifier = modifier.padding(paddingValues),
                    text = stringResource(emptyStateRes),
                )
                else -> MediaList(
                    lazyGridState = lazyGridState,
                    modifier = modifier.padding(paddingValues),
                    listState = state,
                    onRefresh = vm::refreshList,
                    onAddPlusOne = vm::addPlusOne,
                    onEditEntry = navigator::listsEditEntry,
                    onEntryDetails = navigator::listsEntryDetails,
                )
            }
        }
    }
}
