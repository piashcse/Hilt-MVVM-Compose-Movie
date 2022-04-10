package com.piashcse.hilt_mvvm_compose_movie.ui.component.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.Purple500

@Composable
fun HomeAppBar(title: String, openDrawer: () -> Unit, openFilters: () -> Unit) {
    TopAppBar(
        backgroundColor = Purple500,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(Icons.Default.Menu, "Menu")
            }
        },
        /*actions = {
            IconButton(onClick = openFilters) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }*/
    )
}
