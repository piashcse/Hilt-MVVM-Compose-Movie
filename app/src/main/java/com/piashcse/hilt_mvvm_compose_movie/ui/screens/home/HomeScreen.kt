package com.piashcse.hilt_mvvm_compose_movie.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExitAlertDialog
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.defaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.secondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    isAppBarVisible: MutableState<Boolean>,
    movies: LazyPagingItems<MovieItem> = viewModel.nowPlayingMovies().collectAsLazyPagingItems()
) {
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember { mutableStateOf(false) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val searchData = viewModel.searchData

    BackHandler(enabled = (currentRoute(navController) == NavigationScreen.HOME)) {
        // execute your custome logic here
        openDialog.value = true
    }

    HiltMVVMComposeMovieTheme {
        Column(modifier = Modifier.background(defaultBackgroundColor)) {
            Box {
                Column {
                    CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
                    CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp),
                        content = {
                            items(movies) { item ->
                                item?.let {
                                    MovieItemView(item, navController)
                                }
                            }
                        })
                }
                if (isAppBarVisible.value.not()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(0.dp, 350.dp) // define max height
                            .padding(start = 10.dp, end = 10.dp)
                            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                            .background(color = defaultBackgroundColor)
                            .padding(top = 8.dp)

                    ) {
                        searchData.value?.let {
                            if (it is DataState.Success<BaseModel>) {
                                items(items = it.data.results, itemContent = { item ->
                                    Row(modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .clickable {
                                            navController.navigate(
                                                NavigationScreen.MovieDetail.MOVIE_DETAIL.plus(
                                                    "/${item.id}"
                                                )
                                            )
                                        }) {
                                        Image(
                                            painter = rememberImagePainter(
                                                ApiURL.IMAGE_URL.plus(
                                                    item.backdropPath
                                                )
                                            ),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(60.dp)
                                                .width(40.dp)
                                            // .clip(RoundedCornerShape(10.dp))
                                        )
                                        Column {
                                            Text(
                                                text = item.title,
                                                modifier = Modifier.padding(
                                                    start = 8.dp,
                                                    top = 4.dp
                                                ),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = "${stringResource(R.string.rating_search)}${item.voteAverage}",
                                                color = secondaryFontColor,
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }

        }
        if (openDialog.value) {
            ExitAlertDialog(navController, {
                openDialog.value = it
            }, {
                activity?.finish()
            })
        }
    }
    movies.apply {
        when {
            // data is loading for first time
            loadState.refresh is LoadState.Loading -> {
                progressBar.value = true
            }
            // data is loading for second time or pagination
            loadState.append is LoadState.Loading -> {
                progressBar.value = true
            }
            loadState.refresh is LoadState.NotLoading -> {
                progressBar.value = false
            }
            loadState.append is LoadState.NotLoading -> {
                progressBar.value = false
            }
        }
    }


    searchData.let {
        when (it.value) {
            is DataState.Success<BaseModel> -> {
                searchProgressBar.value = false
            }
            is DataState.Loading -> {
                searchProgressBar.value = true
            }
            is DataState.Error -> {
                searchProgressBar.value = false
            }
            else -> {
            }
        }
    }
}

@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Column(modifier = Modifier.padding(5.dp)) {
        Image(
            painter = rememberImagePainter(ApiURL.IMAGE_URL.plus(item.posterPath)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navController.navigate(NavigationScreen.MovieDetail.MOVIE_DETAIL.plus("/${item.id}"))
                }
        )
    }
}



