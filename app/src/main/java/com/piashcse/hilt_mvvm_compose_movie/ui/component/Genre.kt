package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre

@Composable
fun DisplayGenres(
    genres: List<Genre>,
    selectedName: Genre?,
    onClick: (Genre?) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(genres) { item ->
            SelectableGenreChip(
                selected = item.name == selectedName?.name,
                genre = item.name,
                onclick = { onClick(item) })
        }
    }
}