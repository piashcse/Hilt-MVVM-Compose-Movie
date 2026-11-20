package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Cast
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ErrorState
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExpandingText
import com.piashcse.hilt_mvvm_compose_movie.ui.component.PosterImage

data class DetailInfo(@StringRes val labelRes: Int, val value: String?)

data class RecommendedMedia(val id: Int, val title: String, val posterPath: String?)

/**
 * Shared layout for movie and TV detail screens: backdrop, poster, key facts,
 * overview, recommendations and cast.
 */
@Composable
fun MediaDetailLayout(
    title: String,
    backdropPath: String?,
    posterPath: String?,
    info: List<DetailInfo>,
    overview: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    recommended: List<RecommendedMedia>,
    onRecommendedClick: (Int) -> Unit,
    cast: List<Cast>,
    onCastClick: (Int) -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
) {
    when {
        errorMessage != null && title.isEmpty() -> {
            ErrorState(message = errorMessage, onRetry = onRetry)
        }

        title.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (isLoading) CircularProgressIndicator()
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
            ) {
                Box {
                    PosterImage(
                        path = backdropPath,
                        contentDescription = stringResource(
                            R.string.backdrop_content_description,
                            title,
                        ),
                        highResolution = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        cornerRadius = 0,
                    )
                    IconToggleButton(
                        checked = isFavorite,
                        onCheckedChange = { onFavoriteClick() },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(
                                if (isFavorite) R.string.remove_from_favorites
                                else R.string.add_to_favorites
                            ),
                            tint = if (isFavorite) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                Row(modifier = Modifier.padding(12.dp)) {
                    PosterImage(
                        path = posterPath,
                        contentDescription = stringResource(
                            R.string.poster_content_description,
                            title,
                        ),
                        modifier = Modifier
                            .width(110.dp)
                            .aspectRatio(2f / 3f),
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        info.filter { !it.value.isNullOrBlank() }
                            .chunked(2)
                            .forEach { rowInfo ->
                                Row(modifier = Modifier.padding(bottom = 6.dp)) {
                                    rowInfo.forEach { item ->
                                        InfoItem(item = item, modifier = Modifier.weight(1f))
                                    }
                                    if (rowInfo.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                    }
                }

                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
                ExpandingText(
                    text = overview,
                    visibleLines = 4,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                )

                if (recommended.isNotEmpty()) {
                    SectionTitle(stringResource(R.string.similar))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(recommended, key = { it.id }) { item ->
                            Column(
                                modifier = Modifier.width(110.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                PosterImage(
                                    path = item.posterPath,
                                    contentDescription = stringResource(
                                        R.string.poster_content_description,
                                        item.title,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(2f / 3f),
                                )
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp),
                                )
                            }
                        }
                    }
                }

                if (cast.isNotEmpty()) {
                    SectionTitle(stringResource(R.string.cast))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(cast, key = { it.id }) { person ->
                            Column(
                                modifier = Modifier.width(72.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                PosterImage(
                                    path = person.profilePath,
                                    contentDescription = stringResource(
                                        R.string.profile_content_description,
                                        person.name,
                                    ),
                                    modifier = Modifier.size(64.dp),
                                    cornerRadius = 32,
                                )
                                Text(
                                    text = person.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp),
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun InfoItem(item: DetailInfo, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(end = 8.dp)) {
        Text(
            text = stringResource(item.labelRes),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = item.value.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 8.dp),
    )
}
