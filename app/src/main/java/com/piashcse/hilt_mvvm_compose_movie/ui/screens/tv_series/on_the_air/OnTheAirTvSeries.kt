package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.on_the_air

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.TvSeriesListScreen

@Composable
fun OnTheAirTvSeries(
    navController: Navigator,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<OnTheAirTvSeriesViewModel>()
    TvSeriesListScreen(viewModel, genres) { navController.navigate(TvSeriesDetailRoute(it.id)) }
}
