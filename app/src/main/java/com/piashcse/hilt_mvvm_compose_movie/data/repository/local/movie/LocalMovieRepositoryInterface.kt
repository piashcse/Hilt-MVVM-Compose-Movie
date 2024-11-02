package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie

import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail

interface LocalMovieRepositoryInterface {
    suspend fun favoriteMovies(): List<MovieDetail?>
    suspend fun removeMovieById(movieId: Int)
}