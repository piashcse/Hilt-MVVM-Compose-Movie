package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigation
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.navigationTitle
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchUI
import com.piashcse.hilt_mvvm_compose_movie.ui.component.ShowExitDialog
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.bottom_navigation.BottomNavigationUI
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view.FavoriteTabView
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view.TabView
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.DefaultBackgroundColor
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FloatingActionBackground
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.ConnectionState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.connectivityState
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isFavoriteActive = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { 3 }

    val uiState by mainViewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val hideTopBarScreens = listOf(
        Screen.MovieDetail, Screen.ArtistDetail,
        Screen.TvSeriesDetail, Screen.FavoriteMovie, Screen.FavoriteTvSeries
    )

    val bottomNavScreens = listOf(
        Screen.NowPlaying, Screen.Popular, Screen.TopRated, Screen.Upcoming,
        Screen.AiringTodayTvSeries, Screen.OnTheAirTvSeriesNav,
        Screen.PopularTvSeries, Screen.TopRatedTvSeries,
        Screen.PopularCelebrities, Screen.TrendingCelebrities,
    )

    val showTopAppBarActions = hideTopBarScreens.none { isScreenActive(currentDestination, it) }

    LaunchedEffect(Unit) {
        mainViewModel.loadGenres()
    }

    Scaffold(
        topBar = {
            if (!isAppBarVisible.value) {
                // Get loading state for current search type
                val isSearchLoading = when (uiState.selectedSearchType) {
                    MOVIE_SEARCH -> uiState.isMovieSearchLoading
                    TV_SERIES_SEARCH -> uiState.isTvSeriesSearchLoading
                    CELEBRITIES_SEARCH -> uiState.isCelebritySearchLoading
                    else -> false
                }
                SearchBar(isAppBarVisible, mainViewModel, isSearchLoading)
            } else {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = navigationTitle(navController),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        if (hideTopBarScreens.any { isScreenActive(currentDestination, it) }) {
                            IconButton(onClick = {
                                val isFavoriteScreen = listOf(
                                    Screen.FavoriteMovie, Screen.FavoriteTvSeries
                                ).any { isScreenActive(currentDestination, it) }

                                if (isFavoriteActive.value && isFavoriteScreen) {
                                    val targetRoute =
                                        if (pagerState.currentPage == MOVIE_TAB)
                                            Screen.NowPlaying.route else Screen.AiringTodayTvSeries.route

                                    navController.navigate(targetRoute) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                    isFavoriteActive.value = false
                                } else {
                                    navController.popBackStack()
                                }
                            }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    actions = {
                        if (showTopAppBarActions) {
                            IconButton(onClick = {
                                if (!isFavoriteActive.value) navController.navigate(Screen.FavoriteMovie.route)
                                isFavoriteActive.value = true
                            }) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        floatingActionButton = {
            if (showTopAppBarActions) {
                FloatingActionButton(
                    modifier = Modifier.cornerRadius(30),
                    containerColor = FloatingActionBackground,
                    onClick = { isAppBarVisible.value = false },
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = Color.White)
                }
            }
        },
        bottomBar = {
            if (bottomNavScreens.any { isScreenActive(currentDestination, it) } && isAppBarVisible.value) {
                BottomNavigationUI(navController, pagerState)
            }
        },
        snackbarHost = {
            if (!isConnected) {
                Snackbar(modifier = Modifier.padding(8.dp)) {
                    Text(stringResource(R.string.there_is_no_internet))
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            // Show main content even when search is active, but dim it
            MainView(
                navController = navController,
                pagerState = pagerState,
                genres = uiState.genres?.genres,
                isFavorite = isFavoriteActive.value
            )
            
            CircularIndeterminateProgressBar(isDisplayed = uiState.isLoading, 0.1f)

            if (!isAppBarVisible.value) {
                // Overlay search results on top of main content
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                        .clickable { isAppBarVisible.value = true } // Click outside to close
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp) // Increased height to show more items
                            .background(color = DefaultBackgroundColor)
                            .pointerInput(Unit) {
                                // Consume touch events to prevent closing when clicking inside
                                awaitPointerEventScope {
                                    while (true) {
                                        awaitPointerEvent()
                                    }
                                }
                            }
                    ) {
                        val activeSearchType = uiState.selectedSearchType
                        val results = when (activeSearchType) {
                            MOVIE_SEARCH -> uiState.movieSearchResults
                            TV_SERIES_SEARCH -> uiState.tvSeriesSearchResults
                            CELEBRITIES_SEARCH -> uiState.celebritySearchResults
                            else -> null
                        }
                        // Get the current search query based on active tab
                        val searchQuery = when (activeSearchType) {
                            MOVIE_SEARCH -> uiState.movieSearchQuery
                            TV_SERIES_SEARCH -> uiState.tvSeriesSearchQuery
                            CELEBRITIES_SEARCH -> uiState.celebritySearchQuery
                            else -> ""
                        }
                        // Get loading state for current search type
                        val isLoading = when (activeSearchType) {
                            MOVIE_SEARCH -> uiState.isLoading && uiState.movieSearchQuery.isNotBlank()
                            TV_SERIES_SEARCH -> uiState.isLoading && uiState.tvSeriesSearchQuery.isNotBlank()
                            CELEBRITIES_SEARCH -> uiState.isLoading && uiState.celebritySearchQuery.isNotBlank()
                            else -> false
                        }
                        results?.let {
                            SearchUI(navController, it, activeSearchType, searchQuery, isLoading, itemClick = {
                                isAppBarVisible.value = true
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainView(
    navController: NavHostController,
    pagerState: PagerState,
    genres: List<Genre>?,
    isFavorite: Boolean,
) {
    val activity = LocalActivity.current
    val openDialog = remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val hideTabScreens = listOf(
        Screen.MovieDetail,
        Screen.TvSeriesDetail,
        Screen.ArtistDetail
    )

    val showTabs = hideTabScreens.none { isScreenActive(currentDestination, it) }

    val backDialogScreens = listOf(
        Screen.NowPlaying,
        Screen.AiringTodayTvSeries,
        Screen.PopularCelebrities
    )

    BackHandler(enabled = backDialogScreens.any { isScreenActive(currentDestination, it) }) {
        openDialog.value = true
    }

    Column {
        if (showTabs) {
            if (isFavorite) FavoriteTabView(navController)
            else TabView(navController, pagerState)
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) {
            Navigation(navController, genres)
        }

        if (openDialog.value) {
            ShowExitDialog(activity, openDialog)
        }
    }
}

private fun isScreenActive(destination: NavDestination?, screen: Screen): Boolean {
    val route = screen.route
    return if (route is KClass<*>) {
        destination?.hasRoute(route) == true
    } else {
        destination?.hasRoute(route::class) == true
    }
}