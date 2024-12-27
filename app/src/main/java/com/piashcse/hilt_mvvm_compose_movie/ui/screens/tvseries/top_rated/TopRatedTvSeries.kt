package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.top_rated

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries

@Composable
fun TopRatedTvSeries(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val topRatedViewViewModel = hiltViewModel<TopRatedTvSeriesViewModel>()
    TvSeries (
        navController = navController,
        tvSeries = topRatedViewViewModel.topRatedTvSeries.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = topRatedViewViewModel.selectedGenre.value
    ){
        topRatedViewViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            topRatedViewViewModel.selectedGenre.value = it
        }
    }
}