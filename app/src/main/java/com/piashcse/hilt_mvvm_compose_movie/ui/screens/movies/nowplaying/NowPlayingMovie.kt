package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.nowplaying

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies
import component.base.BaseColumn

@Composable
fun NowPlayingMovie(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewModel = hiltViewModel<NowPlayingMovieViewModel>()
    val moviesItems = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    // ✅ Observe loadState reactively
    LaunchedEffect(moviesItems.loadState) {
        viewModel.updateLoadState(moviesItems.loadState)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        Movies(
            moviesItems = moviesItems,
            genres = genres,
            selectedGenre = viewModel.selectedGenre.value,
            onclickGenre = viewModel::onGenreSelected,
            onclick = {
                navController.navigate(Screen.MovieDetail.route + "/${it.id}")
            }
        )
    }
}