package com.xiaomi.base.domain.usecase.item

import androidx.paging.PagingData
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving popular items.
 */
class GetPopularItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    /**
     * Execute the use case to get popular items.
     *
     * @return A Flow of PagingData containing popular items.
     */
    operator fun invoke(): Flow<PagingData<Item>> {
        return itemRepository.getPopularItems()
    }
}