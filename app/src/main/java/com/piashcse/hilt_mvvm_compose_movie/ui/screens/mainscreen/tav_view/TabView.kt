package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.tav_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.utils.CELEBRITIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.MOVIE_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.TV_SERIES_TAB
import com.piashcse.hilt_mvvm_compose_movie.utils.singleTopNavigator
import kotlinx.coroutines.launch

@Composable
fun TabView(
    navigator: NavHostController,
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(stringResource(R.string.movie), stringResource(R.string.tv_series), stringResource(R.string.celebrities))
    TabRow(modifier = Modifier.background(Color.White),
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = MaterialTheme.colorScheme.primary
            )
        }) {
        tabs.forEachIndexed { index, title ->
            Tab(selected = pagerState.currentPage == index, onClick = {
                when (index) {
                    MOVIE_TAB -> {
                        navigator.singleTopNavigator(Screen.NowPlaying.route)

                    }
                    TV_SERIES_TAB -> {
                        navigator.singleTopNavigator(Screen.AiringTodayTvSeries.route)
                    }
                    CELEBRITIES_TAB -> {
                        navigator.singleTopNavigator(Screen.PopularCelebrities.route)
                    }
                }
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                Text(
                    title,
                    color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray
                )
            })
        }
    }
}

@Composable
fun FavoriteTabView(navigator: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
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
                    selectedTabIndex = index
                    if (index == MOVIE_TAB) {
                        navigator.singleTopNavigator(Screen.FavoriteMovie.route)
                    } else if (index == TV_SERIES_TAB) {
                        navigator.singleTopNavigator(Screen.FavoriteTvSeries.route)
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
