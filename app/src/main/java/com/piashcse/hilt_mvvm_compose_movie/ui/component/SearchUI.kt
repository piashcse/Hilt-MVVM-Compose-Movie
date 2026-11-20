package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.SearchState
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.asRating
import com.piashcse.hilt_mvvm_compose_movie.utils.asYear

@Composable
fun SearchUI(
    searchState: SearchState,
    activeTab: Int,
    onResultClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        searchState.isLoading && searchState.results.isEmpty() -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        searchState.errorMessage != null && searchState.results.isEmpty() -> {
            SearchMessage(text = searchState.errorMessage, modifier = modifier)
        }

        searchState.results.isEmpty() -> {
            SearchMessage(
                text = stringResource(R.string.no_results),
                modifier = modifier,
            )
        }

        else -> {
            val keyboardController = LocalSoftwareKeyboardController.current
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(searchState.results, key = { it.id }) { item ->
                    SearchResultRow(
                        item = item,
                        activeTab = activeTab,
                        onClick = {
                            keyboardController?.hide()
                            onResultClick(item)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchMessage(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun SearchResultRow(
    item: SearchItem,
    activeTab: Int,
    onClick: () -> Unit,
) {
    val title = item.title ?: item.name
    val date = if (activeTab == CELEBRITIES_SEARCH) null else item.releaseDate ?: item.firstAirDate

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PosterImage(
            path = if (activeTab == CELEBRITIES_SEARCH) item.profilePath else item.posterPath,
            contentDescription = stringResource(
                R.string.image_content_description,
                title.orEmpty(),
            ),
            modifier = Modifier
                .width(64.dp)
                .height(92.dp),
            cornerRadius = 8,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (activeTab != CELEBRITIES_SEARCH) {
                val rating = item.voteAverage?.let {
                    stringResource(R.string.rating_value, it.asRating())
                }
                val subtitle = listOfNotNull(date.asYear(), rating).joinToString(" • ")
                if (subtitle.isNotBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
