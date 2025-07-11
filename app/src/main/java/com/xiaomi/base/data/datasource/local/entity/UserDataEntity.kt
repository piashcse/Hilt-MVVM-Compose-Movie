package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.xiaomi.base.domain.model.UserData
import java.util.Date

/**
 * Type converters for UserDataEntity
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromMetadata(value: String?): Map<String, Any> {
        if (value.isNullOrEmpty()) return emptyMap()
        // Simple JSON-like parsing for basic types
        return try {
            value.split(",").associate { 
                val parts = it.split(":")
                parts[0].trim() to (parts.getOrNull(1)?.trim() ?: "") as Any
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    @TypeConverter
    fun metadataToString(metadata: Map<String, Any>): String {
        return metadata.entries.joinToString(",") { "${it.key}:${it.value}" }
    }
}

/**
 * Entity for storing flexible user-generated data.
 * Can be used for metrics, content, preferences, etc.
 */
@Entity(tableName = "user_data")
@TypeConverters(Converters::class)
data class UserDataEntity(
    /**
     * The unique identifier for this data entry.
     */
    @PrimaryKey
    val id: Int,
    
    /**
     * The ID of the user who owns this data.
     */
    val userId: String,
    
    /**
     * The type of data (e.g., "metric", "content", "preference", "setting", etc.).
     */
    val dataType: String,
    
    /**
     * The actual data value stored as string.
     */
    val value: String,
    
    /**
     * Optional unit for numeric values.
     */
    val unit: String? = null,
    
    /**
     * The timestamp when this data was created/updated.
     */
    val timestamp: Date,
    
    /**
     * Optional metadata stored as key-value pairs.
     */
    val metadata: Map<String, Any> = emptyMap()
)

/**
 * Convert UserDataEntity to UserData domain model.
 */
fun UserDataEntity.toUserData(): UserData {
    return UserData(
        id = this.id,
        userId = this.userId,
        dataType = this.dataType,
        value = this.value,
        unit = this.unit,
        timestamp = this.timestamp,
        metadata = this.metadata
    )
}

/**
 * Convert UserData domain model to UserDataEntity.
 */
fun UserData.toUserDataEntity(): UserDataEntity {
    return UserDataEntity(
        id = this.id,
        userId = this.userId,
        dataType = this.dataType,
        value = this.value,
        unit = this.unit,
        timestamp = this.timestamp,
        metadata = this.metadata
    )
}