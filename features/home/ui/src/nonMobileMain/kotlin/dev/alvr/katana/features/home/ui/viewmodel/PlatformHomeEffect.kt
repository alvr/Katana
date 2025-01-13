package dev.alvr.katana.features.home.ui.viewmodel

internal actual sealed interface PlatformHomeEffect : HomeEffect {
    data object ShowTokenInputDialog : PlatformHomeEffect
}
