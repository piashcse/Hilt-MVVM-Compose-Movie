package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.bottom_navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.utils.singleTopNavigator

@Composable
fun BottomNavigationUI(navigator: Navigator, section: Int) {
    val currentKey = navigator.currentKey

    NavigationBar {
        val items = when (section) {
            0 -> {
                listOf(
                    Screen.NowPlayingNav,
                    Screen.PopularNav,
                    Screen.TopRatedNav,
                    Screen.UpcomingNav,
                )
            }

            1 -> {
                listOf(
                    Screen.AiringTodayTvSeriesNav,
                    Screen.OnTheAirTvSeriesNav,
                    Screen.PopularTvSeriesNav,
                    Screen.TopRatedTvSeriesNav,
                )
            }

            else -> {
                listOf(
                    Screen.PopularCelebritiesNav,
                    Screen.TrendingCelebritiesNav,
                )
            }
        }

        items.forEach { item ->
            NavigationBarItem(
                icon = item.navIcon,
                label = { Text(text = stringResource(id = item.title)) },
                selected = item.matches(currentKey),
                onClick = {
                    item.route?.let { navigator.singleTopNavigator(it) }
                })
        }
    }
}
