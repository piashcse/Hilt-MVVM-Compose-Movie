package com.piashcse.hilt_mvvm_compose_movie.utils

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}

@Composable
fun <T : Any> pagingLoadingState(
    pagingItems: LazyPagingItems<T>,
    isLoaded: (pagingState: Boolean) -> Unit,
) {
    pagingItems.apply {
        when {
            // data is loading for first time
            loadState.refresh is LoadState.Loading -> {
                isLoaded(true)
            }
            // data is loading for second time or pagination
            loadState.append is LoadState.Loading -> {
                isLoaded(true)
            }
            loadState.refresh is LoadState.NotLoading -> {
                isLoaded(false)
            }
            loadState.append is LoadState.NotLoading -> {
                isLoaded(false)
            }
        }
    }
}