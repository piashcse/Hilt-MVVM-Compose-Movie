package com.xiaomi.base.ui.kit.components.navigation.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a navigation drawer item
 */
data class XiaomiDrawerItem(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val badge: String? = null,
    val isSelected: Boolean = false,
    val onClick: () -> Unit = {}
)

/**
 * Data class representing a drawer section
 */
data class XiaomiDrawerSection(
    val title: String? = null,
    val items: List<XiaomiDrawerItem>
)

/**
 * Xiaomi Modal Navigation Drawer
 * 
 * A modal navigation drawer that slides in from the side.
 * 
 * @param drawerContent The content of the drawer
 * @param modifier Modifier to be applied to the drawer
 * @param drawerState State of the drawer
 * @param gesturesEnabled Whether gestures are enabled for the drawer
 * @param scrimColor Color of the scrim when drawer is open
 * @param content The main content of the screen
 */
@Composable
fun XiaomiModalNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    scrimColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.DrawerDefaults.scrimColor,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                drawerContent()
            }
        },
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        scrimColor = scrimColor,
        content = content
    )
}

/**
 * Xiaomi Dismissible Navigation Drawer
 * 
 * A dismissible navigation drawer that can be swiped away.
 * 
 * @param drawerContent The content of the drawer
 * @param modifier Modifier to be applied to the drawer
 * @param drawerState State of the drawer
 * @param gesturesEnabled Whether gestures are enabled for the drawer
 * @param content The main content of the screen
 */
@Composable
fun XiaomiDismissibleNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    DismissibleNavigationDrawer(
        drawerContent = {
            DismissibleDrawerSheet {
                drawerContent()
            }
        },
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        content = content
    )
}

/**
 * Xiaomi Permanent Navigation Drawer
 * 
 * A permanent navigation drawer that is always visible.
 * 
 * @param drawerContent The content of the drawer
 * @param modifier Modifier to be applied to the drawer
 * @param content The main content of the screen
 */
@Composable
fun XiaomiPermanentNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet {
                drawerContent()
            }
        },
        modifier = modifier,
        content = content
    )
}

/**
 * Xiaomi Navigation Drawer Item
 * 
 * A single item in the navigation drawer.
 * 
 * @param label The text label for the item
 * @param selected Whether the item is currently selected
 * @param onClick Callback when the item is clicked
 * @param modifier Modifier to be applied to the item
 * @param icon Optional leading icon for the item
 * @param badge Optional badge content for the item
 * @param colors Colors to be used for the item
 */
@Composable
fun XiaomiNavigationDrawerItem(
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    badge: (@Composable () -> Unit)? = null,
    colors: androidx.compose.material3.NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors()
) {
    NavigationDrawerItem(
        label = label,
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        badge = badge,
        colors = colors
    )
}

/**
 * Xiaomi Drawer Header
 * 
 * A header component for the navigation drawer.
 * 
 * @param title The main title text
 * @param subtitle Optional subtitle text
 * @param modifier Modifier to be applied to the header
 * @param avatar Optional avatar content
 * @param onCloseClick Optional callback for close button
 */
@Composable
fun XiaomiDrawerHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    avatar: (@Composable () -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Close button
            if (onCloseClick != null) {
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close drawer",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier.align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.small)
            ) {
                // Avatar
                if (avatar != null) {
                    avatar()
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Subtitle
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Drawer Content
 * 
 * A complete drawer content with header and sections.
 * 
 * @param title The header title
 * @param subtitle Optional header subtitle
 * @param sections List of drawer sections
 * @param modifier Modifier to be applied to the content
 * @param onCloseClick Optional callback for close button
 */
@Composable
fun XiaomiDrawerContent(
    title: String,
    subtitle: String? = null,
    sections: List<XiaomiDrawerSection>,
    modifier: Modifier = Modifier,
    onCloseClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        // Header
        XiaomiDrawerHeader(
            title = title,
            subtitle = subtitle,
            onCloseClick = onCloseClick
        )
        
        // Content
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.extraSmall)
        ) {
            sections.forEachIndexed { sectionIndex, section ->
                // Section title
                if (section.title != null) {
                    item {
                        if (sectionIndex > 0) {
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    horizontal = XiaomiSpacing.medium,
                                    vertical = XiaomiSpacing.small
                                )
                            )
                        }
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(
                                horizontal = XiaomiSpacing.large,
                                vertical = XiaomiSpacing.small
                            )
                        )
                    }
                }
                
                // Section items
                items(section.items) { item ->
                    XiaomiNavigationDrawerItem(
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        selected = item.isSelected,
                        onClick = item.onClick,
                        modifier = Modifier.padding(horizontal = XiaomiSpacing.small),
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        badge = if (item.badge != null) {
                            {
                                Badge {
                                    Text(item.badge)
                                }
                            }
                        } else null
                    )
                }
            }
        }
    }
}

