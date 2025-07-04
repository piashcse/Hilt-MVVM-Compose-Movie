package com.xiaomi.base.domain.repository.local

import com.xiaomi.base.domain.model.Creator
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing local favorite creators.
 */
interface LocalCreatorRepository {
    /**
     * Get all favorite creators.
     *
     * @return A Flow containing a list of favorite creators.
     */
    fun getAllFavoriteCreators(): Flow<List<Creator>>
    
    /**
     * Check if a creator is in favorites.
     *
     * @param creatorId The ID of the creator to check.
     * @return A Flow containing a boolean indicating if the creator is in favorites.
     */
    fun isCreatorFavorite(creatorId: Int): Flow<Boolean>
    
    /**
     * Get a favorite creator by ID.
     *
     * @param creatorId The ID of the creator to retrieve.
     * @return A Flow containing the favorite creator, or null if not found.
     */
    fun getFavoriteCreatorById(creatorId: Int): Flow<Creator?>
    
    /**
     * Add a creator to favorites.
     *
     * @param creator The creator to add to favorites.
     */
    suspend fun addToFavorites(creator: Creator)
    
    /**
     * Remove a creator from favorites.
     *
     * @param creatorId The ID of the creator to remove from favorites.
     */
    suspend fun removeFromFavorites(creatorId: Int)
    
    /**
     * Toggle the favorite status of a creator.
     *
     * @param creator The creator to toggle favorite status for.
     * @return A boolean indicating the new favorite status (true if added, false if removed).
     */
    suspend fun toggleFavorite(creator: Creator): Boolean
    
    /**
     * Remove all creators from favorites.
     */
    suspend fun clearAllFavorites()
}