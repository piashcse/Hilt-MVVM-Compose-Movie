package com.piashcse.hilt_mvvm_compose_movie.data.repository

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.MovieDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val dataSource: MovieDataSource) {

    suspend fun movieList(page: Int): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = dataSource.movieList(page)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = dataSource.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = dataSource.recommendedMovie(movieId, page)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
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

    suspend fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = dataSource.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun moviesByGenreId(page: Int, genreId: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = dataSource.moviesByGenreId(page, genreId)
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


}