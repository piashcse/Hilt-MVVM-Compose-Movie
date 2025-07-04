package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.xiaomi.base.data.datasource.local.entity.FavoriteCategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the favorite categories table.
 */
@Dao
interface FavoriteCategoryDao : BaseDao<FavoriteCategoryEntity> {
    
    /**
     * Get all favorite categories as a Flow.
     *
     * @return a Flow of list of all favorite categories
     */
    @Query("SELECT * FROM favorite_categories ORDER BY addedDate DESC")
    fun getAllFavoriteCategories(): Flow<List<FavoriteCategoryEntity>>
    
    /**
     * Check if a category is marked as favorite.
     *
     * @param categoryId the ID of the category to check
     * @return true if the category is favorite, false otherwise
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_categories WHERE categoryId = :categoryId LIMIT 1)")
    suspend fun isFavorite(categoryId: Int): Boolean
    
    /**
     * Check if a category is marked as favorite as a Flow.
     *
     * @param categoryId the ID of the category to check
     * @return a Flow of boolean indicating if the category is favorite
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_categories WHERE categoryId = :categoryId LIMIT 1)")
    fun isFavoriteFlow(categoryId: Int): Flow<Boolean>
    
    /**
     * Get a favorite category by its ID.
     *
     * @param categoryId the ID of the category to retrieve
     * @return the favorite category entity or null if not found
     */
    @Query("SELECT * FROM favorite_categories WHERE categoryId = :categoryId LIMIT 1")
    suspend fun getFavoriteCategoryById(categoryId: Int): FavoriteCategoryEntity?
    
    /**
     * Get a favorite category by its ID as a Flow.
     *
     * @param categoryId the ID of the category to retrieve
     * @return a Flow of the favorite category entity or null if not found
     */
    @Query("SELECT * FROM favorite_categories WHERE categoryId = :categoryId LIMIT 1")
    fun getFavoriteById(categoryId: Int): Flow<FavoriteCategoryEntity?>
    
    /**
     * Delete a favorite category by its ID.
     *
     * @param categoryId the ID of the category to delete
     * @return the number of categories deleted
     */
    @Query("DELETE FROM favorite_categories WHERE categoryId = :categoryId")
    suspend fun deleteFavoriteById(categoryId: Int): Int
    
    /**
     * Delete all favorite categories.
     *
     * @return the number of categories deleted
     */
    @Query("DELETE FROM favorite_categories")
    suspend fun deleteAllFavorites(): Int
}