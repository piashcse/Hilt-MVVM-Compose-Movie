package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovie
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.navigation.MovieDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.TvSeriesDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ErrorState
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExpandingText
import com.piashcse.hilt_mvvm_compose_movie.ui.component.PosterImage
import com.piashcse.hilt_mvvm_compose_movie.utils.asDisplayDate
import com.piashcse.hilt_mvvm_compose_movie.utils.genderRes

@Composable
fun ArtistDetail(navController: Navigator, personId: Int) {
    val viewModel = hiltViewModel<ArtistDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(personId) {
        viewModel.fetchArtistDetails(personId)
    }

    val detail = uiState.artistDetail
    when {
        uiState.errorMessage != null && detail == null -> {
            ErrorState(message = uiState.errorMessage.orEmpty(), onRetry = viewModel::retry)
        }

        detail == null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (uiState.isLoading) CircularProgressIndicator()
            }
        }

        else -> {
            ArtistDetailContent(
                detail = detail,
                movies = uiState.artistMovies.orEmpty(),
                onMovieClick = { movie ->
                    if (movie.mediaType == "movie") {
                        navController.navigate(MovieDetailRoute(movie.id))
                    } else {
                        navController.navigate(TvSeriesDetailRoute(movie.id))
                    }
                },
            )
        }
    }
}

@Composable
private fun ArtistDetailContent(
    detail: ArtistDetail,
    movies: List<ArtistMovie>,
    onMovieClick: (ArtistMovie) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp),
    ) {
        Row {
            PosterImage(
                path = detail.profilePath,
                contentDescription = stringResource(
                    R.string.profile_content_description,
                    detail.name,
                ),
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(2f / 3f),
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = detail.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                PersonalInfo(stringResource(R.string.know_for), detail.knownForDepartment)
                val gender = detail.gender.genderRes()
                if (gender != 0) {
                    PersonalInfo(stringResource(R.string.gender), stringResource(gender))
                }
                detail.birthday.asDisplayDate()?.let {
                    PersonalInfo(stringResource(R.string.birth_day), it)
                }
                detail.placeOfBirth?.let {
                    PersonalInfo(stringResource(R.string.place_of_birth), it)
                }
            }
        }

        if (detail.biography.isNotBlank()) {
            Text(
                text = stringResource(R.string.biography),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            )
            ExpandingText(text = detail.biography, visibleLines = 8)
        }

        if (movies.isNotEmpty()) {
            Text(
                text = stringResource(R.string.artist_movies),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(movies, key = { "${it.mediaType}-${it.id}" }) { movie ->
                    Column(
                        modifier = Modifier
                            .width(110.dp)
                            .clickable { onMovieClick(movie) },
                    ) {
                        PosterImage(
                            path = movie.posterPath,
                            contentDescription = stringResource(
                                R.string.poster_content_description,
                                movie.title,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f / 3f),
                        )
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = info,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
