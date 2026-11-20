package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.airing_today

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.TvSeriesListScreen

@Composable
fun AiringTodayTvSeries(
    navController: Navigator,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<AiringTodayTvSeriesViewModel>()
    TvSeriesListScreen(viewModel, genres) {
        navController.navigate(TvSeriesDetailRoute(it.id))
    }
}
