package dev.alvr.katana.core.ui.components.login

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.alvr.katana.core.common.empty
import dev.alvr.katana.core.ui.resources.Res
import dev.alvr.katana.core.ui.resources.login_dialog_login_button
import dev.alvr.katana.core.ui.resources.login_dialog_placeholder
import dev.alvr.katana.core.ui.resources.login_dialog_title
import dev.alvr.katana.core.ui.resources.value

@Composable
actual fun KatanaLoginDialog(
    onDismiss: () -> Unit,
    onLogin: (String) -> Unit,
) {
    var token by rememberSaveable { mutableStateOf(String.empty) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(Res.string.login_dialog_title.value) },
        text = {
            TextField(
                value = token,
                placeholder = { Text(Res.string.login_dialog_placeholder.value) },
                onValueChange = { newToken -> token = newToken },
                singleLine = true,
            )
        },
        confirmButton = {
            TextButton(onClick = { onLogin(token) }) {
                Text(Res.string.login_dialog_login_button.value)
            }
        },
    )
}
