package com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging.PagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
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
    val movies: MutableState<BaseModel?> = mutableStateOf(null)
    val movieDetail: MutableState<MovieDetail?> = mutableStateOf(null)
    val recommendedMovie: MutableState<BaseModel?> = mutableStateOf(null)
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)

    @Inject
    lateinit var pagingDataSource: PagingDataSource
    val movie: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 5)) {
        pagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getMovieList(page: Int) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.movieList(page)
                if (response.isSuccessful) {
                    loading.value = false
                    val result = response.body() as BaseModel
                    movies.value = result
                } else {
                    loading.value = false
                    error.value = response.errorBody().toString()
                }

            } catch (e: Throwable) {
                error.value = e.message
            }
        }
    }

    fun movieDetailApi(movieId: Int) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.movieDetail(movieId)
                if (response.isSuccessful) {
                    loading.value = false
                    val result = response.body() as MovieDetail
                    movieDetail.value = result
                } else {
                    loading.value = false
                    error.value = response.errorBody().toString()
                }

            } catch (e: Throwable) {
                error.value = e.message
            }
        }
    }

    fun recommendedMovieApi(movieId: Int, page: Int) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.recommendedMovie(movieId, page)
                if (response.isSuccessful) {
                    loading.value = false
                    val result = response.body() as BaseModel
                    recommendedMovie.value = result
                } else {
                    loading.value = false
                    error.value = response.errorBody().toString()
                }

            } catch (e: Throwable) {
                error.value = e.message
            }
        }
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey:String){
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