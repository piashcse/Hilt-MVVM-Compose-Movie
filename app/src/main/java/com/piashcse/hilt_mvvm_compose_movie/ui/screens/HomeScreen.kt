package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.HomeViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.defaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import com.piashcse.hilt_mvvm_compose_movie.utils.items

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val paginationData: LazyPagingItems<MovieItem> = viewModel.movie.collectAsLazyPagingItems()
    DefaultPreview(paginationData, navController)
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DefaultPreview(movies: LazyPagingItems<MovieItem>?, navController: NavController) {
    val progressBar = remember { mutableStateOf(false) }
    HiltMVVMComposeMovieTheme {
        Column (modifier = Modifier.background(defaultBackgroundColor)){
            HomeAppBarPreview()
            CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
            movies?.let {
                MovieList(it, navController, progressBar)
            }
        }
    }
    movies?.apply {
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
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieList(
    movies: LazyPagingItems<MovieItem>,
    navController: NavController,
    durationState: MutableState<Boolean>
) {
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

@ExperimentalMaterialApi
@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
    Card(elevation = 0.dp, onClick = {
        navController.navigate(NavigationScreen.MovieDetail.MOVIE_DETAIL.plus("/${item.id}"))
    }) {
        Column(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = rememberImagePainter(AppConstants.IMAGE_URL.plus(item.posterPath)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
fun HomeAppBarPreview() {
    HomeAppBar(
        title = stringResource(R.string.app_title),
        openSearch = {},
        openFilters = {}
    )
}

@Composable
fun HomeAppBar(title: String, openSearch: () -> Unit, openFilters: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}


