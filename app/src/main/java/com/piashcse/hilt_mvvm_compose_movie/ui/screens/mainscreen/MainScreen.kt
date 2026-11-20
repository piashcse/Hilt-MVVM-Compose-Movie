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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.AiringTodayTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.FavoriteTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigation
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.NowPlayingMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.OnTheAirTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.PopularCelebritiesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.PopularMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.PopularTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.TopRatedMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.TopRatedTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.TrendingCelebritiesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.UpcomingMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.navigationTitle
import com.piashcse.hilt_mvvm_compose_movie.navigation.rememberNavigator
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
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_SEARCH
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.ConnectionState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.connectivityState

private val topBarHiddenScreens = listOf(
    Screen.MovieDetail, Screen.ArtistDetail,
    Screen.TvSeriesDetail, Screen.FavoriteMovie, Screen.FavoriteTvSeries,
)

private val bottomNavScreens = listOf(
    Screen.NowPlaying, Screen.Popular, Screen.TopRated, Screen.Upcoming,
    Screen.AiringTodayTvSeries, Screen.OnTheAirTvSeries,
    Screen.PopularTvSeries, Screen.TopRatedTvSeries,
    Screen.PopularCelebrities, Screen.TrendingCelebrities,
)

private val tabHiddenScreens = listOf(
    Screen.MovieDetail, Screen.TvSeriesDetail, Screen.ArtistDetail,
)

private val backDialogScreens = listOf(
    Screen.NowPlaying, Screen.AiringTodayTvSeries, Screen.PopularCelebrities,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navigator = rememberNavigator(NowPlayingMovieRoute)
    val isAppBarVisible = remember { mutableStateOf(true) }
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isFavoriteActive = remember { mutableStateOf(false) }
    val favoriteOriginSection = remember { mutableIntStateOf(MOVIE_TAB) }

    val uiState by mainViewModel.uiState.collectAsState()
    val currentKey = navigator.currentKey
    val section = sectionOf(currentKey)

    val showTopAppBarActions = topBarHiddenScreens.none { it.matches(currentKey) }

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
                            text = navigationTitle(navigator),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        if (topBarHiddenScreens.any { it.matches(currentKey) }) {
                            IconButton(onClick = {
                                val isFavoriteScreen = listOf(
                                    Screen.FavoriteMovie, Screen.FavoriteTvSeries
                                ).any { it.matches(currentKey) }

                                if (isFavoriteActive.value && isFavoriteScreen) {
                                    val targetRoute = when (favoriteOriginSection.intValue) {
                                        TV_SERIES_TAB -> AiringTodayTvSeriesRoute
                                        CELEBRITIES_TAB -> PopularCelebritiesRoute
                                        else -> NowPlayingMovieRoute
                                    }

                                    navigator.navigateToRoot(targetRoute)
                                    isFavoriteActive.value = false
                                } else {
                                    navigator.popBackStack()
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
                                if (!isFavoriteActive.value) {
                                    favoriteOriginSection.intValue = section
                                    Screen.FavoriteMovie.route?.let { navigator.navigate(it) }
                                }
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
            if (bottomNavScreens.any { it.matches(currentKey) } && isAppBarVisible.value) {
                BottomNavigationUI(navigator, section)
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
                navigator = navigator,
                section = section,
                genres = uiState.genres?.genres,
                isFavorite = isFavoriteActive.value
            )

            if (!isAppBarVisible.value) {
                // Overlay search results on top of main content
                Box(modifier = Modifier.fillMaxSize()) {
                    // Scrim: tapping outside the results closes the search
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable { isAppBarVisible.value = true }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp) // Increased height to show more items
                            .background(color = DefaultBackgroundColor)
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
                            SearchUI(navigator, it, activeSearchType, searchQuery, isLoading, itemClick = {
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
    navigator: Navigator,
    section: Int,
    genres: List<Genre>?,
    isFavorite: Boolean,
) {
    val activity = LocalActivity.current
    val openDialog = remember { mutableStateOf(false) }
    val currentKey = navigator.currentKey

    val showTabs = tabHiddenScreens.none { it.matches(currentKey) }

    BackHandler(enabled = backDialogScreens.any { it.matches(currentKey) }) {
        openDialog.value = true
    }

    Column {
        if (showTabs) {
            if (isFavorite) FavoriteTabView(navigator)
            else TabView(navigator, section)
        }

        Navigation(navigator, genres, Modifier.weight(1f))

        if (openDialog.value) {
            ShowExitDialog(activity, openDialog)
        }
    }
}

/** Maps the current route to the tab/section that owns it. */
private fun sectionOf(key: NavKey?): Int = when (key) {
    NowPlayingMovieRoute, PopularMovieRoute, TopRatedMovieRoute, UpcomingMovieRoute -> MOVIE_TAB

    AiringTodayTvSeriesRoute, PopularTvSeriesRoute, TopRatedTvSeriesRoute,
    OnTheAirTvSeriesRoute -> TV_SERIES_TAB

    PopularCelebritiesRoute, TrendingCelebritiesRoute -> CELEBRITIES_TAB

    FavoriteTvSeriesRoute -> TV_SERIES_TAB

    else -> MOVIE_TAB
}
