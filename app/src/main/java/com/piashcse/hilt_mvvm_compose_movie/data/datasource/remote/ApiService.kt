package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(AppConstants.MOVIE_LIST)
    suspend fun nowPlayingmovieList(@Query("page") one: Int): BaseModel

    @GET(AppConstants.POPULAR_MOVIE_LIST)
    suspend fun popularMovieList(@Query("page") one: Int): BaseModel

    @GET(AppConstants.TOP_RATED_MOVIE_LIST)
    suspend fun topRatedMovieList(@Query("page") one: Int): BaseModel

    @GET(AppConstants.UP_COMING_MOVIE_LIST)
    suspend fun upcomingMovieList(@Query("page") one: Int): BaseModel

    @GET(AppConstants.MOVIE_DETAIL)
    suspend fun movieDetail(@Path("movieId") movieId: Int): MovieDetail

    @GET(AppConstants.RECOMMENDED_MOVIE)
    suspend fun recommendedMovie(@Path("movieId") movieId: Int, @Query("page") one: Int): BaseModel

    @GET(AppConstants.SEARCH_MOVIE)
    suspend fun search(@Query("query") searchKey: String): BaseModel
}