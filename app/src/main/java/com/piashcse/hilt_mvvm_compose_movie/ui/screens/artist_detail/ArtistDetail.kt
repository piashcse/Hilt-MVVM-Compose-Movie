package com.piashcse.hilt_mvvm_compose_movie.ui.screens.artist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiURL
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.text.BiographyText
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

@Composable
fun ArtistDetail(personId: Int, viewModel: ArtistDetailViewModel = hiltViewModel()) {
    val artistDetail by viewModel.artistDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Trigger data fetch when the composable enters the composition
    LaunchedEffect(personId) {
        viewModel.artistDetail(personId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DefaultBackgroundColor)
            .padding(8.dp)
    ) {
        CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)

        artistDetail?.let { detail ->
            ArtistHeader(detail)
            BiographySection(detail.biography)
        }
    }
}

@Composable
fun ArtistHeader(artistDetail: ArtistDetail) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        CoilImage(
            modifier = Modifier
                .height(250.dp)
                .width(190.dp)
                .cornerRadius(10),
            imageModel = { ApiURL.IMAGE_URL.plus(artistDetail.profilePath) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(duration = 800)
                +ShimmerPlugin(
                    shimmer = Shimmer.Flash(
                        baseColor = SecondaryFontColor,
                        highlightColor = DefaultBackgroundColor
                    )
                )
            }
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = artistDetail.name,
                color = FontColor,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            )
            PersonalInfo(stringResource(R.string.know_for), artistDetail.knownForDepartment)
            PersonalInfo(stringResource(R.string.gender), artistDetail.gender.genderInString())

            artistDetail.birthday?.let { birthday ->
                PersonalInfo(stringResource(R.string.birth_day), birthday)
            }
            artistDetail.placeOfBirth?.let { birthPlace ->
                PersonalInfo(stringResource(R.string.place_of_birth), birthPlace)
            }
        }
    }
}

@Composable
fun BiographySection(biography: String?) {
    val (biographyText, textColor) = if (!biography.isNullOrBlank()) {
        biography to FontColor // Regular font color for valid biography
    } else {
        stringResource(R.string.biography_not_available) to MaterialTheme.colorScheme.error // Warning color for unavailable biography
    }

    Text(
        text = stringResource(R.string.biography),
        color = SecondaryFontColor,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    BiographyText(
        text = biographyText,
        color = textColor // Pass the appropriate text color
    )
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
            text = info,
            color = FontColor,
            fontSize = 16.sp
        )
    }
}