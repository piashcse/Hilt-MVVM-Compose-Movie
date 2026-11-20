package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import java.util.Locale

@Composable
fun Celebrities(
    celebrities: LazyPagingItems<Celebrity>,
    onClick: (Celebrity) -> Unit,
) {
    val isInitialLoading =
        celebrities.itemCount == 0 && celebrities.loadState.refresh is LoadState.Loading

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        if (isInitialLoading) {
            MediaGridShimmer()
        } else {
            DisplayCelebrities(celebrities = celebrities, onClick = onClick)
        }
    }
}

@Composable
fun DisplayCelebrities(
    celebrities: LazyPagingItems<Celebrity>,
    onClick: (Celebrity) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 5.dp),
    ) {
        items(
            count = celebrities.itemCount,
            key = celebrities.itemKey { it.id },
        ) { index ->
            celebrities[index]?.let { item ->
                Column(modifier = Modifier.padding(5.dp)) {
                    CoilImage(
                        modifier = Modifier
                            .size(230.dp)
                            .cornerRadius(10)
                            .clickable { onClick(item) },
                        imageModel = { item.profilePath?.let { ApiURL.IMAGE_URL + it } },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = item.name,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 800)
                            +ShimmerPlugin(
                                shimmer = Shimmer.Flash(
                                    baseColor = SecondaryFontColor,
                                    highlightColor = DefaultBackgroundColor,
                                )
                            )
                        },
                    )
                    Column(Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)) {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                        Text(
                            text = "Popularity: ${
                                String.format(Locale.US, "%.1f", item.popularity)
                            }",
                            fontSize = 14.sp,
                            color = Color.Gray,
                        )
                    }
                }
            }
        }
    }
}
