package com.piashcse.hilt_mvvm_compose_movie.data.repository

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.MovieDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dataSource: MovieDataSource) {
    suspend fun getRepositoryList(): Response<BaseModel> {
        return dataSource.getMovieList()

    }
}