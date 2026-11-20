package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Celebrities

@Composable
fun PopularCelebrities(
    navController: Navigator,
) {
    val popularCelebritiesViewModel = hiltViewModel<PopularCelebritiesViewModel>()
    Celebrities(
        navController = navController,
        celebrities = popularCelebritiesViewModel.items.collectAsLazyPagingItems()
    )
}