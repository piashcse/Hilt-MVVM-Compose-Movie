package com.xiaomi.base.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.usecase.item.GetPopularItemsUseCase
import com.xiaomi.base.domain.usecase.item.GetTopRatedItemsUseCase
import com.xiaomi.base.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the home screen.
 *
 * @property isLoading Whether data is currently being loaded.
 * @property error Error message if an error occurred during data loading.
 * @property trendingItems List of trending items.
 * @property popularItems List of popular items.
 * @property topRatedItems List of top rated items.
 * @property upcomingItems List of upcoming items.
 */
data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val trendingItems: List<Item> = emptyList(),
    val popularItems: List<Item> = emptyList(),
    val topRatedItems: List<Item> = emptyList(),
    val upcomingItems: List<Item> = emptyList()
)

/**
 * ViewModel for the home screen.
 *
 * @property getPopularItemsUseCase Use case for retrieving popular items.
 * @property getTopRatedItemsUseCase Use case for retrieving top rated items.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularItemsUseCase: GetPopularItemsUseCase,
    private val getTopRatedItemsUseCase: GetTopRatedItemsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    /**
     * Flow of popular items with pagination support.
     */
    val popularItems: Flow<PagingData<Item>> = getPopularItemsUseCase()
        .cachedIn(viewModelScope)
    
    /**
     * Flow of top rated items with pagination support.
     */
    val topRatedItems: Flow<PagingData<Item>> = getTopRatedItemsUseCase()
        .cachedIn(viewModelScope)
    
    /**
     * Load data for the home screen.
     */
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load sample data for now
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    trendingItems = emptyList(),
                    popularItems = emptyList(),
                    topRatedItems = emptyList(),
                    upcomingItems = emptyList()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
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