package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Celebrities

@Composable
fun PopularCelebrities(
    navController: NavController,
) {
    val popularCelebritiesViewModel = hiltViewModel<PopularCelebritiesViewModel>()
    Celebrities(
        navController = navController,
        celebrities = popularCelebritiesViewModel.popularCelebrities.collectAsLazyPagingItems()
    )
}