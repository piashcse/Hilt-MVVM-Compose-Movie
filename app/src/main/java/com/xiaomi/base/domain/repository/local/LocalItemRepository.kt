package com.xiaomi.base.domain.repository.local

import com.xiaomi.base.domain.model.Item
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing local favorite items.
 */
interface LocalItemRepository {
    /**
     * Get all favorite items.
     *
     * @return A Flow containing a list of favorite items.
     */
    fun getAllFavoriteItems(): Flow<List<Item>>
    
    /**
     * Check if an item is in favorites.
     *
     * @param itemId The ID of the item to check.
     * @return A Flow containing a boolean indicating if the item is in favorites.
     */
    fun isItemFavorite(itemId: Int): Flow<Boolean>
    
    /**
     * Get a favorite item by ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return A Flow containing the favorite item, or null if not found.
     */
    fun getFavoriteItemById(itemId: Int): Flow<Item?>
    
    /**
     * Add an item to favorites.
     *
     * @param item The item to add to favorites.
     */
    suspend fun addToFavorites(item: Item)
    
    /**
     * Remove an item from favorites.
     *
     * @param itemId The ID of the item to remove from favorites.
     */
    suspend fun removeFromFavorites(itemId: Int)
    
    /**
     * Toggle the favorite status of an item.
     *
     * @param item The item to toggle favorite status for.
     * @return A boolean indicating the new favorite status (true if added, false if removed).
     */
    suspend fun toggleFavorite(item: Item): Boolean
    
    /**
     * Remove all items from favorites.
     */
    suspend fun clearAllFavorites()
}