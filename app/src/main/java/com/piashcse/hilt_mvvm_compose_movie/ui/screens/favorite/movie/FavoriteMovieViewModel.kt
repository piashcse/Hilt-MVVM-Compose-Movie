package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.movie

import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie.LocalMovieRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoriteListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    repository: LocalMovieRepository,
) : FavoriteListViewModel<MovieDetail>(
    favorites = repository.favoriteMovies(),
    removeFavorite = repository::removeMovieById,
)
