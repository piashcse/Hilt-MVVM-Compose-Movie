package com.piashcse.hilt_mvvm_compose_movie.ui.screens.moviedetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Cast
import com.piashcse.hilt_mvvm_compose_movie.ui.component.text.SubtitlePrimary
import com.piashcse.hilt_mvvm_compose_movie.ui.component.text.SubtitleSecondary
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.*
import com.piashcse.hilt_mvvm_compose_movie.utils.hourMinutes
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState

@Composable
fun MovieDetail(navController: NavController, movieId: Int) {
    val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val movieDetail = movieDetailViewModel.movieDetail
    val recommendedMovie = movieDetailViewModel.recommendedMovie
    val artist = movieDetailViewModel.artist

    LaunchedEffect(true) {
        movieDetailViewModel.movieDetailApi(movieId)
        movieDetailViewModel.recommendedMovieApi(movieId, 1)
        movieDetailViewModel.movieCredit(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                defaultBackgroundColor
            )
    ) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        movieDetail.value?.let { it ->
            if (it is DataState.Success<MovieDetail>) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Image(
                        painter = rememberImagePainter(ApiURL.IMAGE_URL.plus(it.data.poster_path)),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                        //.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp, end = 15.dp)
                    ) {
                        Text(
                            text = it.data.title,
                            modifier = Modifier.padding(top = 10.dp),
                            color = FontColor,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W700,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 10.dp)
                        ) {

                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.original_language,
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.language)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.vote_average.toString(),
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.rating)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.runtime.hourMinutes()
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.duration)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.release_date
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.release_date)
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.description),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = it.data.overview,
                            color = secondaryFontColor,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        recommendedMovie.value?.let {
                            if (it is DataState.Success<BaseModel>) {
                                RecommendedMovie(navController, it.data.results)
                            }
                        }
                        artist.value?.let {
                            if (it is DataState.Success<Artist>) {
                                ArtistAndCrew(navController, it.data.cast)
                            }
                        }
                    }
                }
            }
        }
        recommendedMovie.pagingLoadingState {
            progressBar.value = it
        }
        movieDetail.pagingLoadingState {
            progressBar.value = it
        }
    }
}

@Preview(name = "MovieDetail", showBackground = true)
@Composable
fun Preview() {
    // MovieDetail(null, MovieItem())
}

@Composable
fun RecommendedMovie(navController: NavController?, recommendedMovie: List<MovieItem>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(R.string.similar),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(recommendedMovie, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp,
                        end = 8.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                ) {
                    Image(
                        painter = rememberImagePainter(ApiURL.IMAGE_URL.plus(item.posterPath)),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                            .width(140.dp)
                            .cornerRadius10()
                            .clickable {
                                navController?.navigate(
                                    NavigationScreen.MovieDetail.MOVIE_DETAIL.plus(
                                        "/${item.id}"
                                    )
                                )
                            }
                    )
                }
            })
        }
    }
}

@Composable
fun ArtistAndCrew(navController: NavController?, cast: List<Cast>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(R.string.cast),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(cast, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp,
                        end = 10.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter(ApiURL.IMAGE_URL.plus(item.profilePath)),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.padding(bottom = 5.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                            .width(80.dp)
                            .cornerRadius40()
                            .clickable {

                            }
                    )
                    SubtitleSecondary(text = item.name)
                }
            })
        }
    }
}