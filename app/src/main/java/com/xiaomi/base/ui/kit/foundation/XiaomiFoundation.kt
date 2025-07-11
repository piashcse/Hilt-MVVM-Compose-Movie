package com.xiaomi.base.ui.kit.foundation

/**
 * Xiaomi Foundation - Design System Foundation
 * 
 * This file provides organized access to all design tokens and foundational
 * elements of the Xiaomi Base UI Kit. It serves as the central hub for
 * colors, typography, spacing, shapes, and other design primitives.
 * 
 * Inspired by ComposeX organization patterns for better developer experience.
 */

// Import all foundation elements
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.xiaomi.base.ui.kit.foundation.colors.ColorTokens
import com.xiaomi.base.ui.kit.foundation.shapes.ComponentShapes
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.spacing

/**
 * Xiaomi Design System - Central access point for all design tokens
 * 
 * Provides organized access to the complete design system including
 * colors, typography, spacing, shapes, and other design primitives.
 */
object XiaomiDesignSystem {
    
    /**
     * Color System - Complete color palette and semantic colors
     */
    object Colors {
        
        /**
         * Brand Colors - Primary brand colors
         */
        object Brand {
            val Primary = ColorTokens.Primary60
            val Secondary = ColorTokens.Secondary60
            val Tertiary = ColorTokens.Tertiary60
        }
        
        /**
         * Semantic Colors - Colors with semantic meaning
         */
        object Semantic {
            val Error = ColorTokens.Error40
            val Warning = ColorTokens.Warning50
            val Success = ColorTokens.Success50
            val Info = ColorTokens.Info50
        }
        
        /**
         * Surface Colors - Background and surface colors
         */
        object Surface {
            val Background = ColorTokens.Neutral99
            val Surface = ColorTokens.Neutral100
            val SurfaceVariant = ColorTokens.NeutralVariant95
        }
        
        /**
         * Content Colors - Text and content colors
         */
        object Content {
            val OnBackground = ColorTokens.Neutral10
            val OnSurface = ColorTokens.Neutral10
            val OnSurfaceVariant = ColorTokens.NeutralVariant30
        }
        
        /**
         * Get current color scheme from Material Theme
         */
        val current: androidx.compose.material3.ColorScheme
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme
    }
    
    /**
     * Typography System - Complete text styling system
     */
    object Typography {
        
        /**
         * Display Styles - Large, prominent text
         */
        object Display {
            val Large: TextStyle @Composable get() = MaterialTheme.typography.displayLarge
            val Medium: TextStyle @Composable get() = MaterialTheme.typography.displayMedium
            val Small: TextStyle @Composable get() = MaterialTheme.typography.displaySmall
        }
        
        /**
         * Headline Styles - Section headers and titles
         */
        object Headline {
            val Large: TextStyle @Composable get() = MaterialTheme.typography.headlineLarge
            val Medium: TextStyle @Composable get() = MaterialTheme.typography.headlineMedium
            val Small: TextStyle @Composable get() = MaterialTheme.typography.headlineSmall
        }
        
        /**
         * Title Styles - Component and card titles
         */
        object Title {
            val Large: TextStyle @Composable get() = MaterialTheme.typography.titleLarge
            val Medium: TextStyle @Composable get() = MaterialTheme.typography.titleMedium
            val Small: TextStyle @Composable get() = MaterialTheme.typography.titleSmall
        }
        
        /**
         * Body Styles - Main content text
         */
        object Body {
            val Large: TextStyle @Composable get() = MaterialTheme.typography.bodyLarge
            val Medium: TextStyle @Composable get() = MaterialTheme.typography.bodyMedium
            val Small: TextStyle @Composable get() = MaterialTheme.typography.bodySmall
        }
        
        /**
         * Label Styles - UI labels and captions
         */
        object Label {
            val Large: TextStyle @Composable get() = MaterialTheme.typography.labelLarge
            val Medium: TextStyle @Composable get() = MaterialTheme.typography.labelMedium
            val Small: TextStyle @Composable get() = MaterialTheme.typography.labelSmall
        }
        
        /**
         * Get current typography from Material Theme
         */
        val current: androidx.compose.material3.Typography
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.typography
    }
    
    /**
     * Spacing System - Consistent spacing values
     */
    object Spacing {
        
        /**
         * Base Spacing Values
         */
        object Base {
            @get:Composable
            val ExtraSmall: Dp get() = MaterialTheme.spacing.ExtraSmall
            @get:Composable
            val Small: Dp get() = MaterialTheme.spacing.Small
            @get:Composable
            val Medium: Dp get() = MaterialTheme.spacing.Medium
            @get:Composable
            val Large: Dp get() = MaterialTheme.spacing.Large
            @get:Composable
            val ExtraLarge: Dp get() = MaterialTheme.spacing.ExtraLarge
            @get:Composable
            val XXL: Dp get() = MaterialTheme.spacing.XXL
        }
        
        /**
         * Component Spacing - Spacing within components
         */
        object Component {
            @get:Composable
            val Spacing: Dp get() = MaterialTheme.spacing.ComponentSpacingMedium
            @get:Composable
            val SpacingLarge: Dp get() = MaterialTheme.spacing.ComponentSpacingLarge
        }
        
        /**
         * Content Spacing - Spacing for content areas
         */
        object Content {
            @get:Composable
            val Horizontal: Dp get() = MaterialTheme.spacing.ContentPaddingHorizontal
            @get:Composable
            val Vertical: Dp get() = MaterialTheme.spacing.ContentPaddingVertical
        }
        
