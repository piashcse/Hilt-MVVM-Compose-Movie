package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieDatabase
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieEntity
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.mappers.toBeEntity
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NowPlayingMediator(
    private val movieDb: MovieDatabase, private val apiService: ApiService
) : RemoteMediator<Int, MovieEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val movies = apiService.nowPlayingMovieList(
                page = loadKey, null
            )

            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.getMovieDao().clearAll()
                }
                val movieEntities = movies.results.map { it.toBeEntity() }
                movieDb.getMovieDao().upsertAll(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = movies.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}