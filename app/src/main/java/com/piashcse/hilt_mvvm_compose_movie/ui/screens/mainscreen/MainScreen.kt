package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.*
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchUI
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchBar
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.drawer.DrawerUI
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FloatingActionBackground
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstant
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.ConnectionState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.connectivityState
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // genre api call for first time
    LaunchedEffect(key1 = 0) {
        mainViewModel.genreList()
    }

    if (mainViewModel.genres.value is DataState.Success<Genres>) {
        genreList.value =
            (mainViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList
        // All first value as all
        if (genreList.value.first().name != AppConstant.DEFAULT_GENRE_ITEM) genreList.value.add(
            0,
            Genre(null, AppConstant.DEFAULT_GENRE_ITEM)
        )
    }
    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 100.dp, 0.dp)
                    .fillMaxHeight()
            ) {
                DrawerUI(navController, genreList.value as List<Genre>) {
                    genreName.value = it
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            }
        }, gesturesEnabled = true
    ) {
        Scaffold(topBar = {
            if (!isAppBarVisible.value) {
                SearchBar(isAppBarVisible, mainViewModel)
            } else CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        if (currentRoute(navController) == Screen.NavigationDrawer.route) genreName.value
                        else stringResource(R.string.app_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    when (currentRoute(navController)) {
                        Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Localized description",
                                    tint = Color.White
                                )
                            }
                        }

                        else -> {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Localized description",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }, floatingActionButton = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                    FloatingActionButton(
                        modifier = Modifier.cornerRadius(30),
                        containerColor = FloatingActionBackground,
                        onClick = {
                            isAppBarVisible.value = false
                        },
                    ) {
                        Icon(Icons.Filled.Search, "", tint = Color.White)
                    }
                }
            }
        }, bottomBar = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                    BottomNavigationUI(navController)
                }
            }
        }, snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {}, modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Navigation(navController, Modifier.padding(it), genreList.value)
                Column {
                    CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                    if (isAppBarVisible.value.not()) {
                        SearchUI(navController, mainViewModel.searchData) {
                            isAppBarVisible.value = true
                        }
                    }
                }
            }
            mainViewModel.searchData.pagingLoadingState {
                searchProgressBar.value = it
            }
        }
    }

}


@Composable
fun BottomNavigationUI(navController: NavController) {
    NavigationBar {
        val items = listOf(
            Screen.HomeNav,
            Screen.PopularNav,
            Screen.TopRatedNav,
            Screen.UpcomingNav,
        )
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = item.navIcon,
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
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