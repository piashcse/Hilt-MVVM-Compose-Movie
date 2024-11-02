package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    private val _artistDetail = MutableStateFlow<ArtistDetail?>(null)
    val artistDetail: StateFlow<ArtistDetail?> get ()= _artistDetail.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()


    fun artistDetail(personId: Int) {
        viewModelScope.launch {
            repo.artistDetail(personId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _artistDetail.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}