package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.airing_today

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.AutoPaginationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries

@Composable
fun AiringTodayTvSeries(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<AiringTodayTvSeriesViewModel>()
    val tvSeriesItems = viewModel.airingTodayTvSeries.collectAsLazyPagingItems()
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