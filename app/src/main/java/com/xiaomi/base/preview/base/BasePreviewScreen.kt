package com.xiaomi.base.preview.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

/**
 * Base screen for all preview demonstrations
 * Provides consistent layout, theming, and navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasePreviewScreen(
    title: String,
    subtitle: String? = null,
    onBackClick: () -> Unit = {},
    showThemeToggle: Boolean = true,
    showDeviceFrame: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var showDevicePreview by remember { mutableStateOf(showDeviceFrame) }
    
    PreviewContainer(
        isDarkTheme = isDarkTheme,
        showDeviceFrame = showDevicePreview
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            PreviewToolbar(
                title = title,
                subtitle = subtitle,
                onBackClick = onBackClick,
                isDarkTheme = isDarkTheme,
                onThemeToggle = if (showThemeToggle) {
                    { isDarkTheme = !isDarkTheme }
                } else null,
                showDeviceFrame = showDevicePreview,
                onDeviceFrameToggle = { showDevicePreview = !showDevicePreview },
                actions = actions
            )
            
            // Content Area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                if (showDevicePreview) {
                    DeviceFrame {
                        content()
                    }
                } else {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun BasePreviewScreenPreview() {
    BasePreviewScreen(
        title = "Preview Title",
        subtitle = "Preview Subtitle",
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Preview Content")
            }
        }
    )
}
/**
 * Scrollable version of BasePreviewScreen
 */
@Composable
fun ScrollablePreviewScreen(
    title: String,
    subtitle: String? = null,
    onBackClick: () -> Unit = {},
    showThemeToggle: Boolean = true,
    showDeviceFrame: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    BasePreviewScreen(
        title = title,
        subtitle = subtitle,
        onBackClick = onBackClick,
        showThemeToggle = showThemeToggle,
        showDeviceFrame = showDeviceFrame,
        actions = actions
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun ScrollablePreviewScreenPreview() {
    ScrollablePreviewScreen(
        title = "Scrollable Preview",
        subtitle = "This is a scrollable preview screen"
    ) {
        // Add some content to demonstrate scrolling
        repeat(20) {
            Text(
                text = "Item $it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
/**
 * Component showcase section with title and description
 */
@Composable
fun ComponentShowcase(
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Divider()
            
            // Content
            content()
        }
    }
}

@Preview
@Composable
fun ComponentShowcasePreview() {
    ComponentShowcase(
        title = "Sample Component",
        description = "This is a sample component showcase.",
        icon = Icons.Filled.Star
    ) {
        Text(
            text = "Content of the showcased component goes here.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



/**
 * Interactive demo section with controls
 */
@Composable
fun InteractiveDemo(
    title: String,
    description: String? = null,
    modifier: Modifier = Modifier,
    controls: @Composable ColumnScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Controls
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                controls()
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            
            // Demo Content
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun InteractiveDemoPreview() {
    InteractiveDemo(
        title = "Interactive Demo",
        description = "This is an interactive demo component."
    ) {
        // Controls
        Button(onClick = { /*TODO*/ }) {
            Text("Click Me")
        }
        Switch(checked = false, onCheckedChange = {})
    }
    // Content
    Text(
        text = "This is the demo content area.",
        style = MaterialTheme.typography.bodyLarge
    )
}



/**
 * Device frame simulation
 */
@Composable
fun DeviceFrame(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(9f / 16f),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun DeviceFramePreview() {
    DeviceFrame {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Content inside device frame")
        }
    }
}