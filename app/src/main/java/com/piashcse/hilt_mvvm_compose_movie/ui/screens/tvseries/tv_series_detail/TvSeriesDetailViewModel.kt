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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailViewModel @Inject constructor(
    private val repo: TvSeriesRepository,
    private val tvSeriesDao: FavoriteTvSeriesDao,
) : ViewModel() {
    private val _tvSeriesDetail = MutableStateFlow<TvSeriesDetail?>(null)
    val tvSeriesDetail get() = _tvSeriesDetail.asStateFlow()

    private val _recommendedTvSeries = MutableStateFlow<List<TvSeriesItem>>(arrayListOf())
    val recommendedTvSeries get() = _recommendedTvSeries.asStateFlow()

    private val _tvSeriesCredit = MutableStateFlow<Artist?>(null)
    val tvSeriesCredit get() = _tvSeriesCredit.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun tvSeriesDetail(seriesId: Int) {
        viewModelScope.launch {
            repo.tvSeriesDetail(seriesId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _tvSeriesDetail.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun recommendedTvSeries(tvSeriesId: Int) {
        viewModelScope.launch {
            repo.recommendedTvSeries(tvSeriesId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _recommendedTvSeries.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun tvSeriesCredit(movieId: Int) {
        viewModelScope.launch {
            repo.artistDetail(movieId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _tvSeriesCredit.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun insertTvSeriesDetail(movieDetail: TvSeriesDetail) {
        viewModelScope.launch {
            tvSeriesDao.insert(movieDetail)
        }
    }

    suspend fun getTvSeriesDetailById(id: Int): TvSeriesDetail? {
        return tvSeriesDao.getTvSeriesDetailById(id)
    }

    fun deleteTvSeriesById(id: Int) {
        viewModelScope.launch {
            tvSeriesDao.deleteTvSeriesById(id)
        }
    }
}