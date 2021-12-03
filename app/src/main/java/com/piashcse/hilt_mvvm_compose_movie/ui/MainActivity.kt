package com.piashcse.hilt_mvvm_compose_movie.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.HomeDetail
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.HomeScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltMVVMComposeMovieTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                    composable("homeDetail") {
                        HomeDetail(navController = navController)
                    }
                }
            }
        }
    }
}