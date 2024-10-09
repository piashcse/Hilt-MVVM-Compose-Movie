package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.repository.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.data.repository.TvSeriesRepository
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepo: MovieRepository, private val tvSeriesRepo: TvSeriesRepository) : ViewModel() {
    val genres: MutableState<DataState<Genres>?> = mutableStateOf(null)
    val movieSearchData: MutableState<DataState<SearchBaseModel>?> = mutableStateOf(null)
    val tvSeriesSearchData: MutableState<DataState<SearchBaseModel>?> = mutableStateOf(null)

    fun genreList() {
        viewModelScope.launch {
            movieRepo.genreList().onEach {
                genres.value = it
            }.launchIn(viewModelScope)
        }
    }
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchMovie(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    movieRepo.movieSearch(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                        Timber.e(" data ${it.data.totalPages}")
                    }
                    movieSearchData.value = it
                }
        }
    }
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchTvSeries(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    tvSeriesRepo.searchTvSeries(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                        Timber.e(" data ${it.data.totalPages}")
                    }
                    tvSeriesSearchData.value = it
                }
        }
    }
}