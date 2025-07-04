package com.xiaomi.base.ui.screens.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.usecase.item.GetPopularItemsUseCase
import com.xiaomi.base.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the popular items screen.
 *
 * @property getPopularItemsUseCase Use case for retrieving popular items.
 */
@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularItemsUseCase: GetPopularItemsUseCase
) : ViewModel() {
    
    /**
     * Flow of popular items with pagination support.
     */
    val popularItems: Flow<PagingData<Item>> = getPopularItemsUseCase()
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