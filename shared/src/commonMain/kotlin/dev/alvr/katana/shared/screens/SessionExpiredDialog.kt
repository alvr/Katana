package dev.alvr.katana.shared.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import dev.alvr.katana.core.ui.resources.value
import dev.alvr.katana.shared.destinations.RootDestination
import dev.alvr.katana.shared.navigation.RootNavigator
import dev.alvr.katana.shared.navigation.mainNavigationBarItems
import dev.alvr.katana.shared.resources.Res
import dev.alvr.katana.shared.resources.session_expired_error_confirm_button
import dev.alvr.katana.shared.resources.session_expired_error_message
import dev.alvr.katana.shared.resources.session_expired_error_title

internal fun NavGraphBuilder.expiredSessionDialog(navigator: RootNavigator) {
    dialog<RootDestination.ExpiredSessionDialog>(
        dialogProperties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        SessionExpiredDialog(
            onConfirmButton = { navigator.onNavigationBarItemClicked(mainNavigationBarItems.first()) },
        )
    }
}

@Composable
private fun SessionExpiredDialog(
    onConfirmButton: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { /* no-op */ },
        confirmButton = {
            TextButton(onClick = onConfirmButton) {
                Text(text = Res.string.session_expired_error_confirm_button.value)
            }
        },
        title = { Text(text = Res.string.session_expired_error_title.value) },
        text = { Text(text = Res.string.session_expired_error_message.value) },
    )
}
