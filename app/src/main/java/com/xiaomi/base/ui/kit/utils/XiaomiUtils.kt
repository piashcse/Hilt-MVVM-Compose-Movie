package com.xiaomi.base.ui.kit.utils

/**
 * Xiaomi Utils - Utility Functions and Extensions
 * 
 * This file provides organized access to utility functions, extensions,
 * and helper classes that support the Xiaomi Base UI Kit.
 * These utilities enhance developer productivity and code reusability.
 * 
 * Inspired by ComposeX organization patterns for better developer experience.
 */

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.xiaomi.base.ui.kit.foundation.spacing.spacing
import kotlin.math.*

/**
 * Modifier Extensions
 * 
 * Useful modifier extensions for common UI patterns
 * and consistent styling across the design system.
 */
object XiaomiModifiers {
    
    /**
     * Apply consistent card styling
     */
    fun Modifier.xiaomiCard(
        shape: Shape? = null
    ): Modifier = composed {
        val cardShape = shape ?: MaterialTheme.shapes.medium
        this.clip(cardShape)
    }
    
    /**
     * Apply consistent button styling
     */
    fun Modifier.xiaomiButton(
        shape: Shape? = null
    ): Modifier = composed {
        val buttonShape = shape ?: MaterialTheme.shapes.small
        this.clip(buttonShape)
    }
    
    /**
     * Apply consistent container padding
     */
    fun Modifier.xiaomiContainerPadding(): Modifier = composed {
        this.then(
            Modifier.padding(
                horizontal = MaterialTheme.spacing.ScreenPaddingHorizontal,
                vertical = MaterialTheme.spacing.ScreenPaddingVertical
            )
        )
    }
    
    /**
     * Apply consistent content padding
     */
    fun Modifier.xiaomiContentPadding(): Modifier = composed {
        this.then(
            Modifier.padding(
                horizontal = MaterialTheme.spacing.ContentPaddingHorizontal,
                vertical = MaterialTheme.spacing.ContentPaddingVertical
            )
        )
    }
}

/**
 * Color Utilities
 * 
 * Helper functions for working with colors in the design system.
 */
object XiaomiColorUtils {
    
    /**
     * Create a color with alpha transparency
     */
    fun Color.withAlpha(alpha: Float): Color {
        return this.copy(alpha = alpha)
    }
    
    /**
     * Get a lighter version of the color
     */
    fun Color.lighter(factor: Float = 0.1f): Color {
        return Color(
            red = (red + (1f - red) * factor).coerceIn(0f, 1f),
            green = (green + (1f - green) * factor).coerceIn(0f, 1f),
            blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
            alpha = alpha
        )
    }
    
    /**
     * Get a darker version of the color
     */
    fun Color.darker(factor: Float = 0.1f): Color {
        return Color(
            red = (red * (1f - factor)).coerceIn(0f, 1f),
            green = (green * (1f - factor)).coerceIn(0f, 1f),
            blue = (blue * (1f - factor)).coerceIn(0f, 1f),
            alpha = alpha
        )
    }
    
    /**
     * Check if color is light or dark
     */
    fun Color.isLight(): Boolean {
        val luminance = (0.299 * red + 0.587 * green + 0.114 * blue)
        return luminance > 0.5
    }
    
    /**
     * Get contrasting color (black or white)
     */
    fun Color.contrastingColor(): Color {
        return if (isLight()) Color.Black else Color.White
    }
}

/**
 * Spacing Utilities
 * 
 * Helper functions for working with spacing and padding.
 */
object XiaomiSpacingUtils {
    
    /**
     * Create symmetric padding
     */
    fun symmetricPadding(
        horizontal: Dp,
        vertical: Dp
    ): PaddingValues {
        return PaddingValues(
            horizontal = horizontal,
            vertical = vertical
        )
    }
    
    /**
     * Create all-sides padding
     */
    fun allSidesPadding(value: Dp): PaddingValues {
        return PaddingValues(all = value)
    }
    
