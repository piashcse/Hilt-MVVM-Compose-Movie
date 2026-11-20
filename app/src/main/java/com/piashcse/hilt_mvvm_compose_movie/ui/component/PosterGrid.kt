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
import androidx.paging.compose.itemKey
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor

/**
 * Shared poster grid used by the movie and TV lists. The layout matches the
 * original per-screen grids exactly.
 */
@Composable
fun <T : Any> PosterGrid(
    items: LazyPagingItems<T>,
    genres: List<Genre>?,
    selectedGenre: Genre?,
    onGenreSelected: (Genre?) -> Unit,
    posterPath: (T) -> String,
    key: (T) -> Any,
    onClick: (T) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(DefaultBackgroundColor),
    ) {
        genres?.let { DisplayGenres(it, selectedGenre, onGenreSelected) }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .then(if (genres == null) Modifier.padding(top = 8.dp) else Modifier),
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey(key),
            ) { index ->
                items[index]?.let { item ->
                    ItemView(
                        item = item,
                        itemImageUrlExtractor = posterPath,
                        onclick = onClick,
                    )
                }
            }
        }
    }
}
