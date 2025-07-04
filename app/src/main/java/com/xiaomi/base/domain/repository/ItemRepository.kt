package com.xiaomi.base.domain.repository

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Item
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing items.
 */
interface ItemRepository {
    /**
     * Get a paginated list of items by category.
     *
     * @param categoryId The category ID to filter items by.
     * @return A Flow of PagingData containing items.
     */
    fun getItemsByCategory(categoryId: Int): Flow<PagingData<Item>>

    /**
     * Get popular items as a paginated list.
     *
     * @return A Flow of PagingData containing popular items.
     */
    fun getPopularItems(): Flow<PagingData<Item>>

    /**
     * Get top rated items as a paginated list.
     *
     * @return A Flow of PagingData containing top rated items.
     */
    fun getTopRatedItems(): Flow<PagingData<Item>>

    /**
     * Get upcoming items as a paginated list.
     *
     * @return A Flow of PagingData containing upcoming items.
     */
    fun getUpcomingItems(): Flow<PagingData<Item>>

    /**
     * Get trending items as a paginated list.
     *
     * @return A Flow of PagingData containing trending items.
     */
    fun getTrendingItems(): Flow<PagingData<Item>>

    /**
     * Get item details by ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return A Flow containing the item details.
     */
    suspend fun getItemDetails(itemId: Int): Flow<Item>

    /**
     * Get similar items for a specific item.
     *
     * @param itemId The ID of the item to find similar items for.
     * @return A Flow of PagingData containing similar items.
     */
    fun getSimilarItems(itemId: Int): Flow<PagingData<Item>>

    /**
     * Get favorite items as a paginated list.
     *
     * @return A Flow of PagingData containing favorite items.
     */
    fun getFavoriteItems(): Flow<PagingData<Item>>

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
     * Check if an item is in favorites.
     *
     * @param itemId The ID of the item to check.
     * @return A Flow containing a boolean indicating if the item is in favorites.
     */
    fun isItemFavorite(itemId: Int): Flow<Boolean>
}