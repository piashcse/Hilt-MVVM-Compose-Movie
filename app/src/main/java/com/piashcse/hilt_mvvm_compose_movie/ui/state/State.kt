package com.piashcse.hilt_mvvm_compose_movie.ui.state


data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)