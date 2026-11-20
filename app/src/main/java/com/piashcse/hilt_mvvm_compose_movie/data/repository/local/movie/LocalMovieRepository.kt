package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie

import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow

interface LocalMovieRepository {
    fun favoriteMovies(): Flow<List<MovieDetail>>
    suspend fun removeMovieById(movieId: Int)
}
