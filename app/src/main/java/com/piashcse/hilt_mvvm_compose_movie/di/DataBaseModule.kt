package com.piashcse.hilt_mvvm_compose_movie.di

import android.content.Context
import androidx.room.Room
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieDatabase
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteMovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movieWorld.db").build()

    @Singleton
    @Provides
    fun provideMovieDetailDao(database: MovieDatabase): FavoriteMovieDao =
        database.getFavoriteMovieDetailDao()

    @Singleton
    @Provides
    fun provideTvSeriesDao(database: MovieDatabase): FavoriteTvSeriesDao =
        database.getFavoriteTvSeriesDao()
}
