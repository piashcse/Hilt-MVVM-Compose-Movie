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
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.DetailInfo
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailLayout
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.RecommendedMedia
import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.asLanguageName
import com.piashcse.hilt_mvvm_compose_movie.utils.asRating

@Composable
fun TvSeriesDetail(navController: Navigator, tvSeriesId: Int) {
    val viewModel = hiltViewModel<TvSeriesDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(tvSeriesId) {
        viewModel.fetchTvSeriesDetails(tvSeriesId)
    }

    val detail = uiState.tvSeriesDetail
    val rating = detail?.let {
        stringResource(R.string.rating_value, it.voteAverage.asRating())
    }

    MediaDetailLayout(
        title = detail?.name.orEmpty(),
        backdropPath = detail?.backdropPath,
        posterPath = detail?.posterPath,
        info = listOf(
            DetailInfo(R.string.number_of_episodes, detail?.numberOfEpisodes?.toString()),
            DetailInfo(R.string.release_date, detail?.firstAirDate.asDisplayDate()),
            DetailInfo(R.string.language, detail?.originalLanguage.asLanguageName()),
            DetailInfo(R.string.rating, rating),
        ),
        overview = detail?.overview.orEmpty(),
        isFavorite = uiState.isFavorite,
        onFavoriteClick = { detail?.let(viewModel::toggleFavorite) },
        recommended = uiState.recommendedTvSeries.map {
            RecommendedMedia(id = it.id, title = it.name, posterPath = it.posterPath)
        },
        onRecommendedClick = { navController.navigate(TvSeriesDetailRoute(it)) },
        cast = uiState.tvSeriesCredit?.cast.orEmpty(),
        onCastClick = { navController.navigate(ArtistDetailRoute(it)) },
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onRetry = viewModel::retry,
    )
}
