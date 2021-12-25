package com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavDrawerViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    val genres: MutableState<DataState<Genres>?> = mutableStateOf(null)
    fun genreList() {
        viewModelScope.launch {
            repo.genreList().onEach {
                genres.value = it
            }.launchIn(viewModelScope)
        }
    }
}