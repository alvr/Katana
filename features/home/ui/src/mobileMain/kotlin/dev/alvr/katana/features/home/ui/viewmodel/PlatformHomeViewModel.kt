package dev.alvr.katana.features.home.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.common.session.domain.usecases.SaveSessionUseCase
import dev.alvr.katana.common.user.domain.usecases.SaveUserIdUseCase
import dev.alvr.katana.features.home.domain.usecases.HideWelcomeCardUseCase
import dev.alvr.katana.features.home.domain.usecases.ObserveWelcomeCardVisibilityUseCase

internal actual class PlatformHomeViewModel actual constructor(
    savedStateHandle: SavedStateHandle,
    hideWelcomeCardUseCase: HideWelcomeCardUseCase,
    observeActiveSessionUseCase: ObserveActiveSessionUseCase,
    observeWelcomeCardVisibilityUseCase: ObserveWelcomeCardVisibilityUseCase,
    saveSessionUseCase: SaveSessionUseCase,
    saveUserIdUseCase: SaveUserIdUseCase,
) : HomeViewModel(
    savedStateHandle = savedStateHandle,
    hideWelcomeCardUseCase = hideWelcomeCardUseCase,
    observeActiveSessionUseCase = observeActiveSessionUseCase,
    observeWelcomeCardVisibilityUseCase = observeWelcomeCardVisibilityUseCase,
    saveSessionUseCase = saveSessionUseCase,
    saveUserIdUseCase = saveUserIdUseCase,
) {
    override fun platformHandleIntent(intent: PlatformHomeIntent) {
        /* no-op */
    }
}
