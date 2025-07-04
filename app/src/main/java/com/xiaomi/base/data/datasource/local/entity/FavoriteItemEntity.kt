package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity representing a favorite item in the local database.
 */
@Entity(tableName = "favorite_items")
data class FavoriteItemEntity(
    /**
     * The unique identifier of the item.
     */
    @PrimaryKey
    val itemId: Int,
    
    /**
     * The title of the item.
     */
    val title: String,
    
    /**
     * The overview or description of the item.
     */
    val overview: String?,
    
    /**
     * The path to the poster image of the item.
     */
    val posterPath: String?,
    
    /**
     * The average rating of the item.
     */
    val voteAverage: Double,
    
    /**
     * The date when the item was added to favorites.
     */
    val addedDate: Date = Date()
)