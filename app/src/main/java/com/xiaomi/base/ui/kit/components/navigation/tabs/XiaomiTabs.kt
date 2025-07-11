package com.xiaomi.base.ui.kit.components.navigation.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import kotlinx.coroutines.launch

/**
 * Data class for tab items
 */
data class XiaomiTabItem(
    val id: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    val badge: String? = null,
    val enabled: Boolean = true,
    val content: @Composable () -> Unit = {}
)

/**
 * Xiaomi Tab Row - Primary style
 * 
 * A simplified version that doesn't use the complex indicator API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        tabs = tabs
    )
}

/**
 * Xiaomi Secondary Tab Row
 * 
 * A simplified version that doesn't use the complex indicator API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiSecondaryTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        tabs = tabs
    )
}

/**
 * Xiaomi Scrollable Tab Row
 * 
 * A simplified version that doesn't use the complex indicator API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    edgePadding: androidx.compose.ui.unit.Dp = TabRowDefaults.ScrollableTabRowEdgeStartPadding,
    tabs: @Composable () -> Unit
) {
    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        edgePadding = edgePadding,
        tabs = tabs
    )
}

/**
 * Xiaomi Tab
 * 
 * A tab component with optional text and icon.
 * 
 * @param selected Whether this tab is currently selected
 * @param onClick Callback when this tab is clicked
 * @param modifier Modifier to be applied to this tab
 * @param enabled Whether this tab is enabled
 * @param text The text content for this tab
 * @param icon The icon for this tab
 * @param selectedContentColor The color for the content of this tab when selected
 * @param unselectedContentColor The color for the content of this tab when not selected
 * @param interactionSource MutableInteractionSource for handling interactions
 */
@Composable
fun XiaomiTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    selectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    unselectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = text,
        icon = icon,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Tab with Icon and Text
 * 
 * A tab with both icon and text content.
 * 
 * @param selected Whether the tab is selected
 * @param onClick Callback when the tab is clicked
 * @param text Text content for the tab
 * @param icon Icon content for the tab
 * @param modifier Modifier to be applied to the tab
 * @param enabled Whether the tab is enabled
 * @param selectedContentColor Color for the content when selected
 * @param unselectedContentColor Color for the content when not selected
 * @param interactionSource Interaction source for the tab
 */
@Composable
fun XiaomiLeadingIconTab(
    selected: Boolean,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    unselectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = text,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource
    )
}

/**
 * Xiaomi Simple Tabs
 * 
 * A simplified tab implementation with predefined items.
 * 
 * @param items List of tab items
 * @param selectedTabIndex Currently selected tab index
 * @param onTabSelected Callback when a tab is selected
 * @param modifier Modifier to be applied to the tab row
 * @param scrollable Whether the tabs should be scrollable
 */
@Composable
fun XiaomiSimpleTabs(
    items: List<XiaomiTabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            XiaomiTab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                enabled = item.enabled,
                icon = if (item.icon != null) {
                    {
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
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                } else null
            )
        }
    }
}

/**
 * Xiaomi Tabs with Pager
 * 
 * A tab implementation with horizontal pager for content.
 * 
 * @param items List of tab items with content
 * @param modifier Modifier to be applied to the component
 * @param scrollable Whether the tabs should be scrollable
 * @param pagerState State for the horizontal pager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun XiaomiTabsWithPager(
    items: List<XiaomiTabItem>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { items.size }
    val scope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage
    
    Column(modifier = modifier) {
        XiaomiTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                XiaomiTab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    enabled = item.enabled,
                    text = { Text(item.title) },
                    icon = if (item.icon != null) {
                        { Icon(imageVector = item.icon, contentDescription = null) }
                    } else null
                )
            }
        }
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            items[page].content()
        }
    }
}

/**
 * Xiaomi Icon Tabs
 * 
 * Tabs with only icons, no text.
 * 
 * @param items List of tab items with icons
 * @param selectedTabIndex Currently selected tab index
 * @param onTabSelected Callback when a tab is selected
 * @param modifier Modifier to be applied to the tab row
 */
