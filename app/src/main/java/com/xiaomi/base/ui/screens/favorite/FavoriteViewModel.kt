package com.xiaomi.base.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.repository.ItemRepository
import com.xiaomi.base.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the favorite screen.
 *
 * @property isLoading Whether data is currently being loaded.
 * @property error Error message if an error occurred during data loading.
 */
data class FavoriteUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the favorite items screen.
 *
 * @property itemRepository Repository for item operations.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()
    
    /**
     * Flow of favorite items with pagination support.
     */
    val favoriteItems: Flow<PagingData<Item>> = itemRepository.getFavoriteItems()
        .cachedIn(viewModelScope)
    
    /**
     * Load favorite items.
     */
    fun loadFavorites() {
        // PagingData handles loading automatically
        _uiState.value = FavoriteUiState(isLoading = false)
    }
    
    /**
     * Navigate to the item detail screen.
     *
     * @param navController The navigation controller to use for navigation.
     * @param itemId The ID of the item to navigate to.
     */
    fun navigateToItemDetail(navController: NavController, itemId: Int) {
        navController.navigate(Screen.ItemDetail.createRoute(itemId))
    }
}