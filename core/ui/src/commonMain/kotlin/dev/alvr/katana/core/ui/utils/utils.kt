package dev.alvr.katana.core.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination.Companion.hasRoute
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import dev.alvr.katana.core.ui.navigation.KatanaDestination
import kotlin.reflect.KClass

val WindowInsets.Companion.noInsets: WindowInsets
    get() = WindowInsets(0)

val WindowInsets.Companion.scaffoldContent: WindowInsets
    get() = WindowInsets(left = 8.dp, top = 8.dp, right = 8.dp, bottom = 8.dp)

@Composable
fun isLandscape() = calculateWindowSizeClass().widthSizeClass > WindowWidthSizeClass.Medium

fun navDeepLink(deepLinkBuilder: NavDeepLink.Builder.() -> Unit): NavDeepLink =
    NavDeepLink.Builder().apply(deepLinkBuilder).build()

fun <T : KatanaDestination> NavBackStackEntry?.hasRoute(route: KClass<T>) =
    this?.destination?.hasRoute(route) ?: false

fun <T : KatanaDestination> NavBackStackEntry?.hasParentRoute(route: KClass<T>) =
    this?.destination?.parent?.hasRoute(route) ?: false

@Composable
internal expect fun calculateWindowSizeClass(): WindowSizeClass
@Composable
fun imageRequest(builder: ImageRequest.Builder.() -> Unit) =
    ImageRequest.Builder(LocalPlatformContext.current)
        .apply(builder)
        .build()
