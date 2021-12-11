package com.piashcse.hilt_mvvm_compose_movie.ui.screens.drawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.HomeScreen
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun NavigationDrawer(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            // Drawer content
            Column(modifier = Modifier.padding(start=16.dp, top = 16.dp)) {
                Text(  stringResource(R.string.screen_one), modifier = Modifier.padding(top = 8.dp))
                Text(stringResource(R.string.screen_two), modifier = Modifier.padding(top = 8.dp))
            }
        },
        /*floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.open_drawer)) },
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        }*/
    ) {
        HomeScreen(navController = navController) {
            scope.launch {
                scaffoldState.drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
    }

}