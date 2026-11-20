package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.airing_today

import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AiringTodayTvSeriesViewModel @Inject constructor(
    repository: TvSeriesRepository,
) : MediaListViewModel<TvSeriesItem>(repository::airingTodayTvSeriesPagingDataSource)
