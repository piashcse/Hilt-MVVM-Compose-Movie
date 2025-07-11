package com.xiaomi.base.ui.kit.components.navigation.breadcrumbs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Data class representing a breadcrumb item
 */
data class XiaomiBreadcrumbItem(
    val id: String,
    val title: String,
    val icon: ImageVector? = null,
    val enabled: Boolean = true,
    val onClick: (() -> Unit)? = null
)

/**
 * Xiaomi Breadcrumbs
 * 
 * A navigation breadcrumb component following Material Design 3 principles.
 * 
 * @param items List of breadcrumb items
 * @param modifier Modifier to be applied to the breadcrumbs
 * @param separator Custom separator between breadcrumb items
 * @param maxItems Maximum number of items to show before collapsing
 * @param showHomeIcon Whether to show home icon for the first item
 * @param contentColor Color for the breadcrumb content
 * @param separatorColor Color for the separator
 */
@Composable
fun XiaomiBreadcrumbs(
    items: List<XiaomiBreadcrumbItem>,
    modifier: Modifier = Modifier,
    separator: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Breadcrumb separator",
            modifier = Modifier.size(16.dp),
            tint = LocalContentColor.current.copy(alpha = 0.6f)
        )
    },
    maxItems: Int = Int.MAX_VALUE,
    showHomeIcon: Boolean = false,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    separatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    if (items.isEmpty()) return
    
    val displayItems = if (items.size > maxItems && maxItems > 2) {
        listOf(items.first()) + 
        listOf(XiaomiBreadcrumbItem("ellipsis", "...", enabled = false)) +
        items.takeLast(maxItems - 2)
    } else {
        items
    }
    
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(displayItems) { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    XiaomiBreadcrumbItem(
                        item = item,
                        isLast = index == displayItems.lastIndex,
                        showHomeIcon = showHomeIcon && index == 0
                    )
                    
                    if (index < displayItems.lastIndex) {
                        CompositionLocalProvider(LocalContentColor provides separatorColor) {
                            separator()
                        }
                    }
                }
            }
        }
    }
}

/**
 * Individual breadcrumb item component
 */
