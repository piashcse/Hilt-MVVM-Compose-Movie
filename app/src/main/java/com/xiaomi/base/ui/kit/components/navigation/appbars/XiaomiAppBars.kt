package com.xiaomi.base.ui.kit.components.navigation.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme

/**
 * Xiaomi Base UI Kit Top App Bar
 * 
 * A Material Design 3 top app bar component with Xiaomi design tokens.
 * Provides navigation and actions for the current screen.
 * 
 * @param title The title to be displayed in the app bar
 * @param modifier Modifier to be applied to the app bar
 * @param navigationIcon Optional navigation icon at the start of the app bar
 * @param actions Actions to be displayed at the end of the app bar
 * @param windowInsets Window insets to be applied to the app bar
 * @param colors TopAppBarColors that will be used to resolve the colors used for this app bar
 * @param scrollBehavior TopAppBarScrollBehavior to be applied to the app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

/**
 * Xiaomi Center Aligned Top App Bar
 * 
 * A top app bar with center-aligned title.
 * 
 * @param title The title to be displayed in the app bar
 * @param modifier Modifier to be applied to the app bar
 * @param navigationIcon Optional navigation icon at the start of the app bar
 * @param actions Actions to be displayed at the end of the app bar
 * @param windowInsets Window insets to be applied to the app bar
 * @param colors TopAppBarColors that will be used to resolve the colors used for this app bar
 * @param scrollBehavior TopAppBarScrollBehavior to be applied to the app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiCenterAlignedTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

/**
 * Xiaomi Medium Top App Bar
 * 
 * A medium-sized top app bar with larger title area.
 * 
 * @param title The title to be displayed in the app bar
 * @param modifier Modifier to be applied to the app bar
 * @param navigationIcon Optional navigation icon at the start of the app bar
 * @param actions Actions to be displayed at the end of the app bar
 * @param windowInsets Window insets to be applied to the app bar
 * @param colors TopAppBarColors that will be used to resolve the colors used for this app bar
 * @param scrollBehavior TopAppBarScrollBehavior to be applied to the app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiMediumTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    MediumTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

/**
 * Xiaomi Large Top App Bar
 * 
 * A large-sized top app bar with prominent title area.
 * 
 * @param title The title to be displayed in the app bar
 * @param modifier Modifier to be applied to the app bar
 * @param navigationIcon Optional navigation icon at the start of the app bar
 * @param actions Actions to be displayed at the end of the app bar
 * @param windowInsets Window insets to be applied to the app bar
 * @param colors TopAppBarColors that will be used to resolve the colors used for this app bar
 * @param scrollBehavior TopAppBarScrollBehavior to be applied to the app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiLargeTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    LargeTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

/**
 * Xiaomi Simple Top App Bar
 * 
 * A simplified top app bar with just title and optional back button.
 * 
 * @param title The title text to be displayed
 * @param onNavigationClick Callback for navigation icon click
 * @param modifier Modifier to be applied to the app bar
 * @param showNavigationIcon Whether to show the navigation icon
 * @param navigationIcon The navigation icon to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiSimpleTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showNavigationIcon: Boolean = onNavigationClick != null,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
) {
    XiaomiTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = if (showNavigationIcon && onNavigationClick != null) {
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = "Navigate back"
                    )
                }
            }
        } else {
            {}
        }
    )
}

/**
 * Xiaomi Search Top App Bar
 * 
 * A top app bar optimized for search functionality.
 * 
 * @param title The title text to be displayed
 * @param onNavigationClick Callback for navigation icon click
 * @param onSearchClick Callback for search icon click
 * @param modifier Modifier to be applied to the app bar
 * @param actions Additional actions to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiSearchTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    XiaomiTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = if (onNavigationClick != null) {
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            actions()
        }
    )
}

/**
 * Xiaomi Menu Top App Bar
 * 
 * A top app bar with menu icon for navigation drawer.
 * 
 * @param title The title text to be displayed
 * @param onMenuClick Callback for menu icon click
 * @param modifier Modifier to be applied to the app bar
 * @param actions Additional actions to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiMenuTopAppBar(
    title: String,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    XiaomiTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open menu"
                )
            }
        },
        actions = actions
    )
}

/**
 * Xiaomi Action Top App Bar
 * 
 * A top app bar with common action patterns.
 * 
 * @param title The title text to be displayed
 * @param onNavigationClick Callback for navigation icon click
 * @param modifier Modifier to be applied to the app bar
 * @param showMoreActions Whether to show more actions menu
 * @param onMoreActionsClick Callback for more actions click
 * @param actions Additional actions to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiActionTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showMoreActions: Boolean = false,
    onMoreActionsClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    XiaomiTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = if (onNavigationClick != null) {
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        } else {
            {}
        },
        actions = {
            actions()
            if (showMoreActions && onMoreActionsClick != null) {
                IconButton(onClick = onMoreActionsClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More actions"
                    )
                }
            }
        }
    )
}

/**
 * Xiaomi Modal Top App Bar
 * 
 * A top app bar for modal screens with close button.
 * 
 * @param title The title text to be displayed
 * @param onCloseClick Callback for close icon click
 * @param modifier Modifier to be applied to the app bar
 * @param actions Additional actions to be displayed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiModalTopAppBar(
    title: String,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    XiaomiTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        actions = actions
    )
}

// Preview composables for design system documentation
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi App Bars - Light")
@Composable
fun XiaomiAppBarsPreview() {
    XiaomiPreviewTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "App Bar Variants",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            // Simple Top App Bar
            XiaomiSimpleTopAppBar(
                title = "Simple App Bar",
                onNavigationClick = { }
            )
            
            // Center Aligned Top App Bar
            XiaomiCenterAlignedTopAppBar(
                title = {
                    Text(
                        "Center Aligned",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
            
            // Search Top App Bar
            XiaomiSearchTopAppBar(
                title = "Search App Bar",
                onNavigationClick = { },
                onSearchClick = { }
            )
            
            // Menu Top App Bar
            XiaomiMenuTopAppBar(
                title = "Menu App Bar",
                onMenuClick = { },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
                }
            )
            
            // Action Top App Bar
            XiaomiActionTopAppBar(
                title = "Action App Bar",
                onNavigationClick = { },
                showMoreActions = true,
                onMoreActionsClick = { },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
            
            // Modal Top App Bar
            XiaomiModalTopAppBar(
                title = "Modal App Bar",
                onCloseClick = { }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi App Bars - Large")
@Composable
fun XiaomiLargeAppBarsPreview() {
    XiaomiPreviewTheme {
        Column {
            // Medium Top App Bar
            XiaomiMediumTopAppBar(
                title = {
                    Text(
                        "Medium App Bar",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
                }
            )
            
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(8.dp))
            
            // Large Top App Bar
            XiaomiLargeTopAppBar(
                title = {
                    Text(
                        "Large App Bar",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi App Bars - Dark")
@Composable
fun XiaomiAppBarsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            XiaomiSimpleTopAppBar(
                title = "Dark Theme",
                onNavigationClick = { }
            )
            
            XiaomiSearchTopAppBar(
                title = "Search in Dark",
                onSearchClick = { }
            )
        }
    }
}