package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteMovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
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
class MovieDetailViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val movieDetailDao: FavoriteMovieDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private var movieId: Int? = null
    private var loadJob: Job? = null

    fun fetchMovieDetails(movieId: Int) {
        if (this.movieId == movieId) return
        this.movieId = movieId
        observeFavoriteStatus(movieId)
        load(movieId)
    }

    fun retry() {
        movieId?.let(::load)
    }

    private fun load(movieId: Int) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            launch {
                repo.movieDetail(movieId).collect { result ->
                    handleStateUpdate(result) { state, data -> state.copy(movieDetail = data) }
                }
            }
            launch {
                repo.recommendedMovie(movieId).collect { result ->
                    handleStateUpdate(result) { state, data ->
                        state.copy(recommendedMovies = data.orEmpty())
                    }
                }
            }
            launch {
                repo.movieCredit(movieId).collect { result ->
                    handleStateUpdate(result) { state, data -> state.copy(movieCredit = data) }
                }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: DataState<T>,
        stateUpdater: (MovieDetailUiState, T?) -> MovieDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is DataState.Loading -> currentState.copy(
                    // Only block the screen for the initial detail load; the
                    // recommendations and credits load in the background.
                    isLoading = currentState.movieDetail == null,
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

    fun toggleFavorite(movieDetail: MovieDetail) {
        viewModelScope.launch {
            val existing = movieDetailDao.getMovieDetailById(movieDetail.id)
            if (existing != null) {
                movieDetailDao.deleteMovieDetailById(movieDetail.id)
            } else {
                movieDetailDao.insert(movieDetail)
            }
        }
    }

    private fun observeFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            movieDetailDao.observeMovieDetailById(movieId).collect { movie ->
                _uiState.update { it.copy(isFavorite = movie != null) }
            }
        }
    }
}
