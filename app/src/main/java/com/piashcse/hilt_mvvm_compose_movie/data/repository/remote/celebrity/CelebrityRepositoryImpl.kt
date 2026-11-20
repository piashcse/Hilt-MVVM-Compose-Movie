package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity

import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.data.repository.pagedData
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CelebrityRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : CelebrityRepository {

    override fun popularCelebrities(): Flow<PagingData<Celebrity>> =
        pagedData { page, _ -> apiService.popularCelebrities(page) }

    override fun trendingCelebrities(): Flow<PagingData<Celebrity>> =
        pagedData { page, _ -> apiService.trendingCelebrities(page) }

    override suspend fun searchCelebrity(searchKey: String): Flow<DataState<SearchBaseModel>> =
        safeApiCall { apiService.searchCelebrity(searchKey) }
}
