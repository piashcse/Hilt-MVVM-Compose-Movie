package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val tvSeriesRepo: TvSeriesRepository,
    private val celebrityRepo: CelebrityRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> get() = _uiState.asStateFlow()

    private val searchJobs = mutableMapOf<Int, Job>()

    fun setSelectedSearchType(type: Int) {
        _uiState.update { it.copy(selectedSearchType = type) }
    }

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            movieRepo.genreList().collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is DataState.Success -> state.copy(
                            genres = result.data,
                            isLoading = false,
                        )

                        is DataState.Error -> state.copy(
                            isLoading = false,
                            error = result.exception,
                        )

                        is DataState.Loading -> state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun searchMovies(searchKey: String) =
        search(MOVIE_SEARCH, searchKey, movieRepo::movieSearch)

    fun searchTvSeries(searchKey: String) =
        search(TV_SERIES_SEARCH, searchKey, tvSeriesRepo::searchTvSeries)

    fun searchCelebrities(searchKey: String) =
        search(CELEBRITIES_SEARCH, searchKey, celebrityRepo::searchCelebrity)

    /**
     * Debounced search shared by all three tabs. Cancelling the previous job
     * prevents an older response from overwriting a newer one.
     */
    private fun search(
        type: Int,
        query: String,
        search: suspend (String) -> Flow<DataState<SearchBaseModel>>,
    ) {
        _uiState.update { it.withQuery(type, query) }
        searchJobs[type]?.cancel()

        if (query.isBlank()) {
            _uiState.update { it.withResults(type, emptyList()).withLoading(type, false) }
            return
        }

        searchJobs[type] = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            search(query)
                .catch { error ->
                    _uiState.update { it.withLoading(type, false).copy(error = error) }
                }
                .collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is DataState.Loading -> state.withLoading(type, true)
                                .copy(error = null)

                            is DataState.Success -> state
                                .withResults(type, result.data.results)
                                .withLoading(type, false)

                            is DataState.Error -> state.withLoading(type, false)
                                .copy(error = result.exception)
                        }
                    }
                }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 350L
    }
}
