package dev.alvr.katana.features.lists.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.alvr.katana.core.ui.resources.value
import dev.alvr.katana.features.lists.ui.navigation.MangaListsNavigator
import dev.alvr.katana.features.lists.ui.resources.Res
import dev.alvr.katana.features.lists.ui.resources.empty_manga_list
import dev.alvr.katana.features.lists.ui.resources.manga_toolbar
import dev.alvr.katana.features.lists.ui.screens.components.ListScreen
import dev.alvr.katana.features.lists.ui.viewmodel.MangaListsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MangaScreen(
    navigator: MangaListsNavigator,
) {
    ListScreen(
        viewModel = koinViewModel<MangaListsViewModel>(),
        title = Res.string.manga_toolbar.value,
        emptyStateRes = Res.string.empty_manga_list.value,
        backContent = { Filter() },
        onEditEntryClick = navigator::editMangaEntry,
        onEntryDetailsClick = navigator::mangaEntryDetails,
    )
}

@Composable
private fun Filter() {
    Text(text = "Manga Filter")
}
