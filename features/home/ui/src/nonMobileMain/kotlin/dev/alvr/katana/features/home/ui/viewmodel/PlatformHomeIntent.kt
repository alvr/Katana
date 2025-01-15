package dev.alvr.katana.features.home.ui.viewmodel

internal actual sealed interface PlatformHomeIntent : HomeIntent {
    sealed interface TokenInputIntent : PlatformHomeIntent {
        data object ShowTokenInputDialog : TokenInputIntent
        data class SaveTokenInput(val token: String) : TokenInputIntent
    }
}
