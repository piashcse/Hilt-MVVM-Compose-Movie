package com.piashcse.hilt_mvvm_compose_movie.ui.component.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.piashcse.hilt_mvvm_compose_movie.ui.component.MediaGridShimmer

@Composable
fun BaseColumn(
    loading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit = { MediaGridShimmer() },
    content: @Composable () -> Unit,
) {
    var dismissedError by remember(errorMessage) { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (loading) loadingContent() else content()
    }

    if (errorMessage != null && !dismissedError) {
        ErrorAlert(errorMessage = errorMessage) { dismissedError = true }
    }
}

@Composable
fun ErrorAlert(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error") },
        text = { Text(text = errorMessage) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
    )
}
