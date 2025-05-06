package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.utils.conditional
import com.piashcse.hilt_mvvm_compose_movie.utils.items

@Composable
fun Movies(
    moviesItems: LazyPagingItems<MovieItem>,
    genres: List<Genre>? = null,
    selectedGenre: Genre?,
    onclickGenre: (Genre?) -> Unit,
    onclick: (MovieItem) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .background(DefaultBackgroundColor)) {
        genres?.let { DisplayGenres(it, selectedGenre, onclickGenre) }
        DisplayMovies(moviesItems, genres, onclick)
    }
}

@Composable
fun DisplayMovies(
    moviesItems: LazyPagingItems<MovieItem>,
    genres: List<Genre>?,
    onclick: (MovieItem) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .conditional(genres == null) { padding(top = 8.dp) }) {
        items(moviesItems) { item ->
            item?.let {
                ItemView(
                    item = item, itemImageUrlExtractor = { it.posterPath }, onclick = onclick
                )
            }
        }
    }
}