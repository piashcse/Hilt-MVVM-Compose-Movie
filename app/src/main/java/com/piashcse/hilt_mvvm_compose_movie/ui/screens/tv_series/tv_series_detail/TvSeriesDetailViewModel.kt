package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.tv_series_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailViewModel @Inject constructor(
    private val repo: TvSeriesRepository,
    private val tvSeriesDao: FavoriteTvSeriesDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TvSeriesDetailUiState())
    val uiState: StateFlow<TvSeriesDetailUiState> = _uiState.asStateFlow()

    private var tvSeriesId: Int? = null
    private var loadJob: Job? = null

    fun fetchTvSeriesDetails(tvSeriesId: Int) {
        if (this.tvSeriesId == tvSeriesId) return
        this.tvSeriesId = tvSeriesId
        observeFavoriteStatus(tvSeriesId)
        load(tvSeriesId)
    }

    fun retry() {
        tvSeriesId?.let(::load)
    }

    private fun load(tvSeriesId: Int) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            launch {
                repo.tvSeriesDetail(tvSeriesId).collect { result ->
                    handleStateUpdate(result) { state, data ->
                        state.copy(tvSeriesDetail = data)
                    }
                }
            }
            launch {
                repo.recommendedTvSeries(tvSeriesId).collect { result ->
                    handleStateUpdate(result) { state, data ->
                        state.copy(recommendedTvSeries = data.orEmpty())
                    }
                }
            }
            launch {
                repo.tvSeriesCredit(tvSeriesId).collect { result ->
                    handleStateUpdate(result) { state, data ->
                        state.copy(tvSeriesCredit = data)
                    }
                }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (TvSeriesDetailUiState, T?) -> TvSeriesDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(
                    isLoading = currentState.tvSeriesDetail == null,
                    errorMessage = null,
                )

                is DataState.Success -> stateUpdater(currentState, result.data)
                    .copy(isLoading = false, errorMessage = null)

                is DataState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.toUserMessage(),
                )
            }
        }
    }

    fun toggleFavorite(tvSeriesDetail: TvSeriesDetail) {
        viewModelScope.launch {
            val existing = tvSeriesDao.getTvSeriesDetailById(tvSeriesDetail.id)
            if (existing != null) {
                tvSeriesDao.deleteTvSeriesById(tvSeriesDetail.id)
            } else {
                tvSeriesDao.insert(tvSeriesDetail)
            }
        }
    }

    private fun observeFavoriteStatus(tvSeriesId: Int) {
        viewModelScope.launch {
            tvSeriesDao.observeTvSeriesDetailById(tvSeriesId).collect { series ->
                _uiState.update { it.copy(isFavorite = series != null) }
            }
        }
    }
}
