package com.xiaomi.base.domain.model

import java.util.Date

/**
 * Universal model for storing user-generated content and data.
 * Can be used for metrics, preferences, custom content, etc.
 */
data class UserData(
    val id: Int,
    val userId: String,
    val dataType: String, // "metric", "content", "preference", "setting", etc.
    val value: String, // JSON string or simple value
    val unit: String? = null, // Optional unit for numeric values
    val timestamp: Date,
    val metadata: Map<String, Any> = emptyMap()
) 