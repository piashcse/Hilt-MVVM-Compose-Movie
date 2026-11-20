package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity

import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface CelebrityRepository {
    fun popularCelebrities(): Flow<PagingData<Celebrity>>
    fun trendingCelebrities(): Flow<PagingData<Celebrity>>
    suspend fun searchCelebrity(searchKey: String): Flow<DataState<SearchBaseModel>>
}
