package com.xiaomi.base.ui.kit.components

/**
 * Xiaomi Components - Organized Component Access
 * 
 * This file provides organized access to all UI components in the Xiaomi Base UI Kit.
 * Components are grouped by category for easy discovery and usage.
 * 
 * Inspired by ComposeX organization patterns for better developer experience.
 */

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Actions - Interactive Components  
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiPrimaryButton
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiSecondaryButton
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiTertiaryButton

// Containment - Layout Components
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiCard
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiElevatedCard
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiOutlinedCard
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiClickableCard

/**
 * Actions Module - Interactive Components
 * 
 * Contains all components that trigger actions or user interactions.
 * These components are typically used for primary user actions.
 */
object XiaomiActions {
    
    /**
     * Button Components
     * 
     * Various button styles following Material Design 3 guidelines
     * with Xiaomi design tokens applied.
     */
    object Buttons {
        
        /**
         * Primary filled button - Use for main actions
         */
        @Composable
        fun Primary(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            content: @Composable RowScope.() -> Unit
        ) = XiaomiPrimaryButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            content = content
        )
        
        /**
         * Outlined button - Use for secondary actions
         */
        @Composable
        fun Outlined(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            content: @Composable RowScope.() -> Unit
        ) = XiaomiSecondaryButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            content = content
        )
        
        /**
         * Text button - Use for tertiary actions
         */
        @Composable
        fun Text(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            content: @Composable RowScope.() -> Unit
        ) = XiaomiTertiaryButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            content = content
        )
        
        /**
         * Tonal button - Use for alternative primary actions
         */
        @Composable
        fun Tonal(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            content: @Composable RowScope.() -> Unit
        ) = XiaomiPrimaryButton( // Sử dụng PrimaryButton cho Tonal
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            content = content
        )
        
        /**
         * Get all available button variants
         */
        fun getAllVariants() = listOf(
            "Primary",
            "Outlined", 
            "Text",
            "Tonal"
        )
    }
    
    // Future: FABs, Chips, etc.
    // object FABs { ... }
    // object Chips { ... }
}

/**
 * Communication Module - Information Display Components
 * 
 * Contains components that communicate information to users
 * without requiring direct interaction.
 */
object XiaomiCommunication {
    
    // Future implementations
    // object Badges { ... }
    // object Progress { ... }
    // object Snackbars { ... }
    
    /**
     * Get all available communication component categories
     */
    fun getAvailableCategories() = listOf(
        "Badges",
        "Progress Indicators", 
        "Snackbars",
        "Tooltips"
    )
}

/**
 * Containment Module - Layout and Grouping Components
 * 
 * Contains components that group, organize, and contain other content.
 * These components provide structure and hierarchy to the UI.
 */
object XiaomiContainment {
    
    /**
     * Card Components
     * 
     * Various card styles for grouping related content
     * with different elevation and styling options.
     */
    object Cards {
        