        /**
         * Screen Spacing - Screen-level spacing
         */
        object Screen {
            @get:Composable
            val Horizontal: Dp get() = MaterialTheme.spacing.ScreenPaddingHorizontal
            @get:Composable
            val Vertical: Dp get() = MaterialTheme.spacing.ScreenPaddingVertical
            @get:Composable
            val ContentSpacing: Dp get() = MaterialTheme.spacing.ScreenContentSpacing
        }
        
        /**
         * Button Spacing - Button-specific spacing
         */
        object Button {
            @get:Composable
            val Horizontal: Dp get() = MaterialTheme.spacing.ContentPaddingHorizontal
            @get:Composable
            val Vertical: Dp get() = MaterialTheme.spacing.ContentPaddingVertical
        }
        
        /**
         * Card Spacing - Card-specific spacing
         */
        object Card {
            @get:Composable
            val Padding: Dp get() = MaterialTheme.spacing.CardPaddingMedium
            @get:Composable
            val PaddingLarge: Dp get() = MaterialTheme.spacing.CardPaddingLarge
        }
        
        /**
         * Get current spacing from Material Theme extension
         */
        val current: XiaomiSpacing
            @Composable
            get() = MaterialTheme.spacing
    }
    
    /**
     * Shape System - Component shapes and corner radius
     */
    object Shapes {
        
        /**
         * Button Shapes
         */
        object Button {
            val Small = ComponentShapes.ButtonSmall
            val Medium = ComponentShapes.ButtonMedium
            val Large = ComponentShapes.ButtonLarge
        }
        
        /**
         * Card Shapes
         */
        object Card {
            val Small = ComponentShapes.CardSmall
            val Medium = ComponentShapes.CardMedium
            val Large = ComponentShapes.CardLarge
        }
        
        /**
         * Container Shapes
         */
        object Container {
            val Small = ComponentShapes.ContainerSmall
            val Medium = ComponentShapes.Container
            val Large = ComponentShapes.ContainerLarge
        }
        
        /**
         * Get current shapes from Material Theme
         */
        val current: androidx.compose.material3.Shapes
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.shapes
    }
}

/**
 * Quick Access to Design Tokens
 * 
 * Provides quick access to commonly used design tokens
 * for faster development workflow.
 */
object XiaomiTokens {
    
    // Quick color access
    val PrimaryColor = XiaomiDesignSystem.Colors.Brand.Primary
    val BackgroundColor = XiaomiDesignSystem.Colors.Surface.Background
    val TextColor = XiaomiDesignSystem.Colors.Content.OnSurface
    
    // Quick spacing access
    @get:Composable
    val SmallSpacing: Dp get() = XiaomiDesignSystem.Spacing.Base.Small
    @get:Composable
    val MediumSpacing: Dp get() = XiaomiDesignSystem.Spacing.Base.Medium
    @get:Composable
    val LargeSpacing: Dp get() = XiaomiDesignSystem.Spacing.Base.Large
    
    // Quick shape access
    val ButtonShape = XiaomiDesignSystem.Shapes.Button.Medium
    val CardShape = XiaomiDesignSystem.Shapes.Card.Medium
    
    // Quick typography access
    val HeadlineText = XiaomiDesignSystem.Typography.Headline.Medium
    val BodyText = XiaomiDesignSystem.Typography.Body.Medium
    val LabelText = XiaomiDesignSystem.Typography.Label.Medium
}

/**
 * Design Token Categories
 * 
 * Provides information about available design token categories
 * for documentation and tooling purposes.
 */
object XiaomiTokenCategories {
    
    /**
     * Get all available color categories
     */
    fun getColorCategories() = listOf(
        "Brand Colors",
        "Semantic Colors",
        "Surface Colors",
        "Content Colors"
    )
    
    /**
     * Get all available typography categories
     */
    fun getTypographyCategories() = listOf(
        "Display Styles",
        "Headline Styles",
        "Title Styles",
        "Body Styles",
        "Label Styles"
    )
    
    /**
     * Get all available spacing categories
     */
    fun getSpacingCategories() = listOf(
        "Base Spacing",
        "Component Spacing",
        "Content Spacing",
        "Screen Spacing",
        "Button Spacing",
        "Card Spacing"
    )
    
    /**
     * Get all available shape categories
     */
    fun getShapeCategories() = listOf(
        "Button Shapes",
        "Card Shapes",
        "Container Shapes"
    )
    
    /**
     * Get all design token categories
     */
    fun getAllCategories() = mapOf(
        "Colors" to getColorCategories(),
        "Typography" to getTypographyCategories(),
        "Spacing" to getSpacingCategories(),
        "Shapes" to getShapeCategories()
    )
}

/**
 * Design System Utilities
 * 
 * Provides utility functions for working with the design system.
 */
object XiaomiDesignUtils {
    
    /**
     * Validate if a color token exists
     */
    fun hasColorToken(category: String, token: String): Boolean {
        return when (category.lowercase()) {
            "brand" -> token in listOf("Primary", "Secondary", "Tertiary")
            "semantic" -> token in listOf("Error", "Warning", "Success", "Info")
            "surface" -> token in listOf("Background", "Surface", "SurfaceVariant")
            "content" -> token in listOf("OnBackground", "OnSurface", "OnSurfaceVariant")
            else -> false
        }
    }
    
    /**
     * Get design system version
     */
    fun getVersion() = "1.0.0"
    
    /**
     * Get design system info
     */
    fun getInfo() = mapOf(
        "version" to getVersion(),
        "colorCategories" to XiaomiTokenCategories.getColorCategories().size,
        "typographyStyles" to XiaomiTokenCategories.getTypographyCategories().size,
        "spacingTokens" to XiaomiTokenCategories.getSpacingCategories().size,
        "shapeTokens" to XiaomiTokenCategories.getShapeCategories().size
    )
}