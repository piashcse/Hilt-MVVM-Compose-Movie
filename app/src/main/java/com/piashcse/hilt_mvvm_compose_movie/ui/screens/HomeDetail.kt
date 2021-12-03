package com.piashcse.hilt_mvvm_compose_movie.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeDetail(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Home detail")
        }
    }
}