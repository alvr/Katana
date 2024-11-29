package dev.alvr.katana.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.alvr.katana.core.ui.navigation.KatanaDestination
import dev.alvr.katana.core.ui.navigation.KatanaNavigationBarItem
import dev.alvr.katana.features.account.ui.navigation.AccountDestination
import dev.alvr.katana.features.explore.ui.navigation.ExploreDestination
import dev.alvr.katana.features.home.ui.navigation.HomeDestination
import dev.alvr.katana.features.lists.ui.navigation.AnimeListsDestination
import dev.alvr.katana.features.lists.ui.navigation.MangaListsDestination
import dev.alvr.katana.shared.resources.Res
import dev.alvr.katana.shared.resources.navigation_bar_account
import dev.alvr.katana.shared.resources.navigation_bar_anime
import dev.alvr.katana.shared.resources.navigation_bar_explore
import dev.alvr.katana.shared.resources.navigation_bar_home
import dev.alvr.katana.shared.resources.navigation_bar_manga
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.StringResource

@Immutable
@Suppress("UseDataClass")
private class MainNavigationBar(
    override val screen: KatanaDestination,
    override val selectedIcon: ImageVector,
    override val unselectedIcon: ImageVector,
    override val label: StringResource,
    override val requireSession: Boolean,
) : MainNavigationBarItem

internal interface MainNavigationBarItem : KatanaNavigationBarItem {
    val requireSession: Boolean
}

internal val mainNavigationBarItems: ImmutableList<MainNavigationBarItem> = persistentListOf(
    MainNavigationBar(
        screen = HomeDestination.Root,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.navigation_bar_home,
        requireSession = false,
    ),
    MainNavigationBar(
        screen = AnimeListsDestination.Root,
        selectedIcon = Icons.Filled.VideoLibrary,
        unselectedIcon = Icons.Outlined.VideoLibrary,
        label = Res.string.navigation_bar_anime,
        requireSession = true,
    ),
    MainNavigationBar(
        screen = MangaListsDestination.Root,
        selectedIcon = Icons.AutoMirrored.Filled.LibraryBooks,
        unselectedIcon = Icons.AutoMirrored.Outlined.LibraryBooks,
        label = Res.string.navigation_bar_manga,
        requireSession = true,
    ),
    MainNavigationBar(
        screen = ExploreDestination.Root,
        selectedIcon = Icons.Filled.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        label = Res.string.navigation_bar_explore,
        requireSession = false,
    ),
    MainNavigationBar(
        screen = AccountDestination.Root,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        label = Res.string.navigation_bar_account,
        requireSession = false,
    ),
)
