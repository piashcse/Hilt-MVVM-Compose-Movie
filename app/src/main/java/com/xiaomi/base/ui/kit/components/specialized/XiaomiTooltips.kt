package com.xiaomi.base.ui.kit.components.specialized

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.spacing
import kotlinx.coroutines.launch

/**
 * Xiaomi Plain Tooltip
 * 
 * A simple tooltip with plain text content.
 * 
 * @param tooltip The tooltip content
 * @param modifier Modifier to be applied to the tooltip box
 * @param focusable Whether the tooltip is focusable
 * @param enableUserInput Whether user input is enabled
 * @param content The content that triggers the tooltip
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiPlainTooltip(
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    enableUserInput: Boolean = true,
    content: @Composable () -> Unit
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                shape = MaterialTheme.shapes.small
            ) {
                tooltip()
            }
        },
        state = rememberTooltipState(),
        modifier = modifier,
        focusable = focusable,
        enableUserInput = enableUserInput
    ) {
        content()
    }
}

/**
 * Xiaomi Rich Tooltip
 * 
 * A rich tooltip with title, text, and optional action.
 * 
 * @param title The title of the tooltip
 * @param text The body text of the tooltip
 * @param action Optional action composable
 * @param modifier Modifier to be applied to the tooltip box
 * @param focusable Whether the tooltip is focusable
 * @param enableUserInput Whether user input is enabled
 * @param content The content that triggers the tooltip
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiRichTooltip(
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    enableUserInput: Boolean = true,
    content: @Composable () -> Unit
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = title,
                text = text,
                action = action,
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionContentColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                // Rich tooltip content
            }
        },
        state = rememberTooltipState(),
        modifier = modifier,
        focusable = focusable,
        enableUserInput = enableUserInput
    ) {
        content()
    }
}

/**
 * Xiaomi Custom Tooltip
 * 
 * A custom tooltip with flexible positioning and styling.
 * 
 * @param tooltipContent The content of the tooltip
 * @param visible Whether the tooltip is visible
 * @param onDismiss Callback when the tooltip is dismissed
 * @param modifier Modifier to be applied to the tooltip
 * @param offset Offset for tooltip positioning
 * @param backgroundColor Background color of the tooltip
 * @param contentColor Content color of the tooltip
 * @param elevation Elevation of the tooltip
 * @param content The content that triggers the tooltip
 */
@Composable
fun XiaomiCustomTooltip(
    tooltipContent: @Composable () -> Unit,
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    backgroundColor: Color = MaterialTheme.colorScheme.inverseSurface,
    contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    elevation: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()
        
        if (visible) {
            Popup(
                onDismissRequest = onDismiss,
                offset = offset,
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(150)) + scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(150)
                    ),
                    exit = fadeOut(animationSpec = tween(75)) + scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(75)
                    )
                ) {
                    Surface(
                        modifier = Modifier
                            .shadow(elevation, MaterialTheme.shapes.small)
                            .background(
                                backgroundColor,
                                MaterialTheme.shapes.small
                            ),
                        color = backgroundColor,
                        contentColor = contentColor,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Box(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            tooltipContent()
                        }
                    }
                }
            }
        }
    }
}

/**
 * Xiaomi Info Tooltip
 * 
 * A tooltip specifically for providing information with an info icon.
 * 
 * @param text The information text
 * @param title Optional title for the tooltip
 * @param modifier Modifier to be applied to the tooltip
 * @param icon Icon to display
 * @param iconTint Tint color for the icon
 */
@Composable
fun XiaomiInfoTooltip(
    text: String,
    title: String? = null,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Info,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    if (title != null) {
        XiaomiRichTooltip(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            modifier = modifier
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Information",
                modifier = Modifier.size(16.dp),
                tint = iconTint
            )
        }
    } else {
        XiaomiPlainTooltip(
            tooltip = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            modifier = modifier
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Information",
                modifier = Modifier.size(16.dp),
                tint = iconTint
            )
        }
    }
}

/**
 * Xiaomi Help Tooltip
 * 
 * A tooltip specifically for providing help information.
 * 
 * @param helpText The help text
 * @param title Optional title for the help tooltip
 * @param actionText Optional action button text
 * @param onActionClick Optional action button click handler
 * @param modifier Modifier to be applied to the tooltip
 */
