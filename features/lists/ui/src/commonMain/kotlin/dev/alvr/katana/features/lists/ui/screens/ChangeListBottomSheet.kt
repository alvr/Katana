package dev.alvr.katana.features.lists.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alvr.katana.core.ui.resources.value
import dev.alvr.katana.features.lists.ui.entities.UserList
import dev.alvr.katana.features.lists.ui.resources.Res
import dev.alvr.katana.features.lists.ui.resources.change_list_button
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ChangeListSheet(
    isVisible: Boolean,
    lists: Array<UserList>,
    selectedList: String,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit,
) {
    if (!isVisible) return

    val sheetState = rememberModalBottomSheetState(true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
    ) {
        Column(
            modifier = Modifier.navigationBarsPadding(),
        ) {
            lists.forEach { (name, count) ->
                Text(
                    text = buildAnnotatedString {
                        append(name)
                        withStyle(
                            SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        ) {
                            append(" $count")
                        }
                    },
                    fontWeight = if (selectedList == name) FontWeight.SemiBold else FontWeight.Normal,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onClick(name)
                            }
                        }
                        .height(48.dp)
                        .padding(all = 8.dp),
                )
            }
        }
    }
}

@Composable
internal fun ChangeListButton(
    visible: Boolean,
    onClick: () -> Unit,
) {
    if (visible) {
        FloatingActionButton(onClick = onClick) {
            Icon(
                contentDescription = Res.string.change_list_button.value,
                imageVector = Icons.AutoMirrored.TwoTone.List,
            )
        }
    }
}
