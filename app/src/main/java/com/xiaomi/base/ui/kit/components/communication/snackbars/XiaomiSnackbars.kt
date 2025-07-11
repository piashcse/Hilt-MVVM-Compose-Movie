package com.xiaomi.base.ui.kit.components.communication.snackbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import kotlinx.coroutines.launch

/**
 * Xiaomi Base UI Kit Snackbar
 */
@Composable
fun XiaomiSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionColor: Color = SnackbarDefaults.actionColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor
    )
}

@Composable
fun XiaomiSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { XiaomiSnackbar(it) }
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = snackbar
    )
}

@Composable
fun XiaomiSuccessSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        actionContentColor = MaterialTheme.colorScheme.onPrimary,
        action = if (actionLabel != null && onActionClick != null) {
            {
                TextButton(
                    onClick = onActionClick,
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(actionLabel)
                }
            }
        } else null,
        content = { Text(message) }
    )
}

@Composable
fun XiaomiErrorSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError,
        actionContentColor = MaterialTheme.colorScheme.onError,
        action = if (actionLabel != null && onActionClick != null) {
            {
                TextButton(
                    onClick = onActionClick,
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(actionLabel)
                }
            }
        } else null,
        content = { Text(message) }
    )
}

@Composable
fun XiaomiWarningSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        actionContentColor = MaterialTheme.colorScheme.onTertiary,
        action = if (actionLabel != null && onActionClick != null) {
            {
                TextButton(
                    onClick = onActionClick,
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(actionLabel)
                }
            }
        } else null,
        content = { Text(message) }
    )
}

@Composable
fun XiaomiInfoSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        actionContentColor = MaterialTheme.colorScheme.onSecondary,
        action = if (actionLabel != null && onActionClick != null) {
            {
                TextButton(
                    onClick = onActionClick,
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(actionLabel)
                }
            }
        } else null,
        content = { Text(message) }
    )
}

/**
 * Snackbar type enumeration for different message types
 */
enum class SnackbarType {
    Default,
    Success,
    Error,
    Warning,
    Info
}

/**
 * Helper function to show a snackbar with different types
 * 
 * @param hostState The snackbar host state
 * @param message The message to display
 * @param actionLabel Optional action button label
 * @param type The type of snackbar to show
 * @param duration How long to show the snackbar
 * @param withDismissAction Whether to show a dismiss action
 */
suspend fun showXiaomiSnackbar(
    hostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    type: SnackbarType = SnackbarType.Default,
    duration: SnackbarDuration = SnackbarDuration.Short,
    withDismissAction: Boolean = false
): SnackbarResult {
    return hostState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration
    )
}

// Preview composables for design system documentation
@Preview
@Composable
fun XiaomiSnackbarsPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            XiaomiSuccessSnackbar(
                message = "Success message",
                actionLabel = "Action",
                onActionClick = {}
            )
            XiaomiErrorSnackbar(
                message = "Error message",
                actionLabel = "Action",
                onActionClick = {}
            )
            XiaomiWarningSnackbar(
                message = "Warning message",
                actionLabel = "Action",
                onActionClick = {}
            )
            XiaomiInfoSnackbar(
                message = "Info message",
                actionLabel = "Action",
                onActionClick = {}
            )
        }
    }
}

@Preview(name = "Xiaomi Snackbars - Dark")
@Composable
fun XiaomiSnackbarsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            XiaomiSuccessSnackbar(
                message = "File saved successfully",
                actionLabel = "Open",
                onActionClick = { }
            )
            
            XiaomiErrorSnackbar(
                message = "Network connection failed",
                actionLabel = "Retry",
                onActionClick = { }
            )
        }
    }
}

@Preview(name = "Xiaomi Snackbar Host Example")
@Composable
fun XiaomiSnackbarHostPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    XiaomiPreviewTheme {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Snackbar Host Example", style = MaterialTheme.typography.titleMedium)
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                showXiaomiSnackbar(
                                    hostState = snackbarHostState,
                                    message = "This is a test message",
                                    actionLabel = "Action",
                                    type = SnackbarType.Default
                                )
                            }
                        }
                    ) {
                        Text("Show Snackbar")
                    }
                    
                    Button(
                        onClick = {
                            scope.launch {
                                showXiaomiSnackbar(
                                    hostState = snackbarHostState,
                                    message = "Success message",
                                    type = SnackbarType.Success,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    ) {
                        Text("Success")
                    }
                }
            }
            
            XiaomiSnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}