    /**
     * Add padding values together
     */
    @Composable
    fun PaddingValues.plus(other: PaddingValues): PaddingValues {
        val direction = LocalLayoutDirection.current
        return PaddingValues(
            start = this.calculateLeftPadding(direction) + 
                   other.calculateLeftPadding(direction),
            top = this.calculateTopPadding() + other.calculateTopPadding(),
            end = this.calculateRightPadding(direction) + 
                 other.calculateRightPadding(direction),
            bottom = this.calculateBottomPadding() + other.calculateBottomPadding()
        )
    }
    
    /**
     * Scale padding values
     */
    @Composable
    fun PaddingValues.scale(factor: Float): PaddingValues {
        val direction = LocalLayoutDirection.current
        return PaddingValues(
            start = this.calculateLeftPadding(direction) * factor,
            top = this.calculateTopPadding() * factor,
            end = this.calculateRightPadding(direction) * factor,
            bottom = this.calculateBottomPadding() * factor
        )
    }
}

/**
 * Animation Utilities
 * 
 * Helper functions and constants for consistent animations.
 */
object XiaomiAnimationUtils {
    
    // Animation durations
    const val DURATION_SHORT = 150
    const val DURATION_MEDIUM = 300
    const val DURATION_LONG = 500
    
    // Animation delays
    const val DELAY_SHORT = 50
    const val DELAY_MEDIUM = 100
    const val DELAY_LONG = 200
    
    /**
     * Get animation duration based on distance
     */
    fun getDurationForDistance(distance: Float): Int {
        return when {
            distance < 100f -> DURATION_SHORT
            distance < 300f -> DURATION_MEDIUM
            else -> DURATION_LONG
        }
    }
    
    /**
     * Get staggered delay for list animations
     */
    fun getStaggeredDelay(index: Int, baseDelay: Int = DELAY_SHORT): Int {
        return baseDelay * index
    }
}

/**
 * Preview Utilities
 * 
 * Helper functions for creating consistent previews.
 */
object XiaomiPreviewUtils {
    
    /**
     * Common preview names
     */
    object PreviewNames {
        const val LIGHT_THEME = "Light Theme"
        const val DARK_THEME = "Dark Theme"
        const val LARGE_FONT = "Large Font"
        const val SMALL_SCREEN = "Small Screen"
        const val TABLET = "Tablet"
        const val LANDSCAPE = "Landscape"
    }
    
    /**
     * Common preview groups
     */
    object PreviewGroups {
        const val COMPONENTS = "Components"
        const val TEMPLATES = "Templates"
        const val FOUNDATION = "Foundation"
        const val THEMES = "Themes"
    }
    
    /**
     * Generate preview name with theme
     */
    fun previewName(component: String, theme: String = "Light"): String {
        return "$component - $theme"
    }
    
    /**
     * Generate preview name with state
     */
    fun previewNameWithState(component: String, state: String): String {
        return "$component - $state"
    }
}

/**
 * Validation Utilities
 * 
 * Helper functions for validating design system usage.
 */
object XiaomiValidationUtils {
    
    /**
     * Validate color contrast ratio
     */
    fun validateColorContrast(
        foreground: Color,
        background: Color,
        minRatio: Float = 4.5f
    ): Boolean {
        val contrastRatio = calculateContrastRatio(foreground, background)
        return contrastRatio >= minRatio
    }
    
    /**
     * Calculate color contrast ratio
     */
    private fun calculateContrastRatio(color1: Color, color2: Color): Float {
        val luminance1 = calculateLuminance(color1)
        val luminance2 = calculateLuminance(color2)
        
        val lighter = maxOf(luminance1, luminance2)
        val darker = minOf(luminance1, luminance2)
        
        return (lighter + 0.05f) / (darker + 0.05f)
    }
    
