package dev.alvr.katana.shared.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.alvr.katana.core.ui.components.KatanaScaffold
import dev.alvr.katana.core.ui.components.navigation.KatanaNavigationBar
import dev.alvr.katana.core.ui.components.navigation.KatanaNavigationBarType
import dev.alvr.katana.core.ui.navigation.KatanaNavigationBarItem.Companion.hasRoute
import dev.alvr.katana.core.ui.utils.noInsets
import dev.alvr.katana.core.ui.viewmodel.collectAsState
import dev.alvr.katana.features.account.ui.navigation.account
import dev.alvr.katana.features.explore.ui.navigation.explore
import dev.alvr.katana.features.home.ui.navigation.HomeDestination
import dev.alvr.katana.features.home.ui.navigation.home
import dev.alvr.katana.features.lists.ui.navigation.lists
import dev.alvr.katana.shared.navigation.RootNavigator
import dev.alvr.katana.shared.navigation.rememberKatanaNavigator
import dev.alvr.katana.shared.viewmodel.KatanaViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun Katana(
    modifier: Modifier = Modifier,
    navigator: RootNavigator = rememberKatanaNavigator(),
    viewModel: KatanaViewModel = koinViewModel()
) {
    val currentNav by navigator.navController.currentBackStackEntryAsState()
    val uiState by viewModel.collectAsState()

    val navigationBar = @Composable { type: KatanaNavigationBarType ->
        KatanaNavigationBar(
            items = uiState.navigationBarItems,
            isSelected = { item -> currentNav.hasRoute(item) },
            onClick = { item -> navigator.onNavigationBarItemClicked(item) },
            type = type,
        )
    }

    Box(modifier = modifier) {
        KatanaScaffold(
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
                }
            }
        }

        AnimatedVisibility(uiState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
        }
    }
}
