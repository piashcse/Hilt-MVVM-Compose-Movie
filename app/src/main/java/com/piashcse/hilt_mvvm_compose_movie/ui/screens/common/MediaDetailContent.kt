package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Cast
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExpandingText
import com.piashcse.hilt_mvvm_compose_movie.utils.asCompactCount
import com.piashcse.hilt_mvvm_compose_movie.utils.asRating
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

private val StarColor = Color(0xFFF5A623)

data class DetailFact(val label: String, val value: String)

data class RelatedMedia(val id: Int, val title: String, val posterPath: String?)

/**
 * Shared movie/TV detail screen: backdrop, poster, title and rating, genre
 * chips, key facts, overview, cast and recommendations.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MediaDetailContent(
    title: String,
    originalTitle: String?,
    tagline: String?,
    overview: String,
    backdropPath: String?,
    posterPath: String?,
    rating: Double,
    voteCount: Int,
    year: String?,
    genres: List<String>,
    facts: List<DetailFact>,
    cast: List<Cast>,
    recommended: List<RelatedMedia>,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCastClick: (Int) -> Unit,
    onRecommendedClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
            ) {
                DetailImage(
                    path = backdropPath,
                    contentDescription = stringResource(
                        R.string.backdrop_content_description,
                        title,
                    ),
                    highResolution = true,
                    modifier = Modifier.fillMaxSize(),
                    cornerRadius = 0,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.55f to Color.Transparent,
                                1f to MaterialTheme.colorScheme.background,
                            )
                        ),
                )
                IconToggleButton(
                    checked = isFavorite,
                    onCheckedChange = { onFavoriteClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.88f)),
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
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    DetailImage(
                        path = posterPath,
                        contentDescription = stringResource(
                            R.string.poster_content_description,
                            title,
                        ),
                        modifier = Modifier
                            .width(112.dp)
                            .aspectRatio(2f / 3f)
                            .offset(y = (-24).dp),
                        cornerRadius = 12,
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (!originalTitle.isNullOrBlank()) {
                            Text(
                                text = originalTitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (rating > 0) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = StarColor,
                                    modifier = Modifier.size(18.dp),
                                )
                                Text(
                                    text = rating.asRating(),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                                if (voteCount > 0) {
                                    Text(
                                        text = stringResource(
                                            R.string.votes,
                                            voteCount.asCompactCount(),
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(start = 6.dp),
                                    )
                                }
                            }
                            year?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 10.dp),
                                )
                            }
                        }

                        if (genres.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                genres.forEach { genre ->
                                    GenrePill(genre)
                                }
                            }
                        }

                        if (!tagline.isNullOrBlank()) {
                            Text(
                                text = tagline,
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                        }
                    }
                }
            }
        }

        if (facts.isNotEmpty()) {
            item {
                SectionTitle(stringResource(R.string.details), topPadding = 0.dp)
                FactsGrid(facts)
            }
        }

        if (overview.isNotBlank()) {
            item {
                SectionTitle(stringResource(R.string.description))
                ExpandingText(
                    text = overview,
                    visibleLines = 5,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }

        if (cast.isNotEmpty()) {
            item { SectionTitle(stringResource(R.string.cast)) }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(cast, key = { it.id }) { person ->
                        Column(
                            modifier = Modifier
                                .width(92.dp)
                                .clickable { onCastClick(person.id) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DetailImage(
                                path = person.profilePath,
                                contentDescription = stringResource(
                                    R.string.profile_content_description,
                                    person.name,
                                ),
                                modifier = Modifier.size(70.dp),
                                cornerRadius = 35,
                            )
                            Text(
                                text = person.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                            if (person.character.isNotBlank()) {
                                Text(
                                    text = person.character,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        }

        if (recommended.isNotEmpty()) {
            item { SectionTitle(stringResource(R.string.similar)) }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(recommended, key = { it.id }) { item ->
                        Column(
                            modifier = Modifier
                                .width(112.dp)
                                .clickable { onRecommendedClick(item.id) },
                        ) {
                            DetailImage(
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
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun SectionTitle(text: String, topPadding: Dp = 20.dp) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = topPadding, bottom = 8.dp),
    )
}

@Composable
private fun FactsGrid(facts: List<DetailFact>) {
    Column {
        facts.chunked(2).forEach { rowFacts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
            ) {
                rowFacts.forEach { fact ->
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = fact.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = fact.value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                if (rowFacts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun GenrePill(genre: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        )
    }
}

@Composable
private fun DetailImage(
    path: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    highResolution: Boolean = false,
    cornerRadius: Int = 10,
) {
    val url = if (highResolution) ApiURL.backdrop(path) else ApiURL.poster(path)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        if (url != null) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { url },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = contentDescription,
                ),
                component = rememberImageComponent {
                    +CircularRevealPlugin(duration = 400)
                    +ShimmerPlugin(
                        shimmer = Shimmer.Flash(
                            baseColor = MaterialTheme.colorScheme.surfaceVariant,
                            highlightColor = MaterialTheme.colorScheme.surface,
                        )
                    )
                },
            )
        }
    }
}
