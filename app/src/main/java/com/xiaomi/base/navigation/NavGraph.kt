package com.xiaomi.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xiaomi.base.ui.screens.itemdetail.ItemDetailScreen
import com.xiaomi.base.ui.screens.favorite.FavoriteScreen
import com.xiaomi.base.ui.screens.home.HomeScreen
import com.xiaomi.base.ui.screens.popular.PopularScreen
import com.xiaomi.base.ui.screens.toprated.TopRatedScreen
import com.xiaomi.base.ui.screens.preview.PreviewCatalogScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Discover.route) {
            PopularScreen(navController = navController)
        }
        composable(Screen.Featured.route) {
            TopRatedScreen(navController = navController)
        }
        composable(Screen.Favorites.route) {
            FavoriteScreen(navController = navController)
        }
        composable(Screen.PreviewCatalog.route) {
            PreviewCatalogScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            ItemDetailScreen(itemId = itemId)
        }
    }
}