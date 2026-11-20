package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

/**
 * Single image-loading surface for posters, backdrops and profile photos.
 * Handles missing paths with a neutral placeholder instead of requesting
 * `.../null`.
 */
@Composable
fun PosterImage(
    path: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    highResolution: Boolean = false,
    cornerRadius: Int = 10,
) {
    val url = if (highResolution) ApiURL.backdrop(path) else ApiURL.poster(path)
    val shape = RoundedCornerShape(cornerRadius.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        if (url == null) {
            // Placeholder stays visible when the API returns no image path.
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant))
        } else {
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
