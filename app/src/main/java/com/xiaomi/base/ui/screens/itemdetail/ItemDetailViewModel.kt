package com.xiaomi.base.ui.screens.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.usecase.item.GetItemDetailsUseCase
import com.xiaomi.base.domain.usecase.item.ToggleFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the item detail screen.
 *
 * @property isLoading Whether data is currently being loaded.
 * @property error Error message if an error occurred during data loading.
 * @property item The item details to display.
 * @property isFavorite Whether the item is marked as favorite.
 */
data class ItemDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val item: Item? = null,
    val isFavorite: Boolean = false
)

/**
 * ViewModel for the item detail screen.
 *
 * @property getItemDetailsUseCase Use case for retrieving item details.
 * @property toggleFavoriteItemUseCase Use case for toggling favorite status.
 */
@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val toggleFavoriteItemUseCase: ToggleFavoriteItemUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ItemDetailUiState())
    val uiState: StateFlow<ItemDetailUiState> = _uiState.asStateFlow()
    
    private var currentItemId: Int? = null
    
    /**
     * Load item details for the given item ID.
     *
     * @param itemId The ID of the item to load.
     */
    fun loadItemDetails(itemId: Int) {
        if (currentItemId == itemId && _uiState.value.item != null) {
            // Already loaded this item
            return
        }
        
        currentItemId = itemId
        viewModelScope.launch {
            _uiState.value = ItemDetailUiState(isLoading = true)
            
            getItemDetailsUseCase(itemId)
                .catch { e ->
                    _uiState.value = ItemDetailUiState(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
                .collect { item ->
                    _uiState.value = ItemDetailUiState(
                        isLoading = false,
                        item = item,
                        isFavorite = item.isFavorite ?: false
                    )
                }
        }
    }
    
    /**
     * Toggle the favorite status of the current item.
     */
    fun toggleFavorite() {
        val currentItem = _uiState.value.item ?: return
        
        viewModelScope.launch {
            try {
                val newFavoriteStatus = toggleFavoriteItemUseCase(currentItem)
                _uiState.value = _uiState.value.copy(
                    isFavorite = newFavoriteStatus,
                    item = currentItem.copy(isFavorite = newFavoriteStatus)
                )
            } catch (e: Exception) {
                // Handle error - could show a snackbar or toast
                // For now, we'll just ignore the error
            }
        }
    }
}