package com.xiaomi.base.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.dao.UserProfileDao
import com.xiaomi.base.data.datasource.local.dao.UserDataDao
import com.xiaomi.base.data.datasource.local.entity.FavoriteItemEntity
import com.xiaomi.base.data.datasource.local.entity.FavoriteCategoryEntity
import com.xiaomi.base.data.datasource.local.entity.UserProfileEntity
import com.xiaomi.base.data.datasource.local.entity.UserDataEntity
import com.xiaomi.base.data.datasource.local.typeconverter.DateConverter

/**
 * Room database configuration for the universal application.
 * This database stores favorites, user profiles, and flexible user data.
 */
@Database(
    entities = [
        FavoriteItemEntity::class,
        FavoriteCategoryEntity::class,
        UserProfileEntity::class, // New universal entity
        UserDataEntity::class     // New universal entity
        // Removed FavoriteCreatorEntity (movie-specific)
    ],
    version = 2, // Incremented version for schema changes
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * Returns the DAO for favorite items operations.
     */
    abstract fun favoriteItemDao(): FavoriteItemDao
    
    /**
     * Returns the DAO for favorite categories operations.
     */
    abstract fun favoriteCategoryDao(): FavoriteCategoryDao
    
    /**
     * Returns the DAO for user profiles operations.
     */
    abstract fun userProfileDao(): UserProfileDao
    
    /**
     * Returns the DAO for user data operations.
     */
    abstract fun userDataDao(): UserDataDao
    
    // Removed favoriteCreatorDao() - no longer needed
    
    companion object {
        const val DATABASE_NAME = "app_database"
    }
}