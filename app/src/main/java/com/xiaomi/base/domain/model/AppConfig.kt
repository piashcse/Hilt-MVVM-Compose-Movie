package com.xiaomi.base.domain.model

/**
 * Universal model for app-wide configuration management.
 * Supports both system and user-editable settings.
 */
data class AppConfig(
    val key: String,
    val value: String,
    val category: ConfigCategory,
    val isUserEditable: Boolean = false,
    val description: String? = null
)

/**
 * Categories for organizing app configuration
 */
enum class ConfigCategory {
    UI,                // User interface settings
    BEHAVIOR,          // App behavior configurations  
    INTEGRATION,       // Third-party integrations
    USER_PREFERENCE    // User-specific preferences
} 