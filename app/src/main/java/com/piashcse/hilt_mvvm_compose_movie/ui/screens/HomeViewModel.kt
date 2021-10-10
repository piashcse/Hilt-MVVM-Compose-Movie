package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val movies: MutableState<BaseModel?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val error : MutableState<String?> = mutableStateOf(null)

    fun getMovieList() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repo.getRepositoryList()
               if (response.isSuccessful){
                   loading.value = false
                   val result  = response.body() as BaseModel
                   movies.value = result
               }else{
                   loading.value = false
                   error.value = response.errorBody().toString()
               }

            } catch (e: Throwable) {
                error.value = e.message
            }
        }
    }
}