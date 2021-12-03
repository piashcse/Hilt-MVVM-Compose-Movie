package com.piashcse.hilt_mvvm_compose_movie.utils

object AppConstants {
    private const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
    const val BASE_URL = "https://api.themoviedb.org/3/discover/"
    const val MOVIE_LIST = "movie?api_key=${API_KEY}&language=en-US&sort_by=popularity.desc"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w342"
}