/**
 * Xiaomi Simple Drawer Content
 * 
 * A simplified drawer content with just items.
 * 
 * @param items List of drawer items
 * @param modifier Modifier to be applied to the content
 */
@Composable
fun XiaomiSimpleDrawerContent(
    items: List<XiaomiDrawerItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = XiaomiSpacing.small),
        verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.extraSmall)
    ) {
        items(items) { item ->
            XiaomiNavigationDrawerItem(
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = item.isSelected,
                onClick = item.onClick,
                modifier = Modifier.padding(horizontal = XiaomiSpacing.small),
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
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    }
                }
            )
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Navigation Drawer - Light")
@Composable
fun XiaomiNavigationDrawerPreview() {
    XiaomiPreviewTheme {
        val sampleSections = listOf(
            XiaomiDrawerSection(
                title = "Main",
                items = listOf(
                    XiaomiDrawerItem(
                        id = "home",
                        label = "Home",
                        icon = Icons.Default.Home,
                        isSelected = true
                    ),
                    XiaomiDrawerItem(
                        id = "favorites",
                        label = "Favorites",
                        icon = Icons.Default.Favorite,
                        badge = "3"
                    )
                )
            ),
            XiaomiDrawerSection(
                title = "Communication",
                items = listOf(
                    XiaomiDrawerItem(
                        id = "email",
                        label = "Email",
                        icon = Icons.Default.Email,
                        badge = "12"
                    )
                )
            ),
            XiaomiDrawerSection(
                items = listOf(
                    XiaomiDrawerItem(
                        id = "settings",
                        label = "Settings",
                        icon = Icons.Default.Settings
                    )
                )
            )
        )
        
        XiaomiDrawerContent(
            title = "Xiaomi App",
            subtitle = "user@xiaomi.com",
            sections = sampleSections,
            modifier = Modifier.width(280.dp)
        )
    }
}

@Preview(name = "Xiaomi Drawer Header")
@Composable
fun XiaomiDrawerHeaderPreview() {
    XiaomiPreviewTheme {
        XiaomiDrawerHeader(
            title = "John Doe",
            subtitle = "john.doe@xiaomi.com",
            onCloseClick = { },
            modifier = Modifier.width(280.dp)
        )
    }
}

@Preview(name = "Xiaomi Simple Drawer")
@Composable
fun XiaomiSimpleDrawerPreview() {
    XiaomiPreviewTheme {
        val sampleItems = listOf(
            XiaomiDrawerItem(
                id = "home",
                label = "Home",
                icon = Icons.Default.Home,
                isSelected = true
            ),
            XiaomiDrawerItem(
                id = "favorites",
                label = "Favorites",
                icon = Icons.Default.Favorite,
                badge = "5"
            ),
            XiaomiDrawerItem(
                id = "email",
                label = "Email",
                icon = Icons.Default.Email,
                badge = "99+"
            ),
            XiaomiDrawerItem(
                id = "settings",
                label = "Settings",
                icon = Icons.Default.Settings
            )
        )
        
        XiaomiSimpleDrawerContent(
            items = sampleItems,
            modifier = Modifier.width(280.dp)
        )
    }
}

@Preview(name = "Xiaomi Navigation Drawer - Dark")
@Composable
fun XiaomiNavigationDrawerDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        val sampleSections = listOf(
            XiaomiDrawerSection(
                title = "Navigation",
                items = listOf(
                    XiaomiDrawerItem(
                        id = "home",
                        label = "Home",
                        icon = Icons.Default.Home,
                        isSelected = true
                    ),
                    XiaomiDrawerItem(
                        id = "favorites",
                        label = "Favorites",
                        icon = Icons.Default.Favorite
                    )
                )
            )
        )
        
        XiaomiDrawerContent(
            title = "Dark Theme",
            subtitle = "Navigation Drawer",
            sections = sampleSections,
            modifier = Modifier.width(280.dp)
        )
    }
}