package com.xiaomi.base.components.platform.compatibility

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.tooling.preview.Preview
// Window size class functionality
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import com.xiaomi.base.ui.theme.Orange

/**
 * Platform Type
 */
enum class PlatformType {
    ANDROID_PHONE,
    ANDROID_TABLET,
    ANDROID_FOLDABLE,
    ANDROID_TV,
    ANDROID_WEAR,
    ANDROID_AUTO,
    DESKTOP,
    WEB,
    IOS_PHONE,
    IOS_TABLET,
    UNKNOWN
}

/**
 * Screen Size Category
 */
enum class ScreenSizeCategory {
    COMPACT,
    MEDIUM,
    EXPANDED,
    EXTRA_LARGE
}

/**
 * Orientation Type
 */
enum class OrientationType {
    PORTRAIT,
    LANDSCAPE,
    SQUARE
}

/**
 * Device Capability
 */
enum class DeviceCapability {
    TOUCH_SCREEN,
    KEYBOARD,
    MOUSE,
    STYLUS,
    CAMERA,
    GPS,
    NFC,
    BLUETOOTH,
    WIFI,
    CELLULAR,
    BIOMETRIC,
    HAPTIC_FEEDBACK,
    VOICE_INPUT,
    GESTURE_NAVIGATION
}

/**
 * Responsive Breakpoint
 */
data class ResponsiveBreakpoint(
    val name: String,
    val minWidth: Dp,
    val maxWidth: Dp? = null,
    val columns: Int,
    val padding: Dp,
    val spacing: Dp
)

/**
 * Device Information
 */
data class DeviceInfo(
    val platformType: PlatformType,
    val screenSizeCategory: ScreenSizeCategory,
    val orientation: OrientationType,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val density: Float,
    val capabilities: Set<DeviceCapability>,
    val apiLevel: Int,
    val manufacturer: String = "",
    val model: String = "",
    val isTablet: Boolean = false,
    val isFoldable: Boolean = false,
    val hasNotch: Boolean = false
)

/**
 * Compatibility Issue
 */
data class CompatibilityIssue(
    val id: String,
    val title: String,
    val description: String,
    val severity: CompatibilitySeverity,
    val affectedPlatforms: Set<PlatformType>,
    val recommendation: String,
    val workaround: String? = null,
    val apiLevelRequired: Int? = null
)

/**
 * Compatibility Severity
 */
enum class CompatibilitySeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    INFO
}

/**
 * Adaptive Layout Configuration
 */
data class AdaptiveLayoutConfig(
    val enableResponsiveDesign: Boolean = true,
    val enableDynamicTheming: Boolean = true,
    val enableAccessibilityAdaptation: Boolean = true,
    val enablePerformanceOptimization: Boolean = true,
    val customBreakpoints: List<ResponsiveBreakpoint> = emptyList(),
    val fallbackStrategy: FallbackStrategy = FallbackStrategy.GRACEFUL_DEGRADATION
)

/**
 * Fallback Strategy
 */
enum class FallbackStrategy {
    GRACEFUL_DEGRADATION,
    PROGRESSIVE_ENHANCEMENT,
    FEATURE_DETECTION,
    POLYFILL
}

/**
 * Cross-Platform Compatibility Component
 * Comprehensive cross-platform compatibility and responsive design
 * 
 * @param config Adaptive layout configuration
 * @param onCompatibilityIssue Callback when compatibility issue is detected
 * @param content Content to make compatible
 */
@Composable
fun CrossPlatformCompatibilityComponent(
    config: AdaptiveLayoutConfig = AdaptiveLayoutConfig(),
    onCompatibilityIssue: (CompatibilityIssue) -> Unit = {},
    content: @Composable (DeviceInfo) -> Unit
) {
    val deviceInfo = rememberDeviceInfo()
    val compatibilityIssues = remember(deviceInfo) {
        detectCompatibilityIssues(deviceInfo)
    }
    
    // Report compatibility issues
    LaunchedEffect(compatibilityIssues) {
        compatibilityIssues.forEach { issue ->
            onCompatibilityIssue(issue)
        }
    }
    
    // Apply adaptive layout
    AdaptiveLayoutProvider(
        deviceInfo = deviceInfo,
        config = config
    ) {
        content(deviceInfo)
    }
}

