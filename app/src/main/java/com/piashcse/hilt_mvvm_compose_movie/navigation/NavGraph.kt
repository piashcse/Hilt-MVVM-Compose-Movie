package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
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
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.genre.GenreScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.artistdetail.ArtistDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.nowplaying.NowPlaying
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.popular.Popular
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.toprated.TopRated
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.bottomnavigation.upcoming.Upcoming
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier,  genres: ArrayList<Genre>? = null,
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            NowPlaying(
                navController = navController,
                genres
            )
        }
        composable(Screen.Popular.route) {
            Popular(
                navController = navController,
                genres
            )
        }
        composable(Screen.TopRated.route) {
            TopRated(
                navController = navController,
                genres
            )
        }
        composable(Screen.Upcoming.route) {
            Upcoming(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.NavigationDrawer.route.plus(Screen.NavigationDrawer.objectPath),
            arguments = listOf(navArgument(Screen.NavigationDrawer.objectName) {
                type = NavType.StringType
            })
        ) { backStack ->
            val genreId = backStack.arguments?.getString(Screen.NavigationDrawer.objectName)
            genreId?.let {
                GenreScreen(
                    navController = navController, genreId
                )
            }
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            if (movieId != null) {
                MovieDetail(
                    navController = navController, movieId
                )
            }
        }
        composable(
            Screen.ArtistDetail.route.plus(Screen.ArtistDetail.objectPath),
            arguments = listOf(navArgument(Screen.ArtistDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.artist_detail)
            val artistId = it.arguments?.getInt(Screen.ArtistDetail.objectName)
            artistId?.let {
                ArtistDetail(
                    artistId
                )
            }
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.MovieDetail.route -> stringResource(id = R.string.movie_detail)
        Screen.ArtistDetail.route -> stringResource(id = R.string.artist_detail)
        Screen.Login.route -> stringResource(id = R.string.login)
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
