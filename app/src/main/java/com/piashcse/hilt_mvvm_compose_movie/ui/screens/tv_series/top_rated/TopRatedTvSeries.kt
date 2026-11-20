package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.top_rated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.AutoPaginationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries

@Composable
fun TopRatedTvSeries(
    navController: Navigator,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<TopRatedTvSeriesViewModel>()
    val tvSeriesItems = viewModel.items.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    AutoPaginationScreen(
        items = tvSeriesItems,
        uiState = uiState,
        onLoadStateUpdate = viewModel::updateLoadState
    ) {
        TvSeries(
            tvSeries = tvSeriesItems,
            genres = genres,
            selectedName = viewModel.selectedGenre.value,
            onclickGenre = viewModel::onGenreSelected,
            onclick = {
                navController.navigate(TvSeriesDetailRoute(it.id))
            }
        )
    }
}