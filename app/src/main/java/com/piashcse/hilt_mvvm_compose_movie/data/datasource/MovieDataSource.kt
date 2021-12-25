package com.piashcse.hilt_mvvm_compose_movie.data.datasource

import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun movieList(page: Int): BaseModel {
        return apiService.nowPlayingMovieList(page)
    }

    suspend fun movieDetail(movieId: Int): MovieDetail {
        return apiService.movieDetail(movieId)
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): BaseModel {
        return apiService.recommendedMovie(movieId, page)
    }

    suspend fun search(searchKey: String): BaseModel {
        return apiService.search(searchKey)
    }

    suspend fun genreList(): Genres {
        return apiService.genreList()
    }

    suspend fun moviesByGenreId(page: Int, genreId: String): BaseModel {
        return apiService.moviesByGenre(page, genreId)
    }
}