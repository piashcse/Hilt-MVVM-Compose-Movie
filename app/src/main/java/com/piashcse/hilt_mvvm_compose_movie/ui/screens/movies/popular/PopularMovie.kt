package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies

@Composable
fun PopularMovie(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val viewViewModel = hiltViewModel<PopularMovieViewModel>()
    Movies(
        moviesItems = viewViewModel.popularMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedGenre = viewViewModel.selectedGenre.value,
        onclickGenre = {
            viewViewModel.filterData.value = GenreId(it?.id.toString())
            it?.let {
                viewViewModel.selectedGenre.value = it
            }
        },
        onclick = {
            navController.navigate(Screen.MovieDetail.route.plus("/${it.id}"))
        })
}