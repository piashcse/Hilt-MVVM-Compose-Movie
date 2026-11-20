package com.piashcse.hilt_mvvm_compose_movie

import androidx.paging.PagingSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.ApiPagingSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ApiPagingSourceTest {

    private fun refresh(page: Int? = null) = PagingSource.LoadParams.Refresh<Int>(
        key = page,
        loadSize = 20,
        placeholdersEnabled = false,
    )

    @Test
    fun `first page exposes the next page when more results exist`() = runBlocking {
        val source = ApiPagingSource<String> { page, _ ->
            BaseModel(
                page = page,
                results = listOf("a", "b"),
                totalPages = 3,
                totalResults = 6,
            )
        }

        val result = source.load(refresh()) as PagingSource.LoadResult.Page

        assertEquals(listOf("a", "b"), result.data)
        assertNull(result.prevKey)
        assertEquals(2, result.nextKey)
    }

    @Test
    fun `last page has no next key`() = runBlocking {
        val source = ApiPagingSource<String> { page, _ ->
            BaseModel(
                page = page,
                results = listOf("c"),
                totalPages = 3,
                totalResults = 3,
            )
        }

        val result = source.load(refresh(page = 3)) as PagingSource.LoadResult.Page

        assertEquals(2, result.prevKey)
        assertNull(result.nextKey)
    }

    @Test
    fun `empty page stops pagination`() = runBlocking {
        val source = ApiPagingSource<String> { page, _ ->
            BaseModel(page = page, results = emptyList(), totalPages = 5, totalResults = 0)
        }

        val result = source.load(refresh(page = 2)) as PagingSource.LoadResult.Page

        assertNull(result.nextKey)
    }

    @Test
    fun `io errors are returned as load errors`() = runBlocking {
        val source = ApiPagingSource<String> { _, _ -> throw IOException("offline") }

        val result = source.load(refresh())

        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
