package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.AutoPaginationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies

@Composable
fun PopularMovie(
    navController: Navigator,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<PopularMovieViewModel>()
    val moviesItems = viewModel.items.collectAsLazyPagingItems()
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