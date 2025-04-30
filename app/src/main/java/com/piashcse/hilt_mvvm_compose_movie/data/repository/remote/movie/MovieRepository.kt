package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie

import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    suspend fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>>
    suspend fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>>
    suspend fun genreList(): Flow<DataState<Genres>>
    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>>
    fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>
}