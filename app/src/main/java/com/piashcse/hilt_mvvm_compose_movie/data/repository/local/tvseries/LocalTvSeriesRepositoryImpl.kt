package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import javax.inject.Inject

class LocalTvSeriesRepositoryImpl @Inject constructor(
    private val tvSeriesDao: FavoriteTvSeriesDao,
) : LocalTvSeriesRepository {
    override suspend fun favoriteTvSeries(): List<TvSeriesDetail?> {
        return tvSeriesDao.getAllTvSeriesDetails()
    }

    override suspend fun removeTvSeriesById(tvSeriesId: Int) {
        tvSeriesDao.deleteTvSeriesById(tvSeriesId)
    }
}