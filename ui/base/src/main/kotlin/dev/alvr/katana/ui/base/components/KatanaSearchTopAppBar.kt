package dev.alvr.katana.ui.base.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.BackgroundOpacity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import dev.alvr.katana.ui.base.R

@Composable
fun KatanaSearchTopAppBar(
    search: String,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onClear: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(focusManager) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = search,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.toolbar_search_close),
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = stringResource(R.string.toolbar_search_clear),
                )
            }
        },
        singleLine = true,
        shape = RectangleShape,
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onSurface.copy(alpha = BackgroundOpacity),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}
