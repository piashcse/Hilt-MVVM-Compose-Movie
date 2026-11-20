package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.AutoPaginationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Celebrities
import com.piashcse.hilt_mvvm_compose_movie.ui.component.PosterGrid

@Composable
fun MovieListScreen(
    viewModel: MediaListViewModel<MovieItem>,
    genres: List<Genre>?,
    onMovieClick: (MovieItem) -> Unit,
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    AutoPaginationScreen(
        items = items,
        uiState = uiState,
        onLoadStateUpdate = viewModel::updateLoadState,
    ) {
        PosterGrid(
            items = items,
            genres = genres,
            selectedGenre = selectedGenre,
            onGenreSelected = viewModel::onGenreSelected,
            posterPath = { it.posterPath },
            key = { it.id },
            onClick = onMovieClick,
        )
    }
}

@Composable
fun TvSeriesListScreen(
    viewModel: MediaListViewModel<TvSeriesItem>,
    genres: List<Genre>?,
    onTvSeriesClick: (TvSeriesItem) -> Unit,
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    AutoPaginationScreen(
        items = items,
        uiState = uiState,
        onLoadStateUpdate = viewModel::updateLoadState,
    ) {
        PosterGrid(
            items = items,
            genres = genres,
            selectedGenre = selectedGenre,
            onGenreSelected = viewModel::onGenreSelected,
            posterPath = { it.posterPath },
            key = { it.id },
            onClick = onTvSeriesClick,
        )
    }
}

@Composable
fun CelebrityListScreen(
    viewModel: MediaListViewModel<Celebrity>,
    onCelebrityClick: (Celebrity) -> Unit,
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    Celebrities(celebrities = items, onClick = onCelebrityClick)
}
