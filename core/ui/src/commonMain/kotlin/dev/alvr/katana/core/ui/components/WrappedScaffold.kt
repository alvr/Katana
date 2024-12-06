package dev.alvr.katana.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

// https://android-review.googlesource.com/c/platform/frameworks/support/+/3312337/7/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/Scaffold.kt
@Composable
@OptIn(ExperimentalLayoutApi::class)
internal fun WrappedScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val safeInsets = remember(contentWindowInsets) {
        androidx.compose.foundation.layout.MutableWindowInsets(
            contentWindowInsets,
        )
    }
    Surface(
        modifier =
        modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor,
        contentColor = contentColor,
    ) {
        ScaffoldLayout(
            fabPosition = floatingActionButtonPosition,
            topBar = topBar,
            bottomBar = bottomBar,
            content = content,
            snackbar = snackbarHost,
            contentWindowInsets = safeInsets,
            fab = floatingActionButton,
        )
    }
}

@Composable
@Suppress(
    "LongMethod",
    "MagicNumber",
    "CognitiveComplexMethod",
    "CyclomaticComplexMethod",
)
private fun ScaffoldLayout(
    fabPosition: FabPosition,
    topBar: @Composable () -> Unit,
    snackbar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    contentWindowInsets: WindowInsets,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    // Create the backing value for the content padding
    // These values will be updated during measurement, but before subcomposing the body content
    // Remembering and updating a single PaddingValues avoids needing to recompose when the values
    // change
    val contentPadding = remember {
        object : PaddingValues {
            var topContentPadding by mutableStateOf(0.dp)
            var startContentPadding by mutableStateOf(0.dp)
            var endContentPadding by mutableStateOf(0.dp)
            var bottomContentPadding by mutableStateOf(0.dp)

            override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
                when (layoutDirection) {
                    LayoutDirection.Ltr -> startContentPadding
                    LayoutDirection.Rtl -> endContentPadding
                }

            override fun calculateTopPadding(): Dp = topContentPadding

            override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
                when (layoutDirection) {
                    LayoutDirection.Ltr -> endContentPadding
                    LayoutDirection.Rtl -> startContentPadding
                }

            override fun calculateBottomPadding(): Dp = bottomContentPadding
        }
    }

    SubcomposeLayout(modifier = Modifier.semantics { isTraversalGroup = true }) { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        // respect only bottom and horizontal for snackbar and fab
        val leftInset = contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection)
        val rightInset = contentWindowInsets.getRight(this@SubcomposeLayout, layoutDirection)
        val bottomInset = contentWindowInsets.getBottom(this@SubcomposeLayout)

        val topBarPlaceable =
            subcompose(ScaffoldLayoutContent.TopBar) {
                Box(
                    modifier =
                    Modifier.semantics {
                        isTraversalGroup = true
                        traversalIndex = 0f
                    },
                ) {
                    topBar()
                }
            }
                .first()
                .measure(looseConstraints)

        val snackbarPlaceable =
            subcompose(ScaffoldLayoutContent.Snackbar) {
                Box(
                    modifier =
                    Modifier.semantics {
                        isTraversalGroup = true
                        traversalIndex = 4f
                    },
                ) {
                    snackbar()
                }
            }
                .first()
                .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))

        val fabPlaceable =
            subcompose(ScaffoldLayoutContent.Fab) {
                Box(
                    modifier =
                    Modifier.semantics {
                        isTraversalGroup = true
                        traversalIndex = 2f
                    },
                ) {
                    fab()
                }
            }
                .first()
                .measure(looseConstraints.offset(-leftInset - rightInset, -bottomInset))

        val isFabEmpty = fabPlaceable.width == 0 && fabPlaceable.height == 0
        val fabPlacement =
            if (!isFabEmpty) {
                val fabWidth = fabPlaceable.width
                val fabHeight = fabPlaceable.height
                // FAB distance from the left of the layout, taking into account LTR / RTL
                val fabLeftOffset =
                    when (fabPosition) {
                        FabPosition.Start -> if (layoutDirection == LayoutDirection.Ltr) {
                            FabSpacing.roundToPx()
                        } else {
                            layoutWidth - FabSpacing.roundToPx() - fabWidth
                        }

                        FabPosition.End,
                        FabPosition.EndOverlay -> if (layoutDirection == LayoutDirection.Ltr) {
                            layoutWidth - FabSpacing.roundToPx() - fabWidth
                        } else {
                            FabSpacing.roundToPx()
                        }

                        else -> (layoutWidth - fabWidth) / 2
                    }

                FabPlacement(left = fabLeftOffset, width = fabWidth, height = fabHeight)
            } else {
                null
            }

        val bottomBarPlaceable =
            subcompose(ScaffoldLayoutContent.BottomBar) {
                Box(
                    modifier =
                    Modifier.semantics {
                        isTraversalGroup = true
                        traversalIndex = 1f
                    },
                ) {
                    bottomBar()
                }
            }
                .first()
                .measure(looseConstraints)

        val isBottomBarEmpty = bottomBarPlaceable.width == 0 && bottomBarPlaceable.height == 0

        val fabOffsetFromBottom =
            fabPlacement?.let {
                if (isBottomBarEmpty || fabPosition == FabPosition.EndOverlay) {
                    it.height +
                        FabSpacing.roundToPx() +
                        contentWindowInsets.getBottom(this@SubcomposeLayout)
                } else {
                    // Total height is the bottom bar height + the FAB height + the padding
                    // between the FAB and bottom bar
                    bottomBarPlaceable.height + it.height + FabSpacing.roundToPx()
                }
            }

        val snackbarHeight = snackbarPlaceable.height
        val snackbarOffsetFromBottom =
            if (snackbarHeight != 0) {
                snackbarHeight +
                    (
                        fabOffsetFromBottom
                            ?: bottomBarPlaceable.height.takeIf { !isBottomBarEmpty }
                            ?: contentWindowInsets.getBottom(this@SubcomposeLayout)
                        )
            } else {
                0
            }

        // Update the backing state for the content padding before subcomposing the body
        val insets = contentWindowInsets.asPaddingValues(this)
        contentPadding.topContentPadding =
            if (topBarPlaceable.width == 0 && topBarPlaceable.height == 0) {
                insets.calculateTopPadding()
            } else {
                topBarPlaceable.height.toDp()
            }
        contentPadding.bottomContentPadding =
            if (isBottomBarEmpty) {
                insets.calculateBottomPadding()
            } else {
                bottomBarPlaceable.height.toDp()
            }
        contentPadding.startContentPadding = insets.calculateStartPadding(layoutDirection)
        contentPadding.endContentPadding = insets.calculateEndPadding(layoutDirection)

        val bodyContentPlaceable =
            subcompose(ScaffoldLayoutContent.MainContent) {
                Box(
                    modifier =
                    Modifier.semantics {
                        isTraversalGroup = true
                        traversalIndex = 3f
                    },
                ) {
                    content(contentPadding)
                }
            }
                .first()
                .measure(looseConstraints)

        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable
            bodyContentPlaceable.place(0, 0)
            topBarPlaceable.place(0, 0)
            snackbarPlaceable.place(
                (layoutWidth - snackbarPlaceable.width) / 2 +
                    contentWindowInsets.getLeft(this@SubcomposeLayout, layoutDirection),
                layoutHeight - snackbarOffsetFromBottom,
            )
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceable.place(0, layoutHeight - bottomBarPlaceable.height)
            // Explicitly not using placeRelative here as `leftOffset` already accounts for RTL
            fabPlacement?.let { placement ->
                fabPlaceable.place(placement.left, layoutHeight - fabOffsetFromBottom!!)
            }
        }
    }
}

@Immutable
internal data class FabPlacement(val left: Int, val width: Int, val height: Int)

// FAB spacing above the bottom bar / bottom of the Scaffold
private val FabSpacing = 16.dp

private enum class ScaffoldLayoutContent {
    TopBar,
    MainContent,
    Snackbar,
    Fab,
    BottomBar
}
