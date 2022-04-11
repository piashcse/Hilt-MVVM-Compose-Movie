package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.toprated

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.ui.component.HomeScreen

@Composable
fun TopRated(
    navController: NavController,
) {
    val topRatedViewModel = hiltViewModel<TopRatedViewModel>()
    HomeScreen(
        navController = navController,
        movies = topRatedViewModel.topRatedMovies
    )
}