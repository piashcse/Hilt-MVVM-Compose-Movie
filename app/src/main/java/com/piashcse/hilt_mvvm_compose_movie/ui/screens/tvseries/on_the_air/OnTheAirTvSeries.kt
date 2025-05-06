package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.on_the_air

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries
import component.base.BaseColumn

@Composable
fun OnTheAirTvSeries(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<OnTheAirTvSeriesViewModel>()
    val tvSeriesItems = viewModel.onTheAirTvSeries.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(tvSeriesItems.loadState) {
        viewModel.updateLoadState(tvSeriesItems.loadState)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        TvSeries(
            tvSeries = tvSeriesItems,
            genres = genres,
            selectedName = viewModel.selectedGenre.value,
            onclickGenre = viewModel::onGenreSelected,
            onclick = {
                navController.navigate(Screen.TvSeriesDetail.route.plus("/${it.id}"))
            }
        )
    }
}