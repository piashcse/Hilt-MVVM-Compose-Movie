package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
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
    private val repo: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> get() = _uiState.asStateFlow()

    fun fetchArtistDetails(personId: Int) {
        viewModelScope.launch {
            launch { updateArtistDetail(personId) }
        }
    }

    private suspend fun updateArtistDetail(personId: Int) {
        repo.artistDetail(personId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(artistDetail = data) }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (ArtistDetailUiState, T?) -> ArtistDetailUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(isLoading = true)
                is DataState.Success -> stateUpdater(
                    currentState,
                    result.data
                ).copy(isLoading = false)
                is DataState.Error -> currentState.copy(isLoading = false) // Optionally log error details
            }
        }
    }
}

data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val isLoading: Boolean = false
)
