package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.GenreFilterRow
import com.piashcse.hilt_mvvm_compose_movie.ui.component.MediaGrid
import com.piashcse.hilt_mvvm_compose_movie.utils.asYear

@Composable
fun MovieListScreen(
    viewModel: MediaListViewModel<MovieItem>,
    genres: List<Genre>?,
    onMovieClick: (MovieItem) -> Unit,
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    val selectedGenreId by viewModel.selectedGenreId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        genres?.let {
            GenreFilterRow(
                genres = it,
                selectedGenreId = selectedGenreId,
                onGenreSelected = viewModel::onGenreSelected,
            )
        }
        MediaGrid(
            pagingItems = items,
            key = { it.id },
            imagePath = { it.posterPath },
            title = { it.title },
            subtitle = { it.releaseDate.asYear() },
            emptyText = stringResource(R.string.no_movies),
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
    val selectedGenreId by viewModel.selectedGenreId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        genres?.let {
            GenreFilterRow(
                genres = it,
                selectedGenreId = selectedGenreId,
                onGenreSelected = viewModel::onGenreSelected,
            )
        }
        MediaGrid(
            pagingItems = items,
            key = { it.id },
            imagePath = { it.posterPath },
            title = { it.name },
            subtitle = { it.firstAirDate.asYear() },
            emptyText = stringResource(R.string.no_tv_series),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        MediaGrid(
            pagingItems = items,
            key = { it.id },
            imagePath = { it.profilePath },
            title = { it.name },
            subtitle = { it.knownForDepartment },
            emptyText = stringResource(R.string.no_celebrities),
            onClick = onCelebrityClick,
        )
    }
}
