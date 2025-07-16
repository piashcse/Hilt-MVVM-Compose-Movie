package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovie

data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val artistMovies: List<ArtistMovie>? = null,
    val isLoading: Boolean = false,
    val errorMessage:  String? = null // ✅ Added error message field
)