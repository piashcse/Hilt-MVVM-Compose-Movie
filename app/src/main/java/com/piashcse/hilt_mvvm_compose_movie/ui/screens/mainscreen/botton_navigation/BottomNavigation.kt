package com.piashcse.hilt_mvvm_compose_movie.ui.screens.mainscreen.botton_navigation

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.piashcse.hilt_mvvm_compose_movie.navigation.Screen
import com.piashcse.hilt_mvvm_compose_movie.navigation.currentRoute
import com.piashcse.hilt_mvvm_compose_movie.utils.singleTopNavigator

@Composable
fun BottomNavigationUI(navController: NavController, pagerState: PagerState) {
    NavigationBar {
        val items = if (pagerState.currentPage == 0) {
            listOf(
                Screen.NowPlayingNav,
                Screen.PopularNav,
                Screen.TopRatedNav,
                Screen.UpcomingNav,
            )
        } else {
            listOf(
                Screen.AiringTodayTvSeriesNav,
                Screen.OnTheAirTvSeriesNav,
                Screen.PopularTvSeriesNav,
                Screen.TopRatedTvSeriesNav,
            )
        }
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = item.navIcon,
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                onClick = {
                    navController.singleTopNavigator(item.route)
                })
        }
    }
}
