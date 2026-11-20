package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object NowPlayingMovieRoute : NavKey

@Serializable
object PopularMovieRoute : NavKey

@Serializable
object TopRatedMovieRoute : NavKey

@Serializable
object UpcomingMovieRoute : NavKey

@Serializable
object AiringTodayTvSeriesRoute : NavKey

@Serializable
object OnTheAirTvSeriesRoute : NavKey

@Serializable
object PopularTvSeriesRoute : NavKey

@Serializable
object TopRatedTvSeriesRoute : NavKey

@Serializable
object PopularCelebritiesRoute : NavKey

@Serializable
object TrendingCelebritiesRoute : NavKey

@Serializable
data class MovieDetailRoute(val movieId: Int) : NavKey

@Serializable
data class TvSeriesDetailRoute(val seriesId: Int) : NavKey

@Serializable
data class ArtistDetailRoute(val artistId: Int) : NavKey

@Serializable
object FavoriteMovieRoute : NavKey

@Serializable
object FavoriteTvSeriesRoute : NavKey
