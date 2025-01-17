package com.piashcse.hilt_mvvm_compose_movie.ui.component

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.utils.conditional
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState

@Composable
fun Movies(
    navController: NavController,
    moviesItems: LazyPagingItems<MovieItem>,
    genres: List<Genre>? = null,
    selectedName: Genre?,
    onClickGenre: (Genre?) -> Unit,
) {
    val activity = LocalActivity.current
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = currentRoute(navController) == Screen.NowPlaying.route) {
        openDialog.value = true
    }

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        genres?.let { DisplayGenres(it, selectedName, onClickGenre) }
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        DisplayMovies(moviesItems, navController, genres)

        if (openDialog.value) {
            ShowExitDialog(activity, openDialog)
        }
    }

    moviesItems.pagingLoadingState { progressBar.value = it }
}

@Composable
fun DisplayGenres(
    genres: List<Genre>,
    selectedName: Genre?,
    onClick: (Genre?) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(genres) { item ->
            SelectableGenreChip(selected = item.name == selectedName?.name,
                genre = item.name,
                onclick = { onClick(item) })
        }
    }
}

@Composable
fun DisplayMovies(
    moviesItems: LazyPagingItems<MovieItem>,
    navController: NavController,
    genres: List<Genre>?,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .conditional(genres == null) { padding(top = 8.dp) }) {
        items(moviesItems) { item ->
            item?.let {
                ItemView(
                    item = item,
                    navController = navController,
                    itemIdExtractor = { it.id.toString() },
                    itemImageUrlExtractor = { it.posterPath },
                    itemDetailRoute = Screen.MovieDetail.route
                )
            }
        }
    }
}