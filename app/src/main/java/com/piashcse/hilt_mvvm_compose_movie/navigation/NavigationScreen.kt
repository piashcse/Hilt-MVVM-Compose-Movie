package com.piashcse.hilt_mvvm_compose_movie.navigation

object NavigationScreen {
    const val HOME = "home"
    const val DRAWER = "drawer"
    const val POPULAR = "popular"
    const val TOP_RATED = "toprated"
    const val UP_COMING = "upcoming"
    const val GENRE_ID = "genreId"
    const val NAVIGATION_DRAWER = "navigation_drawer_item"
    const val NAVIGATION_DRAWER_WITH_GENRE_ID = "navigation_drawer_item/{$GENRE_ID}"

    object MovieDetail {
        const val MOVIE_DETAIL = "movieDetail"
        const val MOVIE_ITEM = "movieItem"
        const val MOVIE_DETAIL_PATH = "/{movieItem}"
    }
}