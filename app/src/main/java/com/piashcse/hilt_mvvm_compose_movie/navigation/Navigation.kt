package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.genre.GenreScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.Login
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.nowplaying.NowPlaying
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.popular.Popular
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.toprated.TopRated
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.upcoming.Upcoming

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = "home", modifier) {
        composable(NavigationScreen.HOME) {
            NowPlaying(
                navController = navController,
            )
        }
        composable(NavigationScreen.LOGIN) {
            Login(
                navController = navController
            )
        }
        composable(NavigationScreen.POPULAR) {
            Popular(
                navController = navController
            )
        }
        composable(NavigationScreen.TOP_RATED) {
            TopRated(
                navController = navController
            )
        }
        composable(NavigationScreen.UP_COMING) {
            Upcoming(
                navController = navController
            )
        }
        composable(
            NavigationScreen.NAVIGATION_DRAWER_WITH_GENRE_ID,
            arguments = listOf(navArgument(NavigationScreen.GENRE_ID) {
                type = NavType.StringType
            })
        ) { backStack ->
            val genreId = backStack.arguments?.getString(NavigationScreen.GENRE_ID)
            genreId?.let {
                GenreScreen(
                    navController = navController,
                    genreId
                )
            }
        }
        composable(
            NavigationScreen.MovieDetail.MOVIE_DETAIL.plus(NavigationScreen.MovieDetail.MOVIE_DETAIL_PATH),
            arguments = listOf(navArgument(NavigationScreen.MovieDetail.MOVIE_ITEM) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId =
                it.arguments?.getInt(NavigationScreen.MovieDetail.MOVIE_ITEM)
            if (movieId != null) {
                MovieDetail(
                    navController = navController,
                    movieId
                )
            }
            /*movieItem?.fromPrettyJson<MovieItem>()
                ?.let { movieObject ->
                    MovieDetail(
                        movieObject
                    )
                }*/
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        NavigationScreen.MovieDetail.MOVIE_DETAIL -> stringResource(id = R.string.movie_detail)
        NavigationScreen.LOGIN -> stringResource(id = R.string.login)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
