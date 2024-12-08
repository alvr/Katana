package dev.alvr.katana.shared.viewmodel

import androidx.lifecycle.viewModelScope
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.ui.viewmodel.BaseViewModel
import dev.alvr.katana.core.ui.viewmodel.EmptyEffect
import org.orbitmvi.orbit.container

internal class KatanaViewModel(
    private val observeActiveSessionUseCase: ObserveActiveSessionUseCase,
) : BaseViewModel<KatanaState, EmptyEffect>() {
    override val container = viewModelScope.container<KatanaState, EmptyEffect>(KatanaState()) {
        observeActiveSession()
    }

    private fun observeActiveSession() {
        observeActiveSessionUseCase()

        intent {
            observeActiveSessionUseCase.flow.collect { active ->
                updateState { copy(loading = false) }

                active.fold(
                    ifLeft = {
                        updateState { copy(isSessionActive = false) }
                    },
                    ifRight = { isActive ->
                        updateState { copy(isSessionActive = isActive) }
                    },
                )
            }
        }
    }
}
