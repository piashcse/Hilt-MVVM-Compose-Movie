package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepositoryImpl
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepo: MovieRepositoryImpl,
    private val tvSeriesRepo: TvSeriesRepositoryImpl,
    private val celebrityRepo: CelebrityRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get()  = _uiState.asStateFlow()

    fun setSelectedSearchType(type: Int) {
        _uiState.value = _uiState.value.copy(selectedSearchType = type)
    }

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

    fun searchMovies(searchKey: String) {
        // Update the search query
        _uiState.value = _uiState.value.copy(movieSearchQuery = searchKey)
        
        if (searchKey.trim().isEmpty()) {
            _uiState.value = _uiState.value.copy(
                movieSearchResults = emptyList(),
                isMovieSearchLoading = false
            )
            return
        }
        
        viewModelScope.launch {
            movieRepo.movieSearch(searchKey)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isMovieSearchLoading = false,
                        error = exception
                    )
                }
                .collect { result ->
                    when (result) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(
                                movieSearchResults = result.data.results,
                                isMovieSearchLoading = false
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isMovieSearchLoading = false,
                                error = result.exception
                            )
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isMovieSearchLoading = true,
                                error = null
                            )
                        }
                    }
                }
        }
    }

    fun searchTvSeries(searchKey: String) {
        // Update the search query
        _uiState.value = _uiState.value.copy(tvSeriesSearchQuery = searchKey)
        
        if (searchKey.trim().isEmpty()) {
            _uiState.value = _uiState.value.copy(
                tvSeriesSearchResults = emptyList(),
                isTvSeriesSearchLoading = false
            )
            return
        }
        
        viewModelScope.launch {
            tvSeriesRepo.searchTvSeries(searchKey)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isTvSeriesSearchLoading = false,
                        error = exception
                    )
                }
                .collect { result ->
                    when (result) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(
                                tvSeriesSearchResults = result.data.results,
                                isTvSeriesSearchLoading = false
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isTvSeriesSearchLoading = false,
                                error = result.exception
                            )
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isTvSeriesSearchLoading = true,
                                error = null
                            )
                        }
                    }
                }
        }
    }

    fun searchCelebrities(searchKey: String) {
        // Update the search query
        _uiState.value = _uiState.value.copy(celebritySearchQuery = searchKey)
        
        if (searchKey.trim().isEmpty()) {
            _uiState.value = _uiState.value.copy(
                celebritySearchResults = emptyList(),
                isCelebritySearchLoading = false
            )
            return
        }
        
        viewModelScope.launch {
            celebrityRepo.searchCelebrity(searchKey)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isCelebritySearchLoading = false,
                        error = exception
                    )
                }
                .collect { result ->
                    when (result) {
                        is DataState.Success -> {
                            _uiState.value = _uiState.value.copy(
                                celebritySearchResults = result.data.results,
                                isCelebritySearchLoading = false
                            )
                        }
                        is DataState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isCelebritySearchLoading = false,
                                error = result.exception
                            )
                        }
                        is DataState.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isCelebritySearchLoading = true,
                                error = null
                            )
                        }
                    }
                }
        }
    }
}
