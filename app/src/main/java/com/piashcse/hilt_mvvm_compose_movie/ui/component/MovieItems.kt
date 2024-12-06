package com.piashcse.hilt_mvvm_compose_movie.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
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

@Composable
fun MovieItems(
    navController: NavController,
    moviesItems: LazyPagingItems<MovieItem>,
    genres: List<Genre>? = null,
    selectedName: Genre?,
    onClick: (genreId: Genre?) -> Unit,
) {
    val activity = LocalContext.current as? Activity
    val progressBar = remember { mutableStateOf(false) }
    val isExitDialogOpen = remember { mutableStateOf(false) }
    val currentRoute = currentRoute(navController)

    BackHandler(enabled = (currentRoute == Screen.NowPlaying.route)) {
        isExitDialogOpen.value = true
    }

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        genres?.let {
            GenreChipsRow(genres = genres, selectedGenre = selectedName, onGenreClick = onClick)
        }

        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)

        MovieGrid(
            moviesItems = moviesItems,
            navController = navController,
            hasTopPadding = genres == null
        )
    }

    if ((currentRoute == Screen.NowPlaying.route || currentRoute == Screen.AiringTodayTvSeries.route) && isExitDialogOpen.value) {
        ExitAlertDialog(
            title = stringResource(R.string.close_the_app),
            description = stringResource(R.string.do_you_want_to_exit_the_app),
            { isExitDialogOpen.value = it },
            { activity?.finish() }
        )
    }

    moviesItems.pagingLoadingState {
        progressBar.value = it
    }
}

@Composable
fun MovieGrid(
    moviesItems: LazyPagingItems<MovieItem>,
    navController: NavController,
    hasTopPadding: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .conditional(hasTopPadding) { padding(top = 8.dp) }
    ) {
        items(moviesItems) { item ->
            item?.let {
                MovieItemView(item = item, navController = navController)
            }
        }
    }
}

@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)) {
        CoilImage(
            modifier = Modifier
                .size(250.dp)
                .cornerRadius(10)
                .clickable {
                    navController.navigate("${Screen.MovieDetail.route}/${item.id}")
                },
            imageModel = { "${ApiURL.IMAGE_URL}${item.posterPath}" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item"
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
    }
}


