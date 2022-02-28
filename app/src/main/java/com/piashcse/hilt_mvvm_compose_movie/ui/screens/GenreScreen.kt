package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.home.HomeScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.home.HomeViewModel

@ExperimentalFoundationApi
@Composable
fun GenreScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    isAppBarVisible: MutableState<Boolean>,
    genreId: String
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(
        navController = navController,
        viewModel = homeViewModel,
        isAppBarVisible = isAppBarVisible,
        movies = viewModel.moviesByGenre(genreId).collectAsLazyPagingItems()
    )
}