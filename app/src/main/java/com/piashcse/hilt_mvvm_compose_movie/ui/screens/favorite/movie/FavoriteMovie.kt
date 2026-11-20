package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoritesContent

@Composable
fun FavoriteMovie(navController: Navigator) {
    val viewModel = hiltViewModel<FavoriteMovieViewModel>()
    val favorites by viewModel.favorites.collectAsState()

    FavoritesContent(
        items = favorites,
        key = { it.id },
        title = { it.title },
        backdropPath = { it.backdropPath },
        onClick = { navController.navigate(MovieDetailRoute(it.id)) },
        onDelete = { viewModel.remove(it.id) },
    )
}
