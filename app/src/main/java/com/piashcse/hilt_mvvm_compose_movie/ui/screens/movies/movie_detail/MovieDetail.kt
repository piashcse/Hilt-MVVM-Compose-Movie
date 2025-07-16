package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Cast
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExpandingText
import com.piashcse.hilt_mvvm_compose_movie.ui.component.text.SubtitlePrimary
import com.piashcse.hilt_mvvm_compose_movie.ui.component.text.SubtitleSecondary
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.hourMinutes
import com.piashcse.hilt_mvvm_compose_movie.utils.roundTo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import component.base.BaseColumn

@Composable
fun MovieDetail(navController: NavController, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
        viewModel.observeFavoriteStatus(movieId)
    }

    MovieDetailContent(
        uiState = uiState,
        onFavoriteClick = { movie -> viewModel.toggleFavorite(movie) },
        onRecommendedMovieClick = { id -> navController.navigate(Screen.MovieDetail.route + "/$id") },
        onCastClick = { id ->
            navController.navigate(
                Screen.ArtistDetail.route.plus(
                    "/${id}"
                )
            )
        },
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MovieDetailContent(
    uiState: MovieDetailUiState,
    onFavoriteClick: (MovieDetail) -> Unit,
    onRecommendedMovieClick: (Int) -> Unit,
    onCastClick: (Int) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val calculatedOffset = screenHeight / 5.5f
    BaseColumn(loading = uiState.isLoading, errorMessage = uiState.errorMessage) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    DefaultBackgroundColor
                )
        ) {
            uiState.movieDetail?.let { it ->
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    ) {
                        CoilImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .graphicsLayer {
                                    alpha = 0.9f
                                    scaleX = 1f
                                    scaleY = 1f
                                    translationX = 0f
                                    translationY = 0f
                                    shadowElevation = 10f
                                    renderEffect = BlurEffect(8f, 8f)
                                },
                            imageModel = { ApiURL.IMAGE_URL_V2.plus(it.backdropPath) },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                            ),
                            component = rememberImageComponent {
                                +CircularRevealPlugin(duration = 800)
                                +ShimmerPlugin(
                                    shimmer = Shimmer.Flash(
                                        baseColor = SecondaryFontColor,
                                        highlightColor = DefaultBackgroundColor
                                    )
                                )
                            },
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = calculatedOffset)
                                .padding(start = 10.dp)
                        ) {
                            CoilImage(
                                modifier = Modifier
                                    .size(135.dp, 180.dp) // Poster size (width x height)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        1.dp, Color.White, RoundedCornerShape(10.dp)
                                    ),
                                imageModel = { ApiURL.IMAGE_URL.plus(it.posterPath) },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Crop,
                                ),
                                component = rememberImageComponent {
                                    +ShimmerPlugin(
                                        shimmer = Shimmer.Flash(
                                            baseColor = SecondaryFontColor,
                                            highlightColor = DefaultBackgroundColor
                                        )
                                    )
                                },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.Bottom),
                            ) {
                                Text(
                                    text = it.title,
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    Column(Modifier.weight(1f)) {
                                        SubtitlePrimary(
                                            text = stringResource(R.string.duration)
                                        )
                                        SubtitleSecondary(
                                            text = it.runtime.hourMinutes()
                                        )
                                    }
                                    Column(Modifier.weight(1f)) {
                                        SubtitlePrimary(
                                            text = stringResource(R.string.release_date)
                                        )
                                        SubtitleSecondary(
                                            text = it.releaseDate
                                        )
                                    }
                                }
                                Row(modifier = Modifier.padding(top = 4.dp)) {
                                    Column(Modifier.weight(1f)) {
                                        SubtitlePrimary(
                                            text = stringResource(R.string.language)
                                        )
                                        SubtitleSecondary(
                                            text = it.originalLanguage
                                        )
                                    }
                                    Column(Modifier.weight(1f)) {
                                        SubtitlePrimary(
                                            text = stringResource(R.string.rating)
                                        )
                                        SubtitleSecondary(
                                            text = it.voteAverage.roundTo(1).toString()
                                        )
                                    }
                                }
                            }
                        }
                        IconButton(
                            onClick = {
                                onFavoriteClick(it)
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                        ) {
                            if (uiState.isFavorite) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Red
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp, top = 115.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.description),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        ExpandingText(text = it.overview, visibleLines = 3)
                        Spacer(modifier = Modifier.height(8.dp))
                        RecommendedMovie(uiState.recommendedMovies, onRecommendedMovieClick)
                        uiState.movieCredit?.let {
                            ArtistAndCrew(it.cast, onCastClick)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "MovieDetail", showBackground = true)
@Composable
fun Preview() {
    // MovieDetail(null, MovieItem())
}

@Composable
fun RecommendedMovie(recommendedMovie: List<MovieItem>, onRecommendedMovieClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        if (recommendedMovie.isNotEmpty()) {
            Text(
                text = stringResource(R.string.similar),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(recommendedMovie, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    CoilImage(
                        modifier = Modifier
                            .height(180.dp)
                            .width(135.dp)
                            .cornerRadius(10)
                            .clickable {
                                onRecommendedMovieClick(item.id)
                            },
                        imageModel = { ApiURL.IMAGE_URL.plus(item.posterPath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )
                }
            })
        }
    }
}

@Composable
fun ArtistAndCrew(cast: List<Cast>, onCastClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        if (cast.isNotEmpty()) {
            Text(
                text = stringResource(R.string.cast),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(cast, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 10.dp, top = 5.dp, bottom = 5.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoilImage(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .height(80.dp)
                            .width(80.dp)
                            .cornerRadius(40)
                            .clickable {
                                onCastClick(item.id)
                            },
                        imageModel = { ApiURL.IMAGE_URL.plus(item.profilePath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                            +ShimmerPlugin(
                                shimmer = Shimmer.Flash(
                                    baseColor = SecondaryFontColor,
                                    highlightColor = DefaultBackgroundColor
                                )
                            )
                        },
                    )
                    SubtitleSecondary(text = item.name)
                }
            })
        }
    }
}

