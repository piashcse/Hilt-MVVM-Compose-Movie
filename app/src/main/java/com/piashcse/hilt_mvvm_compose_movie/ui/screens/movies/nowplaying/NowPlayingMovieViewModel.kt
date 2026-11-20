package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.nowplaying

import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingMovieViewModel @Inject constructor(
    repository: MovieRepository,
) : MediaListViewModel<MovieItem>(repository::nowPlayingMoviePagingDataSource)
