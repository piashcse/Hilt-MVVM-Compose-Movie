package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.toprated

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MovieListScreen

@Composable
fun TopRatedMovie(
    navController: Navigator,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<TopRatedMovieViewModel>()
    MovieListScreen(viewModel, genres) {
        navController.navigate(MovieDetailRoute(it.id))
    }
}
