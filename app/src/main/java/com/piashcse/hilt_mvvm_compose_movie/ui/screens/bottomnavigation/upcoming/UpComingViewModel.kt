package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.upcoming


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor(repo: MovieRepository) : ViewModel() {
    val upcomingMovies = repo.upcomingPagingDataSource().cachedIn(viewModelScope)
}