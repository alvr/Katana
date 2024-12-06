package dev.alvr.katana.shared.viewmodel

import androidx.compose.runtime.Immutable
import dev.alvr.katana.core.ui.viewmodel.UiState

@Immutable
internal data class KatanaState(
    val loading: Boolean = true,
    val isSessionActive: Boolean = false,
) : UiState
