package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun movieList(page: Int): BaseModel {
        return apiService.nowPlayingmovieList(page)
    }

    suspend fun movieDetail(movieId: Int): MovieDetail {
        return apiService.movieDetail(movieId)
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): BaseModel {
        return apiService.recommendedMovie(movieId, page)
    }

    suspend fun search(searchKey: String): BaseModel {
        return apiService.search(searchKey)
    }
}