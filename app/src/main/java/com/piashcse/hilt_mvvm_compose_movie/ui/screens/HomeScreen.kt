package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import timber.log.Timber

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController){
    val viewModel = hiltViewModel<HomeViewModel>()
    val loading = viewModel.loading.value
    val movies = viewModel.movies.value
    LaunchedEffect(true) {
        viewModel.getMovieList()
    }
    Timber.e("loading : $loading movie ${movies?.results?.size}")
    DefaultPreview(movies?.results, loading, navController )
}
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DefaultPreview(movies: List<MovieItem>?, loading: Boolean, navController: NavController) {
    HiltMVVMComposeMovieTheme {
        CircularIndeterminateProgressBar(isDisplayed = loading, 0.5f)
        movies?.let {
            MovieList(it, navController)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieList(movies: List<MovieItem>, navController: NavController) {
    LazyVerticalGrid(cells =  GridCells.Fixed(2), content ={
        items(items = movies) { item ->
            MovieItemView(item, navController)
        }
    })
}

@ExperimentalMaterialApi
@Composable
fun MovieItemView(item: MovieItem, navController: NavController) {
       Card(elevation = 0.dp, onClick = { navController.navigate("HomeDetail")}) {
           Column(modifier = Modifier.padding(10.dp)) {
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


