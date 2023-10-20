package com.piashcse.hilt_mvvm_compose_movie.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieDatabase
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.MovieEntity
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remote.ApiService
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.remotemediator.NowPlayingMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "beers.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideBeerPager(movieDb: MovieDatabase, apiService: ApiService): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            remoteMediator = NowPlayingMediator(
                movieDb = movieDb,
                apiService = apiService
            ),
            pagingSourceFactory = {
                movieDb.getMovieDao().pagingSource()
            }
        )
    }
}