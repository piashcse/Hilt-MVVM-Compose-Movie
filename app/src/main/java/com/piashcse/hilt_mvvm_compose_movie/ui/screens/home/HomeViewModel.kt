package com.piashcse.hilt_mvvm_compose_movie.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)

    fun nowPlayingMovies(): Flow<PagingData<MovieItem>> {
        return repo.nowPlayingPagingDataSource().cachedIn(viewModelScope)
    }

    fun popularMovies(): Flow<PagingData<MovieItem>> {
        return repo.popularPagingDataSource().cachedIn(viewModelScope)
    }

    fun topRatedMovies(): Flow<PagingData<MovieItem>> {
        return repo.topRatedPagingDataSource().cachedIn(viewModelScope)
    }

    fun upcomingMovies(): Flow<PagingData<MovieItem>> {
        return repo.upcomingPagingDataSource().cachedIn(viewModelScope)
    }

    fun moviesByGenre(genreId: String): Flow<PagingData<MovieItem>> {
        return repo.genrePagingDataSource(genreId).cachedIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.search(it)
                }.collect {
                    searchData.value = it
                }
        }
    }
}