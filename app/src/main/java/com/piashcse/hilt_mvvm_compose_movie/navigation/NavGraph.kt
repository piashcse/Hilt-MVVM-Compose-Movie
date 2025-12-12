package com.piashcse.hilt_mvvm_compose_movie.navigation

// Composable imports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular.PopularCelebrities
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.trending.TrendingCelebrities
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.movie.FavoriteMovie
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.favorite.tvseries.FavoriteTvSeries
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
    navController: NavHostController, genres: List<Genre>? = null,
) {
    NavHost(navController = navController, startDestination = NowPlayingMovieRoute) {
        composable<NowPlayingMovieRoute> {
            NowPlayingMovie(
                navController = navController,
                genres
            )
        }
        composable<PopularMovieRoute> {
            PopularMovie(
                navController = navController,
                genres
            )
        }
        composable<TopRatedMovieRoute> {
            TopRatedMovie(
                navController = navController,
                genres
            )
        }
        composable<UpcomingMovieRoute> {
            UpcomingMovie(
                navController = navController,
                genres
            )
        }
        composable<MovieDetailRoute> {
            val args = it.toRoute<MovieDetailRoute>()
            MovieDetail(
                navController = navController, args.movieId
            )
        }
        composable<ArtistDetailRoute> {
            val args = it.toRoute<ArtistDetailRoute>()
            ArtistDetail(
                navController = navController,
                args.artistId
            )
        }
        composable<AiringTodayTvSeriesRoute> {
            AiringTodayTvSeries(
                navController = navController,
                genres
            )
        }
        composable<OnTheAirTvSeriesRoute> {
            OnTheAirTvSeries(
                navController = navController,
                genres
            )
        }
        composable<PopularTvSeriesRoute> {
            PopularTvSeries(
                navController = navController,
                genres
            )
        }
        composable<TopRatedTvSeriesRoute> {
            TopRatedTvSeries(
                navController = navController,
                genres
            )
        }
        composable<TvSeriesDetailRoute> {
            val args = it.toRoute<TvSeriesDetailRoute>()
            TvSeriesDetail(
                navController = navController, args.seriesId
            )
        }
        composable<FavoriteMovieRoute> {
            FavoriteMovie(navController)
        }
        composable<FavoriteTvSeriesRoute> {
            FavoriteTvSeries(navController)
        }
        composable<PopularCelebritiesRoute> {
            PopularCelebrities(navController)
        }
        composable<TrendingCelebritiesRoute> {
            TrendingCelebrities(navController)
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination ?: return stringResource(R.string.app_name)

    return when {
        destination.hasRoute<MovieDetailRoute>() -> stringResource(id = R.string.movie_detail)
        destination.hasRoute<TvSeriesDetailRoute>() -> stringResource(id = R.string.tv_series_detail)
        destination.hasRoute<ArtistDetailRoute>() -> stringResource(id = R.string.artist_detail)
        destination.hasRoute<FavoriteMovieRoute>() -> stringResource(id = R.string.favorite_movie)
        destination.hasRoute<FavoriteTvSeriesRoute>() -> stringResource(id = R.string.favorite_tv_series)
        else -> stringResource(R.string.app_name)
    }
}


