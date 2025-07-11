package com.xiaomi.base.ui.kit.components.selection.switches

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a switch item
 */
data class XiaomiSwitchItem(
    val id: String,
    val label: String,
    val description: String? = null,
    val checked: Boolean = false,
    val enabled: Boolean = true
)

/**
 * Xiaomi Switch
 * 
 * A Material Design 3 switch component with Xiaomi design tokens.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param modifier Modifier to be applied to the switch
 * @param thumbContent Optional content to be displayed on the thumb
 * @param enabled Whether the switch is enabled
 * @param colors Colors to be used for the switch
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: androidx.compose.material3.SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Labeled Switch
 * 
 * A switch with an associated label that can be clicked to toggle the switch.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param label The label text for the switch
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the switch is enabled
 * @param description Optional description text
 * @param labelFirst Whether to show the label before the switch
 */
@Composable
fun XiaomiLabeledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    description: String? = null,
    labelFirst: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = enabled,
                role = Role.Switch
            ) {
                onCheckedChange(!checked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (labelFirst) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            XiaomiSwitch(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
        } else {
            XiaomiSwitch(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Icon Switch
 * 
 * A switch with icons displayed on the thumb.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param modifier Modifier to be applied to the switch
 * @param enabled Whether the switch is enabled
 * @param checkedIcon Icon to display when checked
 * @param uncheckedIcon Icon to display when unchecked
 */
@Composable
fun XiaomiIconSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedIcon: ImageVector = Icons.Filled.Check,
    uncheckedIcon: ImageVector = Icons.Filled.Close
) {
    XiaomiSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        thumbContent = {
            Icon(
                imageVector = if (checked) checkedIcon else uncheckedIcon,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize)
            )
        }
    )
}

/**
 * Xiaomi Switch Group
 * 
 * A group of switches with a common label.
 * 
 * @param items List of switch items
 * @param onItemCheckedChange Callback when an item's checked state changes
 * @param modifier Modifier to be applied to the group
 * @param title Optional title for the group
 * @param enabled Whether all switches in the group are enabled
 */
@Composable
fun XiaomiSwitchGroup(
    items: List<XiaomiSwitchItem>,
    onItemCheckedChange: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items.forEach { item ->
            XiaomiLabeledSwitch(
                checked = item.checked,
                onCheckedChange = { onItemCheckedChange(item.id, it) },
                label = item.label,
                description = item.description,
                enabled = enabled && item.enabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Xiaomi Compact Switch
 * 
 * A smaller switch for use in dense layouts.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param modifier Modifier to be applied to the switch
 * @param enabled Whether the switch is enabled
 */
@Composable
fun XiaomiCompactSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    XiaomiSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(32.dp, 20.dp),
        enabled = enabled
    )
}

/**
 * Xiaomi Custom Color Switch
 * 
 * A switch with custom colors.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param modifier Modifier to be applied to the switch
 * @param enabled Whether the switch is enabled
 * @param checkedThumbColor Color of the thumb when checked
 * @param checkedTrackColor Color of the track when checked
 * @param uncheckedThumbColor Color of the thumb when unchecked
 * @param uncheckedTrackColor Color of the track when unchecked
 */
@Composable
fun XiaomiCustomColorSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedThumbColor: Color = MaterialTheme.colorScheme.onPrimary,
    checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedThumbColor: Color = MaterialTheme.colorScheme.outline,
    uncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    XiaomiSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = checkedThumbColor,
            checkedTrackColor = checkedTrackColor,
            uncheckedThumbColor = uncheckedThumbColor,
            uncheckedTrackColor = uncheckedTrackColor
        )
    )
}

/**
 * Xiaomi Card Switch
 * 
 * A switch displayed within a card layout.
 * 
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback when the switch state changes
 * @param label The label text for the switch
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the switch is enabled
 * @param description Optional description text
 */
