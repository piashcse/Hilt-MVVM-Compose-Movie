package com.xiaomi.base.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.xiaomi.base.R

/**
 * Universal navigation screens for any type of app.
 * These screens can be adapted for various domains and use cases.
 */
sealed class Screen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    
    // Core universal screens
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Discover : Screen("discover", R.string.discover, Icons.Default.Explore) // Renamed from Popular
    object Featured : Screen("featured", R.string.featured, Icons.Default.Star)    // Renamed from TopRated
    object Favorites : Screen("favorites", R.string.favorite, Icons.Default.Favorite) // Updated
    object Profile : Screen("profile", R.string.profile, Icons.Default.Person)
    object Settings : Screen("settings", R.string.settings, Icons.Default.Settings)
    
    // Preview and development screens
    object PreviewCatalog : Screen("preview_catalog", R.string.preview_catalog, Icons.Default.Preview)
    
    // Dynamic detail screens
    object ItemDetail : Screen("item_detail/{itemId}", R.string.item_detail, Icons.Default.Home) {
        fun createRoute(itemId: Int) = "item_detail/$itemId"
    }
    
    object CategoryView : Screen("category/{categoryId}", R.string.category, Icons.Default.Explore) {
        fun createRoute(categoryId: Int) = "category/$categoryId"
    }
    
    // Future extensible screens (can be enabled based on app type)
    object Dashboard : Screen("dashboard", R.string.dashboard, Icons.Default.Home)
    object DataView : Screen("data_view", R.string.data_view, Icons.Default.Star)
    object Editor : Screen("editor", R.string.editor, Icons.Default.Star)
    object Analytics : Screen("analytics", R.string.analytics, Icons.Default.Star)
    
    // Backward compatibility aliases for old naming
    companion object {
        val Popular = Discover   // Alias for backward compatibility
        val TopRated = Featured  // Alias for backward compatibility
        val Favorite = Favorites // Alias for backward compatibility
    }
}