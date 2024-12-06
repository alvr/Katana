package dev.alvr.katana.core.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.annotation.OrbitDsl
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax

@Immutable
interface UiState

@Stable
interface UiEffect

@Stable
interface UiEvent

data object EmptyState : UiState

data object EmptyEffect : UiEffect

abstract class BaseViewModel<S : UiState, E : UiEffect> : ViewModel(), ContainerHost<S, E> {
    private val eventChannel = Channel<UiEvent>()

    @OrbitDsl
    protected fun createContainer(
        initialState: S,
        buildSettings: SettingsBuilder.() -> Unit = {},
        onCreate: (suspend Syntax<S, E>.() -> Unit)? = null
    ) = viewModelScope.container(initialState, buildSettings) {
        onCreate?.invoke(this)
        viewModelScope.launch { startEventCollector() }
    }

    @OrbitDsl
    protected fun updateState(block: S.() -> S) {
        intent {
            reduce { block(state) }
        }
    }

    @OrbitDsl
    @OptIn(OrbitExperimental::class)
    @Suppress("UnusedReceiverParameter")
    protected suspend fun Syntax<S, E>.updateState(block: S.() -> S) {
        subIntent {
            reduce { block(state) }
        }
    }

    @OrbitDsl
    protected fun postEffect(effect: E) {
        intent {
            postSideEffect(effect)
        }
    }

    @OrbitDsl
    @OptIn(OrbitExperimental::class)
    @Suppress("UnusedReceiverParameter")
    protected suspend fun Syntax<S, E>.postEffect(effect: E) {
        subIntent {
            postSideEffect(effect)
        }
    }

    @OrbitDsl
    fun postEvent(event: UiEvent) {
        viewModelScope.launch { eventChannel.send(event) }
    }

    @OptIn(OrbitExperimental::class)
    private suspend fun startEventCollector() {
        subIntent {
            eventChannel.consumeAsFlow().collect { event ->
                handleEvent(event)
            }
        }
    }

    protected open fun handleEvent(event: UiEvent) {
        // no-op
    }
}

@Composable
fun <S : UiState, E : UiEffect> BaseViewModel<S, E>.collectAsState(): State<S> =
    container.refCountStateFlow.collectAsStateWithLifecycle()

@Composable
@Suppress("ComposableFunctionName", "LambdaParameterInRestartableEffect")
fun <S : UiState, E : UiEffect> BaseViewModel<S, E>.collectEffect(
    onEffect: @DisallowComposableCalls suspend (E) -> Unit
) {
    val sideEffectFlow = container.refCountSideEffectFlow

    LifecycleStartEffect(sideEffectFlow) {
        val job = lifecycleScope.launch {
            sideEffectFlow.collect { effect ->
                onEffect(effect)
            }
        }

        onStopOrDispose { job.cancel() }
    }
}
