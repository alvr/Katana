package dev.alvr.katana.shared.viewmodel

import arrow.core.left
import arrow.core.right
import dev.alvr.katana.common.session.domain.failures.SessionFailure
import dev.alvr.katana.common.session.domain.usecases.ObserveActiveSessionUseCase
import dev.alvr.katana.core.domain.usecases.invoke
import dev.alvr.katana.core.tests.orbitTestScope
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.test.test

internal class KatanaViewModelTest : BehaviorSpec() {
    private val observeActiveSession = mockk<ObserveActiveSessionUseCase>()

    private lateinit var viewModel: KatanaViewModel

    init {
        given("an user") {
            `when`("observing if the user has an active session") {
                and("the user has an active session") {
                    then("it should update the state with the active session") {
                        justRun { observeActiveSession() }
                        every { observeActiveSession.flow } returns flowOf(true.right())

                        viewModel.test(orbitTestScope) {
                            runOnCreate()
                            expectInitialState()
                            expectState { copy(loading = false, isSessionActive = true) }
                            verifyMocks()
                        }
                    }

                    and("the session expires") {
                        then("it should update the state without the active session") {
                            justRun { observeActiveSession() }
                            every { observeActiveSession.flow } returns flowOf(true.right(), false.right())

                            viewModel.test(orbitTestScope) {
                                runOnCreate()
                                expectInitialState()
//                                expectState { copy(loading = false, isSessionActive = true) }
                                expectState { copy(loading = false, isSessionActive = false) }
                                verifyMocks()
                            }
                        }
                    }
                }

                and("the user does not have an active session") {
                    then("it should update the state without the active session") {
                        justRun { observeActiveSession() }
                        every { observeActiveSession.flow } returns flowOf(false.right())

                        viewModel.test(orbitTestScope) {
                            runOnCreate()
                            expectInitialState()
                            expectState { copy(loading = false, isSessionActive = false) }
                            verifyMocks()
                        }
                    }
                }

                and("an error occurs") {
                    then("it should update the state without the active session") {
                        justRun { observeActiveSession() }
                        every { observeActiveSession.flow } returns flowOf(SessionFailure.CheckingActiveSession.left())

                        viewModel.test(orbitTestScope) {
                            runOnCreate()
                            expectInitialState()
                            expectState { copy(loading = false, isSessionActive = false) }
                            verifyMocks()
                        }
                    }
                }
            }
        }
    }

    override suspend fun beforeEach(testCase: TestCase) {
        clearMocks(observeActiveSession)

        viewModel = KatanaViewModel(observeActiveSession)
    }

    private fun verifyMocks() {
        verify(exactly = 1) { observeActiveSession() }
        verify(exactly = 1) { observeActiveSession.flow }
    }
}
