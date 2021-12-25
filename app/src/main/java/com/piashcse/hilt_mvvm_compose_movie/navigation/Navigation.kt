package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.GenreScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.HomeScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.Popular
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.TopRated
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.Upcoming
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.HomeViewModel
import timber.log.Timber

@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    isAppBarVisible: MutableState<Boolean>,
    homeViewModel: HomeViewModel
) {
    NavHost(navController, startDestination = "home") {
        composable(NavigationScreen.HOME) {
            HomeScreen(
                navController = navController,
                homeViewModel,
                isAppBarVisible
            )
        }
        composable(NavigationScreen.POPULAR) {
            Popular(
                navController = navController,
                homeViewModel,
                isAppBarVisible
            )
        }
        composable(NavigationScreen.TOP_RATED) {
            TopRated(
                navController = navController,
                homeViewModel,
                isAppBarVisible
            )
        }
        composable(NavigationScreen.UP_COMING) {
            Upcoming(
                navController = navController,
                homeViewModel,
                isAppBarVisible
            )
        }
        composable(
            NavigationScreen.NAVIGATION_DRAWER_WITH_GENRE_ID,
            arguments = listOf(navArgument(NavigationScreen.GENRE_ID) {
                type = NavType.StringType
            })
        ) {
            val genreId =
                it.arguments?.getString(NavigationScreen.GENRE_ID)
            Timber.e("genre id $genreId")
            genreId?.let {
                GenreScreen(
                    navController = navController,
                    homeViewModel,
                    isAppBarVisible,
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
            val movieItem =
                it.arguments?.getInt(NavigationScreen.MovieDetail.MOVIE_ITEM)
            if (movieItem != null) {
                MovieDetail(
                    navController = navController,
                    movieItem
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
