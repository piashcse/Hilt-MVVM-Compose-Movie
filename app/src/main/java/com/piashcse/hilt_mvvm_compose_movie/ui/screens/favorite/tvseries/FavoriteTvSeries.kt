package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoritesContent

@Composable
fun FavoriteTvSeries(navController: Navigator) {
    val viewModel = hiltViewModel<FavoriteTvSeriesViewModel>()
    val favorites by viewModel.favorites.collectAsState()

    FavoritesContent(
        items = favorites,
        key = { it.id },
        title = { it.name },
        backdropPath = { it.backdropPath },
        onClick = { navController.navigate(TvSeriesDetailRoute(it.id)) },
        onDelete = { viewModel.remove(it.id) },
    )
}
