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
    val tabs = listOf(
        stringResource(R.string.movie),
        stringResource(R.string.tv_series),
        stringResource(R.string.celebrities),
    )
    SectionTabRow(tabs, selectedSection) { index ->
        when (index) {
            MOVIE_TAB -> navigator.singleTopNavigator(NowPlayingMovieRoute)
            TV_SERIES_TAB -> navigator.singleTopNavigator(AiringTodayTvSeriesRoute)
            CELEBRITIES_TAB -> navigator.singleTopNavigator(PopularCelebritiesRoute)
        }
    }
}

@Composable
fun FavoriteTabView(navigator: Navigator) {
    val selectedIndex =
        if (navigator.currentKey == FavoriteTvSeriesRoute) TV_SERIES_TAB else MOVIE_TAB
    val tabs = listOf(stringResource(R.string.movie), stringResource(R.string.tv_series))

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        SectionTabRow(tabs, selectedIndex) { index ->
            when (index) {
                MOVIE_TAB -> navigator.singleTopNavigator(FavoriteMovieRoute)
                TV_SERIES_TAB -> navigator.singleTopNavigator(FavoriteTvSeriesRoute)
            }
        }
    }
}

@Composable
private fun SectionTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    TabRow(
        modifier = Modifier.background(Color.White),
        selectedTabIndex = selectedIndex,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = MaterialTheme.colorScheme.primary,
            )
        },
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onSelect(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedIndex == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Gray
                        },
                    )
                },
            )
        }
    }
}
