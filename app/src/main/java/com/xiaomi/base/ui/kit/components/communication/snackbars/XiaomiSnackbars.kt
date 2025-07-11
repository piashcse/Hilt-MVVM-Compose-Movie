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
 * 
 * A Material Design 3 snackbar component with Xiaomi design tokens.
 * Provides brief feedback about an operation through a message at the bottom of the screen.
 * 
 * @param snackbarData The data for the snackbar including message and action
 * @param modifier Modifier to be applied to the snackbar
 * @param actionOnNewLine Whether to show the action on a new line
 * @param shape The shape of the snackbar
 * @param containerColor The background color of the snackbar
 * @param contentColor The color of the content inside the snackbar
 * @param actionColor The color of the action button
 * @param actionContentColor The color of the action button content
 * @param dismissActionContentColor The color of the dismiss action content
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

/**
 * Xiaomi Base UI Kit Snackbar Host
 * 
 * A host for displaying snackbars. This should be placed in your app's layout hierarchy.
 * 
 * @param hostState The state holder for the snackbar host
 * @param modifier Modifier to be applied to the snackbar host
 * @param snackbar The composable function to create the snackbar
 */
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

/**
 * Xiaomi Success Snackbar
 * 
 * A specialized snackbar for success messages with appropriate styling.
 * 
 * @param message The success message to display
 * @param actionLabel Optional action button label
 * @param onActionClick Callback for action button click
 * @param modifier Modifier to be applied to the snackbar
 */
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
        actionColor = MaterialTheme.colorScheme.onPrimary,
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
        } else null
    ) {
        Text(message)
    }
}

/**
 * Xiaomi Error Snackbar
 * 
 * A specialized snackbar for error messages with appropriate styling.
 * 
 * @param message The error message to display
 * @param actionLabel Optional action button label
 * @param onActionClick Callback for action button click
 * @param modifier Modifier to be applied to the snackbar
 */
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
        actionColor = MaterialTheme.colorScheme.onError,
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
        } else null
    ) {
        Text(message)
    }
}

/**
 * Xiaomi Warning Snackbar
 * 
 * A specialized snackbar for warning messages with appropriate styling.
 * 
 * @param message The warning message to display
 * @param actionLabel Optional action button label
 * @param onActionClick Callback for action button click
 * @param modifier Modifier to be applied to the snackbar
 */
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
        actionColor = MaterialTheme.colorScheme.onTertiary,
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
        } else null
    ) {
        Text(message)
    }
}

/**
 * Xiaomi Info Snackbar
 * 
 * A specialized snackbar for informational messages with appropriate styling.
 * 
 * @param message The info message to display
 * @param actionLabel Optional action button label
 * @param onActionClick Callback for action button click
 * @param modifier Modifier to be applied to the snackbar
 */
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
        actionColor = MaterialTheme.colorScheme.onSecondary,
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
        } else null
    ) {
        Text(message)
    }
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
@Preview(name = "Xiaomi Snackbars - Light")
@Composable
fun XiaomiSnackbarsPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Snackbar Types", style = MaterialTheme.typography.titleMedium)
            
            // Default snackbar
            Snackbar(
                action = {
                    TextButton(onClick = { }) {
                        Text("Action")
                    }
                }
            ) {
                Text("This is a default snackbar message")
            }
            
            // Success snackbar
            XiaomiSuccessSnackbar(
                message = "Operation completed successfully!",
                actionLabel = "View",
                onActionClick = { }
            )
            
            // Error snackbar
            XiaomiErrorSnackbar(
                message = "An error occurred. Please try again.",
                actionLabel = "Retry",
                onActionClick = { }
            )
            
            // Warning snackbar
            XiaomiWarningSnackbar(
                message = "Warning: This action cannot be undone.",
                actionLabel = "Understand",
                onActionClick = { }
            )
            
            // Info snackbar
            XiaomiInfoSnackbar(
                message = "New features are available in settings.",
                actionLabel = "Settings",
                onActionClick = { }
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