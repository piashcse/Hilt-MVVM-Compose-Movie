package com.piashcse.hilt_mvvm_compose_movie.ui.screens.movies.movie_detail

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteMovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie.MovieRepository
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.common.MediaDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    repository: MovieRepository,
    dao: FavoriteMovieDao,
) : MediaDetailViewModel<MovieDetail, MovieItem>(
    loadDetail = repository::movieDetail,
    loadRelated = repository::recommendedMovie,
    loadCredits = repository::movieCredit,
    observeFavorite = { id -> dao.observeMovieDetailById(id).map { it != null } },
    persistFavorite = { detail ->
        if (dao.getMovieDetailById(detail.id) != null) {
            dao.deleteMovieDetailById(detail.id)
        } else {
            dao.insert(detail)
        }
    },
)
