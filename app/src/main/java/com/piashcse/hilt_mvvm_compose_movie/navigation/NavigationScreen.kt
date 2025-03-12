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
    data object NowPlaying : Screen("now_playing_movie")
    data object Popular : Screen("popular_movie")
    data object TopRated : Screen("top_rated_movie")
    data object Upcoming : Screen("upcoming_movie")
    data object AiringTodayTvSeries : Screen("airing_today_tv_series")
    data object OnTheAirTvSeries : Screen("on_the_air_tv_series")
    data object PopularTvSeries : Screen("popular_tv_series")
    data object TopRatedTvSeries : Screen("top_rated_tv_series")
    data object PopularCelebrities : Screen("popular_celebrities")
    data object TrendingCelebrities : Screen("trending_celebrities")

    data object MovieDetail :
        Screen("movie_detail", objectName = "movieItem", objectPath = "/{movieItem}")

    data object TvSeriesDetail :
        Screen("tv_series_detail", objectName = "tvSeriesItem", objectPath = "/{tvSeriesItem}")

    data object ArtistDetail :
        Screen("artist_detail", objectName = "artistId", objectPath = "/{artistId}")

    // Bottom Navigation
    data object NowPlayingNav : Screen("now_playing_movie", title = R.string.now_playing, navIcon = {
        Icon(
            Icons.Filled.Home,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object PopularNav : Screen("popular_movie", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object TopRatedNav : Screen("top_rated_movie", title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })

    data object UpcomingNav : Screen("upcoming_movie", title = R.string.up_coming, navIcon = {
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "search",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object AiringTodayTvSeriesNav : Screen("airing_today_tv_series", title = R.string.airing_today, navIcon = {
        Icon(
            Icons.Filled.LiveTv,
            contentDescription = "Home",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object OnTheAirTvSeriesNav : Screen("on_the_air_tv_series", title = R.string.on_the_air, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "Timeline",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object PopularTvSeriesNav : Screen("popular_tv_series", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Star",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object TopRatedTvSeriesNav : Screen("top_rated_tv_series", title = R.string.top_rated, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object PopularCelebritiesNav : Screen("popular_celebrities", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object TrendingCelebritiesNav : Screen("trending_celebrities", title = R.string.trending, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "KeyboardArrowDown",
            modifier = Modifier
                .padding(end = 16.dp)
                .offset(x = 10.dp)
        )
    })
    data object FavoriteMovie :
        Screen("favorite_movie")
    data object FavoriteTvSeries :
        Screen("favorite_tv_series")
}