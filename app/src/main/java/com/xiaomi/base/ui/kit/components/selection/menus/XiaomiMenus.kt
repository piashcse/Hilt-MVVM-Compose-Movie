package com.xiaomi.base.ui.kit.components.selection.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Xiaomi Dropdown Menu
 * 
 * A Material Design 3 dropdown menu with Xiaomi design tokens.
 * 
 * @param expanded Whether the menu is expanded
 * @param onDismissRequest Callback when the menu is dismissed
 * @param modifier Modifier to be applied to the menu
 * @param offset Offset for menu positioning
 * @param scrollState Scroll state for the menu
 * @param properties Popup properties
 * @param shape Shape of the menu
 * @param containerColor Background color of the menu
 * @param tonalElevation Tonal elevation of the menu
 * @param shadowElevation Shadow elevation of the menu
 * @param border Border for the menu
 * @param content Content of the menu
 */
@Composable
fun XiaomiDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    scrollState: androidx.compose.foundation.ScrollState = androidx.compose.foundation.rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    shape: androidx.compose.ui.graphics.Shape = MaterialTheme.shapes.extraSmall,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: androidx.compose.ui.unit.Dp = 3.dp,
    shadowElevation: androidx.compose.ui.unit.Dp = 3.dp,
    border: androidx.compose.foundation.BorderStroke? = null,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        offset = offset,
        scrollState = scrollState,
        properties = properties,
        shape = shape,
        containerColor = containerColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        content = content
    )
}

/**
 * Xiaomi Dropdown Menu Item
 * 
 * A menu item for dropdown menus.
 * 
 * @param text Text content of the menu item
 * @param onClick Callback when the item is clicked
 * @param modifier Modifier to be applied to the item
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param enabled Whether the item is enabled
 * @param colors Colors for the menu item
 * @param contentPadding Content padding for the item
 * @param interactionSource Interaction source for the item
 */