@Composable
fun XiaomiCardSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    description: String? = null
) {
    Surface(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = { onCheckedChange(!checked) },
                role = Role.Switch
            ),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enabled) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        },
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            XiaomiSwitch(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled
            )
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Switches - Light")
@Composable
fun XiaomiSwitchesPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Switch Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var checked1 by remember { mutableStateOf(false) }
                var checked2 by remember { mutableStateOf(true) }
                var checked3 by remember { mutableStateOf(false) }
                
                // Basic switches
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Basic Switches",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiSwitch(
                            checked = false,
                            onCheckedChange = {}
                        )
                        XiaomiSwitch(
                            checked = true,
                            onCheckedChange = {}
                        )
                        XiaomiSwitch(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false
                        )
                    }
                }
                
                // Icon switches
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Icon Switches",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiIconSwitch(
                            checked = false,
                            onCheckedChange = {}
                        )
                        XiaomiIconSwitch(
                            checked = true,
                            onCheckedChange = {}
                        )
                    }
                }
                
                // Labeled switches
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Labeled Switches",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiLabeledSwitch(
                        checked = checked1,
                        onCheckedChange = { checked1 = it },
                        label = "Enable notifications",
                        description = "Receive push notifications for important updates"
                    )
                    
                    XiaomiLabeledSwitch(
                        checked = checked2,
                        onCheckedChange = { checked2 = it },
                        label = "Auto-sync data",
                        labelFirst = true
                    )
                    
                    XiaomiLabeledSwitch(
                        checked = checked3,
                        onCheckedChange = { checked3 = it },
                        label = "Disabled option",
                        enabled = false
                    )
                }
                
                // Compact switches
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Compact Switches",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiCompactSwitch(
                            checked = false,
                            onCheckedChange = {}
                        )
                        XiaomiCompactSwitch(
                            checked = true,
                            onCheckedChange = {}
                        )
                        Text(
                            "Compact size for dense layouts",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Switch Group")
@Composable
fun XiaomiSwitchGroupPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var items by remember {
                    mutableStateOf(
                        listOf(
                            XiaomiSwitchItem(
                                id = "1",
                                label = "Email notifications",
                                description = "Receive updates via email",
                                checked = true
                            ),
                            XiaomiSwitchItem(
                                id = "2",
                                label = "Push notifications",
                                description = "Receive push notifications on your device",
                                checked = false
                            ),
                            XiaomiSwitchItem(
                                id = "3",
                                label = "SMS notifications",
                                description = "Receive text message updates",
                                checked = true
                            ),
                            XiaomiSwitchItem(
                                id = "4",
                                label = "Marketing emails",
                                description = "Receive promotional content",
                                checked = false
                            )
                        )
                    )
                }
                
                XiaomiSwitchGroup(
                    items = items,
                    onItemCheckedChange = { id, checked ->
                        items = items.map { item ->
                            if (item.id == id) {
                                item.copy(checked = checked)
                            } else {
                                item
                            }
                        }
                    },
                    title = "Notification Preferences"
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Card Switches")
@Composable
fun XiaomiCardSwitchesPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Card Switches",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var darkMode by remember { mutableStateOf(false) }
                var autoBackup by remember { mutableStateOf(true) }
                var locationServices by remember { mutableStateOf(false) }
                
                XiaomiCardSwitch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it },
                    label = "Dark Mode",
                    description = "Use dark theme for better night viewing",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCardSwitch(
                    checked = autoBackup,
                    onCheckedChange = { autoBackup = it },
                    label = "Auto Backup",
                    description = "Automatically backup your data to the cloud",
                    modifier = Modifier.fillMaxWidth()
                )
                
                XiaomiCardSwitch(
                    checked = locationServices,
                    onCheckedChange = { locationServices = it },
                    label = "Location Services",
                    description = "Allow apps to access your location",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Switches - Dark")
@Composable
fun XiaomiSwitchesDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dark Theme Switches",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var checked by remember { mutableStateOf(true) }
                
                XiaomiLabeledSwitch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    label = "Dark mode setting",
                    description = "Enable dark theme for better night viewing"
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    XiaomiCustomColorSwitch(
                        checked = true,
                        onCheckedChange = {},
                        checkedTrackColor = MaterialTheme.colorScheme.secondary
                    )
                    XiaomiCustomColorSwitch(
                        checked = true,
                        onCheckedChange = {},
                        checkedTrackColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}