        /**
         * Basic card - Standard container with subtle elevation
         */
        @Composable
        fun Basic(
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiCard(
            modifier = modifier,
            content = content
        )
        
        /**
         * Elevated card - Higher elevation for emphasis
         */
        @Composable
        fun Elevated(
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiElevatedCard(
            modifier = modifier,
            content = content
        )
        
        /**
         * Outlined card - Bordered card without elevation
         */
        @Composable
        fun Outlined(
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiOutlinedCard(
            modifier = modifier,
            content = content
        )
        
        /**
         * Clickable card - Interactive card for navigation
         */
        @Composable
        fun Clickable(
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiClickableCard(
            onClick = onClick,
            modifier = modifier,
            content = content
        )
        
        /**
         * Product card - Specialized for product display
         */
        @Composable
        fun Product(
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiCard( // Sử dụng basic card thay thế
            modifier = modifier,
            content = content
        )
        
        /**
         * Feature card - Specialized for feature highlights
         */
        @Composable
        fun Feature(
            modifier: Modifier = Modifier,
            content: @Composable ColumnScope.() -> Unit
        ) = XiaomiElevatedCard( // Sử dụng elevated card thay thế
            modifier = modifier,
            content = content
        )
        
        /**
         * Get all available card variants
         */
        fun getAllVariants() = listOf(
            "Basic",
            "Elevated",
            "Outlined",
            "Clickable",
            "Product",
            "Feature"
        )
    }
    
    // Future: Lists, Dividers, etc.
    // object Lists { ... }
    // object Dividers { ... }
}

/**
 * Navigation Module - Navigation Components
 * 
 * Contains components that help users navigate through the application.
 */
object XiaomiNavigation {
    
    // Future implementations
    // object AppBars { ... }
    // object BottomNavigation { ... }
    // object Tabs { ... }
    // object Drawer { ... }
    // object Rail { ... }
    
    /**
     * Get all available navigation component categories
     */
    fun getAvailableCategories() = listOf(
        "App Bars",
        "Bottom Navigation",
        "Tabs",
        "Navigation Drawer",
        "Navigation Rail"
    )
}

/**
 * Selection Module - Input and Selection Components
 * 
 * Contains components for user input, selection, and form interactions.
 */
object XiaomiSelection {
    
    // Future implementations
    // object CheckBoxes { ... }
    // object RadioButtons { ... }
    // object Switches { ... }
    // object Sliders { ... }
    
    /**
     * Get all available selection component categories
     */
    fun getAvailableCategories() = listOf(
        "Checkboxes",
        "Radio Buttons",
        "Switches",
        "Sliders",
        "Toggles"
    )
}

/**
 * Text Inputs Module - Text Input Components
 * 
 * Contains components for text input and forms.
 */
object XiaomiTextInputs {
    
    // Future implementations
    // object TextFields { ... }
    // object TextAreas { ... }
    // object SearchBars { ... }
    
    /**
     * Get all available text input component categories
     */
    fun getAvailableCategories() = listOf(
        "Text Fields",
        "Text Areas", 
        "Search Bars",
        "Date Pickers",
        "Time Pickers"
    )
}

/**
 * Specialized Module - Specialized Components
 * 
 * Contains highly specialized components for specific use cases.
 */
object XiaomiSpecialized {
    
    // Future implementations
    // object Charts { ... }
    // object Maps { ... }
    // object Media { ... }
    
    /**
     * Get all available specialized component categories
     */
    fun getAvailableCategories() = listOf(
        "Charts",
        "Maps",
        "Media Players",
        "Calendars",
        "File Pickers"
    )
}

/**
 * Component Registry - Master Registry
 * 
 * Provides a centralized registry of all available components
 * with metadata and discovery capabilities.
 */
object XiaomiComponentRegistry {
    
    /**
     * Get all available component modules
     */
    fun getAllModules() = mapOf(
        "Actions" to XiaomiActions,
        "Communication" to XiaomiCommunication,
        "Containment" to XiaomiContainment,
        "Navigation" to XiaomiNavigation,
        "Selection" to XiaomiSelection,
        "TextInputs" to XiaomiTextInputs,
        "Specialized" to XiaomiSpecialized
    )
    
    /**
     * Get component count by module
     */
    fun getComponentCounts() = mapOf(
        "Actions" to 4, // 4 button variants
        "Communication" to 0, // Future
        "Containment" to 6, // 6 card variants
        "Navigation" to 0, // Future
        "Selection" to 0, // Future
        "TextInputs" to 0, // Future
        "Specialized" to 0 // Future
    )
    
    /**
     * Check if a component exists in the registry
     */
    fun hasComponent(module: String, category: String, component: String): Boolean {
        return when (module) {
            "Actions" -> when (category) {
                "Buttons" -> component in listOf("Primary", "Outlined", "Text", "Tonal")
                else -> false
            }
            "Containment" -> when (category) {
                "Cards" -> component in listOf("Basic", "Elevated", "Outlined", "Clickable", "Product", "Feature")
                else -> false
            }
            else -> false
        }
    }
}

/**
 * Quick Access - Commonly Used Components
 * 
 * Provides quick access to the most commonly used components
 * for faster development workflow.
 */
object XiaomiQuickAccess {
    
    // Most common button
    val Button = XiaomiActions.Buttons::Primary
    
    // Most common card
    val Card = XiaomiContainment.Cards::Basic
    
    /**
     * Get quick access components
     */
    fun getQuickComponents() = mapOf(
        "Button" to Button,
        "Card" to Card
    )
}