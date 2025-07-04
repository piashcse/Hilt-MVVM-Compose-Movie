package com.xiaomi.base.domain.usecase.item

import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.repository.ItemRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Use case for toggling an item's favorite status.
 */
class ToggleFavoriteItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    /**
     * Execute the use case to toggle an item's favorite status.
     *
     * @param item The item to toggle favorite status for.
     * @return True if the item is now a favorite, false otherwise.
     */
    suspend operator fun invoke(item: Item): Boolean {
        val isFavorite = itemRepository.isItemFavorite(item.id).first()
        
        return if (isFavorite) {
            itemRepository.removeFromFavorites(item.id)
            false
        } else {
            itemRepository.addToFavorites(item)
            true
        }
    }
}