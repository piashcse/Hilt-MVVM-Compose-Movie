package com.piashcse.hilt_mvvm_compose_movie.ui.screens.tv_series.tv_series_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.ArtistDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.MediaDetailShimmer
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.DetailFact
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailContent
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.RelatedMedia
import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.asLanguageName
import com.piashcse.hilt_mvvm_compose_movie.utils.asYear
import com.piashcse.hilt_mvvm_compose_movie.ui.component.base.BaseColumn

@Composable
fun TvSeriesDetail(navController: Navigator, tvSeriesId: Int) {
    val viewModel = hiltViewModel<TvSeriesDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(tvSeriesId) {
        viewModel.fetch(tvSeriesId)
    }

    val series = uiState.detail

    BaseColumn(
        loading = uiState.isLoading && series == null,
        errorMessage = uiState.errorMessage,
        loadingContent = { MediaDetailShimmer() },
    ) {
        series?.let { detail ->
            val facts = listOfNotNull(
                DetailFact(stringResource(R.string.seasons), detail.numberOfSeasons.toString()),
                DetailFact(stringResource(R.string.episodes), detail.numberOfEpisodes.toString()),
                detail.firstAirDate.asDisplayDate()?.let {
                    DetailFact(stringResource(R.string.first_air_date), it)
                },
                detail.lastAirDate.asDisplayDate()?.let {
                    DetailFact(stringResource(R.string.last_air_date), it)
                },
                detail.createdBy
                    .joinToString { it.name }
                    .takeIf { it.isNotBlank() }
                    ?.let { DetailFact(stringResource(R.string.creator), it) },
                detail.networks
                    .map { it.name }
                    .distinct()
                    .joinToString(", ")
                    .takeIf { it.isNotBlank() }
                    ?.let { DetailFact(stringResource(R.string.network), it) },
                detail.originalLanguage.asLanguageName()?.let {
                    DetailFact(stringResource(R.string.language), it)
                },
                detail.status.takeIf { it.isNotBlank() }?.let {
                    DetailFact(stringResource(R.string.status), it)
                },
            )

            MediaDetailContent(
                title = detail.name,
                originalTitle = detail.originalName
                    .takeIf { it.isNotBlank() && it != detail.name },
                tagline = detail.tagline.takeIf { it.isNotBlank() },
                overview = detail.overview,
                backdropPath = detail.backdropPath,
                posterPath = detail.posterPath,
                rating = detail.voteAverage,
                voteCount = detail.voteCount,
                year = detail.firstAirDate.asYear(),
                genres = detail.genres.map { it.name },
                facts = facts,
                cast = uiState.credits?.cast.orEmpty(),
                recommended = uiState.related.map {
                    RelatedMedia(id = it.id, title = it.name, posterPath = it.posterPath)
                },
                isFavorite = uiState.isFavorite,
                onFavoriteClick = { viewModel.toggleFavorite(detail) },
                onCastClick = { navController.navigate(ArtistDetailRoute(it)) },
                onRecommendedClick = { navController.navigate(TvSeriesDetailRoute(it)) },
            )
        }
    }
}
