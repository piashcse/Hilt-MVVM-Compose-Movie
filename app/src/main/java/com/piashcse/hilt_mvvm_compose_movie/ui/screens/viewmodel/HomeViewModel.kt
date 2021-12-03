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
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val movies: MutableState<BaseModel?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf(null)

    @Inject
    lateinit var pagingDataSource: PagingDataSource
    val movie: Flow<PagingData<MovieItem>> = Pager(PagingConfig(pageSize = 5)) {
        pagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getMovieList(page: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.getRepositoryList(page)
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
}