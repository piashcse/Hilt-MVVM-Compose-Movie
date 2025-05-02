package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.upcoming

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies


@Composable
fun UpcomingMovie(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val upComingViewModel = hiltViewModel<UpComingMovieViewModel>()
    Movies(
        navController = navController,
        moviesItems = upComingViewModel.upcomingMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedGenre = upComingViewModel.selectedGenre.value
    ) {
        upComingViewModel.filterData.value =  GenreId(it?.id.toString())
        it?.let {
            upComingViewModel.selectedGenre.value = it
        }
    }
}