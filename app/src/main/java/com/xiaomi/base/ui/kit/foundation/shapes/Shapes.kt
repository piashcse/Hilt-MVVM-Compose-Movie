package com.xiaomi.base.ui.kit.foundation.shapes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Shape tokens for Xiaomi Base UI Kit
 * Following Material Design 3 shape guidelines
 */
object ShapeTokens {
    
    // Corner radius tokens
    val CornerNone = 0.dp
    val CornerExtraSmall = 4.dp
    val CornerSmall = 8.dp
    val CornerMedium = 12.dp
    val CornerLarge = 16.dp
    val CornerExtraLarge = 28.dp
    val CornerFull = 50.dp // For circular shapes
    
    // Basic shapes
    val None = RoundedCornerShape(CornerNone)
    val ExtraSmall = RoundedCornerShape(CornerExtraSmall)
    val Small = RoundedCornerShape(CornerSmall)
    val Medium = RoundedCornerShape(CornerMedium)
    val Large = RoundedCornerShape(CornerLarge)
    val ExtraLarge = RoundedCornerShape(CornerExtraLarge)
    val Full = RoundedCornerShape(CornerFull)
    
    // Top-only rounded shapes
    val ExtraSmallTop = RoundedCornerShape(
        topStart = CornerExtraSmall,
        topEnd = CornerExtraSmall,
        bottomStart = CornerNone,
        bottomEnd = CornerNone
    )
    
    val SmallTop = RoundedCornerShape(
        topStart = CornerSmall,
        topEnd = CornerSmall,
        bottomStart = CornerNone,
        bottomEnd = CornerNone
    )
    
    val MediumTop = RoundedCornerShape(
        topStart = CornerMedium,
        topEnd = CornerMedium,
        bottomStart = CornerNone,
        bottomEnd = CornerNone
    )
    
    val LargeTop = RoundedCornerShape(
        topStart = CornerLarge,
        topEnd = CornerLarge,
        bottomStart = CornerNone,
        bottomEnd = CornerNone
    )
    
    // Bottom-only rounded shapes
    val ExtraSmallBottom = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerNone,
        bottomStart = CornerExtraSmall,
        bottomEnd = CornerExtraSmall
    )
    
    val SmallBottom = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerNone,
        bottomStart = CornerSmall,
        bottomEnd = CornerSmall
    )
    
    val MediumBottom = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerNone,
        bottomStart = CornerMedium,
        bottomEnd = CornerMedium
    )
    
    val LargeBottom = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerNone,
        bottomStart = CornerLarge,
        bottomEnd = CornerLarge
    )
    
    // Start-only rounded shapes (for RTL support)
    val ExtraSmallStart = RoundedCornerShape(
        topStart = CornerExtraSmall,
        topEnd = CornerNone,
        bottomStart = CornerExtraSmall,
        bottomEnd = CornerNone
    )
    
    val SmallStart = RoundedCornerShape(
        topStart = CornerSmall,
        topEnd = CornerNone,
        bottomStart = CornerSmall,
        bottomEnd = CornerNone
    )
    
    val MediumStart = RoundedCornerShape(
        topStart = CornerMedium,
        topEnd = CornerNone,
        bottomStart = CornerMedium,
        bottomEnd = CornerNone
    )
    
    // End-only rounded shapes (for RTL support)
    val ExtraSmallEnd = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerExtraSmall,
        bottomStart = CornerNone,
        bottomEnd = CornerExtraSmall
    )
    
    val SmallEnd = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerSmall,
        bottomStart = CornerNone,
        bottomEnd = CornerSmall
    )
    
    val MediumEnd = RoundedCornerShape(
        topStart = CornerNone,
        topEnd = CornerMedium,
        bottomStart = CornerNone,
        bottomEnd = CornerMedium
    )
}

/**
 * Material Design 3 Shapes for Xiaomi Base UI Kit
 */
val XiaomiShapes = Shapes(
    extraSmall = ShapeTokens.ExtraSmall,
    small = ShapeTokens.Small,
    medium = ShapeTokens.Medium,
    large = ShapeTokens.Large,
    extraLarge = ShapeTokens.ExtraLarge
)

