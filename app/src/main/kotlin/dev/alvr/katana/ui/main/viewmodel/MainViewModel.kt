package dev.alvr.katana.ui.main.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alvr.katana.domain.base.usecases.invoke
import dev.alvr.katana.domain.base.usecases.sync
import dev.alvr.katana.domain.session.usecases.ClearActiveSessionUseCase
import dev.alvr.katana.domain.session.usecases.GetAnilistTokenUseCase
import dev.alvr.katana.domain.session.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.ui.base.viewmodel.BaseViewModel
import dev.alvr.katana.ui.login.navigation.LoginNavGraph
import dev.alvr.katana.ui.main.navigation.NavGraphs
import io.github.aakira.napier.Napier
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val clearActiveSessionUseCase: ClearActiveSessionUseCase,
    private val getAnilistTokenUseCase: GetAnilistTokenUseCase,
    private val observeActiveSessionUseCase: ObserveActiveSessionUseCase,
) : BaseViewModel<MainState, Nothing>() {
    override val container = container<MainState, Nothing>(
        MainState(initialNavGraph = initialNavGraph),
    ) {
        observeSession()
    }

    private val initialNavGraph
        get() = if (getAnilistTokenUseCase.sync().nonEmpty()) {
            NavGraphs.home
        } else {
            LoginNavGraph
        }

    fun clearSession() {
        intent {
            clearActiveSessionUseCase().tapLeft { failure ->
                Napier.e(failure) { "Error clearing session" }
            }
        }
    }

    private fun observeSession() {
        observeActiveSessionUseCase()

        intent {
            observeActiveSessionUseCase.flow.collect { active ->
                active.fold(
                    ifLeft = { failure ->
                        Napier.e(failure) { "Error observing the session, setting the as inactive" }
                        reduce { state.copy(isSessionActive = false) }
                    },
                    ifRight = { isActive ->
                        reduce { state.copy(isSessionActive = isActive) }
                    },
                )
            }
        }
    }
}
