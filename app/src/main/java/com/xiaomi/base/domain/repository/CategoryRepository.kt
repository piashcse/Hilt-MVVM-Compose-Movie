package com.xiaomi.base.domain.repository

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing categories.
 */
interface CategoryRepository {
    /**
     * Get all categories as a paginated list.
     *
     * @return A Flow of PagingData containing categories.
     */
    fun getAllCategories(): Flow<PagingData<Category>>

    /**
     * Get category details by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A Flow containing the category details.
     */
    suspend fun getCategoryDetails(categoryId: Int): Flow<Category>

    /**
     * Get favorite categories as a paginated list.
     *
     * @return A Flow of PagingData containing favorite categories.
     */
    fun getFavoriteCategories(): Flow<PagingData<Category>>

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
     * Check if a category is in favorites.
     *
     * @param categoryId The ID of the category to check.
     * @return A Flow containing a boolean indicating if the category is in favorites.
     */
    fun isCategoryFavorite(categoryId: Int): Flow<Boolean>
}