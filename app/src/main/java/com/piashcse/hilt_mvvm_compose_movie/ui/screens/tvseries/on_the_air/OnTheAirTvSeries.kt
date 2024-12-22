package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.on_the_air

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeriesItems

@Composable
fun OnTheAirTvSeries(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val onTheAirViewViewModel = hiltViewModel<OnTheAirTvSeriesViewModel>()
    TvSeriesItems (
        navController = navController,
        tvSeries = onTheAirViewViewModel.onTheAirTvSeries.collectAsLazyPagingItems(),
        genres = genres,
        selectedGenre = onTheAirViewViewModel.selectedGenre.value
    ){
        onTheAirViewViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            onTheAirViewViewModel.selectedGenre.value = it
        }
    }
}