package com.xiaomi.base.preview.base

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Container that provides theming and device frame simulation for previews
 */
@Composable
fun PreviewContainer(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    showDeviceFrame: Boolean = false,
    useDynamicColor: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useDynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S -> {
            if (isDarkTheme) dynamicDarkColorScheme(androidx.compose.ui.platform.LocalContext.current)
            else dynamicLightColorScheme(androidx.compose.ui.platform.LocalContext.current)
        }
        isDarkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = Shapes()
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showDeviceFrame) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            } else {
                content()
            }
        }
    }
}

@Preview
@Composable
fun PreviewContainerPreview() {
    PreviewContainer(
        isDarkTheme = false,
        showDeviceFrame = false,
        useDynamicColor = true
    ) {
        Text(
            text = "Hello Preview!"
        )
    }
}

/**
 * Theme selector component
 */
@Composable
fun ThemeSelector(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.labelMedium
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !isDarkTheme,
                    onClick = { onThemeChange(false) },
                    label = { Text("Light") }
                )
                
                FilterChip(
                    selected = isDarkTheme,
                    onClick = { onThemeChange(true) },
                    label = { Text("Dark") }
                )
            }
        }
    }
}

@Preview
@Composable
fun ThemeSelectorPreview() {
    var isDarkTheme by remember { mutableStateOf(false) }
    ThemeSelector(
        isDarkTheme = isDarkTheme,
        onThemeChange = { isDarkTheme = it }
    )
}



/**
 * Device size selector component
 */
@Composable
fun DeviceSizeSelector(
    selectedSize: DeviceSize,
    onSizeChange: (DeviceSize) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Device Size",
                style = MaterialTheme.typography.labelMedium
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DeviceSize.values().forEach { size ->
                    FilterChip(
                        selected = selectedSize == size,
                        onClick = { onSizeChange(size) },
                        label = { Text(size.displayName) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DeviceSizeSelectorPreview() {
    var selectedSize by remember { mutableStateOf(DeviceSize.PHONE) }
    DeviceSizeSelector(
        selectedSize = selectedSize,
        onSizeChange = { selectedSize = it }
    )
}

/**
 * Device size options
 */
enum class DeviceSize(
    val displayName: String,
    val width: Int,
    val height: Int
) {
    PHONE(
        displayName = "Phone",
        width = 360,
        height = 640
    ),
    TABLET(
        displayName = "Tablet",
        width = 768,
        height = 1024
    ),
    DESKTOP(
        displayName = "Desktop",
        width = 1200,
        height = 800
    )
}

/**
 * Preview settings panel
 */
@Composable
fun PreviewSettingsPanel(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    deviceSize: DeviceSize,
    onDeviceSizeChange: (DeviceSize) -> Unit,
    showDeviceFrame: Boolean,
    onShowDeviceFrameChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Preview Settings",
                style = MaterialTheme.typography.titleMedium
            )
            
            ThemeSelector(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
            
            DeviceSizeSelector(
                selectedSize = deviceSize,
                onSizeChange = onDeviceSizeChange
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Device Frame",
                    style = MaterialTheme.typography.labelMedium
                )
                
                Switch(
                    checked = showDeviceFrame,
                    onCheckedChange = onShowDeviceFrameChange
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsPanelPreview() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var deviceSize by remember { mutableStateOf(DeviceSize.PHONE) }
    var showDeviceFrame by remember { mutableStateOf(false) }
    PreviewSettingsPanel(
        isDarkTheme = isDarkTheme,
        onThemeChange = { isDarkTheme = it },
        deviceSize = deviceSize,
        onDeviceSizeChange = { deviceSize = it },
        showDeviceFrame = showDeviceFrame,
        onShowDeviceFrameChange = { showDeviceFrame = it }
    )
}
