package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoritesScreen

@Composable
fun FavoriteMovie(navController: Navigator) {
    val viewModel = hiltViewModel<FavoriteMovieViewModel>()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    FavoritesScreen(
        items = favoriteMovies,
        key = { it.id },
        title = { it.title },
        imagePath = { it.posterPath },
        emptyText = stringResource(R.string.favorite_movies_empty),
        onClick = { navController.navigate(MovieDetailRoute(it.id)) },
        onDelete = { viewModel.removeMovieFromDB(it.id) },
    )
}