@Composable
fun XiaomiDropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: androidx.compose.material3.MenuItemColors = MenuDefaults.itemColors(),
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
) {
    DropdownMenuItem(
        text = text,
        onClick = onClick,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Context Menu
 * 
 * A context menu that appears on long press or right click.
 * 
 * @param items List of menu items
 * @param expanded Whether the menu is expanded
 * @param onDismissRequest Callback when the menu is dismissed
 * @param modifier Modifier to be applied to the menu
 */
@Composable
fun XiaomiContextMenu(
    items: List<XiaomiMenuItem>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            if (item.isDivider) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            } else {
                XiaomiDropdownMenuItem(
                    text = {
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        item.onClick()
                        onDismissRequest()
                    },
                    leadingIcon = item.icon?.let {
                        {
                            Icon(
                                imageVector = it,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    enabled = item.enabled,
                    colors = if (item.isDestructive) {
                        MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.error,
                            leadingIconColor = MaterialTheme.colorScheme.error
                        )
                    } else {
                        MenuDefaults.itemColors()
                    }
                )
            }
        }
    }
}

/**
 * Xiaomi Exposed Dropdown Menu
 * 
 * An exposed dropdown menu for selection.
 * 
 * @param options List of options
 * @param selectedOption Currently selected option
 * @param onOptionSelected Callback when an option is selected
 * @param label Label for the dropdown
 * @param modifier Modifier to be applied to the dropdown
 * @param enabled Whether the dropdown is enabled
 * @param placeholder Placeholder text
 * @param supportingText Supporting text
 * @param isError Whether the dropdown is in error state
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiExposedDropdownMenu(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String? = null,
    supportingText: String? = null,
    isError: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded && enabled },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            supportingText = supportingText?.let { { Text(it) } },
            isError = isError,
            enabled = enabled,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    trailingIcon = if (option == selectedOption) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Selected",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else null,
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

/**
 * Xiaomi Cascading Menu
 * 
 * A menu with submenu support.
 * 
 * @param items List of menu items with potential submenus
 * @param expanded Whether the menu is expanded
 * @param onDismissRequest Callback when the menu is dismissed
 * @param modifier Modifier to be applied to the menu
 */
@Composable
fun XiaomiCascadingMenu(
    items: List<XiaomiCascadingMenuItem>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedSubmenu by remember { mutableStateOf<String?>(null) }
    
    XiaomiDropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expandedSubmenu = null
            onDismissRequest()
        },
        modifier = modifier
    ) {
        items.forEach { item ->
            if (item.isDivider) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            } else {
                Box {
                    XiaomiDropdownMenuItem(
                        text = {
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            if (item.subItems.isNotEmpty()) {
                                expandedSubmenu = if (expandedSubmenu == item.text) null else item.text
                            } else {
                                item.onClick()
                                onDismissRequest()
                            }
                        },
                        leadingIcon = item.icon?.let {
                            {
                                Icon(
                                    imageVector = it,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        trailingIcon = if (item.subItems.isNotEmpty()) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Submenu",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else null,
                        enabled = item.enabled
                    )
                    
                    // Submenu
                    if (item.subItems.isNotEmpty()) {
                        XiaomiDropdownMenu(
                            expanded = expandedSubmenu == item.text,
                            onDismissRequest = { expandedSubmenu = null },
                            offset = DpOffset(200.dp, 0.dp)
                        ) {
                            item.subItems.forEach { subItem ->
                                XiaomiDropdownMenuItem(
                                    text = {
                                        Text(
                                            text = subItem.text,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    },
                                    onClick = {
                                        subItem.onClick()
                                        expandedSubmenu = null
                                        onDismissRequest()
                                    },
                                    leadingIcon = subItem.icon?.let {
                                        {
                                            Icon(
                                                imageVector = it,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    },
                                    enabled = subItem.enabled
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Xiaomi Action Menu
 * 
 * A menu specifically for actions with icons.
 * 
 * @param actions List of actions
 * @param expanded Whether the menu is expanded
 * @param onDismissRequest Callback when the menu is dismissed
 * @param modifier Modifier to be applied to the menu
 * @param title Optional title for the menu
 */
@Composable
fun XiaomiActionMenu(
    actions: List<XiaomiMenuAction>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    XiaomiDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
        
        actions.forEach { action ->
            XiaomiDropdownMenuItem(
                text = {
                    Column {
                        Text(
                            text = action.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        if (action.description != null) {
                            Text(
                                text = action.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                onClick = {
                    action.onClick()
                    onDismissRequest()
                },
                leadingIcon = {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (action.isDestructive) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                },
                enabled = action.enabled,
                colors = if (action.isDestructive) {
                    MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.error
                    )
                } else {
                    MenuDefaults.itemColors()
                },
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
            )
        }
    }
}

/**
 * Data classes for menu items
 */
data class XiaomiMenuItem(
    val text: String = "",
    val icon: ImageVector? = null,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true,
    val isDestructive: Boolean = false,
    val isDivider: Boolean = false
)

data class XiaomiCascadingMenuItem(
    val text: String = "",
    val icon: ImageVector? = null,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true,
    val subItems: List<XiaomiMenuItem> = emptyList(),
    val isDivider: Boolean = false
)

data class XiaomiMenuAction(
    val title: String,
    val description: String? = null,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val isDestructive: Boolean = false
)

// Preview composables for design system documentation
@Preview(name = "Xiaomi Menus - Light")
@Composable
fun XiaomiMenusPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium)
            ) {
                Text(
                    "Menu Examples",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var showContextMenu by remember { mutableStateOf(false) }
                var showActionMenu by remember { mutableStateOf(false) }
                var showCascadingMenu by remember { mutableStateOf(false) }
                var selectedDropdownOption by remember { mutableStateOf<String?>(null) }
                
                // Context Menu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small)
                ) {
                    Text("Context Menu:")
                    Box {
                        IconButton(
                            onClick = { showContextMenu = true }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                        
                        XiaomiContextMenu(
                            items = listOf(
                                XiaomiMenuItem(
                                    text = "Edit",
                                    icon = Icons.Filled.Edit,
                                    onClick = { /* Handle edit */ }
                                ),
                                XiaomiMenuItem(
                                    text = "Share",
                                    icon = Icons.Filled.Share,
                                    onClick = { /* Handle share */ }
                                ),
                                XiaomiMenuItem(isDivider = true),
                                XiaomiMenuItem(
                                    text = "Delete",
                                    icon = Icons.Filled.Delete,
                                    onClick = { /* Handle delete */ },
                                    isDestructive = true
                                )
                            ),
                            expanded = showContextMenu,
                            onDismissRequest = { showContextMenu = false }
                        )
                    }
                }
                
                // Action Menu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small)
                ) {
                    Text("Action Menu:")
                    Box {
                        Button(
                            onClick = { showActionMenu = true }
                        ) {
                            Text("Actions")
                        }
                        
                        XiaomiActionMenu(
                            title = "Available Actions",
                            actions = listOf(
                                XiaomiMenuAction(
                                    title = "Edit Document",
                                    description = "Make changes to this document",
                                    icon = Icons.Filled.Edit,
                                    onClick = { /* Handle edit */ }
                                ),
                                XiaomiMenuAction(
                                    title = "Share",
                                    description = "Share with others",
                                    icon = Icons.Filled.Share,
                                    onClick = { /* Handle share */ }
                                ),
                                XiaomiMenuAction(
                                    title = "Settings",
                                    description = "Configure document settings",
                                    icon = Icons.Filled.Settings,
                                    onClick = { /* Handle settings */ }
                                ),
                                XiaomiMenuAction(
                                    title = "Delete",
                                    description = "Permanently delete this document",
                                    icon = Icons.Filled.Delete,
                                    onClick = { /* Handle delete */ },
                                    isDestructive = true
                                )
                            ),
                            expanded = showActionMenu,
                            onDismissRequest = { showActionMenu = false }
                        )
                    }
                }
                
                // Cascading Menu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small)
                ) {
                    Text("Cascading Menu:")
                    Box {
                        TextButton(
                            onClick = { showCascadingMenu = true }
                        ) {
                            Text("File Menu")
                        }
                        
                        XiaomiCascadingMenu(
                            items = listOf(
                                XiaomiCascadingMenuItem(
                                    text = "New",
                                    subItems = listOf(
                                        XiaomiMenuItem(
                                            text = "Document",
                                            onClick = { /* Handle new document */ }
                                        ),
                                        XiaomiMenuItem(
                                            text = "Folder",
                                            onClick = { /* Handle new folder */ }
                                        )
                                    )
                                ),
                                XiaomiCascadingMenuItem(
                                    text = "Open",
                                    icon = Icons.Filled.Edit,
                                    onClick = { /* Handle open */ }
                                ),
                                XiaomiCascadingMenuItem(isDivider = true),
                                XiaomiCascadingMenuItem(
                                    text = "Export",
                                    subItems = listOf(
                                        XiaomiMenuItem(
                                            text = "PDF",
                                            onClick = { /* Handle PDF export */ }
                                        ),
                                        XiaomiMenuItem(
                                            text = "Word",
                                            onClick = { /* Handle Word export */ }
                                        ),
                                        XiaomiMenuItem(
                                            text = "HTML",
                                            onClick = { /* Handle HTML export */ }
                                        )
                                    )
                                )
                            ),
                            expanded = showCascadingMenu,
                            onDismissRequest = { showCascadingMenu = false }
                        )
                    }
                }
                
                // Exposed Dropdown Menu
                XiaomiExposedDropdownMenu(
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    selectedOption = selectedDropdownOption,
                    onOptionSelected = { selectedDropdownOption = it },
                    label = "Select Option",
                    placeholder = "Choose an option",
                    supportingText = "This is a dropdown menu for selection",
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Menu showcase cards
                Text(
                    "Menu Types",
                    style = MaterialTheme.typography.titleSmall
                )
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        listOf(
                            "Context Menu" to "Right-click or long-press menu",
                            "Action Menu" to "Menu with detailed action descriptions",
                            "Cascading Menu" to "Menu with nested submenus",
                            "Dropdown Menu" to "Selection dropdown with options"
                        )
                    ) { (title, description) ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Menus - Dark")
@Composable
fun XiaomiMenusDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium)
            ) {
                Text(
                    "Dark Theme Menus",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var selectedOption by remember { mutableStateOf<String?>(null) }
                
                XiaomiExposedDropdownMenu(
                    options = listOf("Dark Option 1", "Dark Option 2", "Dark Option 3"),
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it },
                    label = "Dark Theme Dropdown",
                    placeholder = "Select an option",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}