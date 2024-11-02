package com.piashcse.hilt_mvvm_compose_movie.data.repository.remote.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.GenrePagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.movie.NowPlayingMoviePagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.movie.PopularMoviePagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.movie.TopRatedMoviePagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.paging_datasource.movie.UpcomingMoviePagingDataSource
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.SearchBaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.Artist
import com.piashcse.hilt_mvvm_compose_movie.data.model.artist.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) : MovieRepositoryInterface {
    override suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

     override suspend fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.recommendedMovie(movieId)
                emit(DataState.Success(searchResult.results))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }


    override suspend fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.searchMovie(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
        emit(DataState.Loading)
        try {
            val artistResult = apiService.movieCredit(movieId)
            emit(DataState.Success(artistResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> = flow {
        emit(DataState.Loading)
        try {
            val artistDetailResult = apiService.artistDetail(personId)
            emit(DataState.Success(artistDetailResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { NowPlayingMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    override fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { PopularMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    override fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { TopRatedMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    override fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { UpcomingMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    override fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { GenrePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

}