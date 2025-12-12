package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.CombinedLoadStates
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.state.UiState
import component.base.BaseColumn

@Composable
fun <T : Any> AutoPaginationScreen(
    items: LazyPagingItems<T>,
    uiState: UiState,
    onLoadStateUpdate: (CombinedLoadStates) -> Unit,
    content: @Composable () -> Unit
) {
    LaunchedEffect(items.loadState) {
        onLoadStateUpdate(items.loadState)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        content()
    }
}
