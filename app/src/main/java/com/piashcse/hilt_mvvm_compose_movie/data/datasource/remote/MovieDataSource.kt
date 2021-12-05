package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import retrofit2.Response
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovieList(page: Int): Response<BaseModel> {
        return apiService.getMovieList(page)
    }

    suspend fun getMovieDetail(movieId: Int): Response<MovieDetail> {
        return apiService.getMovieDetail(movieId)
    }

    suspend fun getRecommendedMovie(movieId: Int, page: Int): Response<BaseModel> {
        return apiService.getRecommendedMovie(movieId, page)
    }
}