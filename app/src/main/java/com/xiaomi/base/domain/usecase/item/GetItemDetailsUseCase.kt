package com.xiaomi.base.domain.usecase.item

import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving item details.
 */
class GetItemDetailsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    /**
     * Execute the use case to get item details.
     *
     * @param itemId The ID of the item to retrieve details for.
     * @return A Flow containing the item details.
     */
    suspend operator fun invoke(itemId: Int): Flow<Item> {
        return itemRepository.getItemDetails(itemId)
    }
}