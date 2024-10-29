package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.data.model.Genres
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigation
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.navigationTitle
import com.piashcse.hilt_mvvm_compose_movie.ui.component.CircularIndeterminateProgressBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchBar
import com.piashcse.hilt_mvvm_compose_movie.ui.component.SearchUI
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.botton_navigation.BottomNavigationUI
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view.FavoriteTabView
import com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view.MovieTvSeriesTabView
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.FloatingActionBackground
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.cornerRadius
import com.piashcse.hilt_mvvm_compose_movie.utils.ACTIVE_MOVIE_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.ACTIVE_TV_SERIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.AppConstant
import com.piashcse.hilt_mvvm_compose_movie.utils.network.DataState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.ConnectionState
import com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection.connectivityState
import com.piashcse.hilt_mvvm_compose_movie.utils.pagingLoadingState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    var genreList = remember { mutableListOf<Genre>() }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isFavoriteActive = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState {
        2
    }

    LaunchedEffect(Unit) {
        mainViewModel.genreList()
    }

    if (mainViewModel.genres.value is DataState.Success<Genres>) {
        genreList =
            (mainViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList
        // All first value as all
        if (genreList.first().name != AppConstant.DEFAULT_GENRE_ITEM) genreList.add(
            0, Genre(null, AppConstant.DEFAULT_GENRE_ITEM)
        )
    }

    Scaffold(topBar = {
        if (!isAppBarVisible.value) {
            SearchBar(isAppBarVisible, mainViewModel, pagerState.currentPage)
        } else CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text(
                text = navigationTitle(navController),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }, navigationIcon = {
            when (currentRoute(navController)) {
                Screen.MovieDetail.route, Screen.ArtistDetail.route, Screen.TvSeriesDetail.route, Screen.FavoriteMovie.route, Screen.FavoriteTvSeries.route -> {
                    val activeScreen = currentRoute(navController)
                    IconButton(onClick = {
                        if (isFavoriteActive.value && (activeScreen == Screen.FavoriteMovie.route || activeScreen == Screen.FavoriteTvSeries.route)) {
                            val activeMovieTab =
                                if (pagerState.currentPage == ACTIVE_MOVIE_TAB) Screen.NowPlaying.route else Screen.AiringTodayTvSeries.route
                            navController.navigate(activeMovieTab) {
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            }
        }, scrollBehavior = scrollBehavior, actions = {
            IconButton(onClick = {
                if (!isFavoriteActive.value) navController.navigate(Screen.FavoriteMovie.route)
                isFavoriteActive.value = true
            }) {
                if (currentRoute(navController) !== Screen.FavoriteMovie.route && currentRoute(
                        navController
                    ) !== Screen.FavoriteTvSeries.route
                ) Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    tint = Color.Gray
                )
            }
        })
    }, floatingActionButton = {
        when (currentRoute(navController)) {
            Screen.NowPlaying.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route,
            Screen.AiringTodayTvSeries.route, Screen.OnTheAirTvSeriesNav.route, Screen.PopularTvSeries.route, Screen.TopRatedTvSeries.route,
                -> {
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
            Screen.NowPlaying.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route,
            Screen.AiringTodayTvSeries.route, Screen.OnTheAirTvSeriesNav.route,
            Screen.PopularTvSeries.route, Screen.TopRatedTvSeries.route,
                -> {
                BottomNavigationUI(navController, pagerState)
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
        Box(Modifier.padding(it)) {
            MainView(
                navController, pagerState, genreList as ArrayList<Genre>?, isFavoriteActive.value
            )
            CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
            if (isAppBarVisible.value.not()) {
                if (pagerState.currentPage == ACTIVE_MOVIE_TAB) {
                    SearchUI(navController, mainViewModel.movieSearchData, pagerState.currentPage) {
                        isAppBarVisible.value = true
                    }
                    mainViewModel.movieSearchData.pagingLoadingState {
                        searchProgressBar.value = it
                    }
                } else if (pagerState.currentPage == ACTIVE_TV_SERIES_TAB) {
                    SearchUI(
                        navController, mainViewModel.tvSeriesSearchData, pagerState.currentPage
                    ) {
                        isAppBarVisible.value = true
                    }
                    mainViewModel.tvSeriesSearchData.pagingLoadingState {
                        searchProgressBar.value = it
                    }
                }

            }
        }
    }
}

@Composable
fun MainView(
    navigator: NavHostController,
    pagerState: PagerState,
    genres: ArrayList<Genre>? = null,
    isFavorite: Boolean,
) {
    Column {
        if (!isFavorite) {
            MovieTvSeriesTabView(navigator, pagerState)
        } else {
            FavoriteTabView(navigator)
        }
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            Navigation(navigator, genres)
        }
    }
}