package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.upcoming

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.MovieItemList
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState


@Composable
fun Upcoming(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val upComingViewModel = hiltViewModel<UpComingViewModel>()
    MovieItemList(
        navController = navController,
        movies = upComingViewModel.upcomingMovies,
        genres = genres,
        selectedName = upComingViewModel.selectedGenre.value
    ) {
        upComingViewModel.filterData.value =  GenreId(it?.id.toString())
        it?.let {
            upComingViewModel.selectedGenre.value = it
        }
    }
}