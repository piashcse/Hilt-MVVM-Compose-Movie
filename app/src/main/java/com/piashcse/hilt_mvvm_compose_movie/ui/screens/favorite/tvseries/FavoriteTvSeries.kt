package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoritesScreen

@Composable
fun FavoriteTvSeries(navController: Navigator) {
    val viewModel = hiltViewModel<FavoriteTvSeriesViewModel>()
    val favoriteTvSeries by viewModel.favoriteTvSeries.collectAsState()

    FavoritesScreen(
        items = favoriteTvSeries,
        key = { it.id },
        title = { it.name },
        imagePath = { it.posterPath },
        emptyText = stringResource(R.string.favorite_tv_series_empty),
        onClick = { navController.navigate(TvSeriesDetailRoute(it.id)) },
        onDelete = { viewModel.removeTvSeriesFromDB(it.id) },
    )
}
