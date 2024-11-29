package dev.alvr.katana.features.home.ui.viewmodel

import androidx.lifecycle.viewModelScope
import dev.alvr.katana.common.session.domain.usecases.ClearActiveSessionUseCase
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.ui.viewmodel.BaseViewModel
import org.orbitmvi.orbit.container

internal class HomeViewModel(
    private val observeActiveSessionUseCase: ObserveActiveSessionUseCase,
    private val clearActiveSessionUseCase: ClearActiveSessionUseCase,
) : BaseViewModel<HomeState, HomeEffect>() {
    override val container = viewModelScope.container<HomeState, HomeEffect>(HomeState()) {
        observeSession()
    }

    fun clearSession() {
        intent {
            clearActiveSessionUseCase()
        }
    }

    private fun observeSession() {
        observeActiveSessionUseCase()

        intent {
            observeActiveSessionUseCase.flow.collect { active ->
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