@Preview
@Composable
fun CrossPlatformCompatibilityComponentPreview() {
    CrossPlatformCompatibilityComponent(
        config = AdaptiveLayoutConfig(
            enableResponsiveDesign = true,
            enableDynamicTheming = true,
            enableAccessibilityAdaptation = true,
            enablePerformanceOptimization = true,
            customBreakpoints = emptyList(),
            fallbackStrategy = FallbackStrategy.GRACEFUL_DEGRADATION
        ),
        onCompatibilityIssue = {}
    ) @Composable { deviceInfo -> Text(text = "Content for ${deviceInfo.platformType}") }
}
/**
 * Responsive Grid Component
 * Adaptive grid that responds to screen size
 * 
 * @param items Items to display
 * @param itemContent Content for each item
 */
@Composable
fun <T> ResponsiveGridComponent(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    val deviceInfo = LocalDeviceInfo.current
    val breakpoint = getResponsiveBreakpoint(deviceInfo.screenWidth)
    
    when (deviceInfo.screenSizeCategory) {
        ScreenSizeCategory.COMPACT -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(breakpoint.spacing),
                contentPadding = PaddingValues(breakpoint.padding)
            ) {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
        
        ScreenSizeCategory.MEDIUM -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(breakpoint.columns),
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(breakpoint.spacing),
                horizontalArrangement = Arrangement.spacedBy(breakpoint.spacing),
                contentPadding = PaddingValues(breakpoint.padding)
            ) {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
        
        ScreenSizeCategory.EXPANDED,
        ScreenSizeCategory.EXTRA_LARGE -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(breakpoint.spacing),
                horizontalArrangement = Arrangement.spacedBy(breakpoint.spacing),
                contentPadding = PaddingValues(breakpoint.padding)
            ) {
                items(items) { item ->
                    itemContent(item)
                }
            }
        }
    }
}

@Preview
@Composable
fun ResponsiveGridComponentPreview() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    ResponsiveGridComponent(
        items = items,
        modifier = Modifier.fillMaxSize()
    ) { item ->
        Text(text = item, modifier = Modifier.padding(8.dp))
    }
}


/**
 * Adaptive Navigation Component
 * Navigation that adapts to different screen sizes
 * 
 * @param navigationItems Navigation items
 * @param selectedItem Currently selected item
 * @param onItemSelected Callback when item is selected
 * @param content Main content
 */
