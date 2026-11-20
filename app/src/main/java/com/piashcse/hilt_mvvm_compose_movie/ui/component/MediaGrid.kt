package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.utils.network.toUserMessage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.stringResource

/**
 * Shared adaptive grid for movies, TV shows and celebrities. Owns loading,
 * empty, error, retry and pull-to-refresh behaviour so every list looks and
 * behaves the same.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> MediaGrid(
    pagingItems: LazyPagingItems<T>,
    key: (T) -> Any,
    imagePath: (T) -> String?,
    title: (T) -> String,
    subtitle: (T) -> String?,
    emptyText: String,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val refresh = pagingItems.loadState.refresh

    when {
        pagingItems.itemCount == 0 && refresh is LoadState.Loading -> {
            SkeletonGrid(modifier)
        }

        pagingItems.itemCount == 0 && refresh is LoadState.Error -> {
            ErrorState(
                message = refresh.error.toUserMessage(),
                onRetry = pagingItems::retry,
                modifier = modifier,
            )
        }

        pagingItems.itemCount == 0 && refresh is LoadState.NotLoading -> {
            EmptyState(message = emptyText, modifier = modifier)
        }

        else -> {
            PullToRefreshBox(
                isRefreshing = refresh is LoadState.Loading,
                onRefresh = pagingItems::refresh,
                modifier = modifier.fillMaxSize(),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey(key),
                    ) { index ->
                        pagingItems[index]?.let { item ->
                            MediaCard(
                                imagePath = imagePath(item),
                                title = title(item),
                                subtitle = subtitle(item),
                                onClick = { onClick(item) },
                            )
                        }
                    }

                    when (val append = pagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        is LoadState.Error -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = append.error.toUserMessage(),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                    TextButton(onClick = pagingItems::retry) {
                                        Text(stringResource(R.string.retry))
                                    }
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun MediaCard(
    imagePath: String?,
    title: String,
    subtitle: String?,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(bottom = 4.dp),
    ) {
        PosterImage(
            path = imagePath,
            contentDescription = stringResource(R.string.image_content_description, title),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f),
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        if (!subtitle.isNullOrBlank()) {
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

@Composable
private fun SkeletonGrid(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "skeleton")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeletonAlpha",
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false,
    ) {
        items(count = 8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)),
            )
        }
    }
}
