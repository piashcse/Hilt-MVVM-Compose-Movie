package com.xiaomi.base.domain.repository.local

import com.xiaomi.base.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing local favorite categories.
 */
interface LocalCategoryRepository {
    /**
     * Get all favorite categories.
     *
     * @return A Flow containing a list of favorite categories.
     */
    fun getAllFavoriteCategories(): Flow<List<Category>>
    
    /**
     * Check if a category is in favorites.
     *
     * @param categoryId The ID of the category to check.
     * @return A Flow containing a boolean indicating if the category is in favorites.
     */
    fun isCategoryFavorite(categoryId: Int): Flow<Boolean>
    
    /**
     * Get a favorite category by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A Flow containing the favorite category, or null if not found.
     */
    fun getFavoriteCategoryById(categoryId: Int): Flow<Category?>
    
    /**
     * Add a category to favorites.
     *
     * @param category The category to add to favorites.
     */
    suspend fun addToFavorites(category: Category)
    
    /**
     * Remove a category from favorites.
     *
     * @param categoryId The ID of the category to remove from favorites.
     */
    suspend fun removeFromFavorites(categoryId: Int)
    
    /**
     * Toggle the favorite status of a category.
     *
     * @param category The category to toggle favorite status for.
     * @return A boolean indicating the new favorite status (true if added, false if removed).
     */
    suspend fun toggleFavorite(category: Category): Boolean
    
    /**
     * Remove all categories from favorites.
     */
    suspend fun clearAllFavorites()
}