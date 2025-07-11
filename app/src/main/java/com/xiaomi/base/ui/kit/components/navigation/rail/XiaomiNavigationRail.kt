package com.xiaomi.base.ui.kit.components.navigation.rail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a navigation rail item
 */
data class XiaomiNavigationRailItem(
    val id: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector = selectedIcon,
    val badge: String? = null,
    val enabled: Boolean = true,
    val onClick: () -> Unit = {}
)

/**
 * Xiaomi Navigation Rail
 * 
 * A Material Design 3 navigation rail component with Xiaomi design tokens.
 * Provides navigation between top-level destinations in an app for larger screens.
 * 
 * @param modifier Modifier to be applied to the navigation rail
 * @param containerColor The color used for the background of this navigation rail
 * @param contentColor The preferred color for content inside this navigation rail
 * @param header Optional header content at the top of the rail
 * @param windowInsets Window insets to be applied to the navigation rail
 * @param content The content of the navigation rail, typically NavigationRailItems
 */
@Composable
fun XiaomiNavigationRail(
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.NavigationRailDefaults.ContainerColor,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    header: (@Composable ColumnScope.() -> Unit)? = null,
    windowInsets: androidx.compose.foundation.layout.WindowInsets = androidx.compose.material3.NavigationRailDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        header = header,
        windowInsets = windowInsets,
        content = content
    )
}

/**
 * Xiaomi Navigation Rail Item
 * 
 * A single item in the navigation rail.
 * 
 * @param selected Whether this item is currently selected
 * @param onClick Callback when this item is clicked
 * @param icon The icon for this item
 * @param modifier Modifier to be applied to this item
 * @param enabled Whether this item is enabled
 * @param label Optional text label for this item
 * @param alwaysShowLabel Whether to always show the label
 * @param colors Colors to be used for this item
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: (@Composable () -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: androidx.compose.material3.NavigationRailItemColors = NavigationRailItemDefaults.colors(),
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Simple Navigation Rail
 * 
 * A simplified navigation rail with predefined items.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation rail
 * @param showLabels Whether to show labels for items
 * @param header Optional header content
 */
