package dev.alvr.katana.shared.viewmodel

import androidx.compose.runtime.Immutable
import dev.alvr.katana.core.ui.viewmodel.UiState
import dev.alvr.katana.shared.navigation.MainNavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class KatanaState(
    val loading: Boolean = true,
    val sessionActive: Boolean = false,
    val navigationBarItems: ImmutableList<MainNavigationBarItem> = persistentListOf(),
) : UiState
