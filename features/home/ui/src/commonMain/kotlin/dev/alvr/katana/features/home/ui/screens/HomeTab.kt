package dev.alvr.katana.features.home.ui.screens

import dev.alvr.katana.features.home.ui.resources.Res
import dev.alvr.katana.features.home.ui.resources.home_tab_activity
import dev.alvr.katana.features.home.ui.resources.home_tab_for_you
import org.jetbrains.compose.resources.StringResource

internal enum class HomeTab(val title: StringResource) {
    ForYou(Res.string.home_tab_for_you),
    Activity(Res.string.home_tab_activity),
}
