package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.trending

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Celebrities
import com.piashcse.hilt_mvvm_compose_movie.ui.component.TvSeries

@Composable
fun TrendingCelebrities(
    navController: NavController,
) {
    val trendingCelebritiesViewModel = hiltViewModel<TrendingCelebritiesViewModel>()
    Celebrities(
        navController = navController,
        celebrities = trendingCelebritiesViewModel.trendingCelebrities.collectAsLazyPagingItems()
    )
}