package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeriesItems

@Composable
fun PopularTvSeries(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val popularViewViewModel = hiltViewModel<PopularTvSeriesViewModel>()
    TvSeriesItems (
        navController = navController,
        tvSeries = popularViewViewModel.popularTvSeries.collectAsLazyPagingItems(),
        genres = genres,
        selectedGenre = popularViewViewModel.selectedGenre.value
    ){
        popularViewViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            popularViewViewModel.selectedGenre.value = it
        }
    }
}