package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist.ArtistRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.state.ArtistDetailUiState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val repo: ArtistRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> get() = _uiState.asStateFlow()

    fun fetchArtistDetails(personId: Int) {
        viewModelScope.launch {
            launch { artistDetail(personId) }
            launch { artistMovies(personId) }
        }
    }

    private suspend fun artistDetail(personId: Int) {
        repo.artistDetail(personId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(artistDetail = data) }
        }
    }

    private suspend fun artistMovies(personId: Int) {
        repo.artistAllMovies(personId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(artistMovies = data?.cast) }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (ArtistDetailUiState, T?) -> ArtistDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(isLoading = true, errorMessage = null)
                is DataState.Success -> stateUpdater(
                    currentState, result.data
                ).copy(isLoading = false, errorMessage = null)

                is DataState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.message ?: "Unknown error"
                )
            }
        }
    }
}