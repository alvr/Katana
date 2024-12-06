package dev.alvr.katana.shared.viewmodel

import androidx.lifecycle.viewModelScope
import dev.alvr.katana.common.session.domain.usecases.ClearActiveSessionUseCase
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container

internal class KatanaViewModel(
    private val clearActiveSessionUseCase: ClearActiveSessionUseCase,
    private val observeActiveSessionUseCase: ObserveActiveSessionUseCase,
) : BaseViewModel<KatanaState, KatanaEffect>() {
    override val container = viewModelScope.container<KatanaState, KatanaEffect>(KatanaState()) {
        viewModelScope.launch { observeActiveSession() }
    }

    @OptIn(OrbitExperimental::class)
    private suspend fun observeActiveSession() {
        observeActiveSessionUseCase()

        subIntent {
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
