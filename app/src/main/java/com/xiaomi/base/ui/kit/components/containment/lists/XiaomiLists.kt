package com.xiaomi.base.ui.kit.components.containment.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.SemanticSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.Spacing

/**
 * Xiaomi Base UI Kit List Item
 * 
 * A Material Design 3 list item component with Xiaomi design tokens.
 * Provides consistent styling for list content.
 * 
 * @param headlineContent The primary content of the list item
 * @param modifier Modifier to be applied to the list item
 * @param overlineContent Optional content above the headline
 * @param supportingContent Optional supporting content below the headline
 * @param leadingContent Optional content at the start of the list item
 * @param trailingContent Optional content at the end of the list item
 * @param colors ListItemColors that will be used to resolve the colors used for this list item
 * @param tonalElevation The tonal elevation of this list item
 * @param shadowElevation The shadow elevation of this list item
 */
@Composable
fun XiaomiListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: androidx.compose.ui.unit.Dp = ListItemDefaults.Elevation,
    shadowElevation: androidx.compose.ui.unit.Dp = ListItemDefaults.Elevation
) {
    ListItem(
        headlineContent = headlineContent,
        modifier = modifier,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation
    )
}

/**
 * Xiaomi Clickable List Item
 * 
 * A clickable variant of the list item for interactive content.
 * 
 * @param onClick Callback for when the list item is clicked
 * @param headlineContent The primary content of the list item
 * @param modifier Modifier to be applied to the list item
 * @param enabled Whether the list item is enabled for interaction
 * @param overlineContent Optional content above the headline
 * @param supportingContent Optional supporting content below the headline
 * @param leadingContent Optional content at the start of the list item
 * @param trailingContent Optional content at the end of the list item
 * @param colors ListItemColors that will be used to resolve the colors used for this list item
 */
@Composable
fun XiaomiClickableListItem(
    onClick: () -> Unit,
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors()
) {
    XiaomiListItem(
        headlineContent = headlineContent,
        modifier = modifier.clickable(enabled = enabled) { onClick() },
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors
    )
}

/**
 * Xiaomi Navigation List Item
 * 
 * A specialized list item for navigation with trailing chevron icon.
 * 
 * @param onClick Callback for when the list item is clicked
 * @param title The title text of the navigation item
 * @param modifier Modifier to be applied to the list item
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param enabled Whether the list item is enabled for interaction
 */
@Composable
fun XiaomiNavigationListItem(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    XiaomiClickableListItem(
        onClick = onClick,
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        enabled = enabled,
        supportingContent = subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

/**
 * Xiaomi Switch List Item
 * 
 * A list item with an integrated switch control.
 * 
 * @param title The title text of the switch item
 * @param checked Whether the switch is checked
 * @param onCheckedChange Callback for when the switch state changes
 * @param modifier Modifier to be applied to the list item
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param enabled Whether the switch is enabled for interaction
 */
@Composable
fun XiaomiSwitchListItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    XiaomiClickableListItem(
        onClick = { onCheckedChange(!checked) },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        enabled = enabled,
        supportingContent = subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    )
}

/**
 * Xiaomi Checkbox List Item
 * 
 * A list item with an integrated checkbox control.
 * 
 * @param title The title text of the checkbox item
 * @param checked Whether the checkbox is checked
 * @param onCheckedChange Callback for when the checkbox state changes
 * @param modifier Modifier to be applied to the list item
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param enabled Whether the checkbox is enabled for interaction
 */
@Composable
fun XiaomiCheckboxListItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    XiaomiClickableListItem(
        onClick = { onCheckedChange(!checked) },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        enabled = enabled,
        supportingContent = subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingContent = {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    )
}

/**
 * Xiaomi Radio List Item
 * 
 * A list item with an integrated radio button control.
 * 
 * @param title The title text of the radio item
 * @param selected Whether the radio button is selected
 * @param onClick Callback for when the radio item is clicked
 * @param modifier Modifier to be applied to the list item
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param enabled Whether the radio button is enabled for interaction
 */
@Composable
fun XiaomiRadioListItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    XiaomiClickableListItem(
        onClick = onClick,
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        enabled = enabled,
        supportingContent = subtitle?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingContent = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingContent = {
            RadioButton(
                selected = selected,
                onClick = onClick,
                enabled = enabled
            )
        }
    )
}

