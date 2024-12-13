package dev.alvr.katana.features.account.ui.viewmodel

import androidx.compose.runtime.Immutable
import dev.alvr.katana.core.ui.viewmodel.UiState
import dev.alvr.katana.features.account.ui.entities.UserInfoUi

@Immutable
internal data class AccountState(
    val error: Boolean = false,
    val loading: Boolean = true,
    val userInfo: UserInfoUi? = null,
) : UiState
