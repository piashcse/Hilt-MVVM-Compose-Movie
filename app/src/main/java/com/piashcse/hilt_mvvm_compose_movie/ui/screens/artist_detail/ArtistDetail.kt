package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistMovie
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ExpandingText
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.SecondaryFontColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.genderInString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import component.base.BaseColumn

@Composable
fun ArtistDetail(navController: NavController, personId: Int) {
    val viewModel = hiltViewModel<ArtistDetailViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchArtistDetails(personId)
    }
    ArtistDetailUi(uiState, navController)
}

@Composable
fun ArtistDetailUi(
    artistUiState: ArtistDetailUiState,
    navController: NavController,
) {
    BaseColumn(loading = artistUiState.isLoading, errorMessage = artistUiState.errorMessage) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(DefaultBackgroundColor)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        ) {
            artistUiState.artistDetail?.let {
                Row {
                    CoilImage(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .height(250.dp)
                            .width(190.dp)
                            .cornerRadius(10),
                        imageModel = { ApiURL.IMAGE_URL.plus(it.profilePath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "artist image",
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 800)
                            +ShimmerPlugin(
                                shimmer = Shimmer.Flash(
                                    baseColor = SecondaryFontColor,
                                    highlightColor = DefaultBackgroundColor
                                )
                            )
                        })
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = it.name,
                            color = FontColor,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                        PersonalInfo(stringResource(R.string.know_for), it.knownForDepartment)
                        PersonalInfo(
                            stringResource(R.string.gender), it.gender.genderInString()
                        )
                        it.birthday?.let { birthday ->
                            PersonalInfo(
                                stringResource(R.string.birth_day), birthday
                            )
                        }
                        it.placeOfBirth?.let { birthPlace ->
                            PersonalInfo(
                                stringResource(R.string.place_of_birth), birthPlace
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(R.string.biography),
                    color = SecondaryFontColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                ExpandingText(
                    text = it.biography, visibleLines = 15
                )
            }
            artistUiState.artistMovies?.let {
                ArtistMovies(it) { item ->
                    if (item.mediaType == "movie") {
                        navController.navigate(Screen.MovieDetail.route + "/${item.id}")
                    } else {
                        navController.navigate(Screen.TvSeriesDetail.route + "/${item.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            color = SecondaryFontColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = info, color = FontColor, fontSize = 16.sp
        )
    }
}

@Composable
fun ArtistMovies(artistMovies: List<ArtistMovie>, onMovieClick: (ArtistMovie) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)) {
        if (artistMovies.isNotEmpty()) {
            Text(
                text = stringResource(R.string.artist_movies),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(artistMovies, itemContent = { item ->
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
                                onMovieClick(item)
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
