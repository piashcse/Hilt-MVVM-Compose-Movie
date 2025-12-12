package com.piashcse.hilt_mvvm_compose_movie.navigation

import kotlinx.serialization.Serializable

@Serializable
object NowPlayingMovieRoute

@Serializable
object PopularMovieRoute

@Serializable
object TopRatedMovieRoute

@Serializable
object UpcomingMovieRoute

@Serializable
object AiringTodayTvSeriesRoute

@Serializable
object OnTheAirTvSeriesRoute

@Serializable
object PopularTvSeriesRoute

@Serializable
object TopRatedTvSeriesRoute

@Serializable
object PopularCelebritiesRoute

@Serializable
object TrendingCelebritiesRoute

@Serializable
data class MovieDetailRoute(val movieId: Int)

@Serializable
data class TvSeriesDetailRoute(val seriesId: Int)

@Serializable
data class ArtistDetailRoute(val artistId: Int)

@Serializable
object FavoriteMovieRoute

@Serializable
object FavoriteTvSeriesRoute
