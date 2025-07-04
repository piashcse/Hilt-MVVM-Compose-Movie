package com.xiaomi.base.data.repository.local

import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.entity.FavoriteCategoryEntity
import com.xiaomi.base.domain.model.Category
import com.xiaomi.base.domain.repository.local.LocalCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

/**
 * Repository implementation for handling local category data operations.
 */
class LocalCategoryRepository @Inject constructor(
    private val favoriteCategoryDao: FavoriteCategoryDao
) : LocalCategoryRepository {
    
    /**
     * Get all favorite categories as a Flow.
     *
     * @return a Flow of list of all favorite categories as domain models
     */
    override fun getAllFavoriteCategories(): Flow<List<Category>> {
        return favoriteCategoryDao.getAllFavoriteCategories().map { entities ->
            entities.map { entity ->
                Category(
                    id = entity.categoryId,
                    name = entity.name,
                    description = entity.description,
                    imageUrl = entity.imagePath ?: ""
                )
            }
        }
    }
    
    /**
     * Check if a category is marked as favorite.
     *
     * @param categoryId the ID of the category to check
     * @return Flow of boolean indicating if the category is favorite
     */
    override fun isCategoryFavorite(categoryId: Int): Flow<Boolean> {
        return favoriteCategoryDao.isFavoriteFlow(categoryId)
    }
    
    /**
     * Get a favorite category by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A Flow containing the favorite category, or null if not found.
     */
    override fun getFavoriteCategoryById(categoryId: Int): Flow<Category?> {
        return favoriteCategoryDao.getFavoriteById(categoryId).map { entity ->
            entity?.let {
                Category(
                    id = it.categoryId,
                    name = it.name,
                    description = it.description,
                    imageUrl = it.imagePath ?: ""
                )
            }
        }
    }
    
    /**
     * Helper method to check if a category is marked as favorite.
     *
     * @param categoryId the ID of the category to check
     * @return true if the category is favorite, false otherwise
     */
    private suspend fun isFavorite(categoryId: Int): Boolean {
        return favoriteCategoryDao.isFavorite(categoryId)
    }
    
    /**
     * Add a category to favorites.
     *
     * @param category the category to add to favorites
     */
    override suspend fun addToFavorites(category: Category) {
        val favoriteEntity = FavoriteCategoryEntity(
            categoryId = category.id,
            name = category.name,
            description = category.description,
            imagePath = category.imageUrl
        )
        favoriteCategoryDao.insert(favoriteEntity)
    }
    
    /**
     * Remove a category from favorites.
     *
     * @param categoryId the ID of the category to remove from favorites
     */
    override suspend fun removeFromFavorites(categoryId: Int) {
        favoriteCategoryDao.deleteFavoriteById(categoryId)
    }
    
    /**
     * Toggle the favorite status of a category.
     * If the category is already a favorite, it will be removed.
     * If the category is not a favorite, it will be added.
     *
     * @param category the category to toggle
     * @return true if the category is now a favorite, false otherwise
     */
    override suspend fun toggleFavorite(category: Category): Boolean {
        return if (isFavorite(category.id)) {
            removeFromFavorites(category.id)
            false
        } else {
            addToFavorites(category)
            true
        }
    }
    
    /**
     * Clear all favorite categories.
     */
    override suspend fun clearAllFavorites() {
        favoriteCategoryDao.deleteAllFavorites()
    }
}