/**
 * Semantic shapes for specific component types
 */
object ComponentShapes {
    
    // Button shapes
    val ButtonSmall = ShapeTokens.Small
    val ButtonMedium = ShapeTokens.Medium
    val ButtonLarge = ShapeTokens.Large
    val ButtonFull = ShapeTokens.Full
    
    // Card shapes
    val CardSmall = ShapeTokens.Small
    val CardMedium = ShapeTokens.Medium
    val CardLarge = ShapeTokens.Large
    
    // Input field shapes
    val InputField = ShapeTokens.ExtraSmall
    val InputFieldFocused = ShapeTokens.Small
    
    // Chip shapes
    val Chip = ShapeTokens.Small
    val ChipMedium = ShapeTokens.Medium
    val ChipAssist = ShapeTokens.Small
    val ChipFilter = ShapeTokens.Small
    val ChipInput = ShapeTokens.Small
    val ChipSuggestion = ShapeTokens.Small
    
    // Dialog shapes
    val Dialog = ShapeTokens.ExtraLarge
    val DialogSmall = ShapeTokens.Large
    
    // Bottom sheet shapes
    val BottomSheet = ShapeTokens.LargeTop
    val BottomSheetModal = ShapeTokens.ExtraLarge
    
    // Navigation shapes
    val NavigationBar = ShapeTokens.None
    val NavigationRail = ShapeTokens.None
    val NavigationDrawer = ShapeTokens.Large
    
    // FAB shapes
    val FABSmall = ShapeTokens.Small
    val FABMedium = ShapeTokens.Large
    val FABLarge = ShapeTokens.ExtraLarge
    val FABExtended = ShapeTokens.Large
    
    // Snackbar shapes
    val Snackbar = ShapeTokens.ExtraSmall
    val SnackbarSingleLine = ShapeTokens.ExtraSmall
    
    // Badge shapes
    val Badge = ShapeTokens.Full
    val BadgeSmall = ShapeTokens.Full
    
    // Progress indicator shapes
    val ProgressLinear = ShapeTokens.Full
    val ProgressCircular = ShapeTokens.Full
    
    // Switch shapes
    val SwitchTrack = ShapeTokens.Full
    val SwitchThumb = ShapeTokens.Full
    
    // Slider shapes
    val SliderTrack = ShapeTokens.Full
    val SliderThumb = ShapeTokens.Full
    
    // Menu shapes
    val Menu = ShapeTokens.ExtraSmall
    val MenuLarge = ShapeTokens.Small
    
    // Tooltip shapes
    val Tooltip = ShapeTokens.ExtraSmall
    val TooltipRich = ShapeTokens.Small
    
    // Surface shapes
    val Surface = ShapeTokens.None
    val SurfaceVariant = ShapeTokens.Small
    
    // Container shapes
    val Container = ShapeTokens.Medium
    val ContainerSmall = ShapeTokens.Small
    val ContainerLarge = ShapeTokens.Large
}

/**
 * Xiaomi-specific shapes for brand consistency
 */
object XiaomiComponentShapes {
    
    // Xiaomi brand specific corner radius
    val XiaomiBrandRadius = 6.dp
    val XiaomiBrand = RoundedCornerShape(XiaomiBrandRadius)
    
    // Product card shapes (common in Xiaomi apps)
    val ProductCard = ShapeTokens.Medium
    val ProductCardLarge = ShapeTokens.Large
    
    // Feature highlight shapes
    val FeatureHighlight = ShapeTokens.Large
    val FeatureCard = ShapeTokens.Medium
    
    // Status indicator shapes
    val StatusIndicator = ShapeTokens.Full
    val StatusBadge = ShapeTokens.Small
    
    // Device control shapes
    val DeviceControl = ShapeTokens.Large
    val DeviceCard = ShapeTokens.Medium
    
    // Settings item shapes
    val SettingsItem = ShapeTokens.Small
    val SettingsCard = ShapeTokens.Medium
}