package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries

import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail

interface LocalTvSeriesRepository {
    suspend fun favoriteTvSeries(): List<TvSeriesDetail?>
    suspend fun removeTvSeriesById(tvSeriesId: Int)
}