@Composable
private fun XiaomiBreadcrumbItem(
    item: XiaomiBreadcrumbItem,
    isLast: Boolean,
    showHomeIcon: Boolean = false,
    modifier: Modifier = Modifier
) {
    val textStyle = if (isLast) {
        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
    } else {
        MaterialTheme.typography.bodyMedium
    }
    
    val contentColor = if (isLast) {
        LocalContentColor.current
    } else if (item.enabled && item.onClick != null) {
        MaterialTheme.colorScheme.primary
    } else {
        LocalContentColor.current.copy(alpha = 0.7f)
    }
    
    val itemModifier = if (item.enabled && item.onClick != null && !isLast) {
        modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable { item.onClick.invoke() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    } else {
        modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    }
    
    Row(
        modifier = itemModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (showHomeIcon && item.icon == null) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
        } else if (item.icon != null) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
        }
        
        Text(
            text = item.title,
            style = textStyle,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Xiaomi Simple Breadcrumbs
 * 
 * A simplified breadcrumb component with basic styling.
 * 
 * @param items List of breadcrumb items
 * @param onItemClick Callback when a breadcrumb item is clicked
 * @param modifier Modifier to be applied to the breadcrumbs
 */
@Composable
fun XiaomiSimpleBreadcrumbs(
    items: List<String>,
    onItemClick: (Int, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val breadcrumbItems = items.mapIndexed { index, title ->
        XiaomiBreadcrumbItem(
            id = index.toString(),
            title = title,
            onClick = if (index < items.lastIndex) {
                { onItemClick(index, title) }
            } else null
        )
    }
    
    XiaomiBreadcrumbs(
        items = breadcrumbItems,
        modifier = modifier
    )
}

/**
 * Xiaomi Compact Breadcrumbs
 * 
 * A compact breadcrumb component that shows only the last few items.
 * 
 * @param items List of breadcrumb items
 * @param modifier Modifier to be applied to the breadcrumbs
 * @param maxVisibleItems Maximum number of visible items
 */
@Composable
fun XiaomiCompactBreadcrumbs(
    items: List<XiaomiBreadcrumbItem>,
    modifier: Modifier = Modifier,
    maxVisibleItems: Int = 3
) {
    XiaomiBreadcrumbs(
        items = items,
        modifier = modifier,
        maxItems = maxVisibleItems
    )
}

/**
 * Xiaomi Icon Breadcrumbs
 * 
 * Breadcrumbs with icons for each item.
 * 
 * @param items List of breadcrumb items with icons
 * @param modifier Modifier to be applied to the breadcrumbs
 * @param showHomeIcon Whether to show home icon for the first item
 */
@Composable
fun XiaomiIconBreadcrumbs(
    items: List<XiaomiBreadcrumbItem>,
    modifier: Modifier = Modifier,
    showHomeIcon: Boolean = true
) {
    XiaomiBreadcrumbs(
        items = items,
        modifier = modifier,
        showHomeIcon = showHomeIcon
    )
}

/**
 * Xiaomi Custom Separator Breadcrumbs
 * 
 * Breadcrumbs with a custom separator.
 * 
 * @param items List of breadcrumb items
 * @param separator Custom separator composable
 * @param modifier Modifier to be applied to the breadcrumbs
 */
@Composable
fun XiaomiCustomSeparatorBreadcrumbs(
    items: List<XiaomiBreadcrumbItem>,
    separator: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiBreadcrumbs(
        items = items,
        modifier = modifier,
        separator = separator
    )
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Breadcrumbs - Light")
@Composable
fun XiaomiBreadcrumbsPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Breadcrumb Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Basic breadcrumbs
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Basic Breadcrumbs",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiBreadcrumbs(
                        items = listOf(
                            XiaomiBreadcrumbItem("1", "Home", onClick = {}),
                            XiaomiBreadcrumbItem("2", "Category", onClick = {}),
                            XiaomiBreadcrumbItem("3", "Subcategory", onClick = {}),
                            XiaomiBreadcrumbItem("4", "Current Page")
                        )
                    )
                }
                
                // With home icon
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "With Home Icon",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiBreadcrumbs(
                        items = listOf(
                            XiaomiBreadcrumbItem("1", "Home", onClick = {}),
                            XiaomiBreadcrumbItem("2", "Products", onClick = {}),
                            XiaomiBreadcrumbItem("3", "Electronics", onClick = {}),
                            XiaomiBreadcrumbItem("4", "Smartphones")
                        ),
                        showHomeIcon = true
                    )
                }
                
                // Compact breadcrumbs
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Compact (Max 3 items)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiCompactBreadcrumbs(
                        items = listOf(
                            XiaomiBreadcrumbItem("1", "Home", onClick = {}),
                            XiaomiBreadcrumbItem("2", "Level 1", onClick = {}),
                            XiaomiBreadcrumbItem("3", "Level 2", onClick = {}),
                            XiaomiBreadcrumbItem("4", "Level 3", onClick = {}),
                            XiaomiBreadcrumbItem("5", "Level 4", onClick = {}),
                            XiaomiBreadcrumbItem("6", "Current")
                        ),
                        maxVisibleItems = 3
                    )
                }
                
                // Simple breadcrumbs
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Simple Breadcrumbs",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiSimpleBreadcrumbs(
                        items = listOf("Dashboard", "Settings", "Profile", "Edit")
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Breadcrumbs - Dark")
@Composable
fun XiaomiBreadcrumbsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Dark Theme Breadcrumbs",
                    style = MaterialTheme.typography.titleMedium
                )
                
                XiaomiBreadcrumbs(
                    items = listOf(
                        XiaomiBreadcrumbItem("1", "Home", onClick = {}),
                        XiaomiBreadcrumbItem("2", "Documents", onClick = {}),
                        XiaomiBreadcrumbItem("3", "Projects", onClick = {}),
                        XiaomiBreadcrumbItem("4", "Current Folder")
                    ),
                    showHomeIcon = true
                )
                
                XiaomiCustomSeparatorBreadcrumbs(
                    items = listOf(
                        XiaomiBreadcrumbItem("1", "Root", onClick = {}),
                        XiaomiBreadcrumbItem("2", "Folder", onClick = {}),
                        XiaomiBreadcrumbItem("3", "Subfolder")
                    ),
                    separator = {
                        Text(
                            text = "/",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Breadcrumbs - Long Path")
@Composable
fun XiaomiBreadcrumbsLongPathPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Long Path Breadcrumbs",
                    style = MaterialTheme.typography.titleMedium
                )
                
                XiaomiBreadcrumbs(
                    items = listOf(
                        XiaomiBreadcrumbItem("1", "Home", onClick = {}),
                        XiaomiBreadcrumbItem("2", "Very Long Category Name", onClick = {}),
                        XiaomiBreadcrumbItem("3", "Another Long Subcategory", onClick = {}),
                        XiaomiBreadcrumbItem("4", "Even Longer Item Name", onClick = {}),
                        XiaomiBreadcrumbItem("5", "Final Very Long Current Page Name")
                    ),
                    maxItems = 4
                )
            }
        }
    }
}