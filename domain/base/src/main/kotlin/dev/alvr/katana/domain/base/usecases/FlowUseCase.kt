package dev.alvr.katana.domain.base.usecases

import arrow.core.Either
import dev.alvr.katana.domain.base.failures.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class FlowUseCase<in P, out R> {
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val flow: Flow<Either<Failure, R>> = paramState.flatMapLatest {
        createFlow(it).distinctUntilChanged()
    }

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract fun createFlow(params: P): Flow<Either<Failure, R>>
}

operator fun <R> FlowUseCase<Unit, R>.invoke() {
    invoke(Unit)
}
