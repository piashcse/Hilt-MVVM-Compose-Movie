package com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.MovieDetailDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val movieDetailDao: MovieDetailDao,
) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<MovieDetail?>>(arrayListOf())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    fun favoriteMoviesFromDB(){
        viewModelScope.launch{
            _favoriteMovies.value = movieDetailDao.getAllMovieDetails()
        }
    }
}