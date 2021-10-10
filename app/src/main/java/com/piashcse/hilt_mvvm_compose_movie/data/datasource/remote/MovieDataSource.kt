package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import retrofit2.Response
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovieList(): Response<BaseModel> {
        return apiService.getMovieList()
    }
}