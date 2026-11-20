package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.AiringTodayTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.FavoriteMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.FavoriteTvSeriesRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.NowPlayingMovieRoute
import com.piashcse.hilt_mvvm_compose_movie.navigation.PopularCelebritiesRoute
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.singleTopNavigator

@Composable
fun TabView(
    navigator: Navigator,
    selectedSection: Int,
) {
    val tabs = listOf(stringResource(R.string.movie), stringResource(R.string.tv_series), stringResource(R.string.celebrities))
    TabRow(modifier = Modifier.background(Color.White),
        selectedTabIndex = selectedSection,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                color = MaterialTheme.colorScheme.primary
            )
        }) {
        tabs.forEachIndexed { index, title ->
            Tab(selected = selectedSection == index, onClick = {
                when (index) {
                    MOVIE_TAB -> navigator.singleTopNavigator(NowPlayingMovieRoute)
                    TV_SERIES_TAB -> navigator.singleTopNavigator(AiringTodayTvSeriesRoute)
                    CELEBRITIES_TAB -> navigator.singleTopNavigator(PopularCelebritiesRoute)
                }
            }, text = {
                Text(
                    title,
                    color = if (selectedSection == index) MaterialTheme.colorScheme.primary else Color.Gray
                )
            })
        }
    }
}

@Composable
fun FavoriteTabView(navigator: Navigator) {
    val selectedTabIndex =
        if (navigator.currentKey == FavoriteTvSeriesRoute) TV_SERIES_TAB else MOVIE_TAB
    val tabs = listOf(stringResource(R.string.movie), stringResource(R.string.tv_series))
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        TabRow(modifier = Modifier.background(Color.White),
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.primary
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTabIndex == index, onClick = {
                    if (index == MOVIE_TAB) {
                        navigator.singleTopNavigator(FavoriteMovieRoute)
                    } else if (index == TV_SERIES_TAB) {
                        navigator.singleTopNavigator(FavoriteTvSeriesRoute)
                    }
                }, text = {
                    Text(
                        title,
                        color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                })
            }
        }
    }
}
