package com.xiaomi.base.ui.kit.foundation.spacing

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Xiaomi Spacing System
 * 
 * Provides consistent spacing values throughout the UI Kit
 * based on an 8dp grid system following Material Design principles.
 */
data class XiaomiSpacing(
    // Base spacing units
    val ExtraSmall: Dp = 4.dp,
    val Small: Dp = 8.dp,
    val Medium: Dp = 16.dp,
    val Large: Dp = 24.dp,
    val ExtraLarge: Dp = 32.dp,
    val XXL: Dp = 48.dp,
    
    // Screen-level spacing
    val ScreenPaddingHorizontal: Dp = 16.dp,
    val ScreenPaddingVertical: Dp = 16.dp,
    val ScreenContentSpacing: Dp = 24.dp,
    
    // Content spacing
    val ContentPaddingHorizontal: Dp = 12.dp,
    val ContentPaddingVertical: Dp = 12.dp,
    
    // Component spacing
    val ComponentSpacingSmall: Dp = 8.dp,
    val ComponentSpacingMedium: Dp = 16.dp,
    val ComponentSpacingLarge: Dp = 24.dp,
    
    // Card spacing
    val CardPaddingSmall: Dp = 12.dp,
    val CardPaddingMedium: Dp = 16.dp,
    val CardPaddingLarge: Dp = 20.dp,
    
    // List spacing
    val ListItemSpacing: Dp = 8.dp,
    val ListSectionSpacing: Dp = 16.dp,
    
    // Button spacing
    val ButtonContentSpacing: Dp = 8.dp,
    val ButtonGroupSpacing: Dp = 12.dp,
    
    // Additional missing properties spotted in errors
    val SpacingSmall: Dp = 8.dp,
    val ContainerMedium: Dp = 16.dp,
    val ChipMedium: Dp = 16.dp
)

/**
 * Local composition for Xiaomi spacing
 */
val LocalXiaomiSpacing = staticCompositionLocalOf { XiaomiSpacing() }

/**
 * Extension property to access spacing from MaterialTheme
 */
val androidx.compose.material3.MaterialTheme.spacing: XiaomiSpacing
    @androidx.compose.runtime.Composable
    get() = LocalXiaomiSpacing.current

// Note: Additional Modifier extensions can be added here if needed 