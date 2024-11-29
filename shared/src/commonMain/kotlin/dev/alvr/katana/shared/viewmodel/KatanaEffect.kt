package dev.alvr.katana.shared.viewmodel

import androidx.compose.runtime.Stable
import dev.alvr.katana.core.ui.viewmodel.UiEffect

@Stable
sealed interface KatanaEffect : UiEffect {
    data object ExpiredToken : KatanaEffect
}
