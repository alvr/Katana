package dev.alvr.katana.core.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dev.alvr.katana.core.common.annotations.KatanaInternalApi
import kotlinx.coroutines.launch

@Composable
@OptIn(KatanaInternalApi::class)
fun <S : UiState, E : UiEffect, I : UiIntent> KatanaViewModel<S, E, I>.collectAsState() =
    uiState.collectAsStateWithLifecycle(context = dispatcher.main)

@Composable
@OptIn(KatanaInternalApi::class)
@Suppress("ComposableFunctionName")
fun <S : UiState, E : UiEffect, I : UiIntent> KatanaViewModel<S, E, I>.collectEffect(
    onEffect: @DisallowComposableCalls suspend (E) -> Unit,
) {
    LifecycleStartEffect(effects) {
        val job = lifecycleScope.launch(dispatcher.main) {
            effects.collect { effect ->
                onEffect(effect)
            }
        }

        onStopOrDispose { job.cancel() }
    }
}
