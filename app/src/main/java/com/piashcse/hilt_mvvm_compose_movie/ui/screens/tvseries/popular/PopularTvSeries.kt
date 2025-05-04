package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries

@Composable
fun PopularTvSeries(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<PopularTvSeriesViewModel>()
    TvSeries(
        tvSeries = viewModel.popularTvSeries.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = viewModel.selectedGenre.value,
        onclickGenre = {
            viewModel.filterData.value = GenreId(it?.id.toString())
            it?.let {
                viewModel.selectedGenre.value = it
            }
        },
        onclick = {
            navController.navigate(Screen.TvSeriesDetail.route.plus("/${it.id}"))
        })
}