package com.xiaomi.base.preview.demos.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.components.accessibility.i18n.AccessibilityI18nComponent
import com.xiaomi.base.components.accessibility.i18n.AccessibilityI18nConfig
import com.xiaomi.base.components.adaptive.layout.ResponsiveGridComponent
import com.xiaomi.base.components.adaptive.layout.AdaptiveNavigationComponent
import com.xiaomi.base.components.adaptive.layout.NavigationItem
import com.xiaomi.base.components.ai.prediction.PredictiveTextComponent
import com.xiaomi.base.components.ai.prediction.PredictionConfig
import com.xiaomi.base.components.animation.advanced.AdvancedAnimationComponent
import com.xiaomi.base.components.animation.advanced.WaveAnimationComponent
import com.xiaomi.base.components.animation.advanced.RippleAnimationComponent
import com.xiaomi.base.components.interaction.gesture.PinchZoomGestureComponent
import com.xiaomi.base.components.interaction.gesture.SwipeToDismissComponent
import com.xiaomi.base.components.interaction.gesture.SwipeDirection
import com.xiaomi.base.components.performance.monitoring.PerformanceMonitoringComponent
import com.xiaomi.base.components.security.privacy.SecurityPrivacyComponent
import com.xiaomi.base.components.visualization.data.DataVisualizationComponent
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseTheme

/**
 * Register UI Components previews
 */
fun registerUIComponentsPreviews() {
    // Accessibility & Internationalization Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "accessibility_i18n",
            title = "Accessibility & Internationalization",
            description = "Comprehensive accessibility features with multi-language support",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.Accessibility,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "45 min",
            tags = listOf("accessibility", "i18n", "localization", "a11y"),
            content = { AccessibilityI18nPreview() }
        )
    )
    
    // Performance Monitoring Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "performance_monitoring",
            title = "Performance Monitoring Dashboard",
            description = "Real-time performance metrics with charts and alerts",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.Speed,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "40 min",
            tags = listOf("performance", "monitoring", "metrics", "dashboard"),
            content = { PerformanceMonitoringPreview() }
        )
    )
    
    // Data Visualization Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "data_visualization",
            title = "Data Visualization Charts",
            description = "Interactive charts and graphs with multiple visualization types",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.BarChart,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "35 min",
            tags = listOf("charts", "graphs", "visualization", "data"),
            content = { DataVisualizationPreview() }
        )
    )
    
    // Security & Privacy Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "security_privacy",
            title = "Security & Privacy Dashboard",
            description = "Comprehensive security monitoring with threat detection",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.Security,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "40 min",
            tags = listOf("security", "privacy", "encryption", "threats"),
            content = { SecurityPrivacyPreview() }
        )
    )
    
    // Advanced Animation Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "advanced_animations",
            title = "Advanced Animation Effects",
            description = "Complex animations including waves, ripples, and advanced effects",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.Animation,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "50 min",
            tags = listOf("animation", "waves", "ripples", "effects"),
            content = { AdvancedAnimationPreview() }
        )
    )
    
    // Gesture Components
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "gesture_interactions",
            title = "Gesture Interactions",
            description = "Touch gestures including pinch-to-zoom and swipe-to-dismiss",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.TouchApp,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 min",
            tags = listOf("gestures", "touch", "zoom", "swipe"),
            content = { GestureInteractionsPreview() }
        )
    )
    
    // Predictive Text Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "predictive_text",
            title = "AI Predictive Text Input",
            description = "Smart text input with AI-powered predictions and auto-complete",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.AutoAwesome,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "25 min",
            tags = listOf("ai", "prediction", "text", "autocomplete"),
            content = { PredictiveTextPreview() }
        )
    )
    
    // Responsive Layout Component
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "responsive_layout",
            title = "Responsive Layout System",
            description = "Adaptive layouts that respond to screen size and orientation",
            category = PreviewCategory.COMPONENTS,
            icon = Icons.Default.ViewQuilt,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 min",
            tags = listOf("responsive", "layout", "adaptive", "grid"),
            content = { ResponsiveLayoutPreview() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AccessibilityI18nPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Accessibility & Internationalization",
            subtitle = "Comprehensive accessibility features"
        ) {
            AccessibilityI18nComponent(
                modifier = Modifier.fillMaxSize(),
                config = AccessibilityI18nConfig()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerformanceMonitoringPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Performance Monitoring",
            subtitle = "Real-time system metrics"
        ) {
            PerformanceMonitoringComponent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataVisualizationPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Data Visualization",
            subtitle = "Interactive charts and graphs"
        ) {
            DataVisualizationComponent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecurityPrivacyPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Security & Privacy",
            subtitle = "Comprehensive security dashboard"
        ) {
            SecurityPrivacyComponent(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedAnimationPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Advanced Animations",
            subtitle = "Wave, ripple and advanced effects"
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Advanced Animation Component",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            AdvancedAnimationComponent {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Advanced Animation Demo")
                                }
                            }
                        }
                    }
                }
                
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Wave Animation",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            WaveAnimationComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )
                        }
                    }
                }
                
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Ripple Animation",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            RippleAnimationComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GestureInteractionsPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Gesture Interactions",
            subtitle = "Touch and gesture handling"
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Pinch to Zoom",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            PinchZoomGestureComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) { state ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(24.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ZoomIn,
                                                contentDescription = "Zoom",
                                                modifier = Modifier.size(48.dp)
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("Pinch to zoom")
                                            Text(
                                                text = "Scale: ${String.format("%.2f", state.scale)}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Swipe to Dismiss",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            SwipeToDismissComponent(
                                directions = setOf(SwipeDirection.LEFT, SwipeDirection.RIGHT),
                                onDismiss = { direction ->
                                    // Handle dismiss
                                }
                            ) { state ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.SwipeLeft,
                                            contentDescription = "Swipe"
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column {
                                            Text("Swipe left or right to dismiss")
                                            Text(
                                                text = "Progress: ${String.format("%.1f", state.progress * 100)}%",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PredictiveTextPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "AI Predictive Text",
            subtitle = "Smart text input with predictions"
        ) {
            var textValue by remember { mutableStateOf(TextFieldValue("")) }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PredictiveTextComponent(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = "Smart Text Input",
                    placeholder = "Start typing to see predictions...",
                    config = PredictionConfig(),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Features",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• Real-time text predictions")
                        Text("• Auto-completion suggestions")
                        Text("• Smart reply generation")
                        Text("• Grammar and style corrections")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResponsiveLayoutPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Responsive Layout",
            subtitle = "Adaptive grid and navigation"
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Responsive Grid",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        ResponsiveGridComponent(
                            modifier = Modifier.height(200.dp)
                        ) {
                            items(12) { index ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Item ${index + 1}")
                                    }
                                }
                            }
                        }
                    }
                }
                
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Adaptive Navigation",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        AdaptiveNavigationComponent(
                            modifier = Modifier.height(300.dp),
                            navigationItems = listOf(
                                NavigationItem("home", "Home", { Icon(Icons.Default.Home, null) }),
                                NavigationItem("search", "Search", { Icon(Icons.Default.Search, null) }),
                                NavigationItem("profile", "Profile", { Icon(Icons.Default.Person, null) })
                            ),
                            selectedItem = "home",
                            onItemSelected = { }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Content adapts to screen size")
                            }
                        }
                    }
                }
            }
        }
    }
} 