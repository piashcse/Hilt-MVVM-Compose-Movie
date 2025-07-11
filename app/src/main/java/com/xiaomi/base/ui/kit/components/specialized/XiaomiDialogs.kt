package com.xiaomi.base.ui.kit.components.specialized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Xiaomi Alert Dialog
 * 
 * A Material Design 3 alert dialog with Xiaomi design tokens.
 * 
 * @param onDismissRequest Callback when the dialog is dismissed
 * @param confirmButton Confirm button composable
 * @param modifier Modifier to be applied to the dialog
 * @param dismissButton Optional dismiss button composable
 * @param icon Optional icon for the dialog
 * @param title Optional title for the dialog
 * @param text Optional body text for the dialog
 * @param shape Shape of the dialog
 * @param containerColor Background color of the dialog
 * @param iconContentColor Color of the icon
 * @param titleContentColor Color of the title text
 * @param textContentColor Color of the body text
 * @param tonalElevation Tonal elevation of the dialog
 * @param properties Dialog properties
 */
@Composable
fun XiaomiAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = MaterialTheme.shapes.extraLarge,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    iconContentColor: Color = MaterialTheme.colorScheme.secondary,
    titleContentColor: Color = MaterialTheme.colorScheme.onSurface,
    textContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    tonalElevation: androidx.compose.ui.unit.Dp = 6.dp,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties
    )
}

/**
 * Xiaomi Confirmation Dialog
 * 
 * A dialog for confirming user actions.
 * 
 * @param onConfirm Callback when the user confirms
 * @param onDismiss Callback when the dialog is dismissed
 * @param title Title of the dialog
 * @param message Message content of the dialog
 * @param confirmText Text for the confirm button
 * @param dismissText Text for the dismiss button
 * @param icon Optional icon for the dialog
 * @param isDestructive Whether this is a destructive action
 */
@Composable
fun XiaomiConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    icon: ImageVector? = null,
    isDestructive: Boolean = false
) {
    XiaomiAlertDialog(
        onDismissRequest = onDismiss,
        icon = icon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isDestructive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            if (isDestructive) {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(confirmText)
                }
            } else {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text(confirmText)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        }
    )
}

/**
 * Xiaomi Information Dialog
 * 
 * A dialog for displaying information to the user.
 * 
 * @param onDismiss Callback when the dialog is dismissed
 * @param title Title of the dialog
 * @param message Message content of the dialog
 * @param buttonText Text for the action button
 * @param type Type of information dialog
 */
@Composable
fun XiaomiInformationDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    buttonText: String = "OK",
    type: XiaomiDialogType = XiaomiDialogType.Info
) {
    val (icon, iconColor) = when (type) {
        XiaomiDialogType.Info -> Icons.Filled.Info to MaterialTheme.colorScheme.primary
        XiaomiDialogType.Success -> Icons.Filled.CheckCircle to Color(0xFF4CAF50)
        XiaomiDialogType.Warning -> Icons.Filled.Warning to Color(0xFFFF9800)
        XiaomiDialogType.Error -> Icons.Filled.Error to MaterialTheme.colorScheme.error
    }
    
    XiaomiAlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconColor
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(buttonText)
            }
        }
    )
}

/**
 * Xiaomi Custom Dialog
 * 
 * A flexible dialog container for custom content.
 * 
 * @param onDismissRequest Callback when the dialog is dismissed
 * @param modifier Modifier to be applied to the dialog
 * @param properties Dialog properties
 * @param content Content of the dialog
 */
@Composable
fun XiaomiCustomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            content()
        }
    }
}

/**
 * Xiaomi Full Screen Dialog
 * 
 * A dialog that takes up the full screen.
 * 
 * @param onDismissRequest Callback when the dialog is dismissed
 * @param title Title of the dialog
 * @param modifier Modifier to be applied to the dialog
 * @param actions Action buttons for the dialog
 * @param content Content of the dialog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiFullScreenDialog(
    onDismissRequest: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                // Header
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        
                        actions()
                    }
                }
                
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * Xiaomi Bottom Sheet Dialog
 * 
 * A dialog that appears from the bottom of the screen.
 * 
 * @param onDismissRequest Callback when the dialog is dismissed
 * @param title Optional title for the dialog
 * @param modifier Modifier to be applied to the dialog
 * @param content Content of the dialog
 */
