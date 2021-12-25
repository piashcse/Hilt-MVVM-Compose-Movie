package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.HomeViewModel

@ExperimentalFoundationApi
@Composable
fun GenreScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    isAppBarVisible: MutableState<Boolean>,
    genreId: String
) {
    HomeScreen(
        navController = navController,
        viewModel = viewModel,
        isAppBarVisible = isAppBarVisible,
        movies = viewModel.moviesByGenre(genreId).collectAsLazyPagingItems()
    )
}