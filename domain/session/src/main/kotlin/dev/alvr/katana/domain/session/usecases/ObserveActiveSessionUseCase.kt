package dev.alvr.katana.domain.session.usecases

import dev.alvr.katana.domain.base.usecases.FlowUseCase
import dev.alvr.katana.domain.session.repositories.SessionRepository

class ObserveActiveSessionUseCase(
    private val repository: SessionRepository,
) : FlowUseCase<Unit, Boolean>() {
    override fun createFlow(params: Unit) = repository.sessionActive
}
