package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre

/**
 * Genre filter with an explicit "All" chip so the filter can always be reset.
 */
@Composable
fun GenreFilterRow(
    genres: List<Genre>,
    selectedGenreId: String?,
    onGenreSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(key = "all") {
            FilterChip(
                selected = selectedGenreId == null,
                onClick = { onGenreSelected(null) },
                label = { Text(stringResource(R.string.all)) },
            )
        }
        items(genres, key = { it.id ?: it.name }) { genre ->
            FilterChip(
                selected = selectedGenreId != null && selectedGenreId == genre.id?.toString(),
                onClick = { onGenreSelected(genre.id) },
                label = { Text(genre.name) },
            )
        }
    }
}
