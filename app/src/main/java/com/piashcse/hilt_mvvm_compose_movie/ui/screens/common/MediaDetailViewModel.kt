package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MediaDetailUiState<T : Any, R : Any>(
    val detail: T? = null,
    val related: List<R> = emptyList(),
    val credits: Artist? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
)

/**
 * Shared movie/TV detail state: detail + recommendations + credits are loaded
 * in parallel, and favorite state is observed from the local database.
 */
abstract class MediaDetailViewModel<T : Any, R : Any>(
    private val loadDetail: suspend (Int) -> Flow<DataState<T>>,
    private val loadRelated: suspend (Int) -> Flow<DataState<List<R>>>,
    private val loadCredits: suspend (Int) -> Flow<DataState<Artist>>,
    private val observeFavorite: (Int) -> Flow<Boolean>,
    private val persistFavorite: suspend (T) -> Unit,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MediaDetailUiState<T, R>())
    val uiState: StateFlow<MediaDetailUiState<T, R>> = _uiState.asStateFlow()

    private var mediaId: Int? = null
    private var loadJob: Job? = null

    fun fetch(id: Int) {
        if (mediaId == id) return
        mediaId = id
        viewModelScope.launch {
            observeFavorite(id).collect { favorite ->
                _uiState.update { it.copy(isFavorite = favorite) }
            }
        }
        load(id)
    }

    fun retry() {
        mediaId?.let(::load)
    }

    fun toggleFavorite(media: T) {
        viewModelScope.launch { persistFavorite(media) }
    }

    private fun load(id: Int) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            launch {
                loadDetail(id).collect { result ->
                    handle(result) { state, detail -> state.copy(detail = detail) }
                }
            }
            launch {
                loadRelated(id).collect { result ->
                    handle(result) { state, related ->
                        state.copy(related = related.orEmpty())
                    }
                }
            }
            launch {
                loadCredits(id).collect { result ->
                    handle(result) { state, credits -> state.copy(credits = credits) }
                }
            }
        }
    }

    private fun <D> handle(
        result: DataState<D>,
        update: (MediaDetailUiState<T, R>, D?) -> MediaDetailUiState<T, R>,
    ) {
        _uiState.update { state ->
            when (result) {
                is DataState.Loading -> state.copy(
                    isLoading = state.detail == null,
                    errorMessage = null,
                )

                is DataState.Success -> update(state, result.data)
                    .copy(isLoading = false, errorMessage = null)

                is DataState.Error -> state.copy(
                    isLoading = false,
                    errorMessage = result.exception.message ?: "Unknown error",
                )
            }
        }
    }
}
