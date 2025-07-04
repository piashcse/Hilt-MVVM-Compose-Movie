package com.xiaomi.base.preview.base

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview

/**
 * Toolbar for preview screens with theme toggle and other controls
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewToolbar(
    title: String,
    subtitle: String? = null,
    onBackClick: () -> Unit = {},
    isDarkTheme: Boolean = false,
    onThemeToggle: (() -> Unit)? = null,
    showDeviceFrame: Boolean = false,
    onDeviceFrameToggle: () -> Unit = {},
    showSettings: Boolean = false,
    onSettingsClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            // Theme toggle
            onThemeToggle?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                    )
                }
            }
            
            // Device frame toggle
            IconButton(onClick = onDeviceFrameToggle) {
                Icon(
                    imageVector = if (showDeviceFrame) Icons.Default.PhoneAndroid else Icons.Default.CropFree,
                    contentDescription = if (showDeviceFrame) "Hide Device Frame" else "Show Device Frame",
                    tint = if (showDeviceFrame) MaterialTheme.colorScheme.primary else LocalContentColor.current
                )
            }
            
            // Settings
            if (showSettings) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
            
            // Custom actions
            actions()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview
@Composable
fun PreviewToolbarPreview() {
    PreviewToolbar(
        title = "Sample Title",
        subtitle = "Sample Subtitle",
        onBackClick = {},
        isDarkTheme = false,
        onThemeToggle = {},
        showDeviceFrame = false,
        onDeviceFrameToggle = {},
        showSettings = true,
        onSettingsClick = {},
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite")
            }
        }
    )
}

/**
 * Compact toolbar for smaller screens
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactPreviewToolbar(
    title: String,
    onBackClick: () -> Unit = {},
    isDarkTheme: Boolean = false,
    onThemeToggle: (() -> Unit)? = null,
    onMoreClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            onThemeToggle?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                    )
                }
            }
            
            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        }
    )
}

@Preview
@Composable
fun CompactPreviewToolbarPreview() {
    CompactPreviewToolbar(
        title = "Compact Title",
        onBackClick = {},
        isDarkTheme = false,
        onThemeToggle = {},
        onMoreClick = {}
    )
}

/**
 * Floating action toolbar for quick actions
 */
@Composable
fun FloatingPreviewToolbar(
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {},
    showDeviceFrame: Boolean = false,
    onDeviceFrameToggle: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onThemeToggle,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = if (isDarkTheme) "Light Theme" else "Dark Theme",
                    modifier = Modifier.size(20.dp)
                )
            }
            
            IconButton(
                onClick = onDeviceFrameToggle,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (showDeviceFrame) Icons.Default.PhoneAndroid else Icons.Default.CropFree,
                    contentDescription = if (showDeviceFrame) "Hide Frame" else "Show Frame",
                    modifier = Modifier.size(20.dp),
                    tint = if (showDeviceFrame) MaterialTheme.colorScheme.primary else LocalContentColor.current
                )
            }
            
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun FloatingPreviewToolbarPreview() {
    FloatingPreviewToolbar(
        isDarkTheme = false,
        onThemeToggle = {},
        showDeviceFrame = true,
        onDeviceFrameToggle = {},
        onSettingsClick = {}
    )
}


/**
 * Preview toolbar with breadcrumb navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreadcrumbPreviewToolbar(
    breadcrumbs: List<String>,
    onBreadcrumbClick: (Int) -> Unit = {},
    isDarkTheme: Boolean = false,
    onThemeToggle: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                breadcrumbs.forEachIndexed { index, breadcrumb ->
                    if (index > 0) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    TextButton(
                        onClick = { onBreadcrumbClick(index) },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = breadcrumb,
                            style = if (index == breadcrumbs.lastIndex) {
                                MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                            } else {
                                MaterialTheme.typography.bodyMedium
                            },
                            color = if (index == breadcrumbs.lastIndex) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            }
        },
        actions = {
            onThemeToggle?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                    )
                }
            }
            
            actions()
        }
    )
}

@Preview
@Composable
fun BreadcrumbPreviewToolbarPreview() {
    BreadcrumbPreviewToolbar(
        breadcrumbs = listOf("Home", "Category", "Product"),
        onBreadcrumbClick = {},
        isDarkTheme = true,
        onThemeToggle = {},
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            }
        }
    )
}
