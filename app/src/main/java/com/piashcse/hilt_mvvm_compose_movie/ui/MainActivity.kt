package com.piashcse.hilt_mvvm_compose_movie.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.HomeScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import com.piashcse.hilt_mvvm_compose_movie.utils.fromPrettyJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltMVVMComposeMovieTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavigationScreen.HOME) {
                    composable(NavigationScreen.HOME) {
                        HomeScreen(navController = navController)
                    }
                    composable(
                        NavigationScreen.MovieDetail.MOVIE_DETAIL.plus(NavigationScreen.MovieDetail.MOVIE_DETAIL_PATH),
                        arguments = listOf(navArgument(NavigationScreen.MovieDetail.MOVIE_ITEM) {
                            type = NavType.IntType
                        })
                    ) {
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
                                    navController = navController,
                                    movieObject
                                )
                            }*/
                    }
                }
            }
        }
    }
}