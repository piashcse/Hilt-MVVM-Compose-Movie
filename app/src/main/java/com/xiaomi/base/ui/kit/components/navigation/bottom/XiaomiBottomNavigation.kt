package com.xiaomi.base.ui.kit.components.navigation.bottom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme

/**
 * Xiaomi Bottom Navigation Components
 * 
 * This file contains bottom navigation components following Material Design 3 principles
 * with Xiaomi's design language. These components provide various bottom navigation styles
 * including standard, floating, and custom implementations.
 */

/**
 * Bottom Navigation Item Data
 */
data class XiaomiBottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val badge: String? = null,
    val enabled: Boolean = true
)

/**
 * Bottom Navigation Styles
 */
enum class XiaomiBottomNavStyle {
    STANDARD,
    FLOATING,
    PILL,
    MINIMAL
}

/**
 * Xiaomi Bottom Navigation Bar
 * Standard bottom navigation with Material Design 3 styling
 */
@Composable
fun XiaomiBottomNavigationBar(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: XiaomiBottomNavStyle = XiaomiBottomNavStyle.STANDARD,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: androidx.compose.ui.unit.Dp = 3.dp
) {
    when (style) {
        XiaomiBottomNavStyle.STANDARD -> {
            StandardBottomNav(
                items = items,
                selectedRoute = selectedRoute,
                onItemSelected = onItemSelected,
                modifier = modifier,
                containerColor = containerColor,
                contentColor = contentColor,
                tonalElevation = tonalElevation
            )
        }
        XiaomiBottomNavStyle.FLOATING -> {
            FloatingBottomNav(
                items = items,
                selectedRoute = selectedRoute,
                onItemSelected = onItemSelected,
                modifier = modifier
            )
        }
        XiaomiBottomNavStyle.PILL -> {
            PillBottomNav(
                items = items,
                selectedRoute = selectedRoute,
                onItemSelected = onItemSelected,
                modifier = modifier
            )
        }
        XiaomiBottomNavStyle.MINIMAL -> {
            MinimalBottomNav(
                items = items,
                selectedRoute = selectedRoute,
                onItemSelected = onItemSelected,
                modifier = modifier
            )
        }
    }
}

/**
 * Standard Bottom Navigation Implementation
 */
@Composable
private fun StandardBottomNav(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: androidx.compose.ui.unit.Dp = 3.dp
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            
            NavigationBarItem(
                icon = {
                    XiaomiBottomNavIcon(
                        item = item,
                        selected = selected
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = selected,
                onClick = { 
                    if (item.enabled) {
                        onItemSelected(item.route)
                    }
                },
                enabled = item.enabled
            )
        }
    }
}

/**
 * Floating Bottom Navigation Implementation
 */
