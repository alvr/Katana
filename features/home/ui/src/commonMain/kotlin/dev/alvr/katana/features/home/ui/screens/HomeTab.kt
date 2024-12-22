package dev.alvr.katana.features.home.ui.screens

import dev.alvr.katana.features.home.ui.resources.Res
import dev.alvr.katana.features.home.ui.resources.tab_activity
import dev.alvr.katana.features.home.ui.resources.tab_for_you
import org.jetbrains.compose.resources.StringResource

internal enum class HomeTab(val title: StringResource) {
    ForYou(Res.string.tab_for_you),
    Activity(Res.string.tab_activity),
}
