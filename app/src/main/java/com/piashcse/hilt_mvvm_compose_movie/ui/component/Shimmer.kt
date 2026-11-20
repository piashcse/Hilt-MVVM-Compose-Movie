package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A moving gradient shared by all skeleton blocks on a screen, so the
 * placeholders shimmer in sync.
 */
@Composable
fun rememberShimmerBrush(): Brush {
    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.surface
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerProgress",
    )

    val sweep = 1200f
    return Brush.linearGradient(
        colors = listOf(base, highlight, base),
        start = Offset(x = -sweep + progress * sweep * 2, y = 0f),
        end = Offset(x = progress * sweep * 2, y = sweep),
    )
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    brush: Brush,
    cornerRadius: Dp = 8.dp,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush),
    )
}

/**
 * Skeleton for the two-column movie/TV/celebrity grids. Matches the current
 * grid padding and poster size so content does not shift when it loads.
 */
@Composable
fun MediaGridShimmer(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 5.dp),
        userScrollEnabled = false,
    ) {
        items(count = 6) {
            ShimmerBox(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(250.dp),
                brush = brush,
                cornerRadius = 10.dp,
            )
        }
    }
}

/** Skeleton for movie/TV detail screens. */
@Composable
fun MediaDetailShimmer(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            brush = brush,
            cornerRadius = 0.dp,
        )
        Row(modifier = Modifier.padding(12.dp)) {
            ShimmerBox(
                modifier = Modifier
                    .width(110.dp)
                    .aspectRatio(2f / 3f),
                brush = brush,
                cornerRadius = 10.dp,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(26.dp),
                    brush = brush,
                    cornerRadius = 6.dp,
                )
                Spacer(modifier = Modifier.height(12.dp))
                repeat(4) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .height(12.dp),
                        brush = brush,
                        cornerRadius = 4.dp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        ShimmerBox(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(120.dp)
                .height(18.dp),
            brush = brush,
            cornerRadius = 4.dp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        repeat(3) {
            ShimmerBox(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .height(12.dp),
                brush = brush,
                cornerRadius = 4.dp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ShimmerBox(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(100.dp)
                .height(18.dp),
            brush = brush,
            cornerRadius = 4.dp,
        )
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            repeat(3) {
                ShimmerBox(
                    modifier = Modifier
                        .width(110.dp)
                        .aspectRatio(2f / 3f),
                    brush = brush,
                    cornerRadius = 10.dp,
                )
            }
        }
    }
}

/** Skeleton for the artist detail screen. */
@Composable
fun ArtistDetailShimmer(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp),
    ) {
        Row {
            ShimmerBox(
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(2f / 3f),
                brush = brush,
                cornerRadius = 10.dp,
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(26.dp),
                    brush = brush,
                    cornerRadius = 6.dp,
                )
                Spacer(modifier = Modifier.height(12.dp))
                repeat(4) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(12.dp),
                        brush = brush,
                        cornerRadius = 4.dp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ShimmerBox(
            modifier = Modifier
                .width(120.dp)
                .height(18.dp),
            brush = brush,
            cornerRadius = 4.dp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        repeat(4) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                brush = brush,
                cornerRadius = 4.dp,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(3) {
                ShimmerBox(
                    modifier = Modifier
                        .width(110.dp)
                        .aspectRatio(2f / 3f),
                    brush = brush,
                    cornerRadius = 10.dp,
                )
            }
        }
    }
}
