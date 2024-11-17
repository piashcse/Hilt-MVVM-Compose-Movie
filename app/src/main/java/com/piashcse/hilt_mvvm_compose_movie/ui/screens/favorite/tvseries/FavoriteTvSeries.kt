package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExitAlertDialog
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
    val favoriteTvSeries by viewModel.favoriteTvSeries.collectAsState()

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
                items(favoriteTvSeries) { item ->
                    item?.let {
                        FavoriteTvSeriesItemView(item, navController, viewModel)
                    }
                }
            })
    }
}

@Composable
fun FavoriteTvSeriesItemView(item: TvSeriesDetail, navController: NavController, viewModel: FavoriteTvSeriesViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    SideEffect {
        viewModel.favoriteTvSeriesFromDB()
    }
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp).cornerRadius(10)) {
        Box {
            CoilImage(
                modifier = Modifier
                    .height(250.dp)
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
            IconButton(
                onClick = {
                    openDialog.value = true
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.9f), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Color.Gray.copy(alpha = 0.5f))) {
                Text(
                    text = item.name,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        if (openDialog.value) {
            ExitAlertDialog(
                title = stringResource(R.string.remove_from_favorite),
                description = stringResource(R.string.do_you_wanna_remove_this_from_favorite),
                {
                    openDialog.value = false
                },
                {
                    viewModel.removeTvSeriesFromDB(item.id)
                    openDialog.value = false
                })
        }
    }
}