package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.tv_series_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val uiState: StateFlow<TvSeriesDetailUiState> get() = _uiState.asStateFlow()

    fun fetchTvSeriesDetails(tvSeriesId: Int) {
        viewModelScope.launch {
            launch { updateTvSeriesDetail(tvSeriesId) }
            launch { updateRecommendedTvSeries(tvSeriesId) }
            launch { updateTvSeriesCredit(tvSeriesId) }
        }
    }

    private suspend fun updateTvSeriesDetail(tvSeriesId: Int) {
        repo.tvSeriesDetail(tvSeriesId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(tvSeriesDetail = data) }
        }
    }

    private suspend fun updateRecommendedTvSeries(tvSeriesId: Int) {
        repo.recommendedTvSeries(tvSeriesId).collect { result ->
            handleStateUpdate(result) { state, data ->
                state.copy(
                    recommendedTvSeries = data ?: emptyList()
                )
            }
        }
    }

    private suspend fun updateTvSeriesCredit(tvSeriesId: Int) {
        repo.artistDetail(tvSeriesId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(tvSeriesCredit = data) }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (TvSeriesDetailUiState, T?) -> TvSeriesDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(isLoading = true)
                is DataState.Success -> stateUpdater(
                    currentState, result.data
                ).copy(isLoading = false)

                is DataState.Error -> currentState.copy(isLoading = false) // Optionally log error details
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
            observeFavoriteStatus(tvSeriesDetail.id)
        }
    }

    fun observeFavoriteStatus(tvSeriesId: Int) {
        viewModelScope.launch {
            val isFavorite = tvSeriesDao.getTvSeriesDetailById(tvSeriesId) != null
            _uiState.update { currentState ->
                currentState.copy(isFavorite = isFavorite)
            }
        }
    }
}

data class TvSeriesDetailUiState(
    val tvSeriesDetail: TvSeriesDetail? = null,
    val recommendedTvSeries: List<TvSeriesItem> = emptyList(),
    val tvSeriesCredit: Artist? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
)