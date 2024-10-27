package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.nowplaying.NowPlayingMovie
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.popular.PopularMovie
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.toprated.TopRatedMovie
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.upcoming.UpcomingMovie
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.airing_today.AiringTodayTvSeries
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.on_the_air.OnTheAirTvSeries
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.popular.PopularTvSeries
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.top_rated.TopRatedTvSeries
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.tv_series_detail.TvSeriesDetail

@Composable
fun Navigation(
    navController: NavHostController, page: Int,  genres: ArrayList<Genre>? = null,
) {
    NavHost(navController, startDestination = initialScreen(page)) {
        composable(Screen.NowPlaying.route) {
            NowPlayingMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.Popular.route) {
            PopularMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.TopRated.route) {
            TopRatedMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.Upcoming.route) {
            UpcomingMovie(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            movieId?.let {
                MovieDetail(
                    navController = navController, movieId
                )
            }
        }
        composable(
            Screen.ArtistDetail.route.plus(Screen.ArtistDetail.objectPath),
            arguments = listOf(navArgument(Screen.ArtistDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.artist_detail)
            val artistId = it.arguments?.getInt(Screen.ArtistDetail.objectName)
            artistId?.let {
                ArtistDetail(
                    artistId
                )
            }
        }
        composable(Screen.AiringTodayTvSeries.route) {
            AiringTodayTvSeries(
                navController = navController,
                genres
            )
        }
        composable(Screen.OnTheAirTvSeries.route) {
            OnTheAirTvSeries(
                navController = navController,
                genres
            )
        }
        composable(Screen.PopularTvSeries.route) {
            PopularTvSeries(
                navController = navController,
                genres
            )
        }
        composable(Screen.TopRatedTvSeries.route) {
            TopRatedTvSeries(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.TvSeriesDetail.route.plus(Screen.TvSeriesDetail.objectPath),
            arguments = listOf(navArgument(Screen.TvSeriesDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.tv_series_detail)
            val movieId = it.arguments?.getInt(Screen.TvSeriesDetail.objectName)
            movieId?.let {
                TvSeriesDetail(
                    navController = navController, movieId
                )
            }
        }
    }
}

fun initialScreen(page:Int): String {
    return if (page == 0) {
        Screen.NowPlaying.route
    } else {
        Screen.AiringTodayTvSeries.route
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.MovieDetail.route -> stringResource(id = R.string.movie_detail)
        Screen.TvSeriesDetail.route -> stringResource(id = R.string.tv_series_detail)
        Screen.ArtistDetail.route -> stringResource(id = R.string.artist_detail)
        else -> {
            stringResource(R.string.app_name)
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