@Composable
private fun FloatingBottomNav(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = selectedRoute == item.route
                
                XiaomiFloatingNavItem(
                    item = item,
                    selected = selected,
                    onClick = { 
                        if (item.enabled) {
                            onItemSelected(item.route)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Pill Bottom Navigation Implementation
 */
@Composable
private fun PillBottomNav(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = selectedRoute == item.route
                
                XiaomiPillNavItem(
                    item = item,
                    selected = selected,
                    onClick = { 
                        if (item.enabled) {
                            onItemSelected(item.route)
                        }
                    }
                )
            }
        }
    }
}

/**
 * Minimal Bottom Navigation Implementation
 */
@Composable
private fun MinimalBottomNav(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            
            XiaomiMinimalNavItem(
                item = item,
                selected = selected,
                onClick = { 
                    if (item.enabled) {
                        onItemSelected(item.route)
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Xiaomi Bottom Navigation Icon with Badge Support
 */
@Composable
fun XiaomiBottomNavIcon(
    item: XiaomiBottomNavItem,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = tween(200),
        label = "icon_scale"
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.label,
            modifier = Modifier.scale(scale)
        )
        
        // Badge
        item.badge?.let { badge ->
            Badge(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    text = badge,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Floating Navigation Item
 */
@Composable
private fun XiaomiFloatingNavItem(
    item: XiaomiBottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        animationSpec = tween(200),
        label = "background_color"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(200),
        label = "content_color"
    )
    
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(animatedColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Icon(
                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            
            item.badge?.let { badge ->
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        
        if (selected) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Pill Navigation Item
 */
@Composable
private fun XiaomiPillNavItem(
    item: XiaomiBottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        animationSpec = tween(200),
        label = "pill_color"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(200),
        label = "pill_content_color"
    )
    
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(animatedColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(horizontal = if (selected) 16.dp else 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                Icon(
                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.label,
                    tint = contentColor,
                    modifier = Modifier.size(20.dp)
                )
                
                item.badge?.let { badge ->
                    Badge(
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
            
            if (selected) {
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Minimal Navigation Item
 */
@Composable
private fun XiaomiMinimalNavItem(
    item: XiaomiBottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(200),
        label = "minimal_content_color"
    )
    
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = tween(200),
        label = "minimal_scale"
    )
    
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Icon(
                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier
                    .size(24.dp)
                    .scale(scale)
            )
            
            item.badge?.let { badge ->
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        // Selection indicator
        if (selected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(contentColor)
            )
        }
    }
}

/**
 * Xiaomi Adaptive Bottom Navigation
 * Automatically adapts based on screen size and item count
 */
@Composable
fun XiaomiAdaptiveBottomNavigation(
    items: List<XiaomiBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val style = when {
        items.size <= 3 -> XiaomiBottomNavStyle.PILL
        items.size == 4 -> XiaomiBottomNavStyle.FLOATING
        else -> XiaomiBottomNavStyle.STANDARD
    }
    
    XiaomiBottomNavigationBar(
        items = items,
        selectedRoute = selectedRoute,
        onItemSelected = onItemSelected,
        style = style,
        modifier = modifier
    )
}

// Preview Functions
@Preview(name = "Standard Bottom Navigation - Light")
@Composable
fun XiaomiBottomNavigationStandardPreview() {
    XiaomiTheme {
        Surface {
            val items = listOf(
                XiaomiBottomNavItem(
                    route = "home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    label = "Home"
                ),
                XiaomiBottomNavItem(
                    route = "search",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search,
                    label = "Search"
                ),
                XiaomiBottomNavItem(
                    route = "notifications",
                    selectedIcon = Icons.Filled.Notifications,
                    unselectedIcon = Icons.Outlined.Notifications,
                    label = "Notifications",
                    badge = "3"
                ),
                XiaomiBottomNavItem(
                    route = "profile",
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person,
                    label = "Profile"
                )
            )
            
            XiaomiBottomNavigationBar(
                items = items,
                selectedRoute = "home",
                onItemSelected = {},
                style = XiaomiBottomNavStyle.STANDARD
            )
        }
    }
}

@Preview(name = "Floating Bottom Navigation - Light")
@Composable
fun XiaomiBottomNavigationFloatingPreview() {
    XiaomiTheme {
        Surface {
            val items = listOf(
                XiaomiBottomNavItem(
                    route = "home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    label = "Home"
                ),
                XiaomiBottomNavItem(
                    route = "explore",
                    selectedIcon = Icons.Filled.Explore,
                    unselectedIcon = Icons.Outlined.Explore,
                    label = "Explore"
                ),
                XiaomiBottomNavItem(
                    route = "favorites",
                    selectedIcon = Icons.Filled.Favorite,
                    unselectedIcon = Icons.Outlined.FavoriteBorder,
                    label = "Favorites",
                    badge = "12"
                ),
                XiaomiBottomNavItem(
                    route = "settings",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings,
                    label = "Settings"
                )
            )
            
            XiaomiBottomNavigationBar(
                items = items,
                selectedRoute = "explore",
                onItemSelected = {},
                style = XiaomiBottomNavStyle.FLOATING
            )
        }
    }
}

@Preview(name = "Pill Bottom Navigation - Light")
@Composable
fun XiaomiBottomNavigationPillPreview() {
    XiaomiTheme {
        Surface {
            val items = listOf(
                XiaomiBottomNavItem(
                    route = "dashboard",
                    selectedIcon = Icons.Filled.Dashboard,
                    unselectedIcon = Icons.Outlined.Dashboard,
                    label = "Dashboard"
                ),
                XiaomiBottomNavItem(
                    route = "analytics",
                    selectedIcon = Icons.Filled.Analytics,
                    unselectedIcon = Icons.Outlined.Analytics,
                    label = "Analytics"
                ),
                XiaomiBottomNavItem(
                    route = "account",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle,
                    label = "Account"
                )
            )
            
            XiaomiBottomNavigationBar(
                items = items,
                selectedRoute = "analytics",
                onItemSelected = {},
                style = XiaomiBottomNavStyle.PILL
            )
        }
    }
}

@Preview(name = "Minimal Bottom Navigation - Light")
@Composable
fun XiaomiBottomNavigationMinimalPreview() {
    XiaomiTheme {
        Surface {
            val items = listOf(
                XiaomiBottomNavItem(
                    route = "home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    label = "Home"
                ),
                XiaomiBottomNavItem(
                    route = "library",
                    selectedIcon = Icons.Filled.LibraryBooks,
                    unselectedIcon = Icons.Outlined.LibraryBooks,
                    label = "Library"
                ),
                XiaomiBottomNavItem(
                    route = "downloads",
                    selectedIcon = Icons.Filled.Download,
                    unselectedIcon = Icons.Outlined.Download,
                    label = "Downloads",
                    badge = "5"
                ),
                XiaomiBottomNavItem(
                    route = "more",
                    selectedIcon = Icons.Filled.MoreHoriz,
                    unselectedIcon = Icons.Outlined.MoreHoriz,
                    label = "More"
                )
            )
            
            XiaomiBottomNavigationBar(
                items = items,
                selectedRoute = "downloads",
                onItemSelected = {},
                style = XiaomiBottomNavStyle.MINIMAL
            )
        }
    }
}

@Preview(name = "Bottom Navigation - Dark")
@Composable
fun XiaomiBottomNavigationDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Dark Theme Bottom Navigation",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                
                val items = listOf(
                    XiaomiBottomNavItem(
                        route = "home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        label = "Home"
                    ),
                    XiaomiBottomNavItem(
                        route = "search",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search,
                        label = "Search"
                    ),
                    XiaomiBottomNavItem(
                        route = "notifications",
                        selectedIcon = Icons.Filled.Notifications,
                        unselectedIcon = Icons.Outlined.Notifications,
                        label = "Notifications",
                        badge = "99+"
                    ),
                    XiaomiBottomNavItem(
                        route = "profile",
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person,
                        label = "Profile"
                    )
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                XiaomiBottomNavigationBar(
                    items = items,
                    selectedRoute = "notifications",
                    onItemSelected = {},
                    style = XiaomiBottomNavStyle.FLOATING
                )
            }
        }
    }
}