package com.piashcse.hilt_mvvm_compose_movie.navigation

object NavigationScreen {
    const val HOME = "home"
    const val DRAWER = "drawer"
    const val POPULAR = "popular"
    const val TOP_RATED = "toprated"
    const val UP_COMING = "upcoming"

    object MovieDetail {
        const val MOVIE_DETAIL = "movieDetail"
        const val MOVIE_DETAIL_PATH = "/{movieItem}"
        const val MOVIE_ITEM = "movieItem"
    }
}