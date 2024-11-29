package dev.alvr.katana.shared.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.alvr.katana.core.ui.components.navigation.KatanaNavigationBar
import dev.alvr.katana.core.ui.components.navigation.KatanaNavigationBarType
import dev.alvr.katana.core.ui.navigation.KatanaNavigationBarItem.Companion.hasRoute
import dev.alvr.katana.core.ui.utils.doNavigation
import dev.alvr.katana.core.ui.utils.noInsets
import dev.alvr.katana.core.ui.viewmodel.collectAsState
import dev.alvr.katana.core.ui.viewmodel.collectEffect
import dev.alvr.katana.features.account.ui.navigation.account
import dev.alvr.katana.features.explore.ui.navigation.explore
import dev.alvr.katana.features.home.ui.navigation.HomeDestination
import dev.alvr.katana.features.home.ui.navigation.home
import dev.alvr.katana.features.lists.ui.navigation.lists
import dev.alvr.katana.shared.navigation.KatanaNavigator
import dev.alvr.katana.shared.navigation.mainNavigationBarItems
import dev.alvr.katana.shared.navigation.rememberKatanaNavigator
import dev.alvr.katana.shared.viewmodel.KatanaEffect
import dev.alvr.katana.shared.viewmodel.KatanaViewModel
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
@OptIn(KoinExperimentalAPI::class)
internal fun Katana(
    modifier: Modifier = Modifier,
    navigator: KatanaNavigator = rememberKatanaNavigator(),
    viewModel: KatanaViewModel = koinNavViewModel()
) {
    val currentNav by navigator.navController.currentBackStackEntryAsState()

    val sessionExpired by rememberUpdatedState(doNavigation(navigator::onSessionExpired))
    viewModel.collectEffect { effect ->
        when (effect) {
            KatanaEffect.ExpiredToken -> sessionExpired()
        }
    }

    val state by viewModel.collectAsState()
    val homeNavigationBarItems = remember(state.isSessionActive) {
        mainNavigationBarItems.filter {
            !it.requireSession || it.requireSession && state.isSessionActive
        }.toImmutableList()
    }

    val navigationBar = remember(homeNavigationBarItems) {
        movableContentOf<KatanaNavigationBarType> { type ->
            KatanaNavigationBar(
                items = homeNavigationBarItems,
                isSelected = { item -> currentNav.hasRoute(item) },
                onClick = { item -> navigator.onNavigationBarItemClicked(item) },
                type = type,
            )
        }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.noInsets,
        bottomBar = { navigationBar(KatanaNavigationBarType.Bottom) },
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .displayCutoutPadding()
                .padding(paddingValues),
        ) {
            navigationBar(KatanaNavigationBarType.Rail)

            NavHost(
                modifier = Modifier,
                navController = navigator.navController,
                startDestination = HomeDestination.Root,
            ) {
                home(homeNavigator = navigator)
                lists(animeListsNavigator = navigator, mangaListsNavigator = navigator)
                explore(exploreNavigator = navigator)
                account(accountNavigator = navigator)

                expiredSessionDialog(navigator)
            }
        }
    }
}
