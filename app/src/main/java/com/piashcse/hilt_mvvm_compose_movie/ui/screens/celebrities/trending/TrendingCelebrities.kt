package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.trending

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Celebrities

@Composable
fun TrendingCelebrities(
    navController: Navigator,
) {
    val trendingCelebritiesViewModel = hiltViewModel<TrendingCelebritiesViewModel>()
    Celebrities(
        navController = navController,
        celebrities = trendingCelebritiesViewModel.trendingCelebrities.collectAsLazyPagingItems()
    )
}