@Composable
fun AdaptiveNavigationComponent(
    navigationItems: List<NavigationItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val deviceInfo = LocalDeviceInfo.current
    
    when (deviceInfo.screenSizeCategory) {
        ScreenSizeCategory.COMPACT -> {
            // Bottom navigation for compact screens
            Column {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    content()
                }
                
                NavigationBar {
                    navigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = selectedItem == item.id,
                            onClick = { onItemSelected(item.id) }
                        )
                    }
                }
            }
        }
        
        ScreenSizeCategory.MEDIUM -> {
            // Navigation rail for medium screens
            Row {
                NavigationRail {
                    navigationItems.forEach { item ->
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = selectedItem == item.id,
                            onClick = { onItemSelected(item.id) }
                        )
                    }
                }
                
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    content()
                }
            }
        }
        
        ScreenSizeCategory.EXPANDED,
        ScreenSizeCategory.EXTRA_LARGE -> {
            // Navigation drawer for large screens
            Row {
                NavigationDrawer(
                    navigationItems = navigationItems,
                    selectedItem = selectedItem,
                    onItemSelected = onItemSelected
                )
                
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun AdaptiveNavigationComponentPreview() {
    val navigationItems = listOf(
        NavigationItem(id = "home", label = "Home", icon = Icons.Filled.Home),
        NavigationItem(id = "settings", label = "Settings", icon = Icons.Filled.Settings)
    )
    var selectedItem by remember { mutableStateOf("home") }

    AdaptiveNavigationComponent(
        navigationItems = navigationItems,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Content for $selectedItem")
        }
    }
}

/**
 * Platform-Specific Component
 * Component that renders differently based on platform
 * 
 * @param androidContent Content for Android
 * @param iosContent Content for iOS
 * @param desktopContent Content for Desktop
 * @param webContent Content for Web
 * @param fallbackContent Fallback content
 */
@Composable
fun PlatformSpecificComponent(
    androidContent: @Composable () -> Unit = {},
    iosContent: @Composable () -> Unit = {},
    desktopContent: @Composable () -> Unit = {},
    webContent: @Composable () -> Unit = {},
    fallbackContent: @Composable () -> Unit = {}
) {
    val deviceInfo = LocalDeviceInfo.current
    
    when (deviceInfo.platformType) {
        PlatformType.ANDROID_PHONE,
        PlatformType.ANDROID_TABLET,
        PlatformType.ANDROID_FOLDABLE,
        PlatformType.ANDROID_TV,
        PlatformType.ANDROID_WEAR,
        PlatformType.ANDROID_AUTO -> androidContent()
        
        PlatformType.IOS_PHONE,
        PlatformType.IOS_TABLET -> iosContent()
        
        PlatformType.DESKTOP -> desktopContent()
        PlatformType.WEB -> webContent()
        PlatformType.UNKNOWN -> fallbackContent()
    }
}

@Preview
@Composable
fun PlatformSpecificComponentPreview() {
    PlatformSpecificComponent(
        androidContent = { Text("Android Content") },
        iosContent = { Text("iOS Content") },
        desktopContent = { Text("Desktop Content") },
        webContent = { Text("Web Content") },
        fallbackContent = { Text("Fallback Content") }
    )
}

/**
 * Responsive Text Component
 * Text that scales based on screen size
 * 
 * @param text Text content
 * @param baseSize Base text size
 * @param scaleFactor Scale factor for different screen sizes
 */
@Composable
fun ResponsiveText(
    text: String,
    modifier: Modifier = Modifier,
    baseSize: androidx.compose.ui.unit.TextUnit = 16.sp,
    scaleFactor: Float = 1.0f,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current
) {
    val deviceInfo = LocalDeviceInfo.current
    
    val adjustedSize = when (deviceInfo.screenSizeCategory) {
        ScreenSizeCategory.COMPACT -> baseSize * 0.9f * scaleFactor
        ScreenSizeCategory.MEDIUM -> baseSize * scaleFactor
        ScreenSizeCategory.EXPANDED -> baseSize * 1.1f * scaleFactor
        ScreenSizeCategory.EXTRA_LARGE -> baseSize * 1.2f * scaleFactor
    }
    
    Text(
        text = text,
        modifier = modifier,
        fontSize = adjustedSize,
        style = style
    )
}

@Preview
@Composable
fun ResponsiveTextPreview() {
    ResponsiveText(
        text = "This is responsive text",
        baseSize = 20.sp,
        scaleFactor = 1.2f
    )
}

/**
 * Adaptive Spacing Component
 * Spacing that adapts to screen size
 * 
 * @param baseSpacing Base spacing value
 */
@Composable
fun AdaptiveSpacing(
    baseSpacing: Dp = 16.dp
): Dp {
    val deviceInfo = LocalDeviceInfo.current
    
    return when (deviceInfo.screenSizeCategory) {
        ScreenSizeCategory.COMPACT -> baseSpacing * 0.75f
        ScreenSizeCategory.MEDIUM -> baseSpacing
        ScreenSizeCategory.EXPANDED -> baseSpacing * 1.25f
        ScreenSizeCategory.EXTRA_LARGE -> baseSpacing * 1.5f
    }
}

@Preview
@Composable
fun AdaptiveSpacingPreview() {
    val spacing = AdaptiveSpacing(baseSpacing = 20.dp)
    Box(modifier = Modifier.padding(spacing)) {
        Text("Content with adaptive spacing")
    }
}
/**
 * Compatibility Dashboard Component
 * Dashboard showing compatibility status
 * 
 * @param deviceInfo Current device information
 * @param issues Detected compatibility issues
 */
@Composable
fun CompatibilityDashboardComponent(
    deviceInfo: DeviceInfo,
    issues: List<CompatibilityIssue> = emptyList()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Platform Compatibility",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            DeviceInfoCard(deviceInfo = deviceInfo)
        }
        
        item {
            CompatibilityStatusCard(
                deviceInfo = deviceInfo,
                issues = issues
            )
        }
        
        if (issues.isNotEmpty()) {
            item {
                Text(
                    text = "Compatibility Issues",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            
            items(issues) { issue ->
                CompatibilityIssueCard(issue = issue)
            }
        }
        
        item {
            ResponsiveDesignDemoCard()
        }
    }
}

@Preview
@Composable
fun CompatibilityDashboardComponentPreview() {
    val deviceInfo = rememberDeviceInfo()
    val issues = listOf(
        CompatibilityIssue(
            id = "issue1",
            title = "Sample Issue 1",
            description = "This is a sample critical issue.",
            severity = CompatibilitySeverity.CRITICAL,
            affectedPlatforms = setOf(PlatformType.ANDROID_PHONE),
            recommendation = "Fix immediately."
        ),
        CompatibilityIssue(
            id = "issue2",
            title = "Sample Issue 2",
            description = "This is a sample medium issue.",
            severity = CompatibilitySeverity.MEDIUM,
            affectedPlatforms = setOf(PlatformType.ANDROID_TABLET, PlatformType.WEB),
            recommendation = "Consider fixing."
        )
    )
    CompatibilityDashboardComponent(
        deviceInfo = deviceInfo,
        issues = issues
    )
}
/**
 * Helper Components
 */

@Composable
private fun AdaptiveLayoutProvider(
    deviceInfo: DeviceInfo,
    config: AdaptiveLayoutConfig,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDeviceInfo provides deviceInfo,
        LocalAdaptiveLayoutConfig provides config
    ) {
        content()
    }
}

