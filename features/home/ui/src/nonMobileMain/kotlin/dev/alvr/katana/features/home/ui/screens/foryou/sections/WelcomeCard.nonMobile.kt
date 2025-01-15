package dev.alvr.katana.features.home.ui.screens.foryou.sections

import dev.alvr.katana.features.home.ui.viewmodel.HomeIntent
import dev.alvr.katana.features.home.ui.viewmodel.PlatformHomeIntent

internal actual fun onLoginButtonClick(intent: (HomeIntent) -> Unit) {
    intent(PlatformHomeIntent.TokenInputIntent.ShowTokenInputDialog)
}
