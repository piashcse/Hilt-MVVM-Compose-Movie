package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
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

@Composable
fun MovieDetail(navController: NavController, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val movieDetail by viewModel.movieDetail.collectAsState()
    val recommendMovie by viewModel.recommendedMovie.collectAsState()
    val movieCredit by viewModel.movieCredit.collectAsState()
    var movieFromDb by remember { mutableStateOf<MovieDetail?>(null) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val calculatedOffset = screenHeight / 5.5f


    LaunchedEffect(Unit) {
        viewModel.movieDetail(movieId)
        viewModel.recommendedMovie(movieId)
        viewModel.movieCredit(movieId)
    }

    LaunchedEffect(movieFromDb) {
        movieFromDb = viewModel.getMovieDetailById(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )
    ) {
        CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)
        movieDetail?.let { it ->
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
                        imageModel = { ApiURL.IMAGE_URL_V2.plus(it.backdrop_path) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            contentDescription = "Backdrop Image",
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
                            imageModel = { ApiURL.IMAGE_URL.plus(it.poster_path) },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                contentDescription = "Poster Image",
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
                            MovieInfo(it)
                        }
                    }
                    IconButton(
                        onClick = {
                            movieFromDb?.let {
                                viewModel.deleteMovieDetailById(it.id)
                                movieFromDb = null
                            } ?: run {
                                viewModel.insertMovieDetail(it)
                                movieFromDb = it
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f))
                    ) {
                        movieFromDb?.let {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                                tint = Color.Red
                            )
                        } ?: Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Gray
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp, top = 115.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    MovieOverview(it)
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendedMovie(navController, recommendMovie)
                    movieCredit?.let {
                        ArtistAndCrew(navController, it.cast)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieInfo(movie: MovieDetail) {
    Column(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = movie.title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        MovieMetaData(movie)
    }
}

@Composable
fun MovieMetaData(movie: MovieDetail) {
    Row {
        Column(Modifier.weight(1f)) {
            SubtitlePrimary(text = stringResource(R.string.duration))
            SubtitleSecondary(text = movie.runtime.hourMinutes())
        }
        Column(Modifier.weight(1f)) {
            SubtitlePrimary(text = stringResource(R.string.release_date))
            SubtitleSecondary(text = movie.release_date)
        }
    }
    Row(modifier = Modifier.padding(top = 4.dp)) {
        Column(Modifier.weight(1f)) {
            SubtitlePrimary(text = stringResource(R.string.language))
            SubtitleSecondary(text = movie.original_language)
        }
        Column(Modifier.weight(1f)) {
            SubtitlePrimary(text = stringResource(R.string.rating))
            SubtitleSecondary(text = movie.vote_average.roundTo(1).toString())
        }
    }
}

@Composable
fun MovieOverview(movie: MovieDetail) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.description),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        ExpandingText(text = movie.overview, visibleLines = 3)
    }
}


@Preview(name = "MovieDetail", showBackground = true)
@Composable
fun Preview() {
    // Mock MovieItem for preview purposes
    val movieItem = MovieItem(
        adult = false,
        backdropPath = "/path/to/backdrop.jpg",
        genreIds = listOf(1, 2, 3),
        id = 123,
        originalLanguage = "en",
        originalTitle = "Mock Movie",
        overview = "This is a mock movie overview.",
        popularity = 8.5,
        posterPath = "/path/to/poster.jpg",
        releaseDate = "2024-12-01",
        title = "Mock Movie Title",
        video = false,
        voteAverage = 7.8,
        voteCount = 1500
    )
}

@Composable
fun RecommendedMovie(navController: NavController?, recommendedMovie: List<MovieItem>) {
    if (recommendedMovie.isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(R.string.similar),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(modifier = Modifier.fillMaxHeight()) {
                items(recommendedMovie) { item ->
                    RecommendedMovieItem(item, navController)
                }
            }
        }
    }
}

@Composable
fun RecommendedMovieItem(item: MovieItem, navController: NavController?) {
    Column(modifier = Modifier.padding(5.dp)) {
        CoilImage(
            modifier = Modifier
                .height(180.dp)
                .width(135.dp)
                .cornerRadius(10)
                .clickable {
                    navController?.navigate(Screen.MovieDetail.route.plus("/${item.id}"))
                },
            imageModel = { ApiURL.IMAGE_URL.plus(item.posterPath) },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            component = rememberImageComponent {
                +CircularRevealPlugin(duration = 800)
            }
        )
    }
}

@Composable
fun ArtistAndCrew(navController: NavController?, cast: List<Cast>) {
    if (cast.isNotEmpty()) {
        Column {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(R.string.cast),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(modifier = Modifier.fillMaxHeight()) {
                items(cast) { item ->
                    ArtistAndCrewItem(item, navController)
                }
            }
        }
    }
}

@Composable
fun ArtistAndCrewItem(item: Cast, navController: NavController?) {
    Column(
        modifier = Modifier.padding(5.dp),
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
                    navController?.navigate(Screen.ArtistDetail.route.plus("/${item.id}"))
                },
            imageModel = { ApiURL.IMAGE_URL.plus(item.profilePath) },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            component = rememberImageComponent {
                +CircularRevealPlugin(duration = 800)
            }
        )
        SubtitleSecondary(text = item.name)
    }
}