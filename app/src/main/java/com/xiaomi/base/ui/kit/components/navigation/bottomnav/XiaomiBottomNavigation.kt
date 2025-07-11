package com.xiaomi.base.ui.kit.components.navigation.bottomnav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme

/**
 * Data class representing a bottom navigation item
 */
data class XiaomiBottomNavItem(
    val id: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector = selectedIcon,
    val badge: String? = null,
    val enabled: Boolean = true,
    val onClick: () -> Unit = {}
)

/**
 * Xiaomi Bottom Navigation Bar
 * 
 * A Material Design 3 bottom navigation bar component with Xiaomi design tokens.
 * Provides navigation between top-level destinations in an app.
 * 
 * @param modifier Modifier to be applied to the navigation bar
 * @param containerColor The color used for the background of this navigation bar
 * @param contentColor The preferred color for content inside this navigation bar
 * @param tonalElevation The tonal elevation of this navigation bar
 * @param windowInsets Window insets to be applied to the navigation bar
 * @param content The content of the navigation bar, typically NavigationBarItems
 */
@Composable
fun XiaomiBottomNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.NavigationBarDefaults.containerColor,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: androidx.compose.ui.unit.Dp = androidx.compose.material3.NavigationBarDefaults.Elevation,
    windowInsets: androidx.compose.foundation.layout.WindowInsets = androidx.compose.material3.NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        windowInsets = windowInsets,
        content = content
    )
}

/**
 * Xiaomi Bottom Navigation Item
 * 
 * A single item in the bottom navigation bar.
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
fun RowScope.XiaomiBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: (@Composable () -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: androidx.compose.material3.NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
) {
    NavigationBarItem(
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
 * Xiaomi Simple Bottom Navigation
 * 
 * A simplified bottom navigation with predefined items.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation bar
 * @param showLabels Whether to show labels for items
 */
@Composable
fun XiaomiSimpleBottomNavigation(
    items: List<XiaomiBottomNavItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    showLabels: Boolean = true
) {
    XiaomiBottomNavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = item.id == selectedItemId
            
            XiaomiBottomNavigationItem(
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
 * Xiaomi Icon Only Bottom Navigation
 * 
 * A bottom navigation with only icons, no labels.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation bar
 */
@Composable
fun XiaomiIconOnlyBottomNavigation(
    items: List<XiaomiBottomNavItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiSimpleBottomNavigation(
        items = items,
        selectedItemId = selectedItemId,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showLabels = false
    )
}

/**
 * Xiaomi Badge Bottom Navigation
 * 
 * A bottom navigation with badge support.
 * 
 * @param items List of navigation items with badges
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation bar
 */
@Composable
fun XiaomiBadgeBottomNavigation(
    items: List<XiaomiBottomNavItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiBottomNavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = item.id == selectedItemId
            
            XiaomiBottomNavigationItem(
                selected = isSelected,
                onClick = {
                    onItemSelected(item.id)
                    item.onClick()
                },
                enabled = item.enabled,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badge != null) {
                                Badge {
                                    Text(
                                        text = item.badge,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

/**
 * Xiaomi Custom Bottom Navigation
 * 
 * A customizable bottom navigation with full control over appearance.
 * 
 * @param items List of navigation items
 * @param selectedItemId Currently selected item ID
 * @param onItemSelected Callback when an item is selected
 * @param modifier Modifier to be applied to the navigation bar
 * @param containerColor Background color of the navigation bar
 * @param contentColor Content color of the navigation bar
 * @param showLabels Whether to show labels
 * @param alwaysShowLabels Whether to always show labels
 */
@Composable
fun XiaomiCustomBottomNavigation(
    items: List<XiaomiBottomNavItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.NavigationBarDefaults.containerColor,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    showLabels: Boolean = true,
    alwaysShowLabels: Boolean = true
) {
    XiaomiBottomNavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor
    ) {
        items.forEach { item ->
            val isSelected = item.id == selectedItemId
            
            XiaomiBottomNavigationItem(
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
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else null,
                alwaysShowLabel = alwaysShowLabels
            )
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Bottom Navigation - Light")
@Composable
fun XiaomiBottomNavigationPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiBottomNavItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiBottomNavItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiBottomNavItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "3"
            ),
            XiaomiBottomNavItem(
                id = "profile",
                label = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Bottom Navigation Variants",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            XiaomiSimpleBottomNavigation(
                items = items,
                selectedItemId = items[selectedItem].id,
                onItemSelected = { id ->
                    selectedItem = items.indexOfFirst { it.id == id }
                }
            )
        }
    }
}

@Preview(name = "Xiaomi Bottom Navigation - Badge")
@Composable
fun XiaomiBadgeBottomNavigationPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiBottomNavItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiBottomNavItem(
                id = "email",
                label = "Email",
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                badge = "12"
            ),
            XiaomiBottomNavItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "99+"
            ),
            XiaomiBottomNavItem(
                id = "settings",
                label = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
        )
        
        XiaomiBadgeBottomNavigation(
            items = items,
            selectedItemId = items[selectedItem].id,
            onItemSelected = { id ->
                selectedItem = items.indexOfFirst { it.id == id }
            }
        )
    }
}

@Preview(name = "Xiaomi Bottom Navigation - Icon Only")
@Composable
fun XiaomiIconOnlyBottomNavigationPreview() {
    XiaomiPreviewTheme {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiBottomNavItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiBottomNavItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiBottomNavItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "5"
            ),
            XiaomiBottomNavItem(
                id = "profile",
                label = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            ),
            XiaomiBottomNavItem(
                id = "settings",
                label = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
        )
        
        XiaomiIconOnlyBottomNavigation(
            items = items,
            selectedItemId = items[selectedItem].id,
            onItemSelected = { id ->
                selectedItem = items.indexOfFirst { it.id == id }
            }
        )
    }
}

@Preview(name = "Xiaomi Bottom Navigation - Dark")
@Composable
fun XiaomiBottomNavigationDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        var selectedItem by remember { mutableIntStateOf(0) }
        
        val items = listOf(
            XiaomiBottomNavItem(
                id = "home",
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            XiaomiBottomNavItem(
                id = "search",
                label = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            XiaomiBottomNavItem(
                id = "favorites",
                label = "Favorites",
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                badge = "2"
            )
        )
        
        XiaomiSimpleBottomNavigation(
            items = items,
            selectedItemId = items[selectedItem].id,
            onItemSelected = { id ->
                selectedItem = items.indexOfFirst { it.id == id }
            }
        )
    }
}