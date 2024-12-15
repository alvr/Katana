package dev.alvr.katana.shared.viewmodel

import androidx.compose.runtime.Stable
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.core.ui.viewmodel.EmptyEffect
import dev.alvr.katana.core.ui.viewmodel.EmptyIntent
import dev.alvr.katana.core.ui.viewmodel.KatanaBaseViewModel

@Stable
internal class KatanaViewModel(
    private val observeActiveSessionUseCase: ObserveActiveSessionUseCase,
) : KatanaBaseViewModel<KatanaState, EmptyEffect, EmptyIntent>(KatanaState()) {
    override fun init() {
        observeActiveSession()
    }

    private fun observeActiveSession() {
        execute(
            useCase = observeActiveSessionUseCase,
            params = Unit,
            onError = { state { copy(loading = false, sessionActive = false) } },
            onSuccess = { isActive ->
                state { copy(loading = false, sessionActive = isActive) }
            },
        )
    }
}