@Composable
fun XiaomiSimpleNavigationRail(
    items: List<XiaomiNavigationRailItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true,
    header: (@Composable ColumnScope.() -> Unit)? = null
) {
    XiaomiNavigationRail(
        modifier = modifier,
        header = header
    ) {
        items.forEach { item ->
            val isSelected = item.id == selectedItemId
            
            XiaomiNavigationRailItem(
                selected = isSelected,
                onClick = {
                    onItemSelected(item.id)
                    item.onClick()
                },
                enabled = item.enabled,
                icon = {
                    if (item.badge != null) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(item.badge)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = if (showLabels) {
                    {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else null,
                alwaysShowLabel = showLabels
            )
        }
    }
}

/**
 * Xiaomi Navigation Rail with FAB
 * 
 * A navigation rail with a floating action button in the header.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param onFabClick Callback when FAB is clicked
 * @param modifier Modifier to be applied to the navigation rail
 * @param fabIcon Icon for the floating action button
 * @param fabContentDescription Content description for the FAB
 * @param showLabels Whether to show labels for items
 */
@Composable
fun XiaomiNavigationRailWithFAB(
    items: List<XiaomiNavigationRailItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
    fabIcon: ImageVector = Icons.Default.Add,
    fabContentDescription: String = "Add",
    showLabels: Boolean = true
) {
    XiaomiSimpleNavigationRail(
        items = items,
        selectedItemId = selectedItemId,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showLabels = showLabels,
        header = {
            FloatingActionButton(
                onClick = onFabClick,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = fabIcon,
                    contentDescription = fabContentDescription
                )
            }
        }
    )
}

/**
 * Xiaomi Navigation Rail with Menu
 * 
 * A navigation rail with a menu button in the header.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param onMenuClick Callback when menu button is clicked
 * @param modifier Modifier to be applied to the navigation rail
 * @param menuIcon Icon for the menu button
 * @param menuContentDescription Content description for the menu button
 * @param showLabels Whether to show labels for items
 */
@Composable
fun XiaomiNavigationRailWithMenu(
    items: List<XiaomiNavigationRailItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    menuIcon: ImageVector = Icons.Default.Menu,
    menuContentDescription: String = "Menu",
    showLabels: Boolean = true
) {
    XiaomiSimpleNavigationRail(
        items = items,
        selectedItemId = selectedItemId,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showLabels = showLabels,
        header = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = menuIcon,
                    contentDescription = menuContentDescription,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

/**
 * Xiaomi Compact Navigation Rail
 * 
 * A compact navigation rail without labels.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation rail
 * @param header Optional header content
 */
@Composable
fun XiaomiCompactNavigationRail(
    items: List<XiaomiNavigationRailItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    header: (@Composable ColumnScope.() -> Unit)? = null
) {
    XiaomiSimpleNavigationRail(
        items = items,
        selectedItemId = selectedItemId,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showLabels = false,
        header = header
    )
}

/**
 * Xiaomi Extended Navigation Rail
 * 
 * A navigation rail with extended content and custom header.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation rail
 * @param headerTitle Optional title for the header
 * @param headerActions Optional actions in the header
 * @param showLabels Whether to show labels for items
 */
@Composable
fun XiaomiExtendedNavigationRail(
    items: List<XiaomiNavigationRailItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    headerTitle: String? = null,
    headerActions: (@Composable () -> Unit)? = null,
    showLabels: Boolean = true
) {
    XiaomiSimpleNavigationRail(
        items = items,
        selectedItemId = selectedItemId,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showLabels = showLabels,
        header = if (headerTitle != null || headerActions != null) {
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (headerTitle != null) {
                        Text(
                            text = headerTitle,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                    
                    if (headerActions != null) {
                        headerActions()
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else null
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Navigation Rail - Light")
@Composable
fun XiaomiNavigationRailPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiNavigationRailItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiNavigationRailItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiNavigationRailItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "3"
            ),
            XiaomiNavigationRailItem(
                id = "profile",
                label = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
        
        Row {
            XiaomiSimpleNavigationRail(
                items = items,
                selectedItemId = items[selectedItem].id,
                onItemSelected = { id ->
                    selectedItem = items.indexOfFirst { it.id == id }
                },
                modifier = Modifier.width(80.dp)
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selected: ${items[selectedItem].label}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Navigation Rail - With FAB")
@Composable
fun XiaomiNavigationRailWithFABPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiNavigationRailItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiNavigationRailItem(
                id = "email",
                label = "Email",
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                badge = "12"
            ),
            XiaomiNavigationRailItem(
                id = "settings",
                label = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
        )
        
        Row {
            XiaomiNavigationRailWithFAB(
                items = items,
                selectedItemId = items[selectedItem].id,
                onItemSelected = { id ->
                    selectedItem = items.indexOfFirst { it.id == id }
                },
                onFabClick = { },
                modifier = Modifier.width(80.dp)
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Navigation Rail with FAB",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Navigation Rail - Compact")
@Composable
fun XiaomiCompactNavigationRailPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiNavigationRailItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiNavigationRailItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiNavigationRailItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "5"
            ),
            XiaomiNavigationRailItem(
                id = "profile",
                label = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            ),
            XiaomiNavigationRailItem(
                id = "settings",
                label = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
        )
        
        Row {
            XiaomiCompactNavigationRail(
                items = items,
                selectedItemId = items[selectedItem].id,
                onItemSelected = { id ->
                    selectedItem = items.indexOfFirst { it.id == id }
                },
                modifier = Modifier.width(56.dp)
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Compact Navigation Rail",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Navigation Rail - Dark")
@Composable
fun XiaomiNavigationRailDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiNavigationRailItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiNavigationRailItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiNavigationRailItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "2"
            )
        )
        
        Row {
            XiaomiSimpleNavigationRail(
                items = items,
                selectedItemId = items[selectedItem].id,
                onItemSelected = { id ->
                    selectedItem = items.indexOfFirst { it.id == id }
                },
                modifier = Modifier.width(80.dp)
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Dark Theme",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}