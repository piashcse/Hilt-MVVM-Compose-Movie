package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val tvSeriesRepo: TvSeriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get()  = _uiState.asStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            movieRepo.genreList()
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = exception)
                }
                .collect { result ->
                    when (result) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(
                                genres = result.data, // Extracting Genres object
                                isLoading = false
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.exception
                            )
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchMovies(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey)
                .debounce(300)
                .filter { it.trim().isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { query -> movieRepo.movieSearch(query) }
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = exception)
                }
                .collect { result ->
                    val movieResults =
                        if (result is DataState.Success) result.data.results else emptyList()
                    _uiState.value = _uiState.value.copy(
                        movieSearchResults = movieResults,
                        isLoading = false
                    )
                }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchTvSeries(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey)
                .debounce(300)
                .filter { it.trim().isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { query -> tvSeriesRepo.searchTvSeries(query) }
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = exception)
                }
                .collect { result ->
                    val tvSeriesResults =
                        if (result is DataState.Success) result.data.results else emptyList()
                    _uiState.value = _uiState.value.copy(
                        tvSeriesSearchResults = tvSeriesResults,
                        isLoading = false
                    )
                }
        }
    }
}
data class MainUiState(
    val genres: Genres? = null,
    val movieSearchResults: List<SearchItem> = emptyList(),
    val tvSeriesSearchResults: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
