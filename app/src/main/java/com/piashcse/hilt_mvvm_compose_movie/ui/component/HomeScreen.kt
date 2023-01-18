package com.piashcse.hilt_mvvm_compose_movie.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius10
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    navController: NavController, movies: Flow<PagingData<MovieItem>>
) {
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val moviesItems: LazyPagingItems<MovieItem> = movies.collectAsLazyPagingItems()

    BackHandler(enabled = (currentRoute(navController) == Screen.Home.route)) {
        // execute your custom logic here
        openDialog.value = true
    }
    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp),
            content = {
                items(moviesItems) { item ->
                    item?.let {
                        MovieItemView(item, navController)
                    }
                }
            })

    }
    if (openDialog.value) {
        ExitAlertDialog(navController, {
            openDialog.value = it
        }, {
            activity?.finish()
        })

    }
    moviesItems.pagingLoadingState {
        progressBar.value = it
    }
}


@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Column(modifier = Modifier.padding(5.dp)) {
        Image(painter = rememberAsyncImagePainter(ApiURL.IMAGE_URL.plus(item.posterPath)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .cornerRadius10()
                .clickable {
                    navController.navigate(Screen.MovieDetail.route.plus("/${item.id}"))
                })
    }
}



