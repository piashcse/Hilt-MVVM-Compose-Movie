package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.piashcse.hilt_mvvm_compose_movie.R


@Composable
fun ExitAlertDialog(
    title: String,
    description: String,
    onDismiss: (isOpen: Boolean) -> Unit,
    onConfirm: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
            },
            // below line is use to display title of our dialog
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(text = description, fontSize = 16.sp)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onConfirm()
                    }) {
                    Text(
                        stringResource(R.string.yes),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = Color.Black)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismiss(false)
                    }) {
                    Text(
                        stringResource(R.string.no),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = Color.Black)
                    )
                }
            },
        )
    }
}