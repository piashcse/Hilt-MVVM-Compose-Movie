package com.xiaomi.base.domain.usecase.category

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Category
import com.xiaomi.base.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a paginated list of categories.
 *
 * @property categoryRepository The repository for category operations.
 */
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    /**
     * Execute the use case to get a paginated list of categories.
     *
     * @return A Flow emitting PagingData of categories.
     */
    operator fun invoke(): Flow<PagingData<Category>> {
        return categoryRepository.getAllCategories()
    }
}