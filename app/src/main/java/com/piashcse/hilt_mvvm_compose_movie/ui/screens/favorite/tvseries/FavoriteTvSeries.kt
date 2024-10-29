package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun FavoriteTvSeries(navController: NavController) {
    val viewModel = hiltViewModel<FavoriteTvSeriesViewModel>()
    val favoriteMovies by viewModel.favoriteTvSeries.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.favoriteTvSeriesFromDB()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(1),
            modifier = Modifier.padding(start = 5.dp, top = 10.dp, end = 5.dp),
            content = {
                items(favoriteMovies) { item ->
                    item?.let {
                        FavoriteTvSeriesItemView(item, navController)
                    }
                }
            })
    }
}

@Composable
fun FavoriteTvSeriesItemView(item: TvSeriesDetail, navController: NavController) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        CoilImage(
            modifier = Modifier
                .height(250.dp)
                .cornerRadius(10)
                .clickable {
                    navController.navigate(Screen.TvSeriesDetail.route.plus("/${item.id}"))
                },
            imageModel = { ApiURL.IMAGE_URL.plus(item.backdropPath) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
                +ShimmerPlugin(
                    shimmer = Shimmer.Flash(
                        baseColor = SecondaryFontColor,
                        highlightColor = DefaultBackgroundColor
                    )
                )
            },
        )
    }
}