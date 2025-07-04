package com.xiaomi.base.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCreatorDao
import com.xiaomi.base.data.datasource.local.entity.FavoriteItemEntity
import com.xiaomi.base.data.datasource.local.entity.FavoriteCategoryEntity
import com.xiaomi.base.data.datasource.local.entity.FavoriteCreatorEntity
import com.xiaomi.base.data.datasource.local.typeconverter.DateConverter

/**
 * Room database configuration for the application.
 * This database stores favorite items, categories, and creators.
 */
@Database(
    entities = [
        FavoriteItemEntity::class,
        FavoriteCategoryEntity::class,
        FavoriteCreatorEntity::class
    ],
    version = 1,
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
     * Returns the DAO for favorite creators operations.
     */
    abstract fun favoriteCreatorDao(): FavoriteCreatorDao
    
    companion object {
        const val DATABASE_NAME = "app_database"
    }
}