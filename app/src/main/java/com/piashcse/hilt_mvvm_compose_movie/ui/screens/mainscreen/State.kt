package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchItem
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH

data class SearchState(
    val query: String = "",
    val results: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class MainUiState(
    val genres: Genres? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val selectedSearchType: Int = MOVIE_SEARCH,
    val movieSearch: SearchState = SearchState(),
    val tvSearch: SearchState = SearchState(),
    val celebritySearch: SearchState = SearchState(),
) {
    fun searchFor(type: Int): SearchState = when (type) {
        TV_SERIES_SEARCH -> tvSearch
        CELEBRITIES_SEARCH -> celebritySearch
        else -> movieSearch
    }

    fun updateSearch(type: Int, update: (SearchState) -> SearchState): MainUiState = when (type) {
        TV_SERIES_SEARCH -> copy(tvSearch = update(tvSearch))
        CELEBRITIES_SEARCH -> copy(celebritySearch = update(celebritySearch))
        else -> copy(movieSearch = update(movieSearch))
    }
}
