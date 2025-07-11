package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity for storing flexible user-generated data.
 * Can be used for metrics, content, preferences, etc.
 */
@Entity(tableName = "user_data")
data class UserDataEntity(
    /**
     * The unique identifier for this data entry.
     */
    @PrimaryKey
    val id: String,
    
    /**
     * The ID of the user who owns this data.
     */
    val userId: String,
    
    /**
     * The type of data (e.g., "health_metric", "photo_asset", "setting", etc.).
     */
    val dataType: String,
    
    /**
     * The actual data stored as JSON string for flexibility.
     */
    val data: String,
    
    /**
     * The timestamp when this data was created/updated.
     */
    val timestamp: Date = Date(),
    
    /**
     * Optional tags for data organization and filtering.
     */
    val tags: String? = null // Comma-separated tags
) 