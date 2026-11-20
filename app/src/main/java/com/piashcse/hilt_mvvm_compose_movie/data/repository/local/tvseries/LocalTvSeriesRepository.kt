package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries

import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import kotlinx.coroutines.flow.Flow

interface LocalTvSeriesRepository {
    fun favoriteTvSeries(): Flow<List<TvSeriesDetail>>
    suspend fun removeTvSeriesById(tvSeriesId: Int)
}
