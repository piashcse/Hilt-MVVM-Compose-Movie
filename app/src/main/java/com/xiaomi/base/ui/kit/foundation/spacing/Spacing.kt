package com.xiaomi.base.ui.kit.foundation.spacing

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing tokens for Xiaomi Base UI Kit
 * Following Material Design 3 spacing guidelines with 4dp base unit
 */
object Spacing {
    
    // Base spacing unit (4dp)
    val Base = 4.dp
    
    // Micro spacing - for fine adjustments
    val Micro = 2.dp
    
    // Extra small spacing
    val ExtraSmall = 4.dp
    
    // Small spacing
    val Small = 8.dp
    
    // Medium spacing
    val Medium = 12.dp
    
    // Large spacing
    val Large = 16.dp
    
    // Extra large spacing
    val ExtraLarge = 20.dp
    
    // XXL spacing
    val XXL = 24.dp
    
    // XXXL spacing
    val XXXL = 32.dp
    
    // Huge spacing
    val Huge = 40.dp
    
    // Massive spacing
    val Massive = 48.dp
    
    // Giant spacing
    val Giant = 64.dp
    
    // Colossal spacing
    val Colossal = 80.dp
}

/**
 * Semantic spacing tokens for specific use cases
 */
object SemanticSpacing {
    
    // Content spacing
    val ContentPaddingHorizontal = Spacing.Large
    val ContentPaddingVertical = Spacing.Large
    val ContentSpacing = Spacing.Medium
    
    // Component spacing
    val ComponentPadding = Spacing.Large
    val ComponentSpacing = Spacing.Small
    val ComponentMargin = Spacing.Medium
    
    // List spacing
    val ListItemPadding = Spacing.Large
    val ListItemSpacing = Spacing.Small
    val ListSectionSpacing = Spacing.XXL
    
    // Card spacing
    val CardPadding = Spacing.Large
    val CardSpacing = Spacing.Medium
    val CardMargin = Spacing.Large
    
    // Button spacing
    val ButtonPaddingHorizontal = Spacing.XXL
    val ButtonPaddingVertical = Spacing.Medium
    val ButtonSpacing = Spacing.Small
    
    // Input field spacing
    val InputPadding = Spacing.Large
    val InputSpacing = Spacing.Small
    val InputMargin = Spacing.Medium
    
    // Navigation spacing
    val NavigationPadding = Spacing.Large
    val NavigationItemSpacing = Spacing.Small
    val NavigationSectionSpacing = Spacing.XXL
    
    // Screen spacing
    val ScreenPaddingHorizontal = Spacing.Large
    val ScreenPaddingVertical = Spacing.Large
    val ScreenContentSpacing = Spacing.XXL
    
    // Dialog spacing
    val DialogPadding = Spacing.XXL
    val DialogContentSpacing = Spacing.Large
    val DialogButtonSpacing = Spacing.Small
    
    // Toolbar spacing
    val ToolbarPadding = Spacing.Large
    val ToolbarItemSpacing = Spacing.Small
    
    // Icon spacing
    val IconPadding = Spacing.Small
    val IconSpacing = Spacing.Small
    val IconMargin = Spacing.Medium
}

/**
 * Grid spacing tokens for layout systems
 */
object GridSpacing {
    
    // Grid gutters
    val GutterSmall = Spacing.Small
    val GutterMedium = Spacing.Medium
    val GutterLarge = Spacing.Large
    
    // Grid margins
    val MarginSmall = Spacing.Large
    val MarginMedium = Spacing.XXL
    val MarginLarge = Spacing.XXXL
    
    // Column spacing
    val ColumnSpacing = Spacing.Medium
    val ColumnPadding = Spacing.Small
    
    // Row spacing
    val RowSpacing = Spacing.Large
    val RowPadding = Spacing.Medium
}

/**
 * Responsive spacing tokens that adapt to screen size
 */
object ResponsiveSpacing {
    
    // Compact screen spacing (phones)
    object Compact {
        val ScreenPadding = Spacing.Large
        val ContentSpacing = Spacing.Medium
        val SectionSpacing = Spacing.XXL
    }
    
    // Medium screen spacing (tablets)
    object Medium {
        val ScreenPadding = Spacing.XXL
        val ContentSpacing = Spacing.Large
        val SectionSpacing = Spacing.XXXL
    }
    
    // Expanded screen spacing (large tablets, desktop)
    object Expanded {
        val ScreenPadding = Spacing.XXXL
        val ContentSpacing = Spacing.XXL
        val SectionSpacing = Spacing.Huge
    }
}

/**
 * Animation spacing tokens for motion design
 */
object AnimationSpacing {
    
    // Slide distances
    val SlideSmall = Spacing.XXXL
    val SlideMedium = Spacing.Huge
    val SlideLarge = Spacing.Giant
    
    // Offset distances
    val OffsetSmall = Spacing.Small
    val OffsetMedium = Spacing.Medium
    val OffsetLarge = Spacing.Large
    
    // Transform distances
    val TransformSmall = Spacing.Medium
    val TransformMedium = Spacing.Large
    val TransformLarge = Spacing.XXL
}