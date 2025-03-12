package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.conditional
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import java.util.Locale

@Composable
fun Celebrities(
    navController: NavController,
    celebrities: LazyPagingItems<Celebrity>,
) {
    val activity = LocalActivity.current
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    // Handling back press for dialog
    BackHandler(enabled = currentRoute(navController) == Screen.AiringTodayTvSeries.route) {
        openDialog.value = true
    }

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        // Display genres if provided

        // Show loading indicator if progressBar is true
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)

        DisplayCelebrities(celebrities = celebrities, navController = navController)
        // Show exit dialog if back button is pressed
        if (openDialog.value) {
            ShowExitDialog(activity, openDialog)
        }
    }

    // Handle loading state for paging
    celebrities.pagingLoadingState { progressBar.value = it }
}

@Composable
fun DisplayCelebrities(
    celebrities: LazyPagingItems<Celebrity>,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 5.dp)
    ) {
        items(celebrities) { item ->
            item?.let {
                Column(modifier = Modifier.padding(5.dp)) {
                    CoilImage(
                        modifier = Modifier
                            .size(230.dp)
                            .cornerRadius(10)
                            .clickable { navController.navigate(Screen.ArtistDetail.route.plus("/${item.id}")) },
                        imageModel = { ApiURL.IMAGE_URL + item.profilePath },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "Item"
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 800)
                            +ShimmerPlugin(
                                shimmer = Shimmer.Flash(
                                    baseColor = SecondaryFontColor,
                                    highlightColor = DefaultBackgroundColor
                                )
                            )
                        }
                    )
                    Column(Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)) {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Popularity: ${String.format(Locale.US, "%.1f", item.popularity)}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}