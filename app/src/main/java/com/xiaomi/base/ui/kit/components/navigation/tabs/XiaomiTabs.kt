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
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import kotlinx.coroutines.launch

/**
 * Data class representing a tab item
 */
data class XiaomiTabItem(
    val id: String,
    val title: String,
    val icon: ImageVector? = null,
    val badge: String? = null,
    val enabled: Boolean = true,
    val content: @Composable () -> Unit = {}
)

/**
 * Xiaomi Primary Tab Row
 * 
 * A Material Design 3 primary tab row component with Xiaomi design tokens.
 * 
 * @param selectedTabIndex The index of the currently selected tab
 * @param modifier Modifier to be applied to the tab row
 * @param containerColor The color used for the background of this tab row
 * @param contentColor The preferred color for content inside this tab row
 * @param indicator The indicator that represents which tab is currently selected
 * @param divider The divider displayed at the bottom of the tab row
 * @param tabs The tabs inside this tab row
 */
@Composable
fun XiaomiPrimaryTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.primaryContainerColor,
    contentColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.primaryContentColor,
    indicator: @Composable (tabPositions: List<androidx.compose.material3.TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            androidx.compose.material3.TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    },
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}

/**
 * Xiaomi Secondary Tab Row
 * 
 * A Material Design 3 secondary tab row component.
 * 
 * @param selectedTabIndex The index of the currently selected tab
 * @param modifier Modifier to be applied to the tab row
 * @param containerColor The color used for the background of this tab row
 * @param contentColor The preferred color for content inside this tab row
 * @param indicator The indicator that represents which tab is currently selected
 * @param divider The divider displayed at the bottom of the tab row
 * @param tabs The tabs inside this tab row
 */
@Composable
fun XiaomiSecondaryTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.secondaryContainerColor,
    contentColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.secondaryContentColor,
    indicator: @Composable (tabPositions: List<androidx.compose.material3.TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            androidx.compose.material3.TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    },
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}

/**
 * Xiaomi Scrollable Tab Row
 * 
 * A scrollable tab row for when there are many tabs.
 * 
 * @param selectedTabIndex The index of the currently selected tab
 * @param modifier Modifier to be applied to the tab row
 * @param containerColor The color used for the background of this tab row
 * @param contentColor The preferred color for content inside this tab row
 * @param edgePadding The padding from the starting edge before the first tab
 * @param indicator The indicator that represents which tab is currently selected
 * @param divider The divider displayed at the bottom of the tab row
 * @param tabs The tabs inside this tab row
 */
@Composable
fun XiaomiScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.primaryContainerColor,
    contentColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.TabRowDefaults.primaryContentColor,
    edgePadding: androidx.compose.ui.unit.Dp = androidx.compose.material3.TabRowDefaults.ScrollableTabRowEdgeStartPadding,
    indicator: @Composable (tabPositions: List<androidx.compose.material3.TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            androidx.compose.material3.TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    },
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit
) {
    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        edgePadding = edgePadding,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}

/**
 * Xiaomi Tab
 * 
 * A single tab in a tab row.
 * 
 * @param selected Whether this tab is currently selected
 * @param onClick Callback when this tab is clicked
 * @param modifier Modifier to be applied to this tab
 * @param enabled Whether this tab is enabled
 * @param text Optional text content for this tab
 * @param icon Optional icon content for this tab
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
    text: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    selectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    unselectedContentColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
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
 * Xiaomi Leading Icon Tab
 * 
 * A tab with a leading icon.
 * 
 * @param selected Whether this tab is currently selected
 * @param onClick Callback when this tab is clicked
 * @param text The text content for this tab
 * @param icon The leading icon for this tab
 * @param modifier Modifier to be applied to this tab
 * @param enabled Whether this tab is enabled
 * @param selectedContentColor The color for the content of this tab when selected
 * @param unselectedContentColor The color for the content of this tab when not selected
 * @param interactionSource MutableInteractionSource for handling interactions
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
    LeadingIconTab(
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
    modifier: Modifier = Modifier,
    scrollable: Boolean = false
) {
    if (scrollable) {
        XiaomiScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
        ) {
            items.forEachIndexed { index, item ->
                XiaomiTab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
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
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    } else null
                )
            }
        }
    } else {
        XiaomiPrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier
        ) {
            items.forEachIndexed { index, item ->
                XiaomiTab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
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
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    } else null
                )
            }
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
    modifier: Modifier = Modifier,
    scrollable: Boolean = false,
    pagerState: PagerState = rememberPagerState(pageCount = { items.size })
) {
    val coroutineScope = rememberCoroutineScope()
    
    Column(modifier = modifier) {
        XiaomiSimpleTabs(
            items = items,
            selectedTabIndex = pagerState.currentPage,
            onTabSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
            scrollable = scrollable
        )
        
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
    XiaomiPrimaryTabRow(
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

// Helper function for tab indicator offset
@Composable
fun Modifier.tabIndicatorOffset(currentTabPosition: androidx.compose.material3.TabPosition): Modifier {
    return TabRowDefaults.tabIndicatorOffset(currentTabPosition)
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
            XiaomiSimpleTabs(
                items = tabItems,
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                scrollable = true
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
                        text = "Scrollable Tabs\nSelected: ${tabItems[selectedTab].title}",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
                            verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium)
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
                            verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium)
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
                            verticalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium)
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