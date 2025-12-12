package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.toprated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.AutoPaginationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies

@Composable
fun TopRatedMovie(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<TopRatedMovieViewModel>()
    val moviesItems = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    AutoPaginationScreen(
        items = moviesItems,
        uiState = uiState,
        onLoadStateUpdate = viewModel::updateLoadState
    ) {
        Movies(
            moviesItems = moviesItems,
            genres = genres,
            selectedGenre = viewModel.selectedGenre.value,
            onclickGenre = viewModel::onGenreSelected,
            onclick = {
                navController.navigate(MovieDetailRoute(it.id))
            }
        )
    }
}