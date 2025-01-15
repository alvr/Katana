package dev.alvr.katana.features.home.ui.screens.foryou.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.alvr.katana.core.ui.resources.value
import dev.alvr.katana.features.home.ui.ANILIST_LOGIN
import dev.alvr.katana.features.home.ui.ANILIST_REGISTER
import dev.alvr.katana.features.home.ui.resources.Res
import dev.alvr.katana.features.home.ui.resources.welcome_card_close_card_a11y
import dev.alvr.katana.features.home.ui.resources.welcome_card_login_button
import dev.alvr.katana.features.home.ui.resources.welcome_card_message
import dev.alvr.katana.features.home.ui.resources.welcome_card_register_button
import dev.alvr.katana.features.home.ui.resources.welcome_card_title
import dev.alvr.katana.features.home.ui.viewmodel.HomeIntent

@Composable
internal fun WelcomeCard(
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isCardVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        modifier = modifier,
        visible = isCardVisible,
        exit = fadeOut() + slideOutHorizontally { it },
    ) {
        ElevatedCard {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp,
                ),
            ) {
                WelcomeCardHeader(
                    onCloseCard = {
                        isCardVisible = false
                        onIntent(HomeIntent.ForYouIntent.CloseWelcomeCard)
                    },
                )

                WelcomeCardBody(
                    onIntent = onIntent,
                )
            }
        }
    }
}

@Composable
private fun WelcomeCardHeader(
    onCloseCard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = Res.string.welcome_card_title.value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
        )

        IconButton(onClick = onCloseCard) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = Res.string.welcome_card_close_card_a11y.value,
            )
        }
    }
}

@Composable
private fun WelcomeCardBody(
    onIntent: (HomeIntent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = Res.string.welcome_card_message.value,
            textAlign = TextAlign.Justify,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            WelcomeCardRegisterButton(Modifier.weight(1f))
            WelcomeCardLoginButton(
                modifier = Modifier.weight(1f),
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun WelcomeCardRegisterButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    OutlinedButton(
        modifier = modifier,
        onClick = { uriHandler.openUri(ANILIST_REGISTER) },
    ) {
        Text(
            text = Res.string.welcome_card_register_button.value,
        )
    }
}

@Composable
private fun WelcomeCardLoginButton(
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Button(
        modifier = modifier,
        onClick = {
            onLoginButtonClick(onIntent)
            uriHandler.openUri(ANILIST_LOGIN)
        },
    ) {
        Text(
            text = Res.string.welcome_card_login_button.value,
        )
    }
}

internal expect fun onLoginButtonClick(intent: (HomeIntent) -> Unit)
