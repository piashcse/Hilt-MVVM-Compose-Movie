package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import retrofit2.HttpException
import java.io.IOException

/**
 * Single generic [PagingSource] used by every list in the app. Callers only
 * provide the endpoint-specific loader, which keeps paging/error handling in
 * one place instead of one class per screen.
 */
class ApiPagingSource<T : Any>(
    private val genreId: String? = null,
    private val loadPage: suspend (page: Int, genreId: String?) -> BaseModel<T>,
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? =
        state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = loadPage(page, genreId)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.results.isNotEmpty() && page < response.totalPages) {
                    page + 1
                } else {
                    null
                },
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
