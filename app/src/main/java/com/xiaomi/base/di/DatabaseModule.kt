package com.xiaomi.base.di

import android.content.Context
import androidx.room.Room
import com.xiaomi.base.data.datasource.local.dao.FavoriteItemDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCategoryDao
import com.xiaomi.base.data.datasource.local.dao.FavoriteCreatorDao
import com.xiaomi.base.data.datasource.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Provides the Room database instance.
     *
     * @param context the application context
     * @return the AppDatabase instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Recreate database if no Migration object specified
            .build()
    }
    
    /**
     * Provides the FavoriteItemDao.
     *
     * @param database the AppDatabase instance
     * @return the FavoriteItemDao instance
     */
    @Provides
    @Singleton
    fun provideFavoriteItemDao(database: AppDatabase): FavoriteItemDao {
        return database.favoriteItemDao()
    }
    
    /**
     * Provides the FavoriteCategoryDao.
     *
     * @param database the AppDatabase instance
     * @return the FavoriteCategoryDao instance
     */
    @Provides
    @Singleton
    fun provideFavoriteCategoryDao(database: AppDatabase): FavoriteCategoryDao {
        return database.favoriteCategoryDao()
    }
    
    /**
     * Provides the FavoriteCreatorDao.
     *
     * @param database the AppDatabase instance
     * @return the FavoriteCreatorDao instance
     */
    @Provides
    @Singleton
    fun provideFavoriteCreatorDao(database: AppDatabase): FavoriteCreatorDao {
        return database.favoriteCreatorDao()
    }
}