    /**
     * Calculate color luminance
     */
    private fun calculateLuminance(color: Color): Float {
        fun adjustGamma(value: Float): Float {
            return if (value <= 0.03928f) {
                value / 12.92f
            } else {
                pow((value + 0.055f) / 1.055f, 2.4f).toFloat()
            }
        }
        
        val r = adjustGamma(color.red)
        val g = adjustGamma(color.green)
        val b = adjustGamma(color.blue)
        
        return 0.2126f * r + 0.7152f * g + 0.0722f * b
    }
    
    /**
     * Validate spacing value
     */
    fun validateSpacing(value: Dp): Boolean {
        return value.value >= 0f && value.value % 4f == 0f // 4dp grid
    }
    
    /**
     * Validate component size
     */
    fun validateMinimumTouchTarget(size: Dp): Boolean {
        return size.value >= 48f // Minimum 48dp touch target
    }
}

/**
 * Debug Utilities
 * 
 * Helper functions for debugging and development.
 */
object XiaomiDebugUtils {
    
    /**
     * Log design system usage
     */
    fun logComponentUsage(componentName: String, properties: Map<String, Any> = emptyMap()) {
        // In a real implementation, this would log to analytics or debugging tools
        println("[Xiaomi UI Kit] Component: $componentName, Properties: $properties")
    }
    
    /**
     * Validate design system compliance
     */
    fun validateDesignSystemCompliance(
        componentName: String,
        usesDesignTokens: Boolean,
        followsNamingConvention: Boolean,
        hasPreview: Boolean
    ): Boolean {
        val isCompliant = usesDesignTokens && followsNamingConvention && hasPreview
        
        if (!isCompliant) {
            println("[Xiaomi UI Kit] Warning: $componentName is not fully compliant with design system")
            println("  - Uses design tokens: $usesDesignTokens")
            println("  - Follows naming convention: $followsNamingConvention")
            println("  - Has preview: $hasPreview")
        }
        
        return isCompliant
    }
    
    /**
     * Get design system metrics
     */
    fun getDesignSystemMetrics(): Map<String, Any> {
        return mapOf(
            "version" to "1.0.0",
            "componentsCount" to 10, // This would be dynamic
            "tokensCount" to 50, // This would be dynamic
            "templatesCount" to 5 // This would be dynamic
        )
    }
}

/**
 * Performance Utilities
 * 
 * Helper functions for optimizing performance.
 */
object XiaomiPerformanceUtils {
    
    /**
     * Check if expensive operations should be performed
     */
    fun shouldPerformExpensiveOperation(): Boolean {
        // In a real implementation, this could check device performance,
        // battery level, or user preferences
        return true
    }
    
    /**
     * Get recommended animation duration based on device performance
     */
    fun getRecommendedAnimationDuration(baseMs: Int): Int {
        // In a real implementation, this could adjust based on device capabilities
        return baseMs
    }
    
    /**
     * Check if high-quality graphics should be used
     */
    fun shouldUseHighQualityGraphics(): Boolean {
        // In a real implementation, this could check device capabilities
        return true
    }
}

/**
 * Accessibility Utilities
 * 
 * Helper functions for improving accessibility.
 */
object XiaomiAccessibilityUtils {
    
    /**
     * Generate content description for components
     */
    fun generateContentDescription(
        componentType: String,
        label: String? = null,
        state: String? = null
    ): String {
        val parts = mutableListOf<String>()
        
        if (label != null) {
            parts.add(label)
        }
        
        parts.add(componentType)
        
        if (state != null) {
            parts.add(state)
        }
        
        return parts.joinToString(", ")
    }
    
    /**
     * Check if text size is accessible
     */
    fun isTextSizeAccessible(textSizeSp: Float): Boolean {
        return textSizeSp >= 12f // Minimum readable text size
    }
    
    /**
     * Get semantic role for component
     */
    fun getSemanticRole(componentType: String): String {
        return when (componentType.lowercase()) {
            "button" -> "button"
            "card" -> "article"
            "textfield" -> "textbox"
            else -> "generic"
        }
    }
}