package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries.LocalTvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.FavoriteListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvSeriesViewModel @Inject constructor(
    repository: LocalTvSeriesRepository,
) : FavoriteListViewModel<TvSeriesDetail>(
    favorites = repository.favoriteTvSeries(),
    removeFavorite = repository::removeTvSeriesById,
)