@Composable
private fun NavigationDrawer(
    navigationItems: List<NavigationItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(navigationItems) { item ->
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label) },
                    selected = selectedItem == item.id,
                    onClick = { onItemSelected(item.id) },
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun DeviceInfoCard(deviceInfo: DeviceInfo) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Device Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            DeviceInfoRow("Platform", deviceInfo.platformType.name)
            DeviceInfoRow("Screen Size", deviceInfo.screenSizeCategory.name)
            DeviceInfoRow("Orientation", deviceInfo.orientation.name)
            DeviceInfoRow("Resolution", "${deviceInfo.screenWidth} x ${deviceInfo.screenHeight}")
            DeviceInfoRow("Density", "${deviceInfo.density}x")
            DeviceInfoRow("API Level", deviceInfo.apiLevel.toString())
            
            if (deviceInfo.manufacturer.isNotEmpty()) {
                DeviceInfoRow("Manufacturer", deviceInfo.manufacturer)
            }
            
            if (deviceInfo.model.isNotEmpty()) {
                DeviceInfoRow("Model", deviceInfo.model)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Capabilities",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(deviceInfo.capabilities.toList()) { capability ->
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = capability.name.replace("_", " "),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CompatibilityStatusCard(
    deviceInfo: DeviceInfo,
    issues: List<CompatibilityIssue>
) {
    val criticalIssues = issues.count { it.severity == CompatibilitySeverity.CRITICAL }
    val highIssues = issues.count { it.severity == CompatibilitySeverity.HIGH }
    
    val statusColor = when {
        criticalIssues > 0 -> Color.Red
        highIssues > 0 -> Orange
        issues.isNotEmpty() -> Color.Yellow
        else -> Color.Green
    }
    
    val statusText = when {
        criticalIssues > 0 -> "Critical Issues Detected"
        highIssues > 0 -> "High Priority Issues"
        issues.isNotEmpty() -> "Minor Issues Detected"
        else -> "Fully Compatible"
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = statusColor.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when {
                    criticalIssues > 0 -> Icons.Default.Error
                    highIssues > 0 -> Icons.Default.Warning
                    issues.isNotEmpty() -> Icons.Default.Info
                    else -> Icons.Default.CheckCircle
                },
                contentDescription = "Status",
                tint = statusColor,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = statusColor
                )
                
                if (issues.isNotEmpty()) {
                    Text(
                        text = "${issues.size} issues found",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun CompatibilityIssueCard(issue: CompatibilityIssue) {
    val severityColor = when (issue.severity) {
        CompatibilitySeverity.CRITICAL -> Color.Red
        CompatibilitySeverity.HIGH -> Color(0xFFFF5722)
        CompatibilitySeverity.MEDIUM -> Color(0xFFFF9800)
        CompatibilitySeverity.LOW -> Color(0xFF4CAF50)
        CompatibilitySeverity.INFO -> Color(0xFF2196F3)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = severityColor.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = issue.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                
                Badge(
                    containerColor = severityColor
                ) {
                    Text(
                        text = issue.severity.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = issue.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Affected Platforms:",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(issue.affectedPlatforms.toList()) { platform ->
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = platform.name,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Recommendation: ${issue.recommendation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            if (issue.workaround != null) {
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Workaround: ${issue.workaround}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun ResponsiveDesignDemoCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Responsive Design Demo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val demoItems = (1..6).map { "Item $it" }
            
            ResponsiveGridComponent(
                items = demoItems,
                modifier = Modifier.height(200.dp)
            ) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

/**
 * Data Classes and Helper Functions
 */

data class NavigationItem(
    val id: String,
    val label: String,
    val icon: ImageVector
)

val LocalDeviceInfo = compositionLocalOf { DeviceInfo(
    platformType = PlatformType.ANDROID_PHONE,
    screenSizeCategory = ScreenSizeCategory.COMPACT,
    orientation = OrientationType.PORTRAIT,
    screenWidth = 360.dp,
    screenHeight = 640.dp,
    density = 1.0f,
    capabilities = emptySet(),
    apiLevel = Build.VERSION.SDK_INT
) }

val LocalAdaptiveLayoutConfig = compositionLocalOf { AdaptiveLayoutConfig() }

@Composable
fun rememberDeviceInfo(): DeviceInfo {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    return remember(configuration, density) {
        val screenWidth = with(density) { configuration.screenWidthDp.dp }
        val screenHeight = with(density) { configuration.screenHeightDp.dp }
        
        val screenSizeCategory = when {
            screenWidth < 600.dp -> ScreenSizeCategory.COMPACT
            screenWidth < 840.dp -> ScreenSizeCategory.MEDIUM
            screenWidth < 1200.dp -> ScreenSizeCategory.EXPANDED
            else -> ScreenSizeCategory.EXTRA_LARGE
        }
        
        val orientation = when {
            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> OrientationType.LANDSCAPE
            configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> OrientationType.PORTRAIT
            else -> OrientationType.SQUARE
        }
        
        val platformType = determinePlatformType(configuration)
        val capabilities = detectDeviceCapabilities()
        
        DeviceInfo(
            platformType = platformType,
            screenSizeCategory = screenSizeCategory,
            orientation = orientation,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            density = density.density,
            capabilities = capabilities,
            apiLevel = Build.VERSION.SDK_INT,
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            isTablet = screenSizeCategory != ScreenSizeCategory.COMPACT,
            isFoldable = false, // Would need specific detection
            hasNotch = false // Would need specific detection
        )
    }
}

private fun determinePlatformType(configuration: Configuration): PlatformType {
    return when {
        configuration.uiMode and Configuration.UI_MODE_TYPE_MASK == Configuration.UI_MODE_TYPE_TELEVISION -> PlatformType.ANDROID_TV
        configuration.uiMode and Configuration.UI_MODE_TYPE_MASK == Configuration.UI_MODE_TYPE_WATCH -> PlatformType.ANDROID_WEAR
        configuration.uiMode and Configuration.UI_MODE_TYPE_MASK == Configuration.UI_MODE_TYPE_CAR -> PlatformType.ANDROID_AUTO
        configuration.screenWidthDp >= 600 -> PlatformType.ANDROID_TABLET
        else -> PlatformType.ANDROID_PHONE
    }
}

private fun detectDeviceCapabilities(): Set<DeviceCapability> {
    val capabilities = mutableSetOf<DeviceCapability>()
    
    // Add basic capabilities (would need proper detection)
    capabilities.add(DeviceCapability.TOUCH_SCREEN)
    capabilities.add(DeviceCapability.WIFI)
    capabilities.add(DeviceCapability.BLUETOOTH)
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        capabilities.add(DeviceCapability.BIOMETRIC)
    }
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        capabilities.add(DeviceCapability.HAPTIC_FEEDBACK)
    }
    
    return capabilities
}

private fun getResponsiveBreakpoint(screenWidth: Dp): ResponsiveBreakpoint {
    return when {
        screenWidth < 600.dp -> ResponsiveBreakpoint(
            name = "compact",
            minWidth = 0.dp,
            maxWidth = 599.dp,
            columns = 1,
            padding = 16.dp,
            spacing = 8.dp
        )
        screenWidth < 840.dp -> ResponsiveBreakpoint(
            name = "medium",
            minWidth = 600.dp,
            maxWidth = 839.dp,
            columns = 2,
            padding = 24.dp,
            spacing = 12.dp
        )
        screenWidth < 1200.dp -> ResponsiveBreakpoint(
            name = "expanded",
            minWidth = 840.dp,
            maxWidth = 1199.dp,
            columns = 3,
            padding = 32.dp,
            spacing = 16.dp
        )
        else -> ResponsiveBreakpoint(
            name = "extra_large",
            minWidth = 1200.dp,
            columns = 4,
            padding = 40.dp,
            spacing = 20.dp
        )
    }
}

private fun detectCompatibilityIssues(deviceInfo: DeviceInfo): List<CompatibilityIssue> {
    val issues = mutableListOf<CompatibilityIssue>()
    
    // Check API level compatibility
    if (deviceInfo.apiLevel < 21) {
        issues.add(
            CompatibilityIssue(
                id = "low_api_level",
                title = "Low API Level",
                description = "Device API level is below minimum supported version",
                severity = CompatibilitySeverity.CRITICAL,
                affectedPlatforms = setOf(deviceInfo.platformType),
                recommendation = "Update minimum SDK version or provide fallback implementation",
                apiLevelRequired = 21
            )
        )
    }
    
    // Check screen size compatibility
    if (deviceInfo.screenSizeCategory == ScreenSizeCategory.COMPACT && deviceInfo.orientation == OrientationType.LANDSCAPE) {
        issues.add(
            CompatibilityIssue(
                id = "small_landscape",
                title = "Small Landscape Screen",
                description = "Limited screen space in landscape mode on small devices",
                severity = CompatibilitySeverity.MEDIUM,
                affectedPlatforms = setOf(PlatformType.ANDROID_PHONE),
                recommendation = "Optimize layout for landscape orientation on small screens",
                workaround = "Hide non-essential UI elements in landscape mode"
            )
        )
    }
    
    // Check platform-specific issues
    when (deviceInfo.platformType) {
        PlatformType.ANDROID_TV -> {
            if (!deviceInfo.capabilities.contains(DeviceCapability.KEYBOARD)) {
                issues.add(
                    CompatibilityIssue(
                        id = "tv_no_keyboard",
                        title = "No Keyboard Input",
                        description = "TV platform lacks keyboard input capability",
                        severity = CompatibilitySeverity.HIGH,
                        affectedPlatforms = setOf(PlatformType.ANDROID_TV),
                        recommendation = "Provide alternative input methods for TV",
                        workaround = "Use D-pad navigation and on-screen keyboard"
                    )
                )
            }
        }
        
        PlatformType.ANDROID_WEAR -> {
            if (deviceInfo.screenWidth < 200.dp) {
                issues.add(
                    CompatibilityIssue(
                        id = "wear_small_screen",
                        title = "Very Small Screen",
                        description = "Wear device has extremely limited screen space",
                        severity = CompatibilitySeverity.HIGH,
                        affectedPlatforms = setOf(PlatformType.ANDROID_WEAR),
                        recommendation = "Design minimal UI specifically for wear devices",
                        workaround = "Show only essential information and actions"
                    )
                )
            }
        }
        
        else -> {}
    }
    
    return issues
}