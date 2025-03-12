package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity

import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.model.celebrities.Celebrity
import kotlinx.coroutines.flow.Flow

interface CelebrityRepositoryInterface {
    fun popularCelebrities(page: Int): Flow<PagingData<Celebrity>>
    fun trendingCelebrities(page: Int): Flow<PagingData<Celebrity>>
}