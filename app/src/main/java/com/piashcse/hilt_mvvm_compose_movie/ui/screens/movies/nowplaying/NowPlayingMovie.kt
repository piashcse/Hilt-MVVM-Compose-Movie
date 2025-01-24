package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.nowplaying

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.piashcse.hilt_mvvm_compose_movie.data.model.GenreId
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.component.Movies

@Composable
fun NowPlayingMovie(
    navController: NavController,
    genres: List<Genre>? = null,
) {
    val nowPlayViewModel = hiltViewModel<NowPlayingMovieViewModel>()
    Movies(
        navController = navController,
        moviesItems = nowPlayViewModel.nowPlayingMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = nowPlayViewModel.selectedGenre.value
    ){
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}