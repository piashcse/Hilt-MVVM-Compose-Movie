package com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote

import com.piashcse.hilt_mvvm_compose_movie.data.model.BaseModel
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiURL.MOVIE_LIST)
    suspend fun nowPlayingMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.POPULAR_MOVIE_LIST)
    suspend fun popularMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.TOP_RATED_MOVIE_LIST)
    suspend fun topRatedMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.UP_COMING_MOVIE_LIST)
    suspend fun upcomingMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiURL.MOVIE_DETAIL)
    suspend fun movieDetail(@Path("movieId") movieId: Int): MovieDetail

    @GET(ApiURL.RECOMMENDED_MOVIE)
    suspend fun recommendedMovie(@Path("movieId") movieId: Int, @Query("page") one: Int): BaseModel

    @GET(ApiURL.SEARCH_MOVIE)
    suspend fun search(@Query("query") searchKey: String): BaseModel

    @GET(ApiURL.GENRE_LIST)
    suspend fun genreList(): Genres

    @GET(ApiURL.GENRE_MOVIES_BY_ID)
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String
    ): BaseModel
}