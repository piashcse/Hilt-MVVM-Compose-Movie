package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries.LocalTvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteTvSeriesViewModel @Inject constructor(
    private val localTvSeriesRepo: LocalTvSeriesRepository,
) : ViewModel() {
    private val _favoriteTvSeries = MutableStateFlow<List<TvSeriesDetail?>>(arrayListOf())
    val favoriteTvSeries get() = _favoriteTvSeries.asStateFlow()

    fun favoriteTvSeriesFromDB() {
        viewModelScope.launch {
            _favoriteTvSeries.value = localTvSeriesRepo.favoriteTvSeries()
        }
    }

    fun removeTvSeriesFromDB(tvSeries: Int) {
        viewModelScope.launch {
            localTvSeriesRepo.removeTvSeriesById(tvSeries)
        }
    }
}