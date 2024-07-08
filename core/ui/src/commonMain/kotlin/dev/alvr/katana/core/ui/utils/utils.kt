package dev.alvr.katana.core.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination.Companion.hasRoute
import dev.alvr.katana.core.ui.screens.KatanaScreen
import kotlin.reflect.KClass
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest

val WindowInsets.Companion.noInsets: WindowInsets
    get() = WindowInsets(0)

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun isLandscape() = calculateWindowSizeClass().widthSizeClass > WindowWidthSizeClass.Medium

@Composable
fun doNavigation(onNavigation: () -> Unit) = dropUnlessResumed(block = onNavigation)

fun navDeepLink(deepLinkBuilder: NavDeepLink.Builder.() -> Unit): NavDeepLink =
    NavDeepLink.Builder().apply(deepLinkBuilder).build()

fun <T : KatanaScreen> NavBackStackEntry?.hasRoute(route: KClass<T>) = this?.destination?.hasRoute(route) ?: false
@Composable
fun imageRequest(builder: ImageRequest.Builder.() -> Unit) =
    ImageRequest.Builder(LocalPlatformContext.current)
        .apply(builder)
        .build()
