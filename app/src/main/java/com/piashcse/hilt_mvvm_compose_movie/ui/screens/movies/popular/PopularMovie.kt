package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies

@Composable
fun PopularMovie(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val popularViewModel = hiltViewModel<PopularMovieViewModel>()
    Movies(
        navController = navController,
        moviesItems = popularViewModel.popularMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = popularViewModel.selectedGenre.value
    ){
        popularViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            popularViewModel.selectedGenre.value = it
        }
    }
}