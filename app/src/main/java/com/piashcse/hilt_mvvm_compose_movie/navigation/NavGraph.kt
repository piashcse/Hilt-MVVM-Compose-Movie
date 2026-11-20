package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
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
    navigator: Navigator,
    genres: List<Genre>? = null,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = navigator.backStack,
        modifier = modifier,
        onBack = { navigator.popBackStack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NowPlayingMovieRoute> {
                NowPlayingMovie(navController = navigator, genres = genres)
            }
            entry<PopularMovieRoute> {
                PopularMovie(navController = navigator, genres = genres)
            }
            entry<TopRatedMovieRoute> {
                TopRatedMovie(navController = navigator, genres = genres)
            }
            entry<UpcomingMovieRoute> {
                UpcomingMovie(navController = navigator, genres = genres)
            }
            entry<MovieDetailRoute> { route ->
                MovieDetail(navController = navigator, movieId = route.movieId)
            }
            entry<ArtistDetailRoute> { route ->
                ArtistDetail(navController = navigator, personId = route.artistId)
            }
            entry<AiringTodayTvSeriesRoute> {
                AiringTodayTvSeries(navController = navigator, genres = genres)
            }
            entry<OnTheAirTvSeriesRoute> {
                OnTheAirTvSeries(navController = navigator, genres = genres)
            }
            entry<PopularTvSeriesRoute> {
                PopularTvSeries(navController = navigator, genres = genres)
            }
            entry<TopRatedTvSeriesRoute> {
                TopRatedTvSeries(navController = navigator, genres = genres)
            }
            entry<TvSeriesDetailRoute> { route ->
                TvSeriesDetail(navController = navigator, tvSeriesId = route.seriesId)
            }
            entry<FavoriteMovieRoute> {
                FavoriteMovie(navController = navigator)
            }
            entry<FavoriteTvSeriesRoute> {
                FavoriteTvSeries(navController = navigator)
            }
            entry<PopularCelebritiesRoute> {
                PopularCelebrities(navController = navigator)
            }
            entry<TrendingCelebritiesRoute> {
                TrendingCelebrities(navController = navigator)
            }
        }
    )
}

@Composable
fun navigationTitle(navigator: Navigator): String {
    return when (navigator.currentKey) {
        NowPlayingMovieRoute -> stringResource(R.string.now_playing)
        PopularMovieRoute, PopularTvSeriesRoute, PopularCelebritiesRoute ->
            stringResource(R.string.popular)

        TopRatedMovieRoute, TopRatedTvSeriesRoute -> stringResource(R.string.top_rated)
        UpcomingMovieRoute -> stringResource(R.string.up_coming)
        AiringTodayTvSeriesRoute -> stringResource(R.string.airing_today)
        OnTheAirTvSeriesRoute -> stringResource(R.string.on_the_air)
        TrendingCelebritiesRoute -> stringResource(R.string.trending)
        is MovieDetailRoute -> stringResource(R.string.movie_detail)
        is TvSeriesDetailRoute -> stringResource(R.string.tv_series_detail)
        is ArtistDetailRoute -> stringResource(R.string.artist_detail)
        FavoriteMovieRoute -> stringResource(R.string.favorite_movie)
        FavoriteTvSeriesRoute -> stringResource(R.string.favorite_tv_series)
        else -> stringResource(R.string.app_name)
    }
}
