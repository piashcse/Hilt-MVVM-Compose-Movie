package com.piashcse.hilt_mvvm_compose_movie.ui.component

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.utils.conditional
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState

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

        DisplayTvSeries(celebrities = celebrities, navController = navController)
        // Show exit dialog if back button is pressed
        if (openDialog.value) {
            ShowExitDialog(activity, openDialog)
        }
    }

    // Handle loading state for paging
    celebrities.pagingLoadingState { progressBar.value = it }
}

@Composable
fun DisplayTvSeries(
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
                ItemView(
                    item = it,
                    navController = navController,
                    itemIdExtractor = { it.id.toString() },
                    itemImageUrlExtractor = { it.profilePath },
                    itemDetailRoute = Screen.ArtistDetail.route // Correct route for TvSeriesDetail
                )
            }
        }
    }
}