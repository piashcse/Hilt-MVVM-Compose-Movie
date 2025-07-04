package com.xiaomi.base.ui.screens.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.usecase.item.GetTopRatedItemsUseCase
import com.xiaomi.base.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the top rated items screen.
 *
 * @property getTopRatedItemsUseCase Use case for retrieving top rated items.
 */
@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val getTopRatedItemsUseCase: GetTopRatedItemsUseCase
) : ViewModel() {
    
    /**
     * Flow of top rated items with pagination support.
     */
    val topRatedItems: Flow<PagingData<Item>> = getTopRatedItemsUseCase()
        .cachedIn(viewModelScope)
    
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