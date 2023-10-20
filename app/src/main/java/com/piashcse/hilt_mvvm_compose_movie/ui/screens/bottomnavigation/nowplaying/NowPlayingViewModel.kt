package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.nowplaying


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieEntity
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.mappers.toMovie
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre

import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor( val  pager: Pager<Int, MovieEntity>) : ViewModel() {
    var selectedGenre: MutableState<Genre> =
        mutableStateOf(Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    val filterData = MutableStateFlow<GenreId?>(null)

  /*  @OptIn(ExperimentalCoroutinesApi::class)
    val nowPlayingMovies = filterData.flatMapLatest {
        pager.nowPlayingPagingDataSource(it?.genreId)
    }.cachedIn(viewModelScope)*/
    val nowPlayingMovies = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toMovie() }
        }
        .cachedIn(viewModelScope)

}