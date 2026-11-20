package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.artist.ArtistRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.network.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    val uiState: StateFlow<ArtistDetailUiState> = _uiState.asStateFlow()

    private var personId: Int? = null
    private var loadJob: Job? = null

    fun fetchArtistDetails(personId: Int) {
        if (this.personId == personId) return
        this.personId = personId
        load(personId)
    }

    fun retry() {
        personId?.let(::load)
    }

    private fun load(personId: Int) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            launch {
                repo.artistDetail(personId).collect { result ->
                    handleStateUpdate(result) { state, data -> state.copy(artistDetail = data) }
                }
            }
            launch {
                repo.artistAllMovies(personId).collect { result ->
                    handleStateUpdate(result) { state, data ->
                        state.copy(artistMovies = data?.cast)
                    }
                }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (ArtistDetailUiState, T?) -> ArtistDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(
                    isLoading = currentState.artistDetail == null,
                    errorMessage = null,
                )

                is DataState.Success -> stateUpdater(currentState, result.data)
                    .copy(isLoading = false, errorMessage = null)

                is DataState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.toUserMessage(),
                )
            }
        }
    }
}
