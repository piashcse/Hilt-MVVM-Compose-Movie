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

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null // ✅ Added field
)

data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val artistMovies: List<ArtistMovie>? = null,
    val isLoading: Boolean = false,
    val errorMessage:  String? = null // ✅ Added error message field
)

data class TvSeriesDetailUiState(
    val tvSeriesDetail: TvSeriesDetail? = null,
    val recommendedTvSeries: List<TvSeriesItem> = emptyList(),
    val tvSeriesCredit: Artist? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null // ✅ Added field
)
