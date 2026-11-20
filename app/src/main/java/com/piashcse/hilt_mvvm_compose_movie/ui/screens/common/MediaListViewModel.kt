package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Shared implementation for every paged list screen: a genre filter that
 * re-creates the paging flow plus the load state consumed by the existing
 * list UI.
 */
abstract class MediaListViewModel<T : Any>(
    pagingData: (String?) -> Flow<PagingData<T>>,
) : ViewModel() {

    private val genreId = MutableStateFlow<String?>(null)
    private val _selectedGenre = MutableStateFlow<Genre?>(null)

    /** Currently selected genre, or null for no filter. */
    val selectedGenre: StateFlow<Genre?> = _selectedGenre.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: Flow<PagingData<T>> = genreId
        .flatMapLatest(pagingData)
        .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onGenreSelected(genre: Genre?) {
        val newGenreId = genre?.id?.toString()
        if (genre != null && genreId.value == newGenreId) {
            // Tapping the active genre clears the filter again.
            genreId.value = null
            _selectedGenre.value = null
        } else {
            genreId.value = newGenreId
            _selectedGenre.value = genre
        }
    }

    fun updateLoadState(loadState: CombinedLoadStates) {
        _uiState.value = UiState(
            isLoading = loadState.refresh is LoadState.Loading,
            errorMessage = (loadState.refresh as? LoadState.Error)?.error?.message,
        )
    }
}
