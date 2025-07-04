package com.xiaomi.base.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entity representing a favorite category in the local database.
 */
@Entity(tableName = "favorite_categories")
data class FavoriteCategoryEntity(
    /**
     * The unique identifier of the category.
     */
    @PrimaryKey
    val categoryId: Int,
    
    /**
     * The name of the category.
     */
    val name: String,
    
    /**
     * The description of the category.
     */
    val description: String?,
    
    /**
     * The path to the image representing the category.
     */
    val imagePath: String?,
    
    /**
     * The date when the category was added to favorites.
     */
    val addedDate: Date = Date()
)