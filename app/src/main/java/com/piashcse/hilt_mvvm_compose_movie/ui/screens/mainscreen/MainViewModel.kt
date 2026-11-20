package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.tvseries.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    /** One debounced search job per search type; replaced on every keystroke. */
    private val searchJobs = mutableMapOf<Int, Job>()

    fun setSelectedSearchType(type: Int) {
        _uiState.update { it.copy(selectedSearchType = type) }
    }

    fun onSearchQueryChange(query: String) {
        val type = _uiState.value.selectedSearchType
        _uiState.update { state ->
            state.updateSearch(type) { search ->
                search.copy(
                    query = query,
                    results = if (query.isBlank()) emptyList() else search.results,
                    errorMessage = null,
                )
            }
        }

        // Cancel any in-flight request for this type so older responses cannot
        // overwrite newer ones.
        searchJobs[type]?.cancel()

        if (query.isBlank()) {
            _uiState.update { state ->
                state.updateSearch(type) { it.copy(isLoading = false, results = emptyList()) }
            }
            return
        }

        searchJobs[type] = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            val search: suspend (String) -> Flow<DataState<SearchBaseModel>> = when (type) {
                TV_SERIES_SEARCH -> tvSeriesRepo::searchTvSeries
                CELEBRITIES_SEARCH -> celebrityRepo::searchCelebrity
                else -> movieRepo::movieSearch
            }
            search(query).collect { result ->
                _uiState.update { state ->
                    state.updateSearch(type) { current ->
                        when (result) {
                            is DataState.Loading -> current.copy(isLoading = true)
                            is DataState.Success -> current.copy(
                                isLoading = false,
                                results = result.data.results,
                                errorMessage = null,
                            )

                            is DataState.Error -> current.copy(
                                isLoading = false,
                                results = emptyList(),
                                errorMessage = result.exception.toUserMessage(),
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearSearch() = onSearchQueryChange("")

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            movieRepo.genreList().collect { result ->
                when (result) {
                    is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is DataState.Success -> _uiState.update {
                        it.copy(genres = result.data, isLoading = false, error = null)
                    }

                    is DataState.Error -> _uiState.update {
                        it.copy(isLoading = false, error = result.exception)
                    }
                }
            }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 350L
    }
}
