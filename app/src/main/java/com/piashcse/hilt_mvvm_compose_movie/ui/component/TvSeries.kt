package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.utils.conditional
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState

@Composable
fun TvSeries(
    tvSeries: LazyPagingItems<TvSeriesItem>,
    genres: List<Genre>? = null,
    selectedName: Genre?,
    onclickGenre: (Genre?) -> Unit,
    onclick: (TvSeriesItem) -> Unit,
) {
    val progressBar = remember { mutableStateOf(false) }

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        // Display genres if provided
        genres?.let { DisplayGenres(it, selectedName, onclickGenre) }
        // Show loading indicator if progressBar is true
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        // Display TV series items using LazyVerticalGrid
        DisplayTvSeries(tvSeries, genres, onclick)
    }

    // Handle loading state for paging
    tvSeries.pagingLoadingState { progressBar.value = it }
}


@Composable
fun DisplayTvSeries(
    tvSeriesItems: LazyPagingItems<TvSeriesItem>,
    genres: List<Genre>?,
    onclick: (TvSeriesItem) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .conditional(genres == null) { padding(top = 8.dp) }
    ) {
        items(tvSeriesItems) { item ->
            item?.let {
                ItemView(
                    item = it,
                    itemImageUrlExtractor = { it.posterPath },
                    onclick = onclick
                )
            }
        }
    }
}