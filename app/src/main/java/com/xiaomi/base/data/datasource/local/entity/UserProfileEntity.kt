package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity representing a user profile in the local database.
 * Universal entity that can be used across different app types.
 */
@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    /**
     * The unique identifier of the user.
     */
    @PrimaryKey
    val userId: String,
    
    /**
     * The display name of the user.
     */
    val displayName: String,
    
    /**
     * The email address of the user.
     */
    val email: String?,
    
    /**
     * The path to the profile image of the user.
     */
    val profileImagePath: String?,
    
    /**
     * User preferences stored as JSON string.
     */
    val preferences: String = "{}",
    
    /**
     * The date when the profile was created.
     */
    val createdDate: Date = Date(),
    
    /**
     * The date when the user was last active.
     */
    val lastActiveDate: Date = Date()
) 