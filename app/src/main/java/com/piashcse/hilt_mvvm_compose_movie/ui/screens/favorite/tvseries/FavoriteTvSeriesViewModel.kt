package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.tvseries.LocalTvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteTvSeriesViewModel @Inject constructor(
    private val localTvSeriesRepo: LocalTvSeriesRepository,
) : ViewModel() {

    val favoriteTvSeries: StateFlow<List<TvSeriesDetail>> = localTvSeriesRepo.favoriteTvSeries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun removeTvSeriesFromDB(tvSeriesId: Int) {
        viewModelScope.launch {
            localTvSeriesRepo.removeTvSeriesById(tvSeriesId)
        }
    }
}
