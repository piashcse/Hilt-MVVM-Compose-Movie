package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.xiaomi.base.data.datasource.local.entity.FavoriteItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the favorite items table.
 */
@Dao
interface FavoriteItemDao : BaseDao<FavoriteItemEntity> {
    
    /**
     * Get all favorite items as a Flow.
     *
     * @return a Flow of list of all favorite items
     */
    @Query("SELECT * FROM favorite_items ORDER BY addedDate DESC")
    fun getAllFavoriteItems(): Flow<List<FavoriteItemEntity>>
    
    /**
     * Check if an item is marked as favorite.
     *
     * @param itemId the ID of the item to check
     * @return true if the item is favorite, false otherwise
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_items WHERE itemId = :itemId LIMIT 1)")
    suspend fun isFavorite(itemId: Int): Boolean
    
    /**
     * Check if an item is marked as favorite as a Flow.
     *
     * @param itemId the ID of the item to check
     * @return a Flow of boolean indicating if the item is favorite
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_items WHERE itemId = :itemId LIMIT 1)")
    fun isFavoriteFlow(itemId: Int): Flow<Boolean>
    
    /**
     * Get a favorite item by its ID.
     *
     * @param itemId the ID of the item to retrieve
     * @return the favorite item entity or null if not found
     */
    @Query("SELECT * FROM favorite_items WHERE itemId = :itemId LIMIT 1")
    suspend fun getFavoriteItemById(itemId: Int): FavoriteItemEntity?
    
    /**
     * Get a favorite item by its ID as a Flow.
     *
     * @param itemId the ID of the item to retrieve
     * @return a Flow of the favorite item entity or null if not found
     */
    @Query("SELECT * FROM favorite_items WHERE itemId = :itemId LIMIT 1")
    fun getFavoriteById(itemId: Int): Flow<FavoriteItemEntity?>
    
    /**
     * Delete a favorite item by its ID.
     *
     * @param itemId the ID of the item to delete
     * @return the number of items deleted
     */
    @Query("DELETE FROM favorite_items WHERE itemId = :itemId")
    suspend fun deleteFavoriteById(itemId: Int): Int
    
    /**
     * Delete all favorite items.
     *
     * @return the number of items deleted
     */
    @Query("DELETE FROM favorite_items")
    suspend fun deleteAllFavorites(): Int
}