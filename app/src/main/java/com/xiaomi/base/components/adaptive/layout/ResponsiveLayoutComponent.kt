package com.xiaomi.base.components.adaptive.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Screen Size Categories
 */
enum class ScreenSize {
    COMPACT,    // < 600dp
    MEDIUM,     // 600dp - 840dp
    EXPANDED    // > 840dp
}

/**
 * Screen Orientation
 */
enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE
}

/**
 * Device Type Classification
 */
enum class DeviceType {
    PHONE,
    TABLET,
    DESKTOP,
    FOLDABLE
}

/**
 * Layout Configuration
 */
data class LayoutConfiguration(
    val screenSize: ScreenSize,
    val widthDp: Dp,
    val heightDp: Dp,
    val isLandscape: Boolean,
    val density: Float
)

/**
 * Navigation Item Data Class
 */
data class NavigationItem(
    val id: String,
    val label: String,
    val icon: @Composable () -> Unit,
    val selectedIcon: (@Composable () -> Unit)? = null
)

/**
 * Remember Layout Configuration
 * Composable function to get current layout configuration
 */
@Composable
fun rememberLayoutConfiguration(): LayoutConfiguration {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    
    val widthDp = configuration.screenWidthDp.dp
    val heightDp = configuration.screenHeightDp.dp
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp
    
    val screenSize = when (configuration.screenWidthDp) {
        in 0..599 -> ScreenSize.COMPACT
        in 600..839 -> ScreenSize.MEDIUM
        else -> ScreenSize.EXPANDED
    }
    
    return LayoutConfiguration(
        screenSize = screenSize,
        widthDp = widthDp,
        heightDp = heightDp,
        isLandscape = isLandscape,
        density = density
    )
}

/**
 * Responsive Grid Component
 * Creates adaptive grid based on screen size
 */
@Composable
fun ResponsiveGridComponent(
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    content: LazyGridScope.() -> Unit
) {
    val configuration = rememberLayoutConfiguration()
    
    val columns = when (configuration.screenSize) {
        ScreenSize.COMPACT -> 1
        ScreenSize.MEDIUM -> 2
        ScreenSize.EXPANDED -> 3
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        content = content
    )
}

/**
 * Adaptive Navigation Component
 * Switches between bottom navigation and navigation rail based on screen size
 */
@Composable
fun AdaptiveNavigationComponent(
    modifier: Modifier = Modifier,
    useNavigationRail: Boolean? = null,
    navigationItems: List<NavigationItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val configuration = rememberLayoutConfiguration()
    
    val shouldUseRail = useNavigationRail ?: (
        configuration.screenSize != ScreenSize.COMPACT || 
        configuration.isLandscape
    )
    
    if (shouldUseRail) {
        Row(modifier = modifier) {
            // Navigation Rail
            NavigationRailComponent(
                items = navigationItems,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
            
            // Content
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    } else {
        Column(modifier = modifier) {
            // Content
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            
            // Bottom Navigation
            BottomNavigationComponent(
                items = navigationItems,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        }
    }
}

/**
 * Adaptive Pane Component
 * Creates single or dual pane layout based on screen size
 */
@Composable
fun AdaptivePaneComponent(
    modifier: Modifier = Modifier,
    primaryContent: @Composable () -> Unit,
    secondaryContent: @Composable () -> Unit,
    showSecondaryPane: Boolean = true,
    paneRatio: Float = 0.4f
) {
    val configuration = rememberLayoutConfiguration()
    
    val useDualPane = configuration.screenSize != ScreenSize.COMPACT && showSecondaryPane
    
    if (useDualPane) {
        Row(modifier = modifier) {
            // Primary pane
            Box(modifier = Modifier.weight(1f - paneRatio)) {
                primaryContent()
            }
            
            // Divider
            Spacer(modifier = Modifier.width(1.dp))
            
            // Secondary pane
            Box(modifier = Modifier.weight(paneRatio)) {
                secondaryContent()
            }
        }
    } else {
        Box(modifier = modifier) {
            primaryContent()
        }
    }
}

/**
 * Breakpoint Aware Component
 * Renders different content based on custom breakpoints
 */
@Composable
fun BreakpointAwareComponent(
    modifier: Modifier = Modifier,
    breakpoints: Map<Dp, @Composable (LayoutConfiguration) -> Unit>
) {
    val configuration = rememberLayoutConfiguration()
    
    val sortedBreakpoints = breakpoints.toList().sortedBy { it.first }
    val activeBreakpoint = sortedBreakpoints.findLast { 
        configuration.widthDp >= it.first 
    }?.second ?: sortedBreakpoints.firstOrNull()?.second
    
    Box(modifier = modifier) {
        activeBreakpoint?.invoke(configuration)
    }
}

/**
 * Orientation Aware Component
 * Renders different content based on orientation
 */
@Composable
fun OrientationAwareComponent(
    modifier: Modifier = Modifier,
    portraitContent: @Composable (LayoutConfiguration) -> Unit,
    landscapeContent: (@Composable (LayoutConfiguration) -> Unit)? = null
) {
    val configuration = rememberLayoutConfiguration()
    
    Box(modifier = modifier) {
        if (configuration.isLandscape && landscapeContent != null) {
            landscapeContent(configuration)
        } else {
            portraitContent(configuration)
        }
    }
}

/**
 * Navigation Rail Component
 */
@Composable
private fun NavigationRailComponent(
    items: List<NavigationItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    // Implementation would use Material3 NavigationRail
    Column(
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
    ) {
        items.forEach { item ->
            // Navigation rail item implementation
        }
    }
}

/**
 * Bottom Navigation Component
 */
@Composable
private fun BottomNavigationComponent(
    items: List<NavigationItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    // Implementation would use Material3 NavigationBar
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        items.forEach { item ->
            // Bottom navigation item implementation
        }
    }
}

/**
 * Responsive Metrics
 * Calculate responsive metrics for the current layout
 */
@Composable
fun rememberResponsiveMetrics(): ResponsiveMetrics {
    val configuration = rememberLayoutConfiguration()
    
    return ResponsiveMetrics(
        isCompact = configuration.screenSize == ScreenSize.COMPACT,
        isMedium = configuration.screenSize == ScreenSize.MEDIUM,
        isExpanded = configuration.screenSize == ScreenSize.EXPANDED,
        isLandscape = configuration.isLandscape,
        isPortrait = !configuration.isLandscape,
        screenWidth = configuration.widthDp,
        screenHeight = configuration.heightDp,
        availableColumns = when (configuration.screenSize) {
            ScreenSize.COMPACT -> 1
            ScreenSize.MEDIUM -> 2
            ScreenSize.EXPANDED -> 3
        }
    )
}

/**
 * Responsive Metrics Data Class
 */
data class ResponsiveMetrics(
    val isCompact: Boolean,
    val isMedium: Boolean,
    val isExpanded: Boolean,
    val isLandscape: Boolean,
    val isPortrait: Boolean,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val availableColumns: Int
)