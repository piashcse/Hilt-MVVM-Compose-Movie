package com.xiaomi.base.domain.usecase.item

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a paginated list of top rated items.
 *
 * @property itemRepository The repository for item operations.
 */
class GetTopRatedItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    /**
     * Execute the use case to get a paginated list of top rated items.
     *
     * @return A Flow emitting PagingData of top rated items.
     */
    operator fun invoke(): Flow<PagingData<Item>> {
        return itemRepository.getTopRatedItems()
    }
}