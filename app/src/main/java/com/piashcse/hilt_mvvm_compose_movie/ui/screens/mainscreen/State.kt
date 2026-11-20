package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH

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
    val error: Throwable? = null,
) {
    fun withQuery(type: Int, query: String): MainUiState = when (type) {
        TV_SERIES_SEARCH -> copy(tvSeriesSearchQuery = query)
        CELEBRITIES_SEARCH -> copy(celebritySearchQuery = query)
        else -> copy(movieSearchQuery = query)
    }

    fun withResults(type: Int, results: List<SearchItem>): MainUiState = when (type) {
        TV_SERIES_SEARCH -> copy(tvSeriesSearchResults = results)
        CELEBRITIES_SEARCH -> copy(celebritySearchResults = results)
        else -> copy(movieSearchResults = results)
    }

    fun withLoading(type: Int, loading: Boolean): MainUiState = when (type) {
        TV_SERIES_SEARCH -> copy(isTvSeriesSearchLoading = loading)
        CELEBRITIES_SEARCH -> copy(isCelebritySearchLoading = loading)
        else -> copy(isMovieSearchLoading = loading)
    }
}
