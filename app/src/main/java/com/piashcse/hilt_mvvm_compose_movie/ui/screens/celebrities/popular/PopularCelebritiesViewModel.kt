package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.celebrity.CelebrityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PopularCelebritiesViewModel @Inject constructor(val repo: CelebrityRepository) : ViewModel() {
    val filterData = MutableStateFlow<GenreId?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val popularCelebrities = filterData.flatMapLatest {
        repo.popularCelebrities(1)
    }.cachedIn(viewModelScope)

}