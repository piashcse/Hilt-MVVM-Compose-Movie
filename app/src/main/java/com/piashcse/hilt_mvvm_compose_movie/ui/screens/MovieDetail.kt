package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.MovieDetailViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.defaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.secondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import com.piashcse.hilt_mvvm_compose_movie.utils.hourMinutes
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import timber.log.Timber

@Composable
fun MovieDetail(navController: NavController?, movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val movieDetail = viewModel.movieDetail.value
    val recommendedMovie = viewModel.recommendedMovie.value
    LaunchedEffect(true) {
        viewModel.movieDetailApi(movieId)
        viewModel.recommendedMovieApi(movieId, 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                defaultBackgroundColor
            )
    ) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        movieDetail?.let { it ->
            if (it is DataState.Success<MovieDetail>) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Image(
                        painter = rememberImagePainter(AppConstants.IMAGE_URL.plus(it.data.poster_path)),
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
                                Text(
                                    text = it.data.original_language,
                                    color = FontColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = stringResource(R.string.language),
                                    color = secondaryFontColor,
                                    fontSize = 10.sp,
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = it.data.vote_average.toString(),
                                    color = FontColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = stringResource(R.string.rating),
                                    color = secondaryFontColor,
                                    fontSize = 10.sp,
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = it.data.runtime.hourMinutes(),
                                    color = FontColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = stringResource(R.string.duration),
                                    color = secondaryFontColor,
                                    fontSize = 10.sp,
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = it.data.release_date,
                                    color = FontColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = stringResource(R.string.release_date),
                                    color = secondaryFontColor,
                                    fontSize = 10.sp,
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
                        recommendedMovie?.let {
                            if (it is DataState.Success<BaseModel>) {
                                RecommendedMovie(navController, it.data.results)
                            }
                        }
                    }
                }
            }
        }

        recommendedMovie?.let {
            when (it) {
                is DataState.Success<BaseModel> -> {
                    progressBar.value = false
                }
                is DataState.Loading -> {
                    progressBar.value = true
                }
                is DataState.Error -> {
                    progressBar.value = false
                }
            }
        }
        movieDetail?.let {
            when (it) {
                is DataState.Success<MovieDetail> -> {
                    progressBar.value = false
                }
                is DataState.Loading -> {
                    progressBar.value = true
                }
                is DataState.Error -> {
                    progressBar.value = false
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
                        painter = rememberImagePainter(AppConstants.IMAGE_URL.plus(item.posterPath)),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
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