package com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTvSeriesRepositoryImpl @Inject constructor(
    private val tvSeriesDao: FavoriteTvSeriesDao,
) : LocalTvSeriesRepository {

    override fun favoriteTvSeries(): Flow<List<TvSeriesDetail>> = tvSeriesDao.observeAll()

    override suspend fun removeTvSeriesById(tvSeriesId: Int) {
        tvSeriesDao.deleteTvSeriesById(tvSeriesId)
    }
}
