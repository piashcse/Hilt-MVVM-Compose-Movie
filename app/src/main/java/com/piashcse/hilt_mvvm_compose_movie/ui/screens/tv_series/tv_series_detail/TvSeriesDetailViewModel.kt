package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.tv_series_detail

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailViewModel @Inject constructor(
    repository: TvSeriesRepository,
    dao: FavoriteTvSeriesDao,
) : MediaDetailViewModel<TvSeriesDetail, TvSeriesItem>(
    loadDetail = repository::tvSeriesDetail,
    loadRelated = repository::recommendedTvSeries,
    loadCredits = repository::tvSeriesCredit,
    observeFavorite = { id -> dao.observeTvSeriesDetailById(id).map { it != null } },
    persistFavorite = { detail ->
        if (dao.getTvSeriesDetailById(detail.id) != null) {
            dao.deleteTvSeriesById(detail.id)
        } else {
            dao.insert(detail)
        }
    },
)
