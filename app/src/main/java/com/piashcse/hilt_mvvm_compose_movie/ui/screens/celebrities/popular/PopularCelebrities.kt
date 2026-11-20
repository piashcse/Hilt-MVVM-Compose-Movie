package com.piashcse.hilt_mvvm_compose_movie.ui.screens.celebrities.popular

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.piashcse.hilt_mvvm_compose_movie.navigation.ArtistDetailRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.CelebrityListScreen

@Composable
fun PopularCelebrities(
    navController: Navigator,
) {
    val viewModel = hiltViewModel<PopularCelebritiesViewModel>()
    CelebrityListScreen(viewModel) {
        navController.navigate(ArtistDetailRoute(it.id))
    }
}
