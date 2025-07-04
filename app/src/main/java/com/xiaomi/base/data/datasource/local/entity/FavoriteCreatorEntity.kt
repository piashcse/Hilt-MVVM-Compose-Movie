package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity representing a favorite creator in the local database.
 */
@Entity(tableName = "favorite_creators")
data class FavoriteCreatorEntity(
    /**
     * The unique identifier of the creator.
     */
    @PrimaryKey
    val creatorId: Int,
    
    /**
     * The name of the creator.
     */
    val name: String,
    
    /**
     * The path to the profile image of the creator.
     */
    val profilePath: String?,
    
    /**
     * The biography of the creator.
     */
    val biography: String?,
    
    /**
     * The department the creator is known for (e.g., Acting, Directing).
     */
    val knownForDepartment: String?,
    
    /**
     * The date when the creator was added to favorites.
     */
    val addedDate: Date = Date()
)