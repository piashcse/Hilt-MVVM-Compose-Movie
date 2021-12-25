package com.piashcse.hilt_mvvm_compose_movie.ui.screens.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.viewmodel.NavDrawerViewModel
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import timber.log.Timber

@Composable
fun DrawerUI(navController: NavController, genres: List<Genre>, closeDrawer: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(items = genres, itemContent = { item ->
            DrawerItem(
                item = item,
                selected = currentRoute(navController) == "",
                onItemClick = {
                    navController.navigate(NavigationScreen.NAVIGATION_DRAWER.plus("/${it.id}")) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    // Close drawer
                    closeDrawer()
                })
        })
    }
}

@Composable
fun DrawerItem(item: Genre, selected: Boolean, onItemClick: (Genre) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .padding(start = 10.dp)
    ) {
        Icon(
            Icons.Outlined.Favorite, "", modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.name,
            fontSize = 18.sp
        )
    }
}
