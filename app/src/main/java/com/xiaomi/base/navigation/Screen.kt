package com.xiaomi.base.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import com.xiaomi.base.R

sealed class Screen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Popular : Screen("popular", R.string.popular, Icons.Default.ThumbUp)
    object TopRated : Screen("top_rated", R.string.top_rated, Icons.Default.Star)
    object Favorite : Screen("favorite", R.string.favorite, Icons.Default.Favorite)
    object ItemDetail : Screen("item_detail/{itemId}", R.string.item_detail, Icons.Default.Home) {
        fun createRoute(itemId: Int) = "item_detail/$itemId"
    }
}