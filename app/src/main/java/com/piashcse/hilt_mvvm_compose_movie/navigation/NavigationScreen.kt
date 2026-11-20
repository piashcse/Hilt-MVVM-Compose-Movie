package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.piashcse.hilt_mvvm_compose_movie.R

sealed class Screen(
    val route: NavKey? = null,
    @StringRes val title: Int = R.string.app_name,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    private val matcher: ((NavKey?) -> Boolean)? = null,
) {
    /** Returns true when [key] is the screen described by this item. */
    fun matches(key: NavKey?): Boolean =
        matcher?.invoke(key) ?: (route != null && route == key)

    data object NowPlaying : Screen(NowPlayingMovieRoute)
    data object Popular : Screen(PopularMovieRoute)
    data object TopRated : Screen(TopRatedMovieRoute)
    data object Upcoming : Screen(UpcomingMovieRoute)
    data object AiringTodayTvSeries : Screen(AiringTodayTvSeriesRoute)
    data object OnTheAirTvSeries : Screen(OnTheAirTvSeriesRoute)
    data object PopularTvSeries : Screen(PopularTvSeriesRoute)
    data object TopRatedTvSeries : Screen(TopRatedTvSeriesRoute)
    data object PopularCelebrities : Screen(PopularCelebritiesRoute)
    data object TrendingCelebrities : Screen(TrendingCelebritiesRoute)

    data object MovieDetail : Screen(matcher = { it is MovieDetailRoute })
    data object TvSeriesDetail : Screen(matcher = { it is TvSeriesDetailRoute })
    data object ArtistDetail : Screen(matcher = { it is ArtistDetailRoute })

    // Bottom Navigation
    data object NowPlayingNav : Screen(NowPlayingMovieRoute, title = R.string.now_playing, navIcon = {
        Icon(
            Icons.Filled.Home,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object PopularNav : Screen(PopularMovieRoute, title = R.string.popular, navIcon = {
        Icon(
            Icons.AutoMirrored.Filled.List,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object TopRatedNav : Screen(TopRatedMovieRoute, title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object UpcomingNav : Screen(UpcomingMovieRoute, title = R.string.up_coming, navIcon = {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object AiringTodayTvSeriesNav : Screen(AiringTodayTvSeriesRoute, title = R.string.airing_today, navIcon = {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "Home",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object OnTheAirTvSeriesNav : Screen(OnTheAirTvSeriesRoute, title = R.string.on_the_air, navIcon = {
        Icon(
            Icons.AutoMirrored.Filled.List,
            contentDescription = "Timeline",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object PopularTvSeriesNav : Screen(PopularTvSeriesRoute, title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object TopRatedTvSeriesNav : Screen(TopRatedTvSeriesRoute, title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object PopularCelebritiesNav : Screen(PopularCelebritiesRoute, title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object TrendingCelebritiesNav : Screen(TrendingCelebritiesRoute, title = R.string.trending, navIcon = {
        Icon(
            Icons.AutoMirrored.Filled.List,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object FavoriteMovie :
        Screen(FavoriteMovieRoute)
    data object FavoriteTvSeries :
        Screen(FavoriteTvSeriesRoute)
}
