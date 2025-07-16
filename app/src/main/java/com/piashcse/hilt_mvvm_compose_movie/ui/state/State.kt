package com.piashcse.hilt_mvvm_compose_movie.ui.state

import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.TvSeriesItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovie
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail


data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)