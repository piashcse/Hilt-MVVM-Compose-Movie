package com.piashcse.hilt_mvvm_compose_movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.ApiPagingSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 20

/**
 * Creates a paged flow for a TMDB endpoint. All list repositories use this so
 * the paging configuration stays identical across the app.
 */
fun <T : Any> pagedData(
    genreId: String? = null,
    loadPage: suspend (page: Int, genreId: String?) -> BaseModel<T>,
): Flow<PagingData<T>> = Pager(
    config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
    pagingSourceFactory = { ApiPagingSource(genreId, loadPage) },
).flow
