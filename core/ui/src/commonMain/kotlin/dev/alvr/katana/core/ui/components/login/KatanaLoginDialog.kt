package dev.alvr.katana.core.ui.components.login

import androidx.compose.runtime.Composable

@Composable
expect fun KatanaLoginDialog(
    onDismiss: () -> Unit,
    onLogin: (String) -> Unit,
)
