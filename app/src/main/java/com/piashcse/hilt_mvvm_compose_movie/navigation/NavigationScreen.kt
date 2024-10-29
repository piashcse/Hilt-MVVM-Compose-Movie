package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piashcse.hilt_mvvm_compose_movie.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    object NowPlaying : Screen("now_playing_movie")
    object Popular : Screen("popular_movie")
    object TopRated : Screen("top_rated_movie")
    object Upcoming : Screen("upcoming_movie")
    object AiringTodayTvSeries : Screen("airing_today_tv_series")
    object OnTheAirTvSeries : Screen("on_the_air_tv_series")
    object PopularTvSeries : Screen("popular_tv_series")
    object TopRatedTvSeries : Screen("top_rated_tv_series")

    object MovieDetail :
        Screen("movie_detail", objectName = "movieItem", objectPath = "/{movieItem}")

    object TvSeriesDetail :
        Screen("tv_series_detail", objectName = "tvSeriesItem", objectPath = "/{tvSeriesItem}")

    object ArtistDetail :
        Screen("artist_detail", objectName = "artistId", objectPath = "/{artistId}")

    // Bottom Navigation
    object NowPlayingNav : Screen("now_playing_movie", title = R.string.now_playing, navIcon = {
        Icon(
            Icons.Filled.Home,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object PopularNav : Screen("popular_movie", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object TopRatedNav : Screen("top_rated_movie", title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    object UpcomingNav : Screen("upcoming_movie", title = R.string.up_coming, navIcon = {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    object AiringTodayTvSeriesNav : Screen("airing_today_tv_series", title = R.string.airing_today, navIcon = {
        Icon(
            Icons.Filled.LiveTv,
            contentDescription = "Home",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    object OnTheAirTvSeriesNav : Screen("on_the_air_tv_series", title = R.string.on_the_air, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "Timeline",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    object PopularTvSeriesNav : Screen("popular_tv_series", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    object TopRatedTvSeriesNav : Screen("top_rated_tv_series", title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    object FavoriteMovie :
        Screen("favorite_movie")
    object FavoriteTvSeries :
        Screen("favorite_tv_series")
}