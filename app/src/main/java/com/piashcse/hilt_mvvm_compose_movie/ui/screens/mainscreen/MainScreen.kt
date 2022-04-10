package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigation
import com.piashcse.hilt_mvvm_compose_movie.navigation.NavigationScreen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.navigationTitle
import com.piashcse.hilt_mvvm_compose_movie.ui.component.appbar.AppBarWithArrow
import com.piashcse.hilt_mvvm_compose_movie.ui.component.NavigationItem
import com.piashcse.hilt_mvvm_compose_movie.ui.component.appbar.SearchBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.appbar.HomeAppBar
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.drawer.DrawerUI
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.home.HomeViewModel
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.floatingActionBackground
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.ConnectionState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.connectivityState
import kotlinx.coroutines.launch


@Composable
fun MainScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val genreName = remember { mutableStateOf("") }
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    // genre list for navigation drawer
    val genres = mainViewModel.genres.value
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    // genre api call for first time
    LaunchedEffect(true) {
        mainViewModel.genreList()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            when (currentRoute(navController)) {
                NavigationScreen.HOME, NavigationScreen.POPULAR, NavigationScreen.TOP_RATED, NavigationScreen.UP_COMING, NavigationScreen.NAVIGATION_DRAWER -> {
                    if (isAppBarVisible.value) {
                        val appTitle: String =
                            if (currentRoute(navController) == NavigationScreen.NAVIGATION_DRAWER)
                                genreName.value
                            else
                                stringResource(R.string.app_title)
                        HomeAppBar(
                            title = appTitle,
                            openDrawer = {
                                scope.launch {
                                    scaffoldState.drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            openFilters = {
                                isAppBarVisible.value = false
                            }
                        )
                    } else {
                        SearchBar(isAppBarVisible, homeViewModel)
                    }
                }
                else -> {
                    AppBarWithArrow(navigationTitle(navController)) {
                        navController.popBackStack()
                    }
                }
            }
        },
        drawerContent = {
            // Drawer content
            if (genres is DataState.Success<Genres>) {
                DrawerUI(navController, genres.data.genres) {
                    genreName.value = it
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            }
        },
        floatingActionButton = {
            when (currentRoute(navController)) {
                NavigationScreen.HOME, NavigationScreen.POPULAR, NavigationScreen.TOP_RATED, NavigationScreen.UP_COMING -> {
                    FloatingActionButton(
                        onClick = {
                            isAppBarVisible.value = false
                        },
                        backgroundColor = floatingActionBackground
                    ) {
                        Icon(Icons.Filled.Search, "", tint = Color.White)
                    }
                }
            }
        },
        bottomBar = {
            when (currentRoute(navController)) {
                NavigationScreen.HOME, NavigationScreen.POPULAR, NavigationScreen.TOP_RATED, NavigationScreen.UP_COMING -> {
                    BottomNavigationUI(navController)
                }
            }
        },
        snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }
    ){
        Navigation(navController, isAppBarVisible, homeViewModel,  Modifier.padding(it))
    }
}


@Composable
fun BottomNavigationUI(navController: NavController) {
    BottomNavigation {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Popular,
            NavigationItem.TopRated,
            NavigationItem.Upcoming,
        )
        items.forEach { item ->
            BottomNavigationItem(
                label = { Text(text = item.title) },
                selected = currentRoute(navController) == item.route,
                icon = item.icon,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(item.route) {
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
                })
        }
    }
}