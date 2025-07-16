package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null // ✅ Added field
)
