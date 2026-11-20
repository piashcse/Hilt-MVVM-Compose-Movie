package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.local.movie.LocalMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val localMovieRepo: LocalMovieRepository,
) : ViewModel() {

    val favoriteMovies: StateFlow<List<MovieDetail>> = localMovieRepo.favoriteMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun removeMovieFromDB(movieId: Int) {
        viewModelScope.launch {
            localMovieRepo.removeMovieById(movieId)
        }
    }
}
