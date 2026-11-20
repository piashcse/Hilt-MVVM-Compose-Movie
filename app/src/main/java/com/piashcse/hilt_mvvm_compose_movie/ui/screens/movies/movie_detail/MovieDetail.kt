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
import com.piashcse.hilt_mvvm_compose_movie.ui.component.MediaDetailShimmer
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.DetailFact
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailContent
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.RelatedMedia
import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.asDuration
import com.piashcse.hilt_mvvm_compose_movie.utils.asLanguageName
import com.piashcse.hilt_mvvm_compose_movie.utils.asYear
import component.base.BaseColumn

@Composable
fun MovieDetail(navController: Navigator, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    val movie = uiState.movieDetail

    BaseColumn(
        loading = uiState.isLoading && movie == null,
        errorMessage = uiState.errorMessage,
        loadingContent = { MediaDetailShimmer() },
    ) {
        movie?.let { detail ->
            val facts = listOfNotNull(
                detail.runtime.takeIf { it > 0 }?.let {
                    DetailFact(stringResource(R.string.runtime), it.asDuration())
                },
                detail.releaseDate.asDisplayDate()?.let {
                    DetailFact(stringResource(R.string.release_date), it)
                },
                uiState.movieCredit?.crew
                    ?.firstOrNull { it.job.equals("Director", ignoreCase = true) }
                    ?.name
                    ?.takeIf { it.isNotBlank() }
                    ?.let { DetailFact(stringResource(R.string.director), it) },
                detail.originalLanguage.asLanguageName()?.let {
                    DetailFact(stringResource(R.string.language), it)
                },
                detail.status.takeIf { it.isNotBlank() }?.let {
                    DetailFact(stringResource(R.string.status), it)
                },
            )

            MediaDetailContent(
                title = detail.title,
                originalTitle = detail.originalTitle
                    .takeIf { it.isNotBlank() && it != detail.title },
                tagline = detail.tagline.takeIf { it.isNotBlank() },
                overview = detail.overview,
                backdropPath = detail.backdropPath,
                posterPath = detail.posterPath,
                rating = detail.voteAverage,
                voteCount = detail.voteCount,
                year = detail.releaseDate.asYear(),
                genres = detail.genres.map { it.name },
                facts = facts,
                cast = uiState.movieCredit?.cast.orEmpty(),
                recommended = uiState.recommendedMovies.map {
                    RelatedMedia(id = it.id, title = it.title, posterPath = it.posterPath)
                },
                isFavorite = uiState.isFavorite,
                onFavoriteClick = { viewModel.toggleFavorite(detail) },
                onCastClick = { navController.navigate(ArtistDetailRoute(it)) },
                onRecommendedClick = { navController.navigate(MovieDetailRoute(it)) },
            )
        }
    }
}
