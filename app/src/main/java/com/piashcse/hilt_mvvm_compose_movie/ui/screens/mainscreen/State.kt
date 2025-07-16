package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem

data class MainUiState(
    val genres: Genres? = null,
    val movieSearchResults: List<SearchItem> = emptyList(),
    val tvSeriesSearchResults: List<SearchItem> = emptyList(),
    val celebritySearchResults: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)