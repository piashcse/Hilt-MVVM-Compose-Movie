package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.ui.component.HomeScreen

@Composable
fun Popular(
    navController: NavController
) {
    val popularViewModel = hiltViewModel<PopularViewModel>()
    HomeScreen(
        navController = navController,
        movies = popularViewModel.popularMovies
    )
}