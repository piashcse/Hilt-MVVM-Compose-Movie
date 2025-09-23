package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH

data class MainUiState(
    val genres: Genres? = null,
    val movieSearchResults: List<SearchItem> = emptyList(),
    val tvSeriesSearchResults: List<SearchItem> = emptyList(),
    val celebritySearchResults: List<SearchItem> = emptyList(),
    val movieSearchQuery: String = "",
    val tvSeriesSearchQuery: String = "",
    val celebritySearchQuery: String = "",
    val isMovieSearchLoading: Boolean = false,
    val isTvSeriesSearchLoading: Boolean = false,
    val isCelebritySearchLoading: Boolean = false,
    val selectedSearchType: Int = MOVIE_SEARCH,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)