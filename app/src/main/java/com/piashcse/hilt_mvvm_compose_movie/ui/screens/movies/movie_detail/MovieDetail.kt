package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.ArtistDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.DetailInfo
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailLayout
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.RecommendedMedia
import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.asDuration
import com.piashcse.hilt_mvvm_compose_movie.utils.asLanguageName
import com.piashcse.hilt_mvvm_compose_movie.utils.asRating

@Composable
fun MovieDetail(navController: Navigator, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    val detail = uiState.movieDetail
    val rating = detail?.let {
        stringResource(R.string.rating_value, it.voteAverage.asRating())
    }

    MediaDetailLayout(
        title = detail?.title.orEmpty(),
        backdropPath = detail?.backdropPath,
        posterPath = detail?.posterPath,
        info = listOf(
            DetailInfo(R.string.duration, detail?.runtime?.takeIf { it > 0 }?.asDuration()),
            DetailInfo(R.string.release_date, detail?.releaseDate.asDisplayDate()),
            DetailInfo(R.string.language, detail?.originalLanguage.asLanguageName()),
            DetailInfo(R.string.rating, rating),
        ),
        overview = detail?.overview.orEmpty(),
        isFavorite = uiState.isFavorite,
        onFavoriteClick = { detail?.let(viewModel::toggleFavorite) },
        recommended = uiState.recommendedMovies.map {
            RecommendedMedia(id = it.id, title = it.title, posterPath = it.posterPath)
        },
        onRecommendedClick = { navController.navigate(MovieDetailRoute(it)) },
        cast = uiState.movieCredit?.cast.orEmpty(),
        onCastClick = { navController.navigate(ArtistDetailRoute(it)) },
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onRetry = viewModel::retry,
    )
}
