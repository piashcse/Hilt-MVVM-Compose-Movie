package com.piashcse.hilt_mvvm_compose_movie.ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Shared favorites state for the movie and TV favorite screens.
 */
abstract class FavoriteListViewModel<T : Any>(
    favorites: Flow<List<T>>,
    private val removeFavorite: suspend (Int) -> Unit,
) : ViewModel() {

    val favorites: StateFlow<List<T>> = favorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun remove(id: Int) {
        viewModelScope.launch { removeFavorite(id) }
    }
}
