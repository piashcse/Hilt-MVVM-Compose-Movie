package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.IntTypeConverter
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.MovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.RemoteKeysDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem
import com.piashcse.hilt_mvvm_compose_movie.data.model.RemoteKeys

@TypeConverters(IntTypeConverter::class)
@Database(version = 1, entities = [MovieItem::class, RemoteKeys::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}