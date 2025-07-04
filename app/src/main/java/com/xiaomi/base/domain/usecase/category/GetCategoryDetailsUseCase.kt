package com.xiaomi.base.domain.usecase.category

import com.xiaomi.base.domain.model.Category
import com.xiaomi.base.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving details of a specific category.
 *
 * @property categoryRepository The repository for category operations.
 */
class GetCategoryDetailsUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    /**
     * Execute the use case to get details of a specific category.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A Flow emitting the category details.
     */
    suspend operator fun invoke(categoryId: Int): Flow<Category> {
        return categoryRepository.getCategoryDetails(categoryId)
    }
}