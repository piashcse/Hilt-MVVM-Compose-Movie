package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.HomeViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.blue
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.defaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.secondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import com.piashcse.hilt_mvvm_compose_movie.utils.items
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController, openDrawer: () -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val movies: LazyPagingItems<MovieItem> = viewModel.movie.collectAsLazyPagingItems()
    val searchData = viewModel.searchData
    var text by remember { mutableStateOf("") }
    val isAppBarVisible = remember { mutableStateOf(true) }
    val focusRequester = FocusRequester()
    BackHandler(isAppBarVisible.value.not()) {
        isAppBarVisible.value = true
    }
    HiltMVVMComposeMovieTheme {
        Column(modifier = Modifier.background(defaultBackgroundColor)) {
            if (isAppBarVisible.value) {
                HomeAppBar(
                    title = stringResource(R.string.app_title),
                    openDrawer = { openDrawer() },
                    openFilters = {
                        isAppBarVisible.value = false
                    }
                )
            } else {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = text,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = blue,
                        cursorColor = Color.Black,
                        disabledLabelColor = blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    onValueChange = {
                        text = it
                        viewModel.searchApi(it)
                    },
                    //shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (text.trim().isNotEmpty()) {
                            Icon(Icons.Filled.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .offset(x = 10.dp)
                                    .clickable {
                                        text = ""
                                    }
                            )
                        } else {
                            Icon(Icons.Filled.Search,
                                contentDescription = "search",
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .offset(x = 10.dp)
                                    .clickable {

                                    }
                            )
                        }
                    }
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }

            Box {
                Column {
                    CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
                    CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)

                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
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
                                                AppConstants.IMAGE_URL.plus(
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
    }
    movies.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                progressBar.value = true
            }
            loadState.append is LoadState.Loading -> {
                progressBar.value = true
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
            painter = rememberImagePainter(AppConstants.IMAGE_URL.plus(item.posterPath)),
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

@Composable
fun HomeAppBar(title: String, openDrawer: () -> Unit, openFilters: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        actions = {
            IconButton(onClick = openFilters) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}


