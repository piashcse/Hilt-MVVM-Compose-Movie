package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.MovieDetailDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repo: MovieRepository, private val movieDetailDao: MovieDetailDao) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _recommendedMovie = MutableStateFlow<List<MovieItem>>(arrayListOf())
    val recommendedMovie get() = _recommendedMovie.asStateFlow()

    private val _movieCredit = MutableStateFlow<Artist?>(null)
    val movieCredit get() = _movieCredit.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.movieDetail(movieId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _movieDetail.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun recommendedMovie(movieId: Int) {
        viewModelScope.launch {
            repo.recommendedMovie(movieId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _recommendedMovie.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun movieCredit(movieId: Int) {
        viewModelScope.launch {
            repo.movieCredit(movieId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _movieCredit.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun insertMovieDetail(movieDetail: MovieDetail) {
        viewModelScope.launch {
            movieDetailDao.insert(movieDetail)
        }
    }

    suspend fun getMovieDetailById(id: Int): MovieDetail? {
        return movieDetailDao.getMovieDetailById(id)
    }

    fun clearAllMovieDetails() {
        viewModelScope.launch {
            movieDetailDao.clearAll()
        }
    }
}