@Composable
fun XiaomiBottomSheetDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Spacer to allow dismissing by tapping outside
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            
            Surface(
                modifier = modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge.copy(
                    bottomStart = androidx.compose.foundation.shape.CornerSize(0.dp),
                    bottomEnd = androidx.compose.foundation.shape.CornerSize(0.dp)
                ),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Handle bar
                    Surface(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    ) {}
                    
                    if (title != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    } else {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    content()
                }
            }
        }
    }
}

/**
 * Dialog type enumeration for information dialogs
 */
enum class XiaomiDialogType {
    Info,
    Success,
    Warning,
    Error
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Dialogs - Light")
@Composable
fun XiaomiDialogsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dialog Examples",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var showConfirmDialog by remember { mutableStateOf(false) }
                var showInfoDialog by remember { mutableStateOf(false) }
                var showSuccessDialog by remember { mutableStateOf(false) }
                var showWarningDialog by remember { mutableStateOf(false) }
                var showErrorDialog by remember { mutableStateOf(false) }
                var showCustomDialog by remember { mutableStateOf(false) }
                var showBottomSheet by remember { mutableStateOf(false) }
                
                // Dialog trigger buttons
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Confirmation Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showInfoDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Info Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showSuccessDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Success Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showWarningDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Warning Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showErrorDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Error Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showCustomDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Custom Dialog")
                    }
                    
                    OutlinedButton(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Show Bottom Sheet")
                    }
                }
                
                // Dialogs
                if (showConfirmDialog) {
                    XiaomiConfirmationDialog(
                        onConfirm = { /* Handle confirmation */ },
                        onDismiss = { showConfirmDialog = false },
                        title = "Delete Item",
                        message = "Are you sure you want to delete this item? This action cannot be undone.",
                        confirmText = "Delete",
                        dismissText = "Cancel",
                        icon = Icons.Filled.Warning,
                        isDestructive = true
                    )
                }
                
                if (showInfoDialog) {
                    XiaomiInformationDialog(
                        onDismiss = { showInfoDialog = false },
                        title = "Information",
                        message = "This is an informational message to provide additional context to the user.",
                        type = XiaomiDialogType.Info
                    )
                }
                
                if (showSuccessDialog) {
                    XiaomiInformationDialog(
                        onDismiss = { showSuccessDialog = false },
                        title = "Success",
                        message = "Your action has been completed successfully!",
                        type = XiaomiDialogType.Success
                    )
                }
                
                if (showWarningDialog) {
                    XiaomiInformationDialog(
                        onDismiss = { showWarningDialog = false },
                        title = "Warning",
                        message = "Please be aware that this action may have consequences.",
                        type = XiaomiDialogType.Warning
                    )
                }
                
                if (showErrorDialog) {
                    XiaomiInformationDialog(
                        onDismiss = { showErrorDialog = false },
                        title = "Error",
                        message = "An error occurred while processing your request. Please try again.",
                        type = XiaomiDialogType.Error
                    )
                }
                
                if (showCustomDialog) {
                    XiaomiCustomDialog(
                        onDismissRequest = { showCustomDialog = false }
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Custom Dialog",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "This is a custom dialog with flexible content. You can add any composable content here.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TextButton(
                                    onClick = { showCustomDialog = false }
                                ) {
                                    Text("Cancel")
                                }
                                
                                Button(
                                    onClick = { showCustomDialog = false }
                                ) {
                                    Text("OK")
                                }
                            }
                        }
                    }
                }
                
                if (showBottomSheet) {
                    XiaomiBottomSheetDialog(
                        onDismissRequest = { showBottomSheet = false },
                        title = "Bottom Sheet Dialog"
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "This is a bottom sheet dialog that slides up from the bottom of the screen.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            
                            Button(
                                onClick = { showBottomSheet = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Close")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Dialogs - Dark")
@Composable
fun XiaomiDialogsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dark Theme Dialogs",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var showDialog by remember { mutableStateOf(false) }
                
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Dark Theme Dialog")
                }
                
                if (showDialog) {
                    XiaomiInformationDialog(
                        onDismiss = { showDialog = false },
                        title = "Dark Theme",
                        message = "This dialog demonstrates how the Xiaomi dialog components look in dark theme.",
                        type = XiaomiDialogType.Info
                    )
                }
            }
        }
    }
}