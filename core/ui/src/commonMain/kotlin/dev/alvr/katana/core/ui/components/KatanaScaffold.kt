package dev.alvr.katana.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.alvr.katana.core.ui.utils.noInsets
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@Composable
@OptIn(ExperimentalHazeMaterialsApi::class)
fun KatanaScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarHost: @Composable () -> Unit = { KatanaSnackbarHost(hostState = snackbarHostState) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = WindowInsets.noInsets,
    hazeState: HazeState = remember { HazeState() },
    blurTopBar: Boolean = false,
    blurBottomBar: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    val rememberedTopBar = remember(topBar) { movableContentOf(topBar) }
    val rememberedBottomBar = remember(bottomBar) { movableContentOf(bottomBar) }

    WrappedScaffold(
        modifier = modifier,
        topBar = {
            if (blurTopBar) {
                Box(
                    modifier = Modifier.hazeChild(state = hazeState, style = HazeMaterials.thin()),
                ) {
                    rememberedTopBar()
                }
            } else {
                rememberedTopBar()
            }
        },
        bottomBar = {
            if (blurBottomBar) {
                Box(
                    modifier = Modifier.hazeChild(state = hazeState, style = HazeMaterials.thin()),
                ) {
                    rememberedBottomBar()
                }
            } else {
                rememberedBottomBar()
            }
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { contentPadding ->
        Box(Modifier.haze(state = hazeState)) {
            content(contentPadding)
        }
    }
}
