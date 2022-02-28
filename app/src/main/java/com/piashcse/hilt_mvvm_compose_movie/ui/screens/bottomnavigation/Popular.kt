package com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.home.HomeScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.home.HomeViewModel

@ExperimentalFoundationApi
@Composable
fun Popular(
    navController: NavController,
    viewModel: HomeViewModel,
    isAppBarVisible: MutableState<Boolean>
) {
    HomeScreen(
        navController = navController,
        viewModel = viewModel,
        isAppBarVisible = isAppBarVisible,
        movies = viewModel.popularMovies().collectAsLazyPagingItems()
    )
}