@Composable
fun XiaomiIconTabs(
    items: List<XiaomiTabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    XiaomiTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            XiaomiTab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                enabled = item.enabled,
                icon = if (item.icon != null) {
                    {
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
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                } else null
            )
        }
    }
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Tabs - Light")
@Composable
fun XiaomiTabsPreview() {
    XiaomiPreviewTheme {
        var selectedTab by remember { mutableIntStateOf(0) }
        
        val tabItems = listOf(
            XiaomiTabItem(
                id = "home",
                title = "Home",
                icon = Icons.Default.Home
            ),
            XiaomiTabItem(
                id = "favorites",
                title = "Favorites",
                icon = Icons.Default.Favorite,
                badge = "3"
            ),
            XiaomiTabItem(
                id = "profile",
                title = "Profile",
                icon = Icons.Default.Person
            )
        )
        
        Column {
            Text(
                "Tab Variants",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            XiaomiSimpleTabs(
                items = tabItems,
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Content for ${tabItems[selectedTab].title}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Scrollable Tabs")
@Composable
fun XiaomiScrollableTabsPreview() {
    XiaomiPreviewTheme {
        var selectedTab by remember { mutableIntStateOf(0) }
        
        val tabItems = listOf(
            XiaomiTabItem(id = "tab1", title = "Home", icon = Icons.Default.Home),
            XiaomiTabItem(id = "tab2", title = "Favorites", icon = Icons.Default.Favorite, badge = "5"),
            XiaomiTabItem(id = "tab3", title = "Email", icon = Icons.Default.Email, badge = "12"),
            XiaomiTabItem(id = "tab4", title = "Profile", icon = Icons.Default.Person),
            XiaomiTabItem(id = "tab5", title = "Settings", icon = Icons.Default.Settings),
            XiaomiTabItem(id = "tab6", title = "More Options"),
            XiaomiTabItem(id = "tab7", title = "Additional"),
            XiaomiTabItem(id = "tab8", title = "Extra Tab")
        )
        
        Column {
            XiaomiScrollableTabRow(
                selectedTabIndex = selectedTab
            ) {
                tabItems.forEachIndexed { index, item ->
                    XiaomiTab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        enabled = item.enabled,
                        text = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = if (item.icon != null) {
                            {
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
                                            contentDescription = item.title,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        } else null
                    )
                }
            }
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Scrollable Tabs\nSelected: ${tabItems[selectedTab].title}",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "Xiaomi Tabs with Pager")
@Composable
fun XiaomiTabsWithPagerPreview() {
    XiaomiPreviewTheme {
        val tabItems = listOf(
            XiaomiTabItem(
                id = "home",
                title = "Home",
                icon = Icons.Default.Home,
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Home Content",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            ),
            XiaomiTabItem(
                id = "favorites",
                title = "Favorites",
                icon = Icons.Default.Favorite,
                badge = "3",
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Favorites Content",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            ),
            XiaomiTabItem(
                id = "profile",
                title = "Profile",
                icon = Icons.Default.Person,
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Profile Content",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            )
        )
        
        XiaomiTabsWithPager(
            items = tabItems
        )
    }
}

@Preview(name = "Xiaomi Tabs - Dark")
@Composable
fun XiaomiTabsDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        var selectedTab by remember { mutableIntStateOf(0) }
        
        val tabItems = listOf(
            XiaomiTabItem(
                id = "home",
                title = "Home",
                icon = Icons.Default.Home
            ),
            XiaomiTabItem(
                id = "favorites",
                title = "Favorites",
                icon = Icons.Default.Favorite,
                badge = "2"
            ),
            XiaomiTabItem(
                id = "settings",
                title = "Settings",
                icon = Icons.Default.Settings
            )
        )
        
        Column {
            XiaomiSimpleTabs(
                items = tabItems,
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Dark Theme Tabs",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}