@Composable
fun XiaomiHelpTooltip(
    helpText: String,
    title: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    XiaomiRichTooltip(
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        text = {
            Text(
                text = helpText,
                style = MaterialTheme.typography.bodySmall
            )
        },
        action = if (actionText != null && onActionClick != null) {
            {
                TextButton(onClick = onActionClick) {
                    Text(
                        text = actionText,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        } else null,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Help,
            contentDescription = "Help",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Xiaomi Tooltip with Arrow
 * 
 * A custom tooltip with an arrow pointing to the target.
 * 
 * @param text The tooltip text
 * @param visible Whether the tooltip is visible
 * @param onDismiss Callback when the tooltip is dismissed
 * @param modifier Modifier to be applied to the tooltip
 * @param arrowPosition Position of the arrow
 * @param content The content that triggers the tooltip
 */
@Composable
fun XiaomiTooltipWithArrow(
    text: String,
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    arrowPosition: XiaomiTooltipArrowPosition = XiaomiTooltipArrowPosition.Bottom,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val arrowSize = 8.dp
    val arrowOffset = with(density) { arrowSize.toPx().toInt() }
    
    val offset = when (arrowPosition) {
        XiaomiTooltipArrowPosition.Top -> IntOffset(0, arrowOffset)
        XiaomiTooltipArrowPosition.Bottom -> IntOffset(0, -arrowOffset)
        XiaomiTooltipArrowPosition.Start -> IntOffset(arrowOffset, 0)
        XiaomiTooltipArrowPosition.End -> IntOffset(-arrowOffset, 0)
    }
    
    Box(modifier = modifier) {
        content()
        
        if (visible) {
            Popup(
                onDismissRequest = onDismiss,
                offset = offset,
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(150)) + scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(150)
                    ),
                    exit = fadeOut(animationSpec = tween(75)) + scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(75)
                    )
                ) {
                    Column(
                        horizontalAlignment = when (arrowPosition) {
                            XiaomiTooltipArrowPosition.Start,
                            XiaomiTooltipArrowPosition.End -> Alignment.CenterHorizontally
                            else -> Alignment.CenterHorizontally
                        }
                    ) {
                        // Arrow at top
                        if (arrowPosition == XiaomiTooltipArrowPosition.Top) {
                            Box(
                                modifier = Modifier
                                    .size(arrowSize)
                                    .background(
                                        MaterialTheme.colorScheme.inverseSurface,
                                        RoundedCornerShape(2.dp)
                                    )
                                    .offset(y = 4.dp)
                            )
                        }
                        
                        Row {
                            // Arrow at start
                            if (arrowPosition == XiaomiTooltipArrowPosition.Start) {
                                Box(
                                    modifier = Modifier
                                        .size(arrowSize)
                                        .background(
                                            MaterialTheme.colorScheme.inverseSurface,
                                            RoundedCornerShape(2.dp)
                                        )
                                        .offset(x = 4.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                            
                            // Tooltip content
                            Surface(
                                color = MaterialTheme.colorScheme.inverseSurface,
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                                shape = MaterialTheme.shapes.small,
                                shadowElevation = 6.dp
                            ) {
                                Text(
                                    text = text,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            
                            // Arrow at end
                            if (arrowPosition == XiaomiTooltipArrowPosition.End) {
                                Box(
                                    modifier = Modifier
                                        .size(arrowSize)
                                        .background(
                                            MaterialTheme.colorScheme.inverseSurface,
                                            RoundedCornerShape(2.dp)
                                        )
                                        .offset(x = (-4).dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                        
                        // Arrow at bottom
                        if (arrowPosition == XiaomiTooltipArrowPosition.Bottom) {
                            Box(
                                modifier = Modifier
                                    .size(arrowSize)
                                    .background(
                                        MaterialTheme.colorScheme.inverseSurface,
                                        RoundedCornerShape(2.dp)
                                    )
                                    .offset(y = (-4).dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Arrow position for tooltips with arrows
 */
enum class XiaomiTooltipArrowPosition {
    Top,
    Bottom,
    Start,
    End
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Tooltips - Light")
@Composable
fun XiaomiTooltipsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
            ) {
                Text(
                    "Tooltip Examples",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Plain Tooltip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Plain Tooltip:")
                    XiaomiPlainTooltip(
                        tooltip = {
                            Text("This is a plain tooltip with simple text")
                        }
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                // Rich Tooltip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Rich Tooltip:")
                    XiaomiRichTooltip(
                        title = {
                            Text(
                                "Rich Tooltip Title",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        text = {
                            Text(
                                "This is a rich tooltip with title and action button",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        action = {
                            TextButton(onClick = {}) {
                                Text("Learn More")
                            }
                        }
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Help,
                                contentDescription = "Help",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                // Info Tooltip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Info Tooltip:")
                    XiaomiInfoTooltip(
                        text = "This provides additional information about the feature",
                        title = "Feature Information"
                    )
                }
                
                // Help Tooltip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Help Tooltip:")
                    XiaomiHelpTooltip(
                        helpText = "Click here to get help with this feature. You can also access the documentation for more details.",
                        title = "Need Help?",
                        actionText = "View Docs",
                        onActionClick = { /* Open documentation */ }
                    )
                }
                
                // Custom Tooltip with Arrow
                var showCustomTooltip by remember { mutableStateOf(false) }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Custom Tooltip:")
                    XiaomiTooltipWithArrow(
                        text = "This is a custom tooltip with an arrow",
                        visible = showCustomTooltip,
                        onDismiss = { showCustomTooltip = false },
                        arrowPosition = XiaomiTooltipArrowPosition.Bottom
                    ) {
                        IconButton(
                            onClick = { showCustomTooltip = !showCustomTooltip }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Custom Tooltip",
                                modifier = Modifier.size(20.dp),
                                tint = if (showCustomTooltip) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Tooltip positioning examples
                Text(
                    "Tooltip Positioning",
                    style = MaterialTheme.typography.titleSmall
                )
                
                var showTopTooltip by remember { mutableStateOf(false) }
                var showBottomTooltip by remember { mutableStateOf(false) }
                var showStartTooltip by remember { mutableStateOf(false) }
                var showEndTooltip by remember { mutableStateOf(false) }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Top arrow
                    XiaomiTooltipWithArrow(
                        text = "Top Arrow",
                        visible = showTopTooltip,
                        onDismiss = { showTopTooltip = false },
                        arrowPosition = XiaomiTooltipArrowPosition.Top
                    ) {
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { showTopTooltip = !showTopTooltip },
                            colors = CardDefaults.cardColors(
                                containerColor = if (showTopTooltip) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "T",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    
                    // Bottom arrow
                    XiaomiTooltipWithArrow(
                        text = "Bottom Arrow",
                        visible = showBottomTooltip,
                        onDismiss = { showBottomTooltip = false },
                        arrowPosition = XiaomiTooltipArrowPosition.Bottom
                    ) {
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { showBottomTooltip = !showBottomTooltip },
                            colors = CardDefaults.cardColors(
                                containerColor = if (showBottomTooltip) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "B",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    
                    // Start arrow
                    XiaomiTooltipWithArrow(
                        text = "Start Arrow",
                        visible = showStartTooltip,
                        onDismiss = { showStartTooltip = false },
                        arrowPosition = XiaomiTooltipArrowPosition.Start
                    ) {
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { showStartTooltip = !showStartTooltip },
                            colors = CardDefaults.cardColors(
                                containerColor = if (showStartTooltip) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "S",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    
                    // End arrow
                    XiaomiTooltipWithArrow(
                        text = "End Arrow",
                        visible = showEndTooltip,
                        onDismiss = { showEndTooltip = false },
                        arrowPosition = XiaomiTooltipArrowPosition.End
                    ) {
                        Card(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { showEndTooltip = !showEndTooltip },
                            colors = CardDefaults.cardColors(
                                containerColor = if (showEndTooltip) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "E",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Tooltips - Dark")
@Composable
fun XiaomiTooltipsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                Text(
                    "Dark Theme Tooltips",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text("Dark Theme Tooltip:")
                    XiaomiInfoTooltip(
                        text = "This tooltip demonstrates how tooltips look in dark theme",
                        title = "Dark Theme"
                    )
                }
            }
        }
    }
}