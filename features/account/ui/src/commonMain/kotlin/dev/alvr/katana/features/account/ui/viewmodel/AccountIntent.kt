package dev.alvr.katana.features.account.ui.viewmodel

import dev.alvr.katana.core.ui.viewmodel.UiIntent

sealed interface AccountIntent : UiIntent {

    data object Logout : AccountIntent
}
