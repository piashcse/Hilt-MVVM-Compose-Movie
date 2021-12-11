package com.piashcse.hilt_mvvm_compose_movie.data.repository

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.MovieDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dataSource: MovieDataSource) {
    suspend fun movieList(page: Int): Response<BaseModel> {
        return dataSource.movieList(page)
    }

    suspend fun movieDetail(movieId: Int): Response<MovieDetail> {
        return dataSource.movieDetail(movieId)
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): Response<BaseModel> {
        return dataSource.recommendedMovie(movieId, page)
    }


    suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = dataSource.search(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


}