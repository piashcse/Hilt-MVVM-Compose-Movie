package com.xiaomi.base.ui.kit.components.selection.checkboxes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a checkbox item
 */
data class XiaomiCheckboxItem(
    val id: String,
    val label: String,
    val description: String? = null,
    val checked: Boolean = false,
    val enabled: Boolean = true
)

/**
 * Xiaomi Checkbox
 * 
 * A Material Design 3 checkbox component with Xiaomi design tokens.
 * 
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback when the checkbox state changes
 * @param modifier Modifier to be applied to the checkbox
 * @param enabled Whether the checkbox is enabled
 * @param colors Colors to be used for the checkbox
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: androidx.compose.material3.CheckboxColors = CheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Tri-State Checkbox
 * 
 * A checkbox that can be in three states: checked, unchecked, or indeterminate.
 * 
 * @param state The current state of the checkbox
 * @param onClick Callback when the checkbox is clicked
 * @param modifier Modifier to be applied to the checkbox
 * @param enabled Whether the checkbox is enabled
 * @param colors Colors to be used for the checkbox
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiTriStateCheckbox(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: androidx.compose.material3.CheckboxColors = CheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    TriStateCheckbox(
        state = state,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Labeled Checkbox
 * 
 * A checkbox with an associated label that can be clicked to toggle the checkbox.
 * 
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback when the checkbox state changes
 * @param label The label text for the checkbox
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the checkbox is enabled
 * @param description Optional description text
 * @param labelFirst Whether to show the label before the checkbox
 */
@Composable
fun XiaomiLabeledCheckbox(
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
                indication = null,
                enabled = enabled,
                role = Role.Checkbox
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
            
            XiaomiCheckbox(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
        } else {
            XiaomiCheckbox(
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
 * Xiaomi Checkbox Group
 * 
 * A group of checkboxes with a common label.
 * 
 * @param items List of checkbox items
 * @param onItemCheckedChange Callback when an item's checked state changes
 * @param modifier Modifier to be applied to the group
 * @param title Optional title for the group
 * @param enabled Whether all checkboxes in the group are enabled
 * @param showSelectAll Whether to show a "Select All" option
 */
@Composable
fun XiaomiCheckboxGroup(
    items: List<XiaomiCheckboxItem>,
    onItemCheckedChange: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    enabled: Boolean = true,
    showSelectAll: Boolean = false
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
        
        if (showSelectAll) {
            val allChecked = items.all { it.checked }
            val someChecked = items.any { it.checked }
            val selectAllState = when {
                allChecked -> ToggleableState.On
                someChecked -> ToggleableState.Indeterminate
                else -> ToggleableState.Off
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .triStateToggleable(
                        state = selectAllState,
                        onClick = {
                            val newState = !allChecked
                            items.forEach { item ->
                                onItemCheckedChange(item.id, newState)
                            }
                        },
                        enabled = enabled,
                        role = Role.Checkbox
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                XiaomiTriStateCheckbox(
                    state = selectAllState,
                    onClick = null,
                    enabled = enabled
                )
                
                Text(
                    text = "Select All",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            }
        }
        
        items.forEach { item ->
            XiaomiLabeledCheckbox(
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
 * Xiaomi Compact Checkbox
 * 
 * A smaller checkbox for use in dense layouts.
 * 
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback when the checkbox state changes
 * @param modifier Modifier to be applied to the checkbox
 * @param enabled Whether the checkbox is enabled
 */
@Composable
fun XiaomiCompactCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    XiaomiCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(20.dp),
        enabled = enabled
    )
}

/**
 * Xiaomi Custom Color Checkbox
 * 
 * A checkbox with custom colors.
 * 
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback when the checkbox state changes
 * @param modifier Modifier to be applied to the checkbox
 * @param enabled Whether the checkbox is enabled
 * @param checkedColor Color when checked
 * @param uncheckedColor Color when unchecked
 * @param checkmarkColor Color of the checkmark
 */
@Composable
fun XiaomiCustomColorCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedColor: Color = MaterialTheme.colorScheme.outline,
    checkmarkColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    XiaomiCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor
        )
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Checkboxes - Light")
@Composable
fun XiaomiCheckboxesPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Checkbox Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var checked1 by remember { mutableStateOf(false) }
                var checked2 by remember { mutableStateOf(true) }
                var checked3 by remember { mutableStateOf(false) }
                var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }
                
                // Basic checkboxes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Basic Checkboxes",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiCheckbox(
                            checked = false,
                            onCheckedChange = {}
                        )
                        XiaomiCheckbox(
                            checked = true,
                            onCheckedChange = {}
                        )
                        XiaomiCheckbox(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false
                        )
                        XiaomiTriStateCheckbox(
                            state = ToggleableState.Indeterminate,
                            onClick = {}
                        )
                    }
                }
                
                // Labeled checkboxes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Labeled Checkboxes",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    XiaomiLabeledCheckbox(
                        checked = checked1,
                        onCheckedChange = { checked1 = it },
                        label = "Enable notifications",
                        description = "Receive push notifications for important updates"
                    )
                    
                    XiaomiLabeledCheckbox(
                        checked = checked2,
                        onCheckedChange = { checked2 = it },
                        label = "Auto-sync data",
                        labelFirst = true
                    )
                    
                    XiaomiLabeledCheckbox(
                        checked = checked3,
                        onCheckedChange = { checked3 = it },
                        label = "Disabled option",
                        enabled = false
                    )
                }
                
                // Compact checkboxes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Compact Checkboxes",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        XiaomiCompactCheckbox(
                            checked = false,
                            onCheckedChange = {}
                        )
                        XiaomiCompactCheckbox(
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

@Preview(name = "Xiaomi Checkbox Group")
@Composable
fun XiaomiCheckboxGroupPreview() {
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
                            XiaomiCheckboxItem(
                                id = "1",
                                label = "Email notifications",
                                description = "Receive updates via email",
                                checked = true
                            ),
                            XiaomiCheckboxItem(
                                id = "2",
                                label = "Push notifications",
                                description = "Receive push notifications on your device",
                                checked = false
                            ),
                            XiaomiCheckboxItem(
                                id = "3",
                                label = "SMS notifications",
                                description = "Receive text message updates",
                                checked = true
                            ),
                            XiaomiCheckboxItem(
                                id = "4",
                                label = "Marketing emails",
                                description = "Receive promotional content",
                                checked = false
                            )
                        )
                    )
                }
                
                XiaomiCheckboxGroup(
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
                    title = "Notification Preferences",
                    showSelectAll = true
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Checkboxes - Dark")
@Composable
fun XiaomiCheckboxesDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Dark Theme Checkboxes",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var checked by remember { mutableStateOf(true) }
                
                XiaomiLabeledCheckbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    label = "Dark mode setting",
                    description = "Enable dark theme for better night viewing"
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    XiaomiCustomColorCheckbox(
                        checked = true,
                        onCheckedChange = {},
                        checkedColor = MaterialTheme.colorScheme.secondary
                    )
                    XiaomiCustomColorCheckbox(
                        checked = true,
                        onCheckedChange = {},
                        checkedColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}