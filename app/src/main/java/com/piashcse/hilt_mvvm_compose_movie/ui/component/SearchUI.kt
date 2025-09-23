package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.roundTo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun SearchUI(
    navController: NavController,
    searchData: List<SearchItem>,
    activeTab: Int,
    searchQuery: String = "", // Move searchQuery parameter before itemClick
    isLoading: Boolean = false, // Add isLoading parameter
    itemClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp) // Increased height to show more items
            .background(color = DefaultBackgroundColor)
    ) {
        // Show placeholder only when API call is complete, there's a search query, but no results
        if (!isLoading && searchQuery.isNotBlank() && searchData.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No results found",
                        color = SecondaryFontColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            items(items = searchData, itemContent = { item ->
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .clickable {
                            itemClick.invoke()
                            if (activeTab == MOVIE_SEARCH) {
                                navController.navigate(
                                    Screen.MovieDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            } else if (activeTab == TV_SERIES_SEARCH) {
                                navController.navigate(
                                    Screen.TvSeriesDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            } else if (activeTab == CELEBRITIES_SEARCH) {
                                navController.navigate(
                                    Screen.ArtistDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            }
                        }
                ) {
                    CoilImage(
                        modifier = Modifier
                            .height(100.dp)
                            .width(80.dp)
                            .cornerRadius(8),
                        imageModel = {
                            val imagePath = if (activeTab == CELEBRITIES_SEARCH) {
                                item.profilePath
                            } else {
                                item.backdropPath
                            }
                            ApiURL.IMAGE_URL.plus(imagePath)
                        },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "search item",
                            colorFilter = null,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 800)
                            +ShimmerPlugin(
                                shimmer = Shimmer.Flash(
                                    baseColor = SecondaryFontColor,
                                    highlightColor = DefaultBackgroundColor
                                )
                            )
                        },
                    )
                    Column {
                        val title = if (activeTab == MOVIE_SEARCH) item.title else item.name
                        val release = if (activeTab == MOVIE_SEARCH) item.releaseDate else item.firstAirDate

                        Text(
                            text = title ?: "",
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                            fontWeight = FontWeight.SemiBold
                        )

                        if (activeTab != CELEBRITIES_SEARCH) {
                            Text(
                                text = release ?: "",
                                color = FontColor,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            Text(
                                text = "${stringResource(R.string.rating_search)} ${
                                    item.voteAverage?.roundTo(1)
                                }",
                                color = SecondaryFontColor,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            })
        }
    }
}