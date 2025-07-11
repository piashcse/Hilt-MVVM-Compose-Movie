package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.celebrities.PopularCelebritiesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.celebrities.TrendingCelebritiesPagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CelebrityRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : CelebrityRepository {
    override fun popularCelebrities(page: Int): Flow<PagingData<Celebrity>> =
        Pager(
            pagingSourceFactory = { PopularCelebritiesPagingDataSource(apiService) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override fun trendingCelebrities(page: Int): Flow<PagingData<Celebrity>> =
        Pager(
            pagingSourceFactory = { TrendingCelebritiesPagingDataSource(apiService) },
            config = PagingConfig(pageSize = 20)
        ).flow

    override fun searchCelebrity(searchKey: String): Flow<DataState<SearchBaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.searchCelebrity(searchKey)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}