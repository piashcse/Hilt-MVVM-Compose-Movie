package com.piashcse.hilt_mvvm_compose_movie.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.MainScreen
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.HiltMVVMComposeMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltMVVMComposeMovieTheme {
                MainScreen()
            }
        }
    }
}