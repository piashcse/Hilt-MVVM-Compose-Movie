package com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.paging.*
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
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
    val movies: MutableState<DataState<BaseModel>?> = mutableStateOf(null)
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)

    @Inject
    lateinit var pagingDataSource: NowPlayingPagingDataSource

    @Inject
    lateinit var popularPagingDataSource: PopularPagingDataSource

    @Inject
    lateinit var topRatedPagingDataSource: TopRatedPagingDataSource

    @Inject
    lateinit var upcomingPagingDataSource: UpcomingPagingDataSource

    @Inject
    lateinit var apiService: ApiService

    val nowPlayingMovies: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 1)) {
        pagingDataSource
    }.flow.cachedIn(viewModelScope)

    val popularMovies: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 1)) {
        popularPagingDataSource
    }.flow.cachedIn(viewModelScope)

    val topRatedMovies: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 1)) {
        topRatedPagingDataSource
    }.flow.cachedIn(viewModelScope)

    val upcomingMovies: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 1)) {
        upcomingPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun moviesByGenre(genreId: String): Flow<PagingData<MovieItem>> =
        Pager(PagingConfig(pageSize = 1)) {
            GenrePagingDataSource(apiService, genreId)
        }.flow.cachedIn(viewModelScope)


    fun getMovieList(page: Int) {
        viewModelScope.launch {
            repo.movieList(page).onEach {
                movies.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun moviesByGenreId(page: Int, genreId: String) {
        viewModelScope.launch {
            repo.moviesByGenreId(page, genreId).onEach {
                movies.value = it
            }.launchIn(viewModelScope)
        }
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