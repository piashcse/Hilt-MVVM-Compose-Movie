package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Shared implementation for every paged list screen: a genre filter that
 * re-creates the paging flow. Loading/error/empty UI is derived directly from
 * Paging in [com.piashcse.hilt_mvvm_compose_movie.ui.component.MediaGrid].
 */
abstract class MediaListViewModel<T : Any>(
    pagingData: (String?) -> Flow<PagingData<T>>,
) : ViewModel() {

    private val genreId = MutableStateFlow<String?>(null)

    /** Currently selected genre id, or null for "All". */
    val selectedGenreId: StateFlow<String?> = genreId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: Flow<PagingData<T>> = genreId
        .flatMapLatest(pagingData)
        .cachedIn(viewModelScope)

    fun onGenreSelected(genreId: Int?) {
        this.genreId.value = genreId?.toString()
    }
}
