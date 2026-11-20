package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH

/**
 * Segmented selector for the search result type. The selection is hoisted so
 * the UI always reflects the ViewModel state.
 */
@Composable
fun SearchTypeSelector(
    selectedType: Int,
    onTypeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val types = listOf(
        MOVIE_SEARCH to stringResource(R.string.movie),
        TV_SERIES_SEARCH to stringResource(R.string.tv_series),
        CELEBRITIES_SEARCH to stringResource(R.string.celebrities),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        types.forEach { (type, label) ->
            FilterChip(
                selected = selectedType == type,
                onClick = { onTypeSelected(type) },
                label = { Text(label) },
            )
        }
    }
}
