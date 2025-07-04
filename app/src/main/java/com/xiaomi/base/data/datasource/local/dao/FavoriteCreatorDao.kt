package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.xiaomi.base.data.datasource.local.entity.FavoriteCreatorEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the favorite creators table.
 */
@Dao
interface FavoriteCreatorDao : BaseDao<FavoriteCreatorEntity> {
    
    /**
     * Get all favorite creators as a Flow.
     *
     * @return a Flow of list of all favorite creators
     */
    @Query("SELECT * FROM favorite_creators ORDER BY addedDate DESC")
    fun getAllFavoriteCreators(): Flow<List<FavoriteCreatorEntity>>
    
    /**
     * Check if a creator is marked as favorite.
     *
     * @param creatorId the ID of the creator to check
     * @return true if the creator is favorite, false otherwise
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_creators WHERE creatorId = :creatorId LIMIT 1)")
    suspend fun isFavorite(creatorId: Int): Boolean
    
    /**
     * Check if a creator is marked as favorite as a Flow.
     *
     * @param creatorId the ID of the creator to check
     * @return Flow of boolean indicating if the creator is favorite
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_creators WHERE creatorId = :creatorId LIMIT 1)")
    fun isFavoriteFlow(creatorId: Int): Flow<Boolean>
    
    /**
     * Get a favorite creator by its ID.
     *
     * @param creatorId the ID of the creator to retrieve
     * @return the favorite creator entity or null if not found
     */
    @Query("SELECT * FROM favorite_creators WHERE creatorId = :creatorId LIMIT 1")
    suspend fun getFavoriteCreatorById(creatorId: Int): FavoriteCreatorEntity?
    
    /**
     * Get a favorite creator by its ID as a Flow.
     *
     * @param creatorId the ID of the creator to retrieve
     * @return Flow of the favorite creator entity or null if not found
     */
    @Query("SELECT * FROM favorite_creators WHERE creatorId = :creatorId LIMIT 1")
    fun getFavoriteById(creatorId: Int): Flow<FavoriteCreatorEntity?>
    
    /**
     * Delete a favorite creator by its ID.
     *
     * @param creatorId the ID of the creator to delete
     * @return the number of creators deleted
     */
    @Query("DELETE FROM favorite_creators WHERE creatorId = :creatorId")
    suspend fun deleteFavoriteById(creatorId: Int): Int
    
    /**
     * Delete all favorite creators.
     *
     * @return the number of creators deleted
     */
    @Query("DELETE FROM favorite_creators")
    suspend fun deleteAllFavorites(): Int
}