package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class PagingDataSource @Inject constructor(private val apiService: ApiService)  :
    PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val nextPage = params.key ?: 1
            val movieList = apiService.movieList(nextPage)
            Timber.e("api call : ${movieList.body()!!.page}")
            LoadResult.Page(
                data = movieList.body()!!.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (movieList.body() == null) null else movieList.body()!!.page + 1
            )
        } catch (exception: IOException) {
            Timber.e("exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Timber.e("httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }
}