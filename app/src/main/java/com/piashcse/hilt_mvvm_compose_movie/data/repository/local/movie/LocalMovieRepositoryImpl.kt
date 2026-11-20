package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteMovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepositoryImpl @Inject constructor(
    private val movieDao: FavoriteMovieDao,
) : LocalMovieRepository {

    override fun favoriteMovies(): Flow<List<MovieDetail>> = movieDao.observeAll()

    override suspend fun removeMovieById(movieId: Int) {
        movieDao.deleteMovieDetailById(movieId)
    }
}
