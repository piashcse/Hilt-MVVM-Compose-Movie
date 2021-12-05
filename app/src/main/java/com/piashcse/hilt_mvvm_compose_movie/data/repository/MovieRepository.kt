package com.piashcse.hilt_mvvm_compose_movie.data.repository

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.MovieDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dataSource: MovieDataSource) {
    suspend fun getMovieList(page: Int): Response<BaseModel> {
        return dataSource.getMovieList(page)
    }

    suspend fun getMovieDetail(movieId: Int): Response<MovieDetail> {
        return dataSource.getMovieDetail(movieId)
    }

    suspend fun getRecommendedMovie(movieId: Int, page: Int): Response<BaseModel> {
        return dataSource.getRecommendedMovie(movieId, page)
    }
}