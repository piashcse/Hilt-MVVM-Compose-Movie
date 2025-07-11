package com.xiaomi.base.data.repository.local

import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.local.entity.FavoriteItemEntity
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.domain.repository.local.LocalItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

/**
 * Repository implementation for handling local item data operations.
 */
class LocalItemRepository @Inject constructor(
    private val favoriteItemDao: FavoriteItemDao
) : LocalItemRepository {
    
    /**
     * Get all favorite items as a Flow.
     *
     * @return a Flow of list of all favorite items as domain models
     */
    override fun getAllFavoriteItems(): Flow<List<Item>> {
        return favoriteItemDao.getAllFavoriteItems().map { entities ->
            entities.map { entity ->
                Item(
                    id = entity.itemId,
                    title = entity.title,
                    description = entity.overview,
                    imageUrl = entity.posterPath,
                    score = entity.voteAverage.toFloat(),
                    createdDate = entity.addedDate,
                    lastModified = Date(),
                    status = ItemStatus.ACTIVE,
                    isFavorite = true
                )
            }
        }
    }
    
    /**
     * Check if an item is marked as favorite.
     *
     * @param itemId the ID of the item to check
     * @return Flow of boolean indicating if the item is favorite
     */
    override fun isItemFavorite(itemId: Int): Flow<Boolean> {
        return favoriteItemDao.isFavoriteFlow(itemId)
    }
    
    /**
     * Get a favorite item by ID.
     *
     * @param itemId The ID of the item to retrieve.
     * @return A Flow containing the favorite item, or null if not found.
     */
    override fun getFavoriteItemById(itemId: Int): Flow<Item?> {
        return favoriteItemDao.getFavoriteById(itemId).map { entity ->
            entity?.let {
                Item(
                    id = it.itemId,
                    title = it.title,
                    description = it.overview,
                    imageUrl = it.posterPath,
                    score = it.voteAverage.toFloat(),
                    createdDate = it.addedDate,
                    lastModified = Date(),
                    status = ItemStatus.ACTIVE,
                    isFavorite = true
                )
            }
        }
    }
    
    /**
     * Helper method to check if an item is marked as favorite.
     *
     * @param itemId the ID of the item to check
     * @return true if the item is favorite, false otherwise
     */
    private suspend fun isFavorite(itemId: Int): Boolean {
        return favoriteItemDao.isFavorite(itemId)
    }
    
    /**
     * Add an item to favorites.
     *
     * @param item the item to add to favorites
     */
    override suspend fun addToFavorites(item: Item) {
        val favoriteEntity = FavoriteItemEntity(
            itemId = item.id,
            title = item.title,
            overview = item.description,
            posterPath = item.imageUrl,
            voteAverage = item.score.toDouble()
        )
        favoriteItemDao.insert(favoriteEntity)
    }
    
    /**
     * Remove an item from favorites.
     *
     * @param itemId the ID of the item to remove from favorites
     */
    override suspend fun removeFromFavorites(itemId: Int) {
        favoriteItemDao.deleteFavoriteById(itemId)
    }
    
    /**
     * Toggle the favorite status of an item.
     * If the item is already a favorite, it will be removed.
     * If the item is not a favorite, it will be added.
     *
     * @param item the item to toggle
     * @return true if the item is now a favorite, false otherwise
     */
    override suspend fun toggleFavorite(item: Item): Boolean {
        return if (isFavorite(item.id)) {
            removeFromFavorites(item.id)
            false
        } else {
            addToFavorites(item)
            true
        }
    }
    
    /**
     * Clear all favorite items.
     */
    override suspend fun clearAllFavorites() {
        favoriteItemDao.deleteAllFavorites()
    }
}