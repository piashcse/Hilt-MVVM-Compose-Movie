package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(AppConstants.MOVIE_LIST)
    suspend fun getMovieList(@Query("page") one:String): Response<BaseModel>
}