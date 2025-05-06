package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tvseries.airing_today


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.state.UiState
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class AiringTodayTvSeriesViewModel @Inject constructor(
    private val repo: TvSeriesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    var selectedGenre = mutableStateOf(Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    val filterData = MutableStateFlow<GenreId?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val airingTodayTvSeries = filterData.flatMapLatest {
        repo.airingTodayTvSeriesPagingDataSource(it?.genreId)
    }.cachedIn(viewModelScope)

    fun onGenreSelected(genre: Genre?) {
        filterData.value = GenreId(genre?.id.toString())
        genre?.let {
            selectedGenre.value = it
        }
    }

    fun updateLoadState(loadState: CombinedLoadStates) {
        val isLoading = loadState.refresh is LoadState.Loading
        val errorMessage = (loadState.refresh as? LoadState.Error)?.error?.message

        _uiState.value = UiState(
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }
}