/**
 * Xiaomi List Section
 * 
 * A section container for grouping related list items with optional header.
 * 
 * @param modifier Modifier to be applied to the section
 * @param header Optional header content for the section
 * @param content The list items content
 */
@Composable
fun XiaomiListSection(
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        header?.invoke()
        content()
    }
}

/**
 * Xiaomi List Section Header
 * 
 * A standardized header for list sections.
 * 
 * @param title The title text of the section
 * @param modifier Modifier to be applied to the header
 */
@Composable
fun XiaomiListSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(
            horizontal = SemanticSpacing.ContentPaddingHorizontal,
            vertical = Spacing.Small
        )
    )
}

/**
 * Xiaomi List Divider
 * 
 * A divider component for separating list items.
 * 
 * @param modifier Modifier to be applied to the divider
 * @param thickness The thickness of the divider
 * @param color The color of the divider
 */
@Composable
fun XiaomiListDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

// Data classes for preview
data class ListItemData(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector? = null,
    val hasAction: Boolean = false
)

// Preview composables for design system documentation
@Preview(name = "Xiaomi Lists - Light")
@Composable
fun XiaomiListsPreview() {
    XiaomiPreviewTheme {
        val sampleItems = listOf(
            ListItemData("1", "Account Settings", "Manage your account", Icons.Default.AccountCircle, true),
            ListItemData("2", "Notifications", "Configure notifications", Icons.Default.Email, true),
            ListItemData("3", "Privacy", "Privacy settings", Icons.Default.Settings, true),
            ListItemData("4", "About", "App information", null, true)
        )
        
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                XiaomiListSectionHeader("Settings")
            }
            
            items(sampleItems) { item ->
                XiaomiNavigationListItem(
                    onClick = { },
                    title = item.title,
                    subtitle = item.subtitle,
                    leadingIcon = item.icon
                )
                
                if (item != sampleItems.last()) {
                    XiaomiListDivider(
                        modifier = Modifier.padding(start = if (item.icon != null) 56.dp else 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Lists - Interactive")
@Composable
fun XiaomiListsInteractivePreview() {
    XiaomiPreviewTheme {
        var switchState by remember { mutableStateOf(true) }
        var checkboxState by remember { mutableStateOf(false) }
        var radioSelection by remember { mutableStateOf("option1") }
        
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                XiaomiListSectionHeader("Preferences")
            }
            
            item {
                XiaomiSwitchListItem(
                    title = "Enable Notifications",
                    subtitle = "Receive push notifications",
                    checked = switchState,
                    onCheckedChange = { switchState = it },
                    leadingIcon = Icons.Default.Email
                )
            }
            
            item {
                XiaomiListDivider(modifier = Modifier.padding(start = 56.dp))
            }
            
            item {
                XiaomiCheckboxListItem(
                    title = "Save to Favorites",
                    subtitle = "Automatically save liked items",
                    checked = checkboxState,
                    onCheckedChange = { checkboxState = it },
                    leadingIcon = Icons.Default.Favorite
                )
            }
            
            item {
                XiaomiListSectionHeader("Theme")
            }
            
            item {
                XiaomiRadioListItem(
                    title = "Light Theme",
                    selected = radioSelection == "light",
                    onClick = { radioSelection = "light" }
                )
            }
            
            item {
                XiaomiRadioListItem(
                    title = "Dark Theme",
                    selected = radioSelection == "dark",
                    onClick = { radioSelection = "dark" }
                )
            }
            
            item {
                XiaomiRadioListItem(
                    title = "System Default",
                    selected = radioSelection == "system",
                    onClick = { radioSelection = "system" }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Lists - Dark")
@Composable
fun XiaomiListsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column {
            XiaomiListSectionHeader("Quick Actions")
            
            XiaomiNavigationListItem(
                onClick = { },
                title = "Profile",
                subtitle = "View and edit profile",
                leadingIcon = Icons.Default.AccountCircle
            )
            
            XiaomiListDivider(modifier = Modifier.padding(start = 56.dp))
            
            XiaomiNavigationListItem(
                onClick = { },
                title = "Settings",
                subtitle = "App preferences",
                leadingIcon = Icons.Default.Settings